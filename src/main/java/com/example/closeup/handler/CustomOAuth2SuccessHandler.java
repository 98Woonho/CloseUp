package com.example.closeup.handler;


import com.example.closeup.domain.dto.OAuth2UserDto;
import com.example.closeup.domain.dto.UserDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
@Component
@Log4j2
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDto) {
            // 이미 가입된 사용자
            response.sendRedirect("/");
        } else if (principal instanceof OAuth2User) {
            OAuth2User oAuth2User = (OAuth2User) principal;
            if (oAuth2User.getAttribute("id") != null) {
                // 이미 가입된 사용자 (CustomOAuth2DetailsService에서 처리됨)
                response.sendRedirect("/");
            } else {
                // 가입되지 않은 사용자
                HttpSession session = request.getSession();
                session.setAttribute("oAuth2UserInfo", new OAuth2UserDto(oAuth2User.getAttributes()));
                SecurityContextHolder.clearContext();
                response.sendRedirect("/user/register?social=true");
            }
        }
    }
}
