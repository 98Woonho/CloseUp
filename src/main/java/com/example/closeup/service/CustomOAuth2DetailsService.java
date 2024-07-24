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


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String clientName = userRequest.getClientRegistration().getClientName();

        if ("naver".equalsIgnoreCase(clientName)) {
            return handleNaverLogin(attributes);
        } else if ("google".equalsIgnoreCase(clientName)) {
            return handleGoogleLogin(attributes);
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
//    private SocialUserDTO naver_login(Map<String, Object> userProperties) {
//        String message = (String) userProperties.get("message");
//        Map<String, String> response = (Map<String, String>) userProperties.get("response");
//        String id = response.get("id");
//        String profileImageURL = response.get("profile_image_url");
//        String email = response.get("email");
//        String name = response.get("name");
//       return SocialUserDTO.builder()
//                .id(id)
//                .ci(CI)
//                .profileImageUrl(profileImageURL)
//                .email(email)
//                .name(name)
//                .build();
//    }
//
//    private SocialUserDTO kakao_login(Map<String, Object> userProperties) {
//            String id = userProperties.get("id").toString();
////                String id = (String) userProperties.get("id");
//        Map<String, String> properties = (Map<String, String>) userProperties.get("properties");
//        String nickName = properties.get("nickname");
//        String profileImages = properties.get("profile_image");
//        return SocialUserDTO.builder()
//                .id(id)
//                .ci(CI)
//                .profileImageUrl(profileImages)
//                .nickName(nickName)
//                .build();
//
//    }
//
//    private SocialUserDTO google_login(Map<String, Object> userProperties) {
//        String id = (String) userProperties.get("sub");
//        String name = (String) userProperties.get("name");
//        String profileImageURL = (String) userProperties.get("picture");
//        String email = (String) userProperties.get("email");
//        return SocialUserDTO.builder()
//                .id(id)
//                .ci(CI)
//                .name(name)
//                .profileImageUrl(profileImageURL)
//                .email(email)
//                .build();
//    }
//}
