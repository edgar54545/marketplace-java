package com.marketplace.gateway.security;

import com.marketplace.gateway.model.Role;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/product/**").permitAll()
                .antMatchers("/user/**/sign-up", "/user/login").permitAll()
                .antMatchers("/verifyProduct").hasAuthority(Role.ROLE_ADMIN.toString())
                .anyRequest().hasAuthority(Role.ROLE_COMMON.toString())
                .and()
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
