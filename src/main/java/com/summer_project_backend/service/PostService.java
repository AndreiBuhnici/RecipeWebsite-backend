package com.summer_project_backend.service;

import com.summer_project_backend.dto.CreatePostDTO;
import com.summer_project_backend.dto.PostDTO;
import com.summer_project_backend.dto.UpdatePostDTO;
import com.summer_project_backend.exception.IllegalAccessException;
import com.summer_project_backend.exception.NotFoundException;
import com.summer_project_backend.model.Ingredient;
import com.summer_project_backend.model.Post;
import com.summer_project_backend.model.Tag;
import com.summer_project_backend.model.User;
import com.summer_project_backend.repository.IngredientRepository;
import com.summer_project_backend.repository.PostRepository;
import com.summer_project_backend.repository.TagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final IngredientRepository ingredientRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PostService(PostRepository postRepository, TagRepository tagRepository,
                       IngredientRepository ingredientRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
        this.ingredientRepository = ingredientRepository;
        this.modelMapper = modelMapper;
    }

    public void createPost(CreatePostDTO createPostDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        List<Tag> tags = new ArrayList<>();
        for (var tagDTO : createPostDTO.getTags()) {
            var tag = tagRepository.findByName(tagDTO.getName());
            if (tag == null)
                throw new NotFoundException("Tag doesn't exist");
            tags.add(tag);
        }

        List<Ingredient> ingredients = new ArrayList<>();
        for (var ingredientDTO : createPostDTO.getIngredients()) {
            var ingredient = ingredientRepository.findByName(ingredientDTO.getName());
            if (ingredient == null)
                throw new NotFoundException("Ingredient doesn't exist");
            ingredients.add(ingredient);
        }

        Post post = new Post(currentUser, createPostDTO.getTitle(), tags,
                ingredients, createPostDTO.getDescription(), createPostDTO.getRecipe());
        postRepository.save(post);
    }

    public Page<PostDTO> getUserPosts(int page, int size, String search, List<String> tags, List<String> ingredients) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Pageable pageable = PageRequest.of(page, size);

        if (tags.isEmpty())
            tags = null;
        if (ingredients.isEmpty())
            ingredients = null;

        Page<Post> postsPaged = postRepository.findAllByUserIdAndTitleAndTagsAndIngredients(user.getId(), search, tags,
                ingredients, pageable);

        return postsPaged.map(post -> modelMapper.map(post, PostDTO.class));
    }

    public void deletePost(UUID postId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Post> post = postRepository.findById(postId);

        if (post.isEmpty()) {
            throw new NotFoundException("Post doesn't exist");
        } else {
            Post p = post.get();
            if (!user.getPosts().contains(p))
                throw new IllegalAccessException("The user doesn't own this post");
            postRepository.deleteById(postId);
        }
    }

    public void updatePost(PostDTO postDTO) {
        Optional<Post> post = postRepository.findById(postDTO.getId());
        if (post.isEmpty())
            throw new NotFoundException("Post doesn't exist");

        Post p = post.get();

        if (postDTO.getTitle() != null)
            p.setTitle(postDTO.getTitle());

        if (postDTO.getDescription() != null)
            p.setDescription(postDTO.getDescription());

        if (postDTO.getRecipe() != null)
            p.setRecipe(postDTO.getRecipe());

        if (postDTO.getTags() != null)
            p.setTags(postDTO.getTags().stream().map(tagDTO -> tagRepository.findByName(tagDTO.getName())).toList());

        if (postDTO.getIngredients() != null)
            p.setIngredients(postDTO.getIngredients().stream()
                    .map(ingredientDTO -> ingredientRepository.findByName(ingredientDTO.getName())).toList());

        postRepository.save(p);
    }
}
