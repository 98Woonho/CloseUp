package com.example.closeup.controller;

import com.example.closeup.domain.dto.ExpertDto;
import com.example.closeup.domain.dto.UserDto;
import com.example.closeup.service.ExpertService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @Autowired private ExpertService expertService;

    // 지도 데이터 요청
    @GetMapping("expert/mapData")
    public List<ExpertDto> getMapData(Model model) {
        List<ExpertDto> expertInformation = expertService.getExpertInformation();
        model.addAttribute("expertInfo", expertInformation);
        return expertInformation;
    }

    @PutMapping("/imgChange")
    public void changeImg(
            @AuthenticationPrincipal UserDto userDto,
            @RequestBody byte[] img
    ) {

    }
}
