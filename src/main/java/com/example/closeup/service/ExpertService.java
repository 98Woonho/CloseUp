package com.example.closeup.service;

import com.example.closeup.domain.dto.ExpertDetailDto;
import com.example.closeup.domain.dto.ExpertDto;
import com.example.closeup.domain.mapper.ExpertDetailMapper;
import com.example.closeup.domain.mapper.ExpertMapper;
import com.example.closeup.domain.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional(rollbackFor = Exception.class)
    public void insertExpertDetails(ExpertDto expertDto) {
        List<ExpertDetailDto> details = new ArrayList<>();
        String nickname = expertDto.getNickname();

        for (String skill : expertDto.getSkills()) {
            details.add(new ExpertDetailDto(nickname, "skill", skill));
        }

        for (String expertise : expertDto.getExpertises()) {
            details.add(new ExpertDetailDto(nickname, "expertise", expertise));
        }

        for (String career : expertDto.getCareers()) {
            details.add(new ExpertDetailDto(nickname, "career", career));
        }

        for (String ability : expertDto.getAbilities()) {
            details.add(new ExpertDetailDto(nickname, "ability", ability));
        }

        expertDetailMapper.insertExpertDetails(details);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteExpertDetails(String nickname) {
        expertDetailMapper.deleteExpertDetailsByNickname(nickname);
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

    public ExpertDto getExpertDtoById(String id) {
        return expertMapper.selectExpertByUserId(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateExpertInfo(String id, ExpertDto expertDto) {
        expertMapper.updateExpertInfo(id, expertDto);
    }
}
