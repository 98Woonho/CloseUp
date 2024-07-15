package com.example.closeup.service;

import com.example.closeup.domain.dto.UserDto;
import com.example.closeup.domain.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        userMapper.insertUser(user);
    }


    public UserDto findUserByNameAndPhone(String name, String phone) {
        return userMapper.selectUserByNameAndPhone(name, phone);
    }


    public UserDto findUserByNameAndId(String name, String id) {
        return userMapper.selectUserByNameAndId(name, id);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean resetPassword(String id, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        return userMapper.updatePassword(id, encodedPassword) > 0;
    }
}

