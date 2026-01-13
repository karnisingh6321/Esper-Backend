package com.project.esperApp.repository;

import com.project.esperApp.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

    Optional<Hashtag> findByTag(String tag);
}



