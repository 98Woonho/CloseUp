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

    public void createUser(UserDto user){

        String encodedPassword = passwordEncoder.encode(user.getPw());
        user.setPw(encodedPassword);

        userMapper.insertUser(user);
    }
}
