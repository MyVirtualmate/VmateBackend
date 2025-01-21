package com.vmate.vmatebe.domain.member.member.entity;

import com.vmate.vmatebe.domain.member.follow.entity.Follow;
import com.vmate.vmatebe.global.jpa.entity.BaseTime;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(toBuilder = true)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Member extends BaseTime {
    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String userEmail;
    private String password;

    private String profileImgUrl;

    @Column(unique = true)
    private String refreshToken;

    @Builder.Default
    private Boolean isStreamer = false;

    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus;

    @Enumerated(EnumType.STRING)
    private PlatformStatus platformStatus;

    //불러오기 필요
    private String accountId;

    @OneToMany(mappedBy = "following")
    private List<Follow> mateFollowingList = new ArrayList<>();

    @OneToMany(mappedBy = "follower")
    private List<Follow> mateFollowerList = new ArrayList<>();

    @OneToOne
    private MemberPreferences memberPreferences;

    public String getName() {
        return username;
    }

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