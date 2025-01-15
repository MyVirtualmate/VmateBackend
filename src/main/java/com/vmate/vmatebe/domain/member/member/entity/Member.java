package com.vmate.vmatebe.domain.member.member.entity;

import com.vmate.vmatebe.domain.member.follow.entity.Follow;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime modifyDate;

    @Column(unique = true)
    private String username;
    private String userEmail;
    private String password;

    private String profileImgUrl;

    @Column(unique = true)
    private String refreshToken;

    private Boolean isStreamer;

    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus;

    @Enumerated(EnumType.STRING)
    private PlatformStatus platformStatus;

    //불러오기 필요
    private String accountId;

    @OneToMany(mappedBy = "mate_following")
    private List<Follow> mateFollowingList = new ArrayList<>();

    @OneToMany(mappedBy = "mate_follower")
    private List<Follow> mateFollowerList = new ArrayList<>();

    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getAuthoritiesAsStringList()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Transient
    public List<String> getAuthoritiesAsStringList() {
        List<String> authorities = new ArrayList<>();
        authorities.add("ROLE_MEMBER");
        if (List.of("system", "admin").contains(userEmail)) {
            authorities.add("ROLE_ADMIN");
        }
        return authorities;
    }
}