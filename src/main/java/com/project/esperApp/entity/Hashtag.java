package com.project.esperApp.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;


import java.util.HashSet;
import java.util.Set;


@Table(name = "hashtags")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString(exclude = "posts")
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String tag;

    @ManyToMany(mappedBy = "hashtags")
    @JsonIgnore
    private Set<Post> posts = new HashSet<>();
}
