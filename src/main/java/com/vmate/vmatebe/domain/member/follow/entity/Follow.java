package com.vmate.vmatebe.domain.member.follow.entity;

import com.vmate.vmatebe.domain.member.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member follower;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member following;
}
