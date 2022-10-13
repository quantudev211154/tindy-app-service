package com.tindy.app.service.impl;

import com.tindy.app.dto.request.UserRequest;
import com.tindy.app.dto.respone.UserRespone;
import com.tindy.app.exceptions.ResourceNotFoundException;
import com.tindy.app.mapper.MapData;
import com.tindy.app.model.entity.User;
import com.tindy.app.model.enums.RoleName;
import com.tindy.app.model.enums.UserStatus;
import com.tindy.app.repository.UserRepository;
import com.tindy.app.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthServiceImpl implements AuthService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByPhone(username).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));
        return new org.springframework.security.core.userdetails.User(user.getPhone(),user.getPassword(),authorities);
    }
    @Override
    public UserRespone register(UserRequest userRequest) {
        User user = MapData.mapOne(userRequest,User.class);
        user.setRole(RoleName.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        user.setStatus(UserStatus.ACTIVE);
        User registerUser = userRepository.save(user);
        return MapData.mapOne(registerUser, UserRespone.class);
    }

    @Override
    public User getUser(String userName) {
        return userRepository.findByPhone(userName).get();
    }

    @Override
    public List<User> getUser() {
        return userRepository.findAll();
    }


}
