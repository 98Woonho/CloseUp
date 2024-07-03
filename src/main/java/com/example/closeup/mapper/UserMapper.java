package com.example.closeup.mapper;

import com.example.closeup.dto.UserDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    /**************회원가입***************/
    @Insert("INSERT INTO `user` " +
            "VALUES (#{id}, #{pw}, #{phone}, #{carrier})")
    void insertUser(UserDto user);
}
