package com.project.esperApp.repository;

import com.project.esperApp.entity.Hashtag;
import com.project.esperApp.entity.Post;
import com.project.esperApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByAuthor(User author);

    List<Post> findByHashtagsTag(String tag);

}
