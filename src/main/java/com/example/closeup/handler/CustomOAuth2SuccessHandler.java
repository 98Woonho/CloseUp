    package com.example.closeup.handler;


    import com.example.closeup.config.auth.PrincipalDetails;
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

            try {
                if (principal instanceof PrincipalDetails) {
                    UserDto userDto = ((PrincipalDetails) principal).getUserDto();
                    log.info("Already registered user logged in: {}", userDto.getId());
                    response.sendRedirect("/");
                } else if (principal instanceof OAuth2User) {
                    OAuth2User oAuth2User = (OAuth2User) principal;
                    Map<String, Object> attributes = oAuth2User.getAttributes();

                    if (attributes.containsKey("response")) {
                        // Naver login
                        Map<String, Object> responseMap = (Map<String, Object>) attributes.get("response");
                        if (responseMap.containsKey("id")) {
                            log.info("Already registered Naver user logged in");
                            response.sendRedirect("/");
                        } else {
                            handleNewUser(request, response, attributes);
                        }
                    } else {
                        // Handle other OAuth2 providers if needed
                        log.warn("Unhandled OAuth2 provider");
                        response.sendRedirect("/login?error=unsupported_provider");
                    }
                } else {
                    log.error("Unexpected principal type: {}", principal.getClass().getName());
                    response.sendRedirect("/login?error=authentication_error");
                }
            } catch (Exception e) {
                log.error("Error in OAuth2 success handler", e);
                response.sendRedirect("/login?error=internal_error");
            }
        }

        private void handleNewUser(HttpServletRequest request, HttpServletResponse response, Map<String, Object> attributes) throws IOException {
            log.info("New user from OAuth2 provider. Redirecting to registration.");
            HttpSession session = request.getSession();
            session.setAttribute("oAuth2UserInfo", new OAuth2UserDto(attributes));
            SecurityContextHolder.clearContext();
            response.sendRedirect("/user/register?social=true");
        }
    }
