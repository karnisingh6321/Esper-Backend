package com.project.esperApp.service;

import com.project.esperApp.entity.Follow;
import com.project.esperApp.entity.User;
import com.project.esperApp.repository.FollowRepository;
import com.project.esperApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


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

    public List<User> getFollowers(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));

        return followRepository.findByFollowing(user)
                .stream()
                .map(Follow::getFollower)
                .toList();
    }

    public List<User> getFollowing(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));

        return followRepository.findByFollower(user)
                .stream()
                .map(Follow::getFollowing)
                .toList();
    }

    public long getFollowersCount(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));

        return followRepository.countByFollowing(user);
    }

    public long getFollowingCount(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));
        return followRepository.countByFollower(user);
    }

    public boolean isFollowing(Long followerId, Long followingId) {

        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new RuntimeException("follower not found"));

        User following = userRepository.findById(followingId)
                .orElseThrow(() -> new RuntimeException("following not found"));

        return followRepository.existsByFollowerAndFollowing(follower, following);
    }



}
