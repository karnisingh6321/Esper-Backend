package com.project.esperApp.service;

import com.project.esperApp.entity.Follow;
import com.project.esperApp.entity.User;
import com.project.esperApp.repository.FollowRepository;
import com.project.esperApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class FollowService {


    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;


    public void follow(Long followerId, Long followingId){

        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new RuntimeException("follower user does not exist"));

        User following = userRepository.findById(followingId)
                .orElseThrow(() -> new RuntimeException("following user does not exist"));

        boolean alreadyExists = followRepository.existsByFollowerAndFollowing(follower, following);

        if (alreadyExists) {
            return;
        }

        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowing(following);
        follow.setFollowedAt(LocalDateTime.now());

        followRepository.save(follow);
    }


    public void unfollow(Long followerId, Long followingId) {

        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new RuntimeException("Follower user not found"));

        User following = userRepository.findById(followingId)
                .orElseThrow(() -> new RuntimeException("Following user not found"));

        followRepository.deleteByFollowerAndFollowing(follower, following);
    }





}
