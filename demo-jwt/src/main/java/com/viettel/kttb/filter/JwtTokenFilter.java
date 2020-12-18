package com.viettel.kttb.filter;

import com.viettel.kttb.config.JwtTokenProvider;
import com.viettel.kttb.domain.model.UserInformation;
import com.viettel.kttb.service.CustomUserDetailsService;
import com.viettel.kttb.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {
    private static final String BEARER = "Bearer ";

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            WebAuthenticationDetails webDetail = new WebAuthenticationDetailsSource().buildDetails(request);
            UserInformation userInformation = userInfoService.findUserByIp(webDetail.getRemoteAddress());
            boolean falseIp = (null == userInformation);
            if(falseIp){
                filterChain.doFilter(request, response);
                return;
            }
            String jwt = getJwtFromRequest(request);
            String userNamePass = this.getBasicAuthen(request);
            boolean isBasicAuthen = StringUtils.hasText(userNamePass) && (userNamePass.split(":").length > 0);
            boolean isTokenAuthen = StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt);
//          ----  test
//            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken("tudn", "pass");
//            authenticationToken.setDetails(webDetail);
//            Authentication authentication1 = authenticationManager.authenticate(authenticationToken);
//            SecurityContextHolder.getContext().setAuthentication(authentication1);
//          ---  end-test

            if (isTokenAuthen) {
                String userId = tokenProvider.getUserIdFromJWT(jwt);
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(userId);
                if(userDetails != null) {
                    UsernamePasswordAuthenticationToken
                           namePassAuthen = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    namePassAuthen.setDetails(webDetail);
                    Authentication authentication = authenticationManager.authenticate(namePassAuthen);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    response.setHeader("Authorization",BEARER + jwt);
                    isBasicAuthen = false;
                }
            }
            if (isBasicAuthen) {
                String[] arr = userNamePass.split(":");
                String userName = arr[0];
                String password = arr[1];
                UsernamePasswordAuthenticationToken usernamePasswordAuthentication = new UsernamePasswordAuthenticationToken(userName,password);
                usernamePasswordAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthentication);
                modifyResponse(response,userName);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            log.error("failed on set user authentication", ex);
        }
//        response.setHeader("Authorization","Authorization");
        filterChain.doFilter(request, response);
    }

    private void modifyResponse(HttpServletResponse response, String userName) {
        String jwt = BEARER + tokenProvider.generateToken(userName);
        response.setHeader("Authorization",jwt);
    }

    private String getJwtFromRequest (HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private String getBasicAuthen (HttpServletRequest request) throws UnsupportedEncodingException {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Basic ")) {
            String basic = bearerToken.substring(6);
//            basic = "dHU6cGFzcw==";
            byte[] result = Base64Utils.decode(basic.getBytes());
            String userNamePass = new String(result,"UTF-8");
            return userNamePass;
        }
        return null;
    }
}
