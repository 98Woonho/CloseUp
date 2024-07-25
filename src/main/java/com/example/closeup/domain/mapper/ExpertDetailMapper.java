package com.example.closeup.domain.mapper;

import com.example.closeup.domain.dto.ExpertDetailDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ExpertDetailMapper {
    List<ExpertDetailDto> selectExpertDetailListByNickname(String nickname);

    List<ExpertDetailDto> selectExpertDetailListByNicknameAndCategory(String nickname, String category);

    void insertExpertDetails(List<ExpertDetailDto> details);

    void deleteExpertDetailsByNickname(String expertNickname);
}
