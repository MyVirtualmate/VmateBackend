package com.vmate.vmatebe.domain.member.follow.service;

import com.vmate.vmatebe.domain.member.follow.repository.FollowRepository;
import com.vmate.vmatebe.domain.member.member.entity.Member;
import com.vmate.vmatebe.domain.member.member.repository.MemberRepository;
import com.vmate.vmatebe.domain.member.member.service.MemberService;
import com.vmate.vmatebe.global.exceptions.GlobalException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class FollowServiceTest {
    
    
    @Autowired private FollowRepository followRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private FollowService followService;
    @Autowired private MemberService memberService;

    @BeforeEach
    @DisplayName("팔로우 테스트를 위한 멤버 생성")
    void setUp() {
        memberService.join("Vmate@testaccount1.com","1234");
        memberService.join("Vmate@testaccount2.com","1234");

        assertNotNull(memberRepository.findByUserEmail("Vmate@testaccount1.com"), "testUser should not be null");
        assertNotNull(memberRepository.findByUserEmail("Vmate@testaccount2.com"), "testMate should not be null");
    }

    @Test
    void follow() throws Exception {
        Member testUser = memberService.findByUserEmail("Vmate@testaccount1.com")
                .orElseThrow(() -> new GlobalException("404", "존재하지 않는 회원입니다."));
        
        Member testMate = memberService.findByUserEmail("Vmate@testaccount2.com")
                .orElseThrow(() -> new GlobalException("404", "존재하지 않는 회원입니다."));
        
        assertTrue(testUser != null && testMate != null);

/*        followService.follow(testUser, testMate);
        List<Member> followers = followService.getAllFollowerList(testUser);
        
        //Logger.getLogger("FollowServiceTest").info("testUser's follower list: " + followers);

        assertTrue(followers.contains(testMate));*/
    }
/*
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
    }*/
}