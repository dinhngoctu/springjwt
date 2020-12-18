package com.viettel.kttb.gateway.CheckInfoPhone.filter;

import com.viettel.kttb.gateway.CheckInfoPhone.config.JwtTokenProvider;
import com.viettel.kttb.gateway.CheckInfoPhone.domain.model.UserInformation;
import com.viettel.kttb.gateway.CheckInfoPhone.service.CustomUserDetailsService;
import com.viettel.kttb.gateway.CheckInfoPhone.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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
import java.net.InetAddress;

@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {
    private static final String BEARER = "Bearer ";
    private static final String AUTHORIZATION = "Authorization";

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
            throws BadCredentialsException, ServletException, IOException {
        WebAuthenticationDetails webDetail = new WebAuthenticationDetailsSource().buildDetails(request);
        UserInformation userInformation = userInfoService.findUserByIp(webDetail.getRemoteAddress());
        InetAddress inaHost = InetAddress.getByName(request.getRemoteAddr());
        String hostname = inaHost.getHostName();
        boolean trueIp = (null != userInformation) && (userInformation.getHostName().equalsIgnoreCase(hostname));
        if(!trueIp){
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
        try {
            String jwt = getJwtFromRequest(request);
            String userNamePass = this.getBasicAuthen(request);
            boolean isBasicAuthen = StringUtils.hasText(userNamePass) && (userNamePass.split(":").length > 0);
            boolean isTokenAuthen = StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt);

            if (isTokenAuthen) {
                String userId = tokenProvider.getUserIdFromJWT(jwt);
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(userId);
                if(userDetails != null) {
                    UsernamePasswordAuthenticationToken
                           namePassAuthen = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    namePassAuthen.setDetails(webDetail);
                    SecurityContextHolder.getContext().setAuthentication(namePassAuthen);
                    response.setHeader(AUTHORIZATION,BEARER + jwt);
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
            throw new BadCredentialsException("BadCredentials authorization");
        }
        filterChain.doFilter(request, response);
    }

    private void modifyResponse(HttpServletResponse response, String userName) {
        String jwt = BEARER + tokenProvider.generateToken(userName);
        response.setHeader("Authorization",jwt);
    }

    private String getJwtFromRequest (HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private String getBasicAuthen (HttpServletRequest request) throws UnsupportedEncodingException {
        String bearerToken = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Basic ")) {
            String basic = bearerToken.substring(6);
            byte[] result = Base64Utils.decode(basic.getBytes());
            String userNamePass = new String(result,"UTF-8");
            return userNamePass;
        }
        return null;
    }
}
