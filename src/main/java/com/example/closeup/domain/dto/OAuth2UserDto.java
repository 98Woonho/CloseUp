package com.example.closeup.domain.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OAuth2UserDto implements OAuth2User {
    private Map<String, Object> attributes;
    private String clientName;
    private UserDto userDto;

    public OAuth2UserDto(Map<String, Object> attributes) {
        this.attributes = attributes;
    }


    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getName() {
        if ("naver".equalsIgnoreCase(clientName)) {
            Map<String, Object> response = (Map<String, Object>) attributes.get("response");
            return response != null ? (String) response.get("name") : null;
        }
        return attributes.get("name") != null ? attributes.get("name").toString() : null;
    }
}
