//package com.viettel.kttb.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.ProviderManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.WebAuthenticationDetails;
//
//import java.util.HashSet;
//import java.util.Set;
//
//public class CustomAuthenticationProvider extends DaoAuthenticationProvider {
//
//    Set<String> whitelist = new HashSet<String>();
//
//    public CustomAuthenticationProvider() {
//        super();
//        whitelist.add("11.11.11.11");
//        whitelist.add("127.0.0.1");
//    }
//    @Override
//    public Authentication authenticate(Authentication authentication) {
//        Authentication authenticationResult = super.authenticate(authentication);
//        WebAuthenticationDetails details = (WebAuthenticationDetails) authenticationResult.getDetails();
//        String userIp = details.getRemoteAddress();
//        if (!whitelist.contains(userIp)) {
//            throw new BadCredentialsException("Invalid IP Address");
//        } else {
//        return authenticationResult;}
//    }
//
//}
