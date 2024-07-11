package com.example.closeup.service;

import com.example.closeup.domain.dto.UserDto;
import com.example.closeup.domain.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;
/*************회원가입*************/
    public boolean createUser(UserDto user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // phone에서 '-' 제거
        user.setPhone(user.getPhone().replace("-", ""));
        userMapper.insertUser(user);
        return true;
    }
/******************아이디 찾기*************/
    public UserDto findUserByNameAndPhone(String name, String phone) {
        return userMapper.selectUserByNameAndPhone(name, phone);
    }

   /**************비밀번호 변경************/
    public UserDto findUserByNameAndId(String name, String id) {
        return userMapper.selectUserByNameAndId(name, id);
    }

    public boolean resetPassword(String id, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        return userMapper.updatePassword(id, encodedPassword) > 0;
    }
}

