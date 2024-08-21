package com.summer_project_backend.service;

import com.summer_project_backend.dto.TagDTO;
import com.summer_project_backend.exception.NotFoundException;
import com.summer_project_backend.model.Tag;
import com.summer_project_backend.repository.TagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TagService {
    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TagService(TagRepository tagRepository, ModelMapper modelMapper) {
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
    }

    public void createTag(TagDTO tagDTO) {
        Tag tag = new Tag(tagDTO.getName(), null);
        tagRepository.save(tag);
    }

    public List<TagDTO> getAllTags() {
        return tagRepository.findAll().stream().map(tag -> modelMapper.map(tag, TagDTO.class)).toList();
    }

    public void deleteTag(UUID tagId) {
        Optional<Tag> tag = tagRepository.findById(tagId);
        if (tag.isEmpty())
            throw new NotFoundException("Tag doesn't exist");

        tagRepository.deleteById(tagId);
    }
}
