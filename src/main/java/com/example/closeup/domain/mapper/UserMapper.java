package com.example.closeup.domain.mapper;

import com.example.closeup.domain.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    UserDto selectUserById(String id);
    /**************아이디 찾기***************/
    UserDto selectUserByNameAndPhone(String name, String phone);
    /**************비밀번호 찾기***************/
    UserDto selectUserByNameAndId(String name, String id);
    int updatePassword(String id, String newPassword);
    /**************회원가입***************/
    void insertUser(UserDto user);
}
