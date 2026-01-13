package com.project.esperApp.repository;

import com.project.esperApp.entity.Comment;
import com.project.esperApp.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Long> {


    List<Comment> findByPostId(Long PostId);



}
