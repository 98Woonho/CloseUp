package com.example.closeup.domain.mapper;

import com.example.closeup.domain.dto.ExpertDto;
import com.example.closeup.domain.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ExpertMapper {
    List<ExpertDto> selectExpertInformation();

    ExpertDto selectExpertByNickname(String nickname);

    ExpertDto selectExpertByUserId(String id);

    /**************전문가 프로필 사진(전문가 등록 후 마이페이지에서 변경)***************/
    void updateExpertProfileImg(String id, byte[] profileImg);

    void updateExpertInfo(String id, ExpertDto expertDto);
}
