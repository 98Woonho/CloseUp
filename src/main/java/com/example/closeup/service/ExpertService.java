package com.example.closeup.service;

import com.example.closeup.domain.dto.ExpertDetailDto;
import com.example.closeup.domain.dto.ExpertDto;
import com.example.closeup.domain.mapper.ExpertDetailMapper;
import com.example.closeup.domain.mapper.ExpertMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpertService {
    @Autowired
    private ExpertMapper expertMapper;
    @Autowired
    private ExpertDetailMapper expertDetailMapper;

    public List<ExpertDto> selectExpertInformation() {
        return expertMapper.selectExpertInformation();
    }

    public ExpertDto getExpertDto(String nickname) {
        return expertMapper.selectExpertByNickname(nickname);
    }

    public List<ExpertDetailDto> getExpertDetailDtoList(String nickname, String category) {
        return expertDetailMapper.selectExpertDetailListByNicknameAndCategory(nickname, category);
    }

    public ExpertDto selectExpertDto(String id) {
        return expertMapper.selectExpertByUserId(id);
    }
}
