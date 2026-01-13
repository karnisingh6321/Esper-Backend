package com.project.esperApp.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {

    private Long id;
    private String content;
    private Long userId;
    private LocalDateTime createdAt;
    private Long parentCommentId;
    private Long postId;

    // nested replies
    private List<CommentResponse> replies = new ArrayList<>();
}
