package com.project.esperApp.service;

import com.project.esperApp.entity.Hashtag;
import com.project.esperApp.repository.HashtagRepository;
import com.project.esperApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class HashtagService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HashtagRepository hashtagRepository;

    private String normalize(String tag) {
        if (tag == null) {
            throw new RuntimeException("Tag cannot be null");
        }
        return tag.trim().toLowerCase().replace("#", "");
    }

    public Hashtag createOrGetTag(String tagName) {

        String normalizedTag = normalize(tagName);

        return hashtagRepository.findByTag(normalizedTag)
                .orElseGet(() -> {
                    Hashtag tag = new Hashtag();
                    tag.setTag(normalizedTag);
                    return hashtagRepository.save(tag);
                });
    }

    public Set<Hashtag> createOrGetTags(Set<String> tags) {

        if (tags == null || tags.isEmpty()) {
            return new HashSet<>();
        }

        return tags.stream()
                .map(this::createOrGetTag)
                .collect(Collectors.toSet());
    }

    public Hashtag getByTag(String tag) {
        return hashtagRepository.findByTag(normalize(tag))
                .orElseThrow(() -> new RuntimeException("Hashtag not found"));
    }

    public void deleteTag(Long id) {
        hashtagRepository.deleteById(id);
    }



}
