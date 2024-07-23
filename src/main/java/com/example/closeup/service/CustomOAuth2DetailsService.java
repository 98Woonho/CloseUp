package com.example.closeup.service;




import com.example.closeup.domain.dto.OAuth2UserDto;
import com.example.closeup.domain.dto.UserDto;
import com.example.closeup.domain.mapper.UserMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Log4j2
@Service
public class CustomOAuth2DetailsService extends DefaultOAuth2UserService {

    @Autowired
    UserMapper userMapper;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String clientName = userRequest.getClientRegistration().getClientName();
        String phone = extractPhone(attributes, clientName);
        String email = extractEmail(attributes, clientName);

        if (phone != null) {
            UserDto user = userMapper.selectUserByPhone(phone);
            if (user != null) {
                user.setAttributes(attributes);
                return user;
            }
        }

        return OAuth2UserDto.builder()
                .attributes(attributes)
                .clientName(clientName)
                .build();
    }

    private String extractPhone(Map<String, Object> attributes, String clientName) {
        if ("naver".equalsIgnoreCase(clientName)) {
            Map<String, Object> response = (Map<String, Object>) attributes.get("response");
            return response != null ? (String) response.get("mobile") : null;
        }
        // 다른 소셜 로그인 케이스 추가
        return null;
    }

    private String extractEmail(Map<String, Object> attributes, String clientName) {
        if ("naver".equalsIgnoreCase(clientName)) {
            Map<String, Object> response = (Map<String, Object>) attributes.get("response");
            return response != null ? (String) response.get("email") : null;
        }
        // 다른 소셜 로그인 케이스 추가
        return null;
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
