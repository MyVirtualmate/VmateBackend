package com.vmate.vmatebe.domain.member.follow.repository;

import com.vmate.vmatebe.domain.member.follow.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFollowerIdAndFollowingId(Long mate_followerId, Long mate_followingId);
}