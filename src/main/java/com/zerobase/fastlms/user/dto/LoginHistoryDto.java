package com.zerobase.fastlms.user.dto;

import com.zerobase.fastlms.user.entity.LoginHistory;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class LoginHistoryDto {

    private Long id;
    private String userId;
    private String ipAddress;
    private String userAgent;
    private LocalDate login_date;


    public static LoginHistoryDto of(LoginHistory loginHistory) {

        return LoginHistoryDto.builder()

                .id(loginHistory.getId())
                .userId(loginHistory.getUserId())
                .ipAddress(loginHistory.getIpAddress())
                .userAgent(loginHistory.getUserAgent())
                .login_date(LocalDate.from(loginHistory.getLoginDate()))
                .build();
    }

}
