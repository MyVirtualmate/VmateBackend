package com.vmate.vmatebe.domain.member.follow.dto;

import com.vmate.vmatebe.domain.member.member.entity.Member;
import lombok.Builder;

@Builder
public record FollowerListResponse(
        Long memberId,
        String username
) {

    public static FollowerListResponse fromEntity(Member member) {
        return FollowerListResponse.builder()
                .memberId(member.getId())
                .username(member.getUsername())
                .build();
    }
}