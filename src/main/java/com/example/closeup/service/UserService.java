package com.example.closeup.service;

import com.example.closeup.domain.dto.ExpertDto;

import com.example.closeup.domain.dto.ExpertDetailDto;
import com.example.closeup.domain.dto.ExpertDto;

import com.example.closeup.domain.dto.UserDto;
import com.example.closeup.domain.mapper.ExpertDetailMapper;
import com.example.closeup.domain.mapper.ExpertMapper;
import com.example.closeup.domain.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;

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
        // 회원가입 시에 기본 프로필 사진 등록
        setUserDefaultProfileImage(user);
        userMapper.insertUser(user);
    }

    // 유저 기본 프로필 사진 등록 메서드
    private void setUserDefaultProfileImage(UserDto userDto){
        try {
            ClassPathResource resource = new ClassPathResource("static/imgs/user-profile-2.jpg");
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

    public ExpertDto findExpertByNickNameWithIsWished(String userId, String expertNickName) {
        return userMapper.selectExpertByNickNameWithIsWished(userId, expertNickName);
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
    public ExpertDto getExpertDto(String nickname) {
        return expertMapper.selectExpertByNickname(nickname);
    }

    @Transactional(rollbackFor = Exception.class)
    public UserDto getUserDto(String id) {
        return userMapper.selectUserById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<ExpertDetailDto> getExpertDetailDtoList(String nickname) {
        System.out.println(nickname);
        return expertDetailMapper.selectExpertDetailListByNickname(nickname);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateUserRoleByToggle(String id, String role) {
        userMapper.updateUserRoleByToggle(id, role);
    }


    @Transactional(rollbackFor = Exception.class)
    public void insertExpertInfo(ExpertDto expertDto) {
        setExpertDefaultProfileImage(expertDto);
        userMapper.insertExpertInfo(expertDto);
    }

    // 전문가 등록 시 기본 프로필 사진 메서드
    private void setExpertDefaultProfileImage(ExpertDto expertDto){
        try {
            ClassPathResource resource = new ClassPathResource("static/imgs/user-profile-2.jpg");
            InputStream in = resource.getInputStream();
            byte[] data = in.readAllBytes();
            expertDto.setProfileImg(data);
        }catch (Exception e){
            System.out.println("setExpertDefaultProfileImage - image 설정 중 에러..: " + e.getMessage());
        }
    }



    @Transactional(rollbackFor = Exception.class)
    public void updateUserSuspendAndRoleById(String id) {
        userMapper.updateUserSuspendAndRoleById(id);
    }


    //소셜 로그인
    public UserDto findUserByPhone(String phone) {

        return userMapper.selectUserByPhone(phone);
    }

    public void socialRegister(UserDto userDto) {
        // 비밀번호 암호화 등 필요한 처리를 수행
        userMapper.insertUser(userDto);
    }
    public UserDto findUserByEmail(String email) {
        return userMapper.selectUserByEmail(email);
    }

    public void updateUserProfileById(String id, String password, String phone) {
        userMapper.updateUserProfileById(id, password, phone);
    }
}

