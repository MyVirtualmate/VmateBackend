package com.vmate.vmatebe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
/**
 * @EnableJpaAuditing은 Spring Boot에서 JPA Auditing을 활성화하는 어노테이션입니다. 
 * 이 기능은 엔티티의 특정 필드(예: createdDate, lastModifiedDate, createdBy, lastModifiedBy)를 자동으로 채워주는 데 유용합니다. 
 * 이를 통해 엔티티의 생성 및 수정 정보를 수동으로 설정하지 않고도 추적할 수 있습니다. 
 */
public class VmateBeApplication {
    public static void main(String[] args) {
        SpringApplication.run(VmateBeApplication.class, args);
    }
}
