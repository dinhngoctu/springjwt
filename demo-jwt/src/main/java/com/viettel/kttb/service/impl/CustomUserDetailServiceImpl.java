package com.viettel.kttb.service.impl;

import com.viettel.kttb.domain.model.UserInformation;
import com.viettel.kttb.service.CustomUserDetailsService;
import com.viettel.kttb.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailServiceImpl implements CustomUserDetailsService {
    private static final String ROLE_USER = "ROLE_USER";
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    UserInfoService userInfoService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserInformation userInformation = userInfoService.findUserByName(s);
        if (null == userInformation) {
            throw new UsernameNotFoundException(s);
        }
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<GrantedAuthority>();
        grantedAuthorityList.add(new SimpleGrantedAuthority(ROLE_USER));
        String pass = userInformation.getPassword();
        String passEndcode = passwordEncoder.encode(pass);
        User user = new User(userInformation.getUserName(),passEndcode,grantedAuthorityList);
        return user;
    }
}
