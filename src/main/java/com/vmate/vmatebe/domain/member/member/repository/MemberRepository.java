package com.vmate.vmatebe.domain.member.member.repository;

import com.vmate.vmatebe.domain.member.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUserEmail(String username);

    Optional<Member> findByRefreshToken(String refreshToken);

    Optional<Member> findByUsername(String userNickName);
}