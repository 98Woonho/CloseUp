package com.example.closeup.service;


import com.example.closeup.dto.UserDto;
import com.example.closeup.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("로그인 시도하는 유저명..: " + username);
        UserDto findUser = userMapper.selectUserById(username);


        if(Objects.isNull(findUser)){
            throw new UsernameNotFoundException("Error : User not found");
        }
        return findUser;
    }
}
