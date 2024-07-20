package com.example.closeup.domain.mapper;

import com.example.closeup.domain.dto.ExpertDto;
import com.example.closeup.domain.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ExpertMapper {
    List<ExpertDto> selectExpertInformation();

//    TODO
    ExpertDto selectExpertByNickname(String nickname);

    ExpertDto selectExpertByUserId(String id);

}
