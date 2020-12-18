//package com.viettel.kttb.controller;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
//
//@RestController
//public class TokenController {
//    @GetMapping(value = "/api/**")
//    public ResponseEntity<String> getGateWay(@RequestBody Object object) {
//        RestTemplate restTemplate = new RestTemplate();
//        String fooResourceUrl
//                = "http://172.1.1.124:8080/api/v1/checkTest";
////        restTemplate.post
//        return restTemplate.getForEntity(fooResourceUrl, String.class);
//    }
//
//    @PostMapping(value = "/spring-rest/foos")
//    public ResponseEntity<String> getData() {
//        return new ResponseEntity<>("foos", HttpStatus.ACCEPTED);
//    }
//}
