package com.example.closeup.domain.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "profileImg")
public class UserDto {
    private String id;
    private String password;
    private String name;
    private String phone;
    private String role;
    private Boolean isSuspended;
    private Boolean isAuth;
    private byte[] profileImg;
}
