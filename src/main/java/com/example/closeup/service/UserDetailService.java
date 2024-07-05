package com.example.closeup.service;


import com.example.closeup.dto.UserDto;
import com.example.closeup.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.PasswordAuthentication;
import java.util.Objects;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("로그인 시도하는 유저명..: " + username);
        UserDto findUser = userMapper.selectUserById(username);

        String encodePw = passwordEncoder.encode(findUser.getPw());
        System.out.println("인코딩됨 패스워드" + encodePw);


        if(Objects.isNull(findUser)){
            throw new UsernameNotFoundException("Error : User not found");
        }
        return (UserDetails) findUser;
    }
}
