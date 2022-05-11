package com.example.thecommunityapi;

import com.example.thecommunityapi.dto.SignupRequestDto;
import com.example.thecommunityapi.model.Role;
import com.example.thecommunityapi.security.AuthService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class TheCommunityApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TheCommunityApiApplication.class, args);
	}

	//to populate the user table once.
//	@Bean
//	CommandLineRunner run(AuthService userService) {
//		return args -> {
//			userService.saveRole(new Role(null, "ROLE_USER"));
//			userService.saveRole(new Role(null, "ROLE_ADMIN"));
//
//
//			userService.saveUser(new SignupRequestDto("Anshuman", "anshuman", "1234"));
//			userService.saveUser(new SignupRequestDto("Yuvraj", "yuvraj", "1234"));
//			userService.saveUser(new SignupRequestDto("Abhishek", "abhishek", "1234"));
//			userService.saveUser(new SignupRequestDto("Anshuman Yuvraj", "uvrajanshuman", "1234"));
//
//			userService.addRoleToUser("anshuman", "ROLE_USER");
//			userService.addRoleToUser("yuvraj", "ROLE_USER");
//			userService.addRoleToUser("abhishek", "ROLE_ADMIN");
//			userService.addRoleToUser("uvrajanshuman", "ROLE_ADMIN");
//			userService.addRoleToUser("uvrajanshuman", "ROLE_USER");
//		};
//	}

}
