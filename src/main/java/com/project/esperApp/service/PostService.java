package com.project.esperApp.service;

import com.project.esperApp.entity.Hashtag;
import com.project.esperApp.entity.Post;
import com.project.esperApp.entity.User;
import com.project.esperApp.repository.HashtagRepository;
import com.project.esperApp.repository.PostRepository;
import com.project.esperApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HashtagRepository hashtagRepository;




    public Post createPost(Long userId, String title, String content, MultipartFile image, Set<String> tags) {

        User author = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setAuthor(author);

        if (image != null && !image.isEmpty()) {

            String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
            Path filePath = Paths.get("uploads/" + fileName);

            try {
                Files.createDirectories(filePath.getParent());
                Files.write(filePath, image.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Image upload failed");
            }

            post.setImage(fileName);
        }

        if (tags != null && !tags.isEmpty()) {
            Set<Hashtag> hashtagEntities = tags.stream()
                    .map(tag -> hashtagRepository.findByTag(tag)
                            .orElseGet(() -> hashtagRepository.save(new Hashtag(null, tag, new HashSet<>()))))
                    .collect(Collectors.toSet());
            post.setHashtags(hashtagEntities);
        }

        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public List<Post> getPostsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return postRepository.findByAuthor(user);
    }

    public List<Post> getPostsByHashtag(String tag) {
        return postRepository.findByHashtagsTag(tag);
    }

    public Post getPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    public void deletePost(Long postId) {
        Post post = getPostById(postId);
        postRepository.delete(post);
    }

    public Post updatePost(Long postId, String title, String content,MultipartFile image, Set<String> tags) {
        Post post = getPostById(postId);
        post.setTitle(title);
        post.setContent(content);

        if (image != null && !image.isEmpty()) {

            String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
            Path filePath = Paths.get("uploads/" + fileName);

            try {
                Files.createDirectories(filePath.getParent());
                Files.write(filePath, image.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Image upload failed");
            }

            post.setImage(fileName);
        }

        Set<Hashtag> hashtagEntities = tags.stream().map(tag -> {
            return hashtagRepository.findByTag(tag)
                    .orElseGet(() -> {
                        Hashtag newTag = new Hashtag();
                        newTag.setTag(tag);
                        return hashtagRepository.save(newTag);
                    });
        }).collect(Collectors.toSet());
        post.setHashtags(hashtagEntities);

        return postRepository.save(post);

    }

    public Post upvotePost(Long postId) {
        Post post = getPostById(postId);
        post.setUpvote(post.getUpvote() + 1);
        return postRepository.save(post);
    }

    public Post toggleSave(Long postId) {
        Post post = getPostById(postId);
        post.setSave(!post.isSave());
        return postRepository.save(post);
    }

    public Post sharePost(Long postId) {
        Post post = getPostById(postId);
        post.setShare(post.getShare() + 1);
        return postRepository.save(post);
    }



}
