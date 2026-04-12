package com.project.esperApp.controller;

import com.project.esperApp.DTO.PostRequest;
import com.project.esperApp.entity.Post;
import com.project.esperApp.entity.User;
import com.project.esperApp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/posts")
public class PostController {

   @Autowired
   private PostService postService;


    @PostMapping("/create")
    public Post createPost(@RequestParam Long userId, @RequestParam String title, @RequestParam String content, @RequestParam(required = false) MultipartFile image, @RequestParam(required = false) Set<String> hashtags) {
        return postService.createPost(userId, title, content, image, hashtags);
    }

   @GetMapping
   public List<Post> getAllPosts(){
      return postService.getAllPosts();
   }

   @GetMapping("/{id}")
   public Post getPostById(@PathVariable Long id) {
       return postService.getPostById(id);
   }

   @DeleteMapping("/{id}")
   public void deletePostById(@PathVariable Long id) {
       postService.deletePost(id);
   }

   @PutMapping("/{id}")
   public Post update(@PathVariable Long id, @RequestParam String title, @RequestParam String content, @RequestParam(required = false) MultipartFile image, @RequestParam(required = false) Set<String> hashtags){
      return postService.updatePost(id, title, content, image, hashtags);

   }





}
