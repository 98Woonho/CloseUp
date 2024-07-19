package com.example.closeup.service;

import com.example.closeup.config.auth.PrincipalDetails;
import com.example.closeup.domain.dto.UserDto;
import com.example.closeup.domain.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = Exception.class)
    public UserDto findUserById(String id) {
        return userMapper.selectUserById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void register(UserDto user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setIsSuspended(false);
        user.setRole("ROLE_USER");
        // 회원가입 시에 기본 프로필 사진 등록
        setUserDefaultProfileImage(user);
        userMapper.insertUser(user);
    }

    // 유저 기본 프로필 사진 등록 메서드
    private void setUserDefaultProfileImage(UserDto userDto){
        try {
            ClassPathResource resource = new ClassPathResource("static/imgs/user-profile.png");
            InputStream in = resource.getInputStream();
            byte[] data = in.readAllBytes();
            userDto.setProfileImg(data);
        }catch (Exception e){
            System.out.println("setUserDefaultProfileImage - image 설정 중 에러..: " + e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public UserDto findUserByNameAndPhone(String name, String phone) {
        return userMapper.selectUserByNameAndPhone(name, phone);
    }

    @Transactional(rollbackFor = Exception.class)
    public UserDto findUserByNameAndId(String name, String id) {
        return userMapper.selectUserByNameAndId(name, id);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean resetPassword(String id, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        return userMapper.updatePassword(id, encodedPassword) > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateUserProfileImg(String id, byte[] profileImg) {
        userMapper.updateUserProfileImg(id, profileImg);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateUserRoleByToggle(String id, String role) {
        userMapper.updateUserRoleByToggle(id, role);
    }
}

