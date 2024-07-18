package com.example.closeup.config.auth;

import com.example.closeup.domain.dto.UserDto;
import com.example.closeup.domain.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailsService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        UserDto userDto = userMapper.selectUserById(id);
        if (userDto == null){
            throw new UsernameNotFoundException(id);
        }

//        System.out.println("PrincipalDetailsService's userDto: " + userDto);

        PrincipalDetails principalDetails = new PrincipalDetails();
        principalDetails.setUserDto(userDto);

//        System.out.println("PrincipalDetailsService's principalDetails's userDto: " + principalDetails.getUserDto());

        return principalDetails;
    }
}
