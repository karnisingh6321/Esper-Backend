package com.project.esperApp.controller;

import com.project.esperApp.entity.Hashtag;
import com.project.esperApp.service.HashtagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hashtags")
public class HashtagController {

    @Autowired
    private HashtagService hashtagService;

    @GetMapping("/tag")
    public ResponseEntity<Hashtag> getByTag(@RequestParam String tag) {
        return ResponseEntity.ok(hashtagService.getByTag(tag));
    }

    @PostMapping
    public ResponseEntity<Hashtag> createTag(@RequestParam String tag) {
        return ResponseEntity.ok(hashtagService.createOrGetTag(tag));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTag(@PathVariable Long id) {
        hashtagService.deleteTag(id);
        return ResponseEntity.ok("Hashtag deleted successfully");
    }
}
