package com.example.closeup.domain.mapper;

import com.example.closeup.domain.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    UserDto selectUserById(String id);

    UserDto selectUserByNameAndPhone(String name, String phone);

    UserDto selectUserByNameAndId(String name, String id);

    int updatePassword(String id, String newPassword);
    /**************회원가입***************/
    void insertUser(UserDto user);
}
