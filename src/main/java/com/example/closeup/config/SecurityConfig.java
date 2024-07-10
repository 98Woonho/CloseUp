package com.example.closeup.config;

import com.example.closeup.handler.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(
                (config) -> config.disable()
        );

        http.authorizeHttpRequests(
                authorize -> {
                    authorize.anyRequest().permitAll();
                }
        );

        http.formLogin(
                formLogin -> {
                    formLogin.permitAll();
                    formLogin.loginPage("/user/login"); //유저가 허가되지 않은 url에 접속시 리다이렉트 될 로그인 페이지를 지정 get 요청
                    formLogin.loginProcessingUrl("/user/login"); //로그인 폼의 action 속성에 사용될 URL을 지정 비동기 처리시 무조건 빼야한다!!!!!! 주의!!!
                    formLogin.usernameParameter("id"); //로그인에 필요한 ID값을 id로 변경 default(username)
//                    formLogin.passwordParameter("password"); // 로그인에 필요한 password값을 password로 변경 default(password)
                    formLogin.defaultSuccessUrl("/"); //로그인 성공시 이동할 url
                    formLogin.failureHandler(customAuthenticationFailureHandler()); // 로그인 실패시 핸들러 작용
                    formLogin.successHandler(customLoginSuccessHandler());
                });

        http.logout(
                logout ->{
                    logout.logoutUrl("/user/logout").permitAll(); // 로그아웃 url 지정
                    logout.logoutSuccessUrl("/"); // 로그아웃 성공시 이동할 url
                    logout.addLogoutHandler(customLogoutHandler());
                    logout.logoutSuccessHandler( customLogoutSuccessHandler() );
                }
        );

        //SESSION INVALIDATE..
        http.sessionManagement(
                httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
        );

//        //OAUTH2-CLIENT
//        http.oauth2Login((oauth2)->{
//            oauth2.loginPage("/user/loginForm");
//            oauth2.successHandler(oauth2JwtLoginSuccessHandler());
//        });

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomAuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }


    @Bean
    public CustomLogoutSuccessHandler customLogoutSuccessHandler(){
        return new CustomLogoutSuccessHandler();
    }
    //CUSTOMLOGOUTHANDLER BEAN
    @Bean
    public CustomLogoutHandler customLogoutHandler(){
        return new CustomLogoutHandler();
    }

    //CUSTOMLOGINSUCCESSHANDLER BEAN
    @Bean
    public CustomLoginSuccessHandler customLoginSuccessHandler(){
        return new CustomLoginSuccessHandler();
    }

    @Bean
    public Oauth2JwtLoginSuccessHandler oauth2JwtLoginSuccessHandler(){
        return new Oauth2JwtLoginSuccessHandler();
    }

}
