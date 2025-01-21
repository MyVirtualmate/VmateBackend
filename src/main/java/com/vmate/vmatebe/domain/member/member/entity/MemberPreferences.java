package com.vmate.vmatebe.domain.member.member.entity;

import com.vmate.vmatebe.global.jpa.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(toBuilder = true)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class MemberPreferences extends BaseEntity {
    private List<String> preferredVtuberStyles; // 선호하는 VTuber 스타일
    private List<String> preferredGenres; // 선호하는 장르
    private List<String> preferredContentTopics; // 선호하는 콘텐츠 주제
    private List<String> preferredVoiceTones; // 선호하는 목소리 톤
    private List<String> preferredTags; // 선호하는 태그
    private int dailyMatchLimit; // 하루 매칭 횟수
    private boolean receiveLiveStreamNotifications; // 실시간 스트리밍 알림 수신 여부
    private boolean receiveWeeklySchedule; // 주간 스케줄 수신 여부
    private List<String> likedVtubers; // 좋아요 누른 VTuber 목록
    private List<String> feedback; // 매칭 결과 피드백
}
