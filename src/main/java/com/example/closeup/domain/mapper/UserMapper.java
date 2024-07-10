package com.example.closeup.domain.mapper;

import com.example.closeup.domain.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    UserDto selectUserById(String id);
}
