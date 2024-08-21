package com.summer_project_backend.controller;

import com.summer_project_backend.dto.TagDTO;
import com.summer_project_backend.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tag")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping("/addTag")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> addTag(@RequestBody TagDTO tagDTO) {
        tagService.createTag(tagDTO);
        return ResponseEntity.ok("Tag created.");
    }

    @GetMapping("/getAllTags")
    public ResponseEntity<List<TagDTO>> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTags());
    }

    @DeleteMapping("/deleteTag")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteTag(@RequestParam UUID tagId) {
        tagService.deleteTag(tagId);
        return ResponseEntity.ok("Tag deleted.");
    }
}
