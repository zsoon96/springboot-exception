package com.example.exceptionprac.domain;

import com.example.exceptionprac.common.PasswordFailedException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

// 비밀번호 요구사항
// 1. 비밀번호 만료기간 기본 14일
// 2. 비밀번호 만료 기간이 지나는 것을 알 수 있어야 함
// 3. 비밀번호 5회 이상 실패했을 경우, 더 이상 시도 못하게 해야 함
// 4. 비밀번호가 일치하는 경우, 실패 카운트를 초기화 해야 함
// 5. 비밀번호 변경 시, 만료일이 현재시간 기준 14일로 연장되어야 함

@Embeddable
@Getter
@NoArgsConstructor
public class Password {

    @Column(name = "password", nullable = false)
    private String value;

    @Column(name = "password_expiration_date")
    private LocalDateTime expirationDate;

    @Column(name = "password_failed_count", nullable = false)
    private int failedCount;

    @Column(name = "password_ttl")
    private long ttl;

    @Builder
    public Password(final String value) {
        this.ttl = 1209_604; // 14일을 의미
        this.value = encodePassword(value); // 비밀번호 암호화
        this.expirationDate = extendExpirationDate(); // 만료 기간
    }

    // 입력한 비밀번호 일치 여부 판단 메서드
    public boolean isMatched(String rawPassword) {
        // 5번 이상 틀릴 경우, 예외 처리
        if (failedCount >= 5) {
            throw new PasswordFailedException();
        }
        // 비밀번호 일치 여부 판단
        final boolean matches = isMatches(rawPassword);
        updateFailedCount(matches);
        return matches;
    }

    // 비밀번호 변경 시, 변경된 비밀번호와 만료기간 연장해주는 메서드
    public void changePassword(String newPassword, String oldPassword) {
        if (isMatched(oldPassword)) {
            value = encodePassword(newPassword);
            extendExpirationDate();
        }
    }

    // 입력한 비밀번호 암호화하는 메서드
    private String encodePassword(String value) {
        return new BCryptPasswordEncoder().encode(value);
    }

    // 만료 기간 연장하는 메서드
    private LocalDateTime extendExpirationDate() {
        return LocalDateTime.now().plusSeconds(ttl); // 현재 시간에서 시간 초만큼 더하는 함수
    }

    // 비밀번호 일치 여부 확인 메서드
    private boolean isMatches(String rawPassword) {
        return new BCryptPasswordEncoder().matches(rawPassword, this.value);
    }

    // 입력값 불일치 시, 실패 횟수에 대한 처리 메서드
    private void updateFailedCount(boolean matches) {
        // 비밀번호가 일치하면 실패횟수 리셋
        if (matches) {
            this.failedCount = 0;
        // 일치하지 않으면 실패횟수 증가
        } else {
            this.failedCount ++;
        }
    }

}
