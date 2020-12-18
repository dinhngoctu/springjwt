package com.viettel.kttb.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class PreFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "http://172.1.1.124:8080/api/v1/checkTest2";
//        restTemplate.post
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(fooResourceUrl, Object.class);
        response.setStatus(responseEntity.getStatusCode().value());
//        response.sendRedirect("http://172.1.1.124:8080/api/v1/checkTest");
//        response.resetBuffer();
//        response.setStatus(HttpStatus.OK);
//        response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
//        response.getOutputStream().print(new ObjectMapper().writeValueAsString(myData));
//        response.flushBuffer();

        Object responseObj = responseEntity.getBody();
        String json = new ObjectMapper().writeValueAsString(responseObj);
        response.getWriter().write(json);
        response.flushBuffer();
    }
}
