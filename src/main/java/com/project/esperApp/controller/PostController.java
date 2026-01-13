package com.project.esperApp.controller;

import com.project.esperApp.DTO.PostRequest;
import com.project.esperApp.entity.Post;
import com.project.esperApp.entity.User;
import com.project.esperApp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/posts")
public class PostController {

   @Autowired
   private PostService postService;


    @PostMapping("/create")
    public Post createPost(@RequestBody PostRequest request) {
        return postService.createPost(
                request.getUserId(),
                request.getTitle(),
                request.getContent(),
                request.getImage(),
                request.getHashtags()
        );
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
   public Post update(@PathVariable Long id, @RequestBody PostRequest request){
      return postService.updatePost(
              id,
              request.getTitle(),
              request.getContent(),
              request.getImage(),
              request.getHashtags()
      );

   }



//    // For file upload
//    @PostMapping(value = "/create-with-image", consumes = "multipart/form-data")
//    public Post createPostWithImage(
//            @RequestParam Long userId,
//            @RequestParam String title,
//            @RequestParam String content,
//            @RequestParam MultipartFile image,
//            @RequestParam Set<String> tags
//    ) throws IOException {
//
//        String imagePath = null; // this will store the final image path
//
//        // ✅ Only save image if provided
//        if (image != null && !image.isEmpty()) {
//            // 1️⃣ Define where to store images
//            String uploadDir = "uploads/";
//
//            // 2️⃣ Ensure directory exists
//            Files.createDirectories(Paths.get(uploadDir));
//
//            // 3️⃣ Generate a unique filename
//            String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
//
//            // 4️⃣ Save the file locally
//            Path filePath = Paths.get(uploadDir, fileName);
//            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//
//            // 5️⃣ Save relative or absolute path to DB
//            imagePath = filePath.toString(); // (e.g., "uploads/1234_photo.png")
//        }
//        return postService.createPost(userId, title, content, imagePath, tags);
//    }





}
