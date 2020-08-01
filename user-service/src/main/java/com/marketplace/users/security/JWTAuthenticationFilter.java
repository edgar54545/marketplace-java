package com.marketplace.users.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplace.users.dtos.LoginRequest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

import static com.marketplace.users.constants.Constants.*;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private Environment env;
    private ObjectMapper objectMapper;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager,
                                   Environment env,
                                   ObjectMapper objectMapper) {
        this.authenticationManager = authenticationManager;
        this.env = env;
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            LoginRequest creds = objectMapper
                    .readValue(req.getInputStream(), LoginRequest.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) {
        String userName = ((User) authResult.getPrincipal()).getUsername();
        String roles = authResult.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] secretBytes = DatatypeConverter.parseBase64Binary(env.getProperty(TOKEN_SECRET));

        Key signingKey = new SecretKeySpec(secretBytes, signatureAlgorithm.getJcaName());
        String token = Jwts.builder()
                .setSubject(userName)
                .setExpiration(new Date(System.currentTimeMillis() +
                        env.getRequiredProperty(TOKEN_EXPIRATION_TIME, Long.class)))
                .claim(ROLES_CLAIM, roles)
                .signWith(signatureAlgorithm, signingKey)
                .compact();

        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }
}
