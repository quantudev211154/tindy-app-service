package com.tindy.app;

import com.tindy.app.dto.request.UserRequest;
import com.tindy.app.service.AuthService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class AppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
//	@Bean
//	CommandLineRunner runner(AuthService authService){
//		UserRequest userRequest = new UserRequest();
//		userRequest.setPhone("098813123");
//		userRequest.setEmail("axy2@gmail.com");
//		userRequest.setPassword("12312");
//		userRequest.setFullName("Hieu Tran");
//		return args -> {
//			authService.register(userRequest);
//		};
//	}
}
