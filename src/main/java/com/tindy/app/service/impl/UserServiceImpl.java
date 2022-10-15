package com.tindy.app.service.impl;

import com.tindy.app.dto.respone.UserRespone;
import com.tindy.app.mapper.MapData;
import com.tindy.app.repository.UserRepository;
import com.tindy.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public UserRespone getUserInfo(String phone) {
        return MapData.mapOne(userRepository.findByPhone(phone).orElseThrow(()->new UsernameNotFoundException("User is not found!")),UserRespone.class);
    }
}
