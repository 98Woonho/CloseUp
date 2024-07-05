package com.example.closeup.mapper;

import com.example.closeup.dto.UserDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    UserDto selectUserById(String id);
    /**************회원가입***************/
    void insertUser(UserDto user);
}
