package com.example.closeup.service;

import com.example.closeup.dto.UserDto;
import com.example.closeup.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createUser(UserDto user){
    userMapper.insertUser(user);
    }
}
