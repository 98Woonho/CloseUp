package com.example.closeup.domain.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "profileImg")
public class UserDto implements OAuth2User{
    private String id;
    private String password;
    private String name;
    private String phone;
    private String role;
    private Boolean isSuspended;
//    private Boolean isAuth;
    private byte[] profileImg;
    private Map<String, Object> attributes;

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public String getName() {
        return this.name;
    }
}
