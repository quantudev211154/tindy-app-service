package com.tindy.app.service.impl;

import com.tindy.app.dto.request.UserRequest;
import com.tindy.app.dto.respone.LoginRespone;
import com.tindy.app.dto.respone.UserRespone;
import com.tindy.app.exceptions.ForbiddenException;
import com.tindy.app.mapper.MapData;
import com.tindy.app.model.entity.User;
import com.tindy.app.repository.UserRepository;
import com.tindy.app.sercurity.jwt.JwtProvider;
import com.tindy.app.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import com.tindy.app.model.enums.RoleName;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  AuthenticationManager authenticationManager;
    @Autowired
    private  JwtProvider jwtProvider;
    @Autowired
    private  PasswordEncoder passwordEncoder;

//    @Autowired
//    public AuthServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, JwtProvider jwtProvider, PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.authenticationManager = authenticationManager;
//        this.jwtProvider = jwtProvider;
//        this.passwordEncoder = passwordEncoder;
//    }


    @Override
    public LoginRespone login(String phone, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(phone, password));
            String role = authentication.getAuthorities().iterator().next().getAuthority();
            String token = jwtProvider.createToken(phone, String.valueOf(role));
            return new LoginRespone(phone, password, role, token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            throw new ForbiddenException("phone number or password is incorrect!");
        }
    }

    @Override
    public UserRespone register(UserRequest userRequest) {
        User user = MapData.mapOne(userRequest, User.class);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setCreatedAt(new Date());
        user.setRole(RoleName.valueOf(userRequest.getRole()));
        User accountSaved = this.userRepository.save(user);
        return MapData.mapOne(accountSaved, UserRespone.class);
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, null);
    }
}
