package com.example.closeup.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class HomeController {
    @GetMapping("/")
    public String getHome() {
        return "index";
    }
}
/* 전체 검색 기능
* 메인 페이지의 검색 창에 키워드를 입력 시,
* 키워드가 포함된 게시글을 조회
* (서비스 게시글, 전문가 이름조회, 기능 검색 등)*/