package com.project.esperApp.controller;


import com.project.esperApp.DTO.CommentRequest;
import com.project.esperApp.DTO.CommentResponse;
import com.project.esperApp.entity.Comment;
import com.project.esperApp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;


    @PostMapping("/{postId}")
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable Long postId,
            @RequestBody CommentRequest request
    ) {
        CommentResponse created = commentService.createComment(postId, request);
        return ResponseEntity.ok(created);
    }


    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentResponse>> getCommentsForPost(@PathVariable Long postId) {

        List<CommentResponse> comments = commentService.getCommentsForPost(postId);
        return ResponseEntity.ok(comments);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Comment> deleteComment(@PathVariable Long commentId){

        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();

    }


}
