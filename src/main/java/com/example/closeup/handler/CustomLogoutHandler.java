package com.example.closeup.handler;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class CustomLogoutHandler implements LogoutHandler {

    private RestTemplate restTemplate;

    public CustomLogoutHandler(){
        restTemplate = new RestTemplate();
    }

//    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
//    private String naverClientId;
//
//    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
//    private String naverClientSecret;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication auth) {

//        PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
//        String provider =  principalDetails.getUserDto().getProvider();
//        System.out.println("[CustomLogoutHandler] logout() provider : " + provider);
//
//        if(provider!=null&&provider.equals("kakao")){
//
//            //AccessToken 추출
//            String accessToken =  principalDetails.getAccessToken();
//            //Requeset URL
//            String url = "https://kapi.kakao.com/v1/user/logout";
//            //Request Header
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("Content-Type","application/x-www-form-urlencoded");
//            headers.add("Authorization","Bearer "+accessToken);
//
//            //parameter(생략)
//            //MultiValueMap<String,String> params =new LinkedMultiValueMap<>();
//
//            //Header + Parameter 단위 생성
//            HttpEntity<MultiValueMap<String,String>> entity = new HttpEntity(headers);
//
//            //Restamplate에 HttpEntity등록
//            ResponseEntity<String> resp =  restTemplate.exchange(url, HttpMethod.POST,entity,String.class);
//            System.out.println("[CustomLogoutHandler] logout() resp "+resp);
//            System.out.println("[CustomLogoutHandler] logout() resp.getBody() "+resp.getBody());
//
//
//        }else if(provider!=null&&provider.equals("naver")){
//
//
//
//            //AccessToken 추출
//            String accessToken =  principalDetails.getAccessToken();
//            String url ="https://nid.naver.com/oauth2.0/token?grant_type=delete&client_id="+naverClientId+"&client_secret="+naverClientSecret+"&access_token="+accessToken+"&service_provider=NAVER";
//
//            restTemplate.exchange(url,HttpMethod.GET,null,String.class);
//
//
//        }else if(provider!=null&&provider.equals("google")){
//            //AccessToken 추출
//            String accessToken =  principalDetails.getAccessToken();
//            //URL
//            String url = "https://accounts.google.com/o/oauth2/revoke?token=" + accessToken;
//            //Rest Request
//            ResponseEntity<String>resp =  restTemplate.exchange(url,HttpMethod.GET,null,String.class);
//
//            System.out.println("[CustomLogoutHandler] logout() google resp : " + resp);
//
//        }
        HttpSession session =  request.getSession(false);
        if(session!=null)
            session.invalidate();
    }
}
