package com.example.closeup.service;




import com.example.closeup.config.auth.PrincipalDetails;
import com.example.closeup.domain.dto.OAuth2UserDto;
import com.example.closeup.domain.dto.UserDto;
import com.example.closeup.domain.mapper.UserMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Map;

@Log4j2
@Service
public class CustomOAuth2DetailsService extends DefaultOAuth2UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    UserService userService;

    private final String CI = "fztTI/+lumx7dXYgxrDyitPn/s7K9EJv5+Tcu3yBnP5KU9lZJaNzm5+MigJwgfaOWCq0yTIu6l00g7tQvJTACg==";



    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String clientName = userRequest.getClientRegistration().getClientName();

        if ("naver".equalsIgnoreCase(clientName)) {
            return handleNaverLogin(attributes);
        } else if ("kakao".equalsIgnoreCase(clientName)) {
            return handleKakaoLogin(attributes);
        }

        return OAuth2UserDto.builder()
                .attributes(attributes)
                .clientName(clientName)
                .build();
    }

    private OAuth2User handleNaverLogin(Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        String id = (String) response.get("id");
        String email = (String) response.get("email");
        String name = (String) response.get("name");
        String phone = ((String) response.get("mobile")).replaceAll("-", "");

        UserDto existingUser = userService.findUserByPhone(phone);

        PrincipalDetails principalDetails = new PrincipalDetails();
        if (existingUser != null) {
            existingUser.setAttributes(attributes);
            principalDetails.setUserDto(existingUser);
            return principalDetails;
        }

        UserDto newUser = UserDto.builder()
                .id(email)
                .password(id)
                .name(name)
                .phone(phone)
                .isSuspended(false)
                .role("ROLE_USER")
                .build();
        setUserDefaultProfileImage(newUser);

        userService.socialRegister(newUser);

        newUser.setAttributes(attributes);

        principalDetails.setUserDto(newUser);
        return principalDetails;
    }

    private OAuth2User handleKakaoLogin(Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        String nickname = (String) profile.get("nickname");
        String profileImageUrl = (String) profile.get("profile_image_url");

        UserDto tempUser = UserDto.builder()
                .id(CI)
                .name(nickname)
                .profileImg(null) // 프로필 이미지는 나중에 처리
                .build();
        tempUser.setAttributes(attributes);

        return new PrincipalDetails(tempUser);
    }

    private OAuth2User handleGoogleLogin(Map<String, Object> attributes) {
        System.out.println(attributes);
        String id = (String) attributes.get("sub");
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        String phone = null; // Google API는 기본적으로 전화번호를 제공하지 않음

        UserDto existingUser = userService.findUserByEmail(email);

        PrincipalDetails principalDetails = new PrincipalDetails();
        if (existingUser != null) {
            existingUser.setAttributes(attributes);
            principalDetails.setUserDto(existingUser);
            return principalDetails;
        }

        UserDto newUser = UserDto.builder()
                .id(email)
                .password(id)
                .name(name)
                .phone(phone)
                .isSuspended(false)
                .role("ROLE_USER")
                .build();
        setUserDefaultProfileImage(newUser);

        userService.socialRegister(newUser);

        newUser.setAttributes(attributes);

        principalDetails.setUserDto(newUser);
        return principalDetails;
    }

    private void setUserDefaultProfileImage(UserDto userDto) {
        try {
            ClassPathResource resource = new ClassPathResource("static/imgs/user-profile-2.jpg");
            InputStream in = resource.getInputStream();
            byte[] data = in.readAllBytes();
            userDto.setProfileImg(data);
        } catch (Exception e) {
            System.out.println("setUserDefaultProfileImage - image 설정 중 에러..: " + e.getMessage());
        }
    }
}
