package com.vmate.vmatebe.domain.member.follow.dto;

import com.vmate.vmatebe.domain.member.member.entity.Member;
import lombok.Builder;

@Builder
public record FollowingListResponse(
        Long memberId,
        String username
) {

    public static FollowingListResponse fromEntity(Member member) {
        return FollowingListResponse.builder()
                .memberId(member.getId())
                .username(member.getUsername())
                .build();
    }
}