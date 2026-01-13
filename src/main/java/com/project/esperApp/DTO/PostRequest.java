package com.project.esperApp.DTO;


import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {

    private Long userId;
    private String title;
    private String content;
    private String image;


    @NotEmpty(message = "At least one hashtag is required")
    private Set<@NotBlank String> hashtags;
}
