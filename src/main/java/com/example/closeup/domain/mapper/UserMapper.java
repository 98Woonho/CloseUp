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
    /**************토글로 유저 롤 변경***************/
    void updateUserRoleByToggle(String id, String role);
    /**************유저 프로필 사진(회원가입 후 마이페이지에서 변경)***************/
    void updateUserProfileImg(String id, byte[] profileImg);
}
