package com.tindy.app.sercurity;

import com.tindy.app.model.entity.User;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPrinciple implements UserDetails {

    private Integer id;

    private String phone;

    private String password;

    private Collection<? extends GrantedAuthority> grantedAuthorities;

    public static UserPrinciple createUser(User user) {
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()));
        return UserPrinciple.builder()
                .id(user.getId())
                .phone(user.getPhone())
                .password(user.getPassword())
                .grantedAuthorities(authorities)
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return phone;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
