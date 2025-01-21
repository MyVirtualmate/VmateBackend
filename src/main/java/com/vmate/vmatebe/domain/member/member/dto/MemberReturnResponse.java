package com.vmate.vmatebe.domain.member.member.dto;

import lombok.Builder;

@Builder
public record MemberReturnResponse(
        Long memberId,
        String username,
        String email,
        String nickname,
        String profileImageUrl,
        Boolean isStreamer
) {
}
