package com.tindy.app.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tindy.app.dto.respone.UserRespone;
import com.tindy.app.repository.UserRepository;
import com.tindy.app.service.AuthService;
import com.tindy.app.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
@Slf4j

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, UserService userService){
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String phone = request.getParameter("phone"); //change "username" to "phone"
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(phone, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();

        UserRespone userRespone = userService.getUserInfo(user.getUsername());

        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

        try {
            String access_token = JWT.create()
                    .withClaim("userId", userRespone.getId())
                    .withClaim("name", userRespone.getFullName())
                    .withClaim("phone", userRespone.getPhone())
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() +10*60*1000))
                    .withClaim("role", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                    .sign(algorithm);
            response.setHeader("access_token", access_token);

            String refresh_token = JWT.create()
                    .withClaim("userId", userRespone.getId())
                    .withClaim("name", userRespone.getFullName())
                    .withClaim("phone", userRespone.getPhone())
                    .withClaim("tokenVersion", userRespone.getTokenVersion())
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() +30*60*1000))
                    .sign(algorithm);

            Cookie refreshTokenCookie = new Cookie("REFRESH_TOKEN", refresh_token);
            refreshTokenCookie.setHttpOnly(true);
            refreshTokenCookie.setSecure(true);
            refreshTokenCookie.setPath("/refresh_token");

            response.addCookie(refreshTokenCookie);

            Map<String,Object> tokens = new HashMap<>();
            tokens.put("userId",userRespone.getId().toString());
            tokens.put("phone",user.getUsername());
            tokens.put("loginDate", new Date());
            tokens.put("accessToken", access_token);
            response.setContentType(APPLICATION_JSON_VALUE);

            new ObjectMapper().writeValue(response.getOutputStream(),tokens);
        }catch (Exception e){
            response.setHeader("error", e.getMessage());
            response.setStatus(HttpStatus.FORBIDDEN.value());
            Map<String,String> error = new HashMap<>();
            error.put("error_message",e.getMessage());
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(),error);
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setHeader("error", "Phone number or password is wrong!");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        Map<String,String> error = new HashMap<>();
        error.put("error_message",failed.getMessage());
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(),error);
    }
}
