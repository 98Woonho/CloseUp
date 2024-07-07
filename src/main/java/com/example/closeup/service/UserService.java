package com.example.closeup.service;

import com.example.closeup.dto.UserDto;
import com.example.closeup.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean createUser(UserDto user){

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userMapper.insertUser(user);
        return true;
    }

    public UserDto findUserByNameAndPhone(String name, String phone) {
        return userMapper.selectUserByNameAndPhone(name, phone);
    }

    public UserDto findUserByNameAndId(String name, String id) {
        return userMapper.selectUserByNameAndId(name, id);
    }

    public boolean resetPassword(String id, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        return userMapper.updatePassword(id, encodedPassword) > 0;
    }
}

