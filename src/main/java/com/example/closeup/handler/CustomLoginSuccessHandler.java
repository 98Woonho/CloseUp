package com.example.closeup.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Collection;

public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler  {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        Collection<? extends GrantedAuthority> collection =  authentication.getAuthorities();
        collection.forEach( (role)->{
            String role_str =  role.getAuthority();


            try {
                if (role_str.equals("ROLE_USER")) {
                    response.sendRedirect("/");
                }
//                else if (role_str.equals("ROLE_MEMBER")) {
//                    response.sendRedirect("/member");
//            }
            else if (role_str.equals("ROLE_ADMIN")) {
                    response.sendRedirect("/admin/main");
                }
                response.sendRedirect("/");
            } catch(Exception e){
                e.printStackTrace();
            }

        });


    }

}

