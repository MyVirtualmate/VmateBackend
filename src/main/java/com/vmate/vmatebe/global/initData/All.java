package com.vmate.vmatebe.global.initData;

import com.vmate.vmatebe.domain.member.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class All {
    private final MemberService memberService;

    @Bean
    @Order(2)
    public ApplicationRunner initAll() {
        return args -> {
            if (memberService.findByUserEmail("Vmate@Admin.com").isPresent()) return;
            memberService.join("Vmate@Admin.com", "1234");
        };
    }
}
