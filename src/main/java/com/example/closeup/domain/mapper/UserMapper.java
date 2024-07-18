package com.example.closeup.domain.mapper;

import com.example.closeup.domain.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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
    /**************유저 프로필 사진(회원가입 후 마이페이지에서 변경)***************/
    void updateUserProfileImg(String id, byte[] profileImg);
    /**************유저 프로필 사진 조회***************/
    byte[] selectUserProfileImgById(String id);



    /**********관리자 페이지************/
    List<UserDto> selectAllUsers();
    void updateUser(UserDto user);
    void deleteUser(String id);
}
