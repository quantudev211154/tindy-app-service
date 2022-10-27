package com.tindy.app.sercurity;

import com.tindy.app.filter.CustomAuthenticationFilter;
import com.tindy.app.filter.CustomAuthorizationFilter;
import com.tindy.app.service.AuthService;
import com.tindy.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SercurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthService authService;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean(), authService);
        customAuthenticationFilter.setFilterProcessesUrl("/api/auth/login");
        http.cors().and().csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers(POST ,"/api/auth/login/**","/api/auth/register/**").permitAll();
        http.authorizeRequests().antMatchers(GET,"/api/auth/exist/**").permitAll();
        http.authorizeRequests().antMatchers(POST ,"/api/auth/refresh_token/**" ).permitAll();

//        http.authorizeRequests().antMatchers(GET ,"/api/auth/refresh/**" ).permitAll();
        http.authorizeRequests().antMatchers("/api/auth/users/**","/api/users/contacts/**","/api/conversations/**","/api/participants/**","/api/messages/**","/api/contacts/**").hasAnyAuthority("USER");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Set-Cookie", "Authorization", "Cache-Control", "Content-Type"));
        configuration.setAllowedOrigins(Arrays.asList("http://127.0.0.1:5173","http://localhost:5173")); // evn
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration.applyPermitDefaultValues());
        return source;
    }

}
