package com.zerobase.fastlms.user.service;


import com.zerobase.fastlms.user.dto.LoginHistoryDto;
import com.zerobase.fastlms.user.entity.LoginHistory;
import com.zerobase.fastlms.user.repository.LoginHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.time.LocalDateTime;

@Service
@Slf4j
public class LoginHistoryService {

    private final LoginHistoryRepository loginHistoryRepository;

    public LoginHistoryService(LoginHistoryRepository loginHistoryRepository) {
        this.loginHistoryRepository = loginHistoryRepository;
    }


    @Transactional
    public void insetHistoryLogin(LoginHistoryDto loginHistoryDto) {

        LoginHistory loginHistory = LoginHistory.builder()
                .userId(loginHistoryDto.getUserId())
                .ipAddress(loginHistoryDto.getIpAddress())
                .userAgent(loginHistoryDto.getUserAgent())
                .loginDate(LocalDateTime.now())
                .build();

        loginHistoryRepository.save(loginHistory);
    }

    public List<LoginHistory> selectLoginHistory(String user_id) {

        return loginHistoryRepository.findByUserIdOrderByIdDesc(user_id);

    }
}
