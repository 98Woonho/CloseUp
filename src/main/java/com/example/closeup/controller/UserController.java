package com.example.closeup.controller;

import com.example.closeup.domain.dto.UserDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("user")
public class UserController {

    @GetMapping("login")
    public void getLogin(@RequestParam(value="error", required = false) boolean error, Model model) {
        model.addAttribute("error", error);
    }

    // portOne 엑세스 토큰 받기
    @GetMapping("token")
    public String AccessToken(){
        String url = "https://api.iamport.kr/users/getToken";
        // HEADER
        HttpHeaders headers = new HttpHeaders();

        // PARAM
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("imp_key", "7582034642764268");
        params.add("imp_secret", "JxMwheK2PKBrxFxOifDLwwZvdyzjwDERKj4TzStgSZ06Wmg3oQp7h3WjK3nOfdjXsSXF0ZNgCbBWyPrV");

        // Entity
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        // REQUEST
        RestTemplate rt = new RestTemplate();
        ResponseEntity<PortOneTokenResponse> response = rt.exchange(url, HttpMethod.POST, entity, PortOneTokenResponse.class);

        //RESPONSE
        return response.getBody().getResponse().getAccess_token();
    }

    //access토큰 객체
    @Data
    private static class TokenResponse{
        public String access_token;
        public int now;
        public int expired_at;
    }

    @Data
    private static class PortOneTokenResponse{
        public int code;
        public Object message;
        public TokenResponse response;
    }


    @PostMapping(value = "certification/{imp_uid}")
    public @ResponseBody PortOneAuthInfoResponse postCertification(@PathVariable("imp_uid")String imp_uid){
        String accessToken = AccessToken(); // 엑세스 토큰 가져오기

        String url = "https://api.iamport.kr/certifications/"+imp_uid;

        //header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization","Bearer "+ accessToken);

        //params
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        //Entity
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        //request
        RestTemplate rt = new RestTemplate();

        //response
        ResponseEntity<PortOneAuthInfoResponse> response = rt.exchange(url, HttpMethod.GET,entity,PortOneAuthInfoResponse.class);

        return response.getBody();
    }

    //인증 객체
    @Data
    private static class AuthInfoResponse{
        public int birth;
        public String birthday;
        public boolean certified;
        public int certified_at;
        public boolean foreigner;
        public Object foreigner_v2;
        public Object gender;
        public String imp_uid;
        public String merchant_uid;
        public String name;
        public String origin;
        public String pg_provider;
        public String pg_tid;
        public String phone;
        public Object unique_in_site;
        public String unique_key;
    }

    @Data
    private static class PortOneAuthInfoResponse{
        public int code;
        public Object message;
        public AuthInfoResponse response;
    }
}
