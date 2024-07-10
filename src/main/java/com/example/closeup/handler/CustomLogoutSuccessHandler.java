package com.example.closeup.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;
import java.util.Arrays;


public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {


//    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
//    private String kakaoClientId;
//
//
//    private final String REDIRECT_URI="http://localhost:8080/login";


    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
//        PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
//        String provider =  principalDetails.getUserDto().getProvider();
//        System.out.println("[CustomLogoutSuccessHandler] onLogoutSuccess() provider : " + provider);
//
//        if(provider!=null&&provider.equals("kakao")){
//            String url = "https://kauth.kakao.com/oauth/logout?client_id="+kakaoClientId+"&logout_redirect_uri="+REDIRECT_URI;
//            response.sendRedirect(url);
//            return ;
//        }else if(provider!=null&&provider.equals("naver")){
//            String url = "https://nid.naver.com/nidlogin.logout";
//            response.sendRedirect(url);
//            return ;
//
//        }else if(provider!=null&&provider.equals("google")){
//            String url = "http://accounts.google.com/Logout";
//            response.sendRedirect(url);
//            return ;
//
//        }



        response.sendRedirect("/"); // request.getContextPath() == "/";
    }
}
