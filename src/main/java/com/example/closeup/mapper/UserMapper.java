package com.example.closeup.mapper;

import com.example.closeup.domain.dto.UserDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    UserDto selectUserByNameAndPhone(String name, String phone);

    UserDto selectUserByNameAndId(String name, String id);

    int updatePassword(String id, String newPassword);

    UserDto selectUserById(String id);

    /**************회원가입***************/
    void insertUser(UserDto user);
}
