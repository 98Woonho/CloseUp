package com.example.closeup.handler;


import com.example.closeup.domain.dto.UserDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Log4j2
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Object principal = authentication.getPrincipal();

        if(principal instanceof UserDto){
            response.sendRedirect("/");
            return;
        }
        //로그인 안했다고 재판다. 세션을 초기화.
        authentication.setAuthenticated(false);
        request.getSession().invalidate();
        //회원가입 창으로 이동시키기
        response.sendRedirect("/user/register?isNotRegisted");
    }
}

