package com.example.closeup.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private String id;
    private String password;
    private String phone;
    private String role;
    private String name;
    private Boolean isSuspended;
    private Boolean isAuth;
}
