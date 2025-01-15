package com.vmate.vmatebe.domain.member.follow.service;

import com.vmate.vmatebe.domain.member.follow.entity.Follow;
import com.vmate.vmatebe.domain.member.follow.repository.FollowRepository;
import com.vmate.vmatebe.domain.member.member.entity.Member;
import com.vmate.vmatebe.domain.member.member.repository.MemberRepository;
import com.vmate.vmatebe.domain.member.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class FollowServiceTest {

    @Mock
    private FollowRepository followRepository;
    
    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private FollowService followService;

    @Mock
    private MemberService memberService;

    private Member mate;
    private Member follower;

    @BeforeEach
    @Transactional
    void setUp() {
        MockitoAnnotations.openMocks(this);
        if (memberService.findByUserEmail("Vmate@testaccount1.com").isPresent()) return;
        memberService.join("Vmate@testaccount1.com", "1234");

        if (memberService.findByUserEmail("Vmate@testaccount2.com").isPresent()) return;
        memberService.join("Vmate@testaccount2.com", "1234");

        mate = memberRepository.findByUserEmail("Vmate@testaccount1.com").orElse(null);
        follower = memberRepository.findByUserEmail("Vmate@testaccount2.com").orElse(null);
    }

    @Test
    void follow() {
        when(followRepository.findByFollowerIdAndFollowingId(follower.getId(), mate.getId()))
                .thenReturn(Optional.empty());

        followService.follow(mate, follower);

        verify(followRepository, times(1)).save(any(Follow.class));
    }

    @Test
    void unfollow() {
        Follow follow = new Follow();
        when(followRepository.findByFollowerIdAndFollowingId(follower.getId(), mate.getId()))
                .thenReturn(Optional.of(follow));

        followService.unfollow(mate, follower);

        verify(followRepository, times(1)).delete(follow);
    }

    @Test
    void getAllFollowingList() {
        when(follower.getMateFollowingList()).thenReturn(Collections.emptyList());

        assertTrue(followService.getAllFollowingList(follower).isEmpty());
    }

    @Test
    void getAllFollowerList() {
        when(mate.getMateFollowerList()).thenReturn(Collections.emptyList());

        assertTrue(followService.getAllFollowerList(mate).isEmpty());
    }
}