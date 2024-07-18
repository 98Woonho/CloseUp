package com.example.closeup.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String id;
    private String password;
    private String phone;
    private String role;
    private String name;
    private Boolean isSuspended;
    private Boolean isAuth;
    private byte[] profileImg;
}
