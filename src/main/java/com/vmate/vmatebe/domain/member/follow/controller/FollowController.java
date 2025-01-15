package com.vmate.vmatebe.domain.member.follow.controller;

import com.vmate.vmatebe.domain.member.follow.service.FollowService;
import com.vmate.vmatebe.domain.member.member.entity.Member;
import com.vmate.vmatebe.domain.member.member.service.MemberService;
import com.vmate.vmatebe.global.exceptions.GlobalException;
import com.vmate.vmatebe.global.response.GlobalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowController {
    private final FollowService followService;
    private final MemberService memberService;

    @PostMapping("/{username}")
    public GlobalResponse<String> follow(@PathVariable("username") String followingUsername, Principal principal) {
        Member mate = memberService.findByUsername(followingUsername)
                .orElseThrow(() -> new GlobalException("404", "존재하지 않는 회원입니다."));
        
        Member username = memberService.getMemberByUsername(principal.getName());
        
        followService.follow(mate, username);

        return GlobalResponse.of("201");
    }
/*
    @DeleteMapping("/{username}")
    public GlobalResponse<String> unfollow(@PathVariable("username") String followingUsername, Principal principal) {
        Member artist = memberService.getMemberByUsername(followingUsername);
        Member follower = memberService.getMemberByUsername(principal.getName());
        followService.unfollow(artist, follower);

        return GlobalResponse.of("200");
    }

    @GetMapping
    public GlobalResponse<List<FollowingListResponse>> getAllFollowing(Principal principal) {
        Member member = memberService.getMemberByUsername(principal.getName());
        List<FollowingListResponse> response = followService.getAllFollowingList(member);
        
        return GlobalResponse.of("200", response);
    }*/
}
