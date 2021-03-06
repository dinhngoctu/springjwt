package com.viettel.kttb.gateway.CheckInfoPhone.config;


import com.viettel.kttb.gateway.CheckInfoPhone.filter.JwtTokenFilter;
//import com.viettel.kttb.gateway.CheckInfoPhone.filter.PreFilter;
import com.viettel.kttb.gateway.CheckInfoPhone.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder;
    }

//    @Bean
//    @Primary
//    public CustomAuthenticationProvider getAuthenticationProvider(){
//        CustomAuthenticationProvider result = new CustomAuthenticationProvider();
//        result.setUserDetailsService(userDetailsService);
//        result.setPasswordEncoder(passwordEncoder());
//        return result;
//    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(getAuthenticationProvider());
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();}

    @Bean
    public JwtTokenFilter jwtFilter() {
        return new JwtTokenFilter();
    }

//    @Bean
//    public PreFilter getPreFilter() {
//        return new PreFilter();
//    }

    @Bean
    public JwtTokenProvider getJwtTokenProvider() {
        return new JwtTokenProvider();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        http.addFilterBefore(characterEncodingFilter, CsrfFilter.class);
//        http.csrf().ignoringAntMatchers("/**");
        http.authorizeRequests().antMatchers( HttpMethod.POST,"/login").permitAll()
                .and()
                .authorizeRequests().antMatchers(HttpMethod.GET,"/api/**","/api").hasRole("USER")
                .and()
                .authorizeRequests().antMatchers(HttpMethod.POST,"/api/**","/api").hasRole("USER")
                .and()
                .csrf().disable()
//                .formLogin()
//                .loginPage("/login")
//                .usernameParameter("userName")
//                .passwordParameter("password")
//                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/logoutSuccessful");
        http.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
//        http.addFilterAfter(getPreFilter(), FilterSecurityInterceptor.class);
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.cors();
    }


}
