package com.example.closeup.service;


import com.example.closeup.domain.dto.ExpertDetailDto;
import com.example.closeup.domain.dto.ExpertDto;

import com.example.closeup.config.auth.PrincipalDetails;

import com.example.closeup.domain.dto.UserDto;
import com.example.closeup.domain.mapper.ExpertDetailMapper;
import com.example.closeup.domain.mapper.ExpertMapper;
import com.example.closeup.domain.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
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
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ExpertMapper expertMapper;

    @Autowired
    private ExpertDetailMapper expertDetailMapper;

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
        setUserDefaultProfileImage(user);
        userMapper.insertUser(user);
    }

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

    public boolean isSuspendedUserById(String id) {
        return userMapper.selectIsSuspendedUserById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public ExpertDto getExpertDto(String nickname) {
        return expertMapper.selectExpertByNickname(nickname);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<ExpertDetailDto> getExpertDetailDtoList(String nickname) {
        return expertDetailMapper.selectExpertDetailListByNickname(nickname);
    }
}

