package com.project.esperApp.service;

import com.project.esperApp.DTO.CommentRequest;
import com.project.esperApp.DTO.CommentResponse;
import com.project.esperApp.entity.Comment;
import com.project.esperApp.entity.Post;
import com.project.esperApp.entity.User;
import com.project.esperApp.repository.CommentRepository;
import com.project.esperApp.repository.PostRepository;
import com.project.esperApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {


    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public CommentResponse createComment(Long postId, CommentRequest request) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("post not found"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("user not found"));

        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setAuthor(user);
        comment.setPost(post);
        comment.setCreatedAt(LocalDateTime.now());

        if (request.getParentCommentId() != null) {
            Comment parent = commentRepository.findById(request.getParentCommentId())
                    .orElseThrow(() -> new RuntimeException("Parent comment not found"));
            comment.setParentComment(parent);
        }

        Comment saved = commentRepository.save(comment);
        return mapToDto(saved);
    }

    public List<CommentResponse> getCommentsForPost(Long postId) {
         List<Comment> allComments = commentRepository.findByPostId(postId);

         return allComments.stream()
                 .map(this::mapToDtoWithReplies)
                 .collect(Collectors.toList());

    }

    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));

        commentRepository.delete(comment);
    }


    private CommentResponse mapToDto(Comment comment) {
        CommentResponse dto = new CommentResponse();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setUserId(comment.getAuthor().getId());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setPostId(comment.getPost().getId());
        dto.setParentCommentId(
                comment.getParentComment() != null ? comment.getParentComment().getId() : null
        );
        return dto;
    }

    private CommentResponse mapToDtoWithReplies(Comment comment) {
        CommentResponse dto = mapToDto(comment);

        if (comment.getReplies() != null && !comment.getReplies().isEmpty()) {
            dto.setReplies(
                    comment.getReplies().stream()
                            .map(this::mapToDtoWithReplies)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }



}
