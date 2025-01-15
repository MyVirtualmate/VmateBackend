package com.vmate.vmatebe.domain.member.follow.service;

import com.vmate.vmatebe.domain.member.follow.dto.FollowingListResponse;
import com.vmate.vmatebe.domain.member.follow.entity.Follow;
import com.vmate.vmatebe.domain.member.follow.repository.FollowRepository;
import com.vmate.vmatebe.domain.member.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowService {
    
    private final FollowRepository followRepository;

    @Transactional
    public void follow(Member mate, Member follower) {
        if (mate.equals(follower)) {
            throw new IllegalArgumentException("자기 자신을 팔로우할 수 없습니다.");
        }

        Optional<Follow> isExist = followRepository.findByFollowerIdAndFollowingId(follower.getId(),
                mate.getId());

        if (isExist.isPresent()) {
            throw new IllegalArgumentException("이미 팔로우한 아티스트입니다.");
        }

        Follow follow = Follow.builder()
                .follower(follower)
                .following(mate)
                .build();

        followRepository.save(follow);
    }

    @Transactional
    public void unfollow(Member mate, Member follower) {
        Follow follow = followRepository.findByFollowerIdAndFollowingId(follower.getId(), mate.getId())
                .orElseThrow(() -> new IllegalArgumentException("팔로우하지 않은 아티스트입니다."));

        followRepository.delete(follow);
    }

    public List<FollowingListResponse> getAllFollowingList(Member member) {
        return member.getMateFollowingList().stream()
                .map(follow -> FollowingListResponse.fromEntity(follow.getFollowing()))
                .toList();
    }

    public List<Member> getAllFollowerList(Member mate) {
        return mate.getMateFollowerList().stream()
                .map(Follow::getFollower).toList();
    }
}
