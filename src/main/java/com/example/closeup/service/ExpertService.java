package com.example.closeup.service;

import com.example.closeup.domain.dto.ExpertDto;
import com.example.closeup.domain.mapper.ExpertMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpertService {
    @Autowired
    private ExpertMapper expertMapper;

    public List<ExpertDto> selectExpertInformation() {
        return expertMapper.selectExpertInformation();
    }

    public ExpertDto selectExpertDto(String id) {
        return expertMapper.selectExpertByUserId(id);
    }
}
