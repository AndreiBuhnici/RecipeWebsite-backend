package com.summer_project_backend.controller;

import com.summer_project_backend.dto.CreatePostDTO;
import com.summer_project_backend.dto.PostDTO;
import com.summer_project_backend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/createPost")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> createPost(@RequestBody CreatePostDTO createPostDTO) {
        postService.createPost(createPostDTO);
        return ResponseEntity.ok("Post created.");
    }

    @GetMapping("/getCurrentUserPosts")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Page<PostDTO>> getCurrentUserPosts(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "5") int size,
                                                             @RequestParam(defaultValue = "") String search,
                                                             @RequestParam(value = "tags[]", defaultValue = "", required = false)
                                                                 List<String> tags,
                                                             @RequestParam(value = "ingreds[]", defaultValue = "", required = false)
                                                                 List<String> ingreds) {
        return ResponseEntity.ok(postService.getUserPosts(page, size, search, tags, ingreds));
    }

    @DeleteMapping("/deletePost")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> deletePost(@RequestParam UUID postID) {
        postService.deletePost(postID);
        return ResponseEntity.ok("Post deleted.");
    }

    @PutMapping("/updatePost")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> updatePost(@RequestBody PostDTO postDTO) {
        postService.updatePost(postDTO);
        return ResponseEntity.ok("Post updated");
    }
}
