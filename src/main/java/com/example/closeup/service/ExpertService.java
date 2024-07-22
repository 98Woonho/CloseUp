package com.example.closeup.service;

import com.example.closeup.domain.dto.ExpertDetailDto;
import com.example.closeup.domain.dto.ExpertDto;
import com.example.closeup.domain.mapper.ExpertDetailMapper;
import com.example.closeup.domain.mapper.ExpertMapper;
import com.example.closeup.domain.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExpertService {
    @Autowired
    private ExpertMapper expertMapper;
    @Autowired
    private UserMapper userMapper;
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

        public ExpertDto selectExpertDtoByUserId(String id) {
        return expertMapper.selectExpertByUserId(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateExpertProfileImg(String id, byte[] profileImg) {
        expertMapper.updateExpertProfileImg(id, profileImg);

    }
}
