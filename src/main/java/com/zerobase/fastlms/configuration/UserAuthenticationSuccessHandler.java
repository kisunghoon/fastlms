package com.zerobase.fastlms.configuration;

import com.zerobase.fastlms.member.service.MemberService;
import com.zerobase.fastlms.user.dto.LoginHistoryDto;
import com.zerobase.fastlms.user.service.LoginHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    private final MemberService memberService;
    private final LoginHistoryService loginHistoryService;

    public UserAuthenticationSuccessHandler(MemberService memberService, LoginHistoryService loginHistoryService) {
        this.memberService = memberService;
        this.loginHistoryService = loginHistoryService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String username = authentication.getName();
        memberService.updateLastLogin(username);

        loginHistoryService.insetHistoryLogin(
                LoginHistoryDto.builder()
                        .userId(username)
                        .ipAddress(String.valueOf(request.getRemoteAddr()))
                        .userAgent(request.getHeader("User-Agent"))
                        .build()
        );


        response.sendRedirect("/");

    }
}
