package com.exterro;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.exterro.entity.User;
import com.exterro.repository.UserRepository;

@SpringBootApplication
public class SpringBootRelationshipApplication {

	
	public static void main(String[] args) {
		
		SpringApplication.run(SpringBootRelationshipApplication.class, args);
	}

	
	
//	@Bean
//	CommandLineRunner commandLineRunner(UserRepository users, PasswordEncoder encoder) {
//		return args -> {
//			users.save(new User("admin",encoder.encode("admin123"),"ROLE_ADMIN"));
//		};
//	}
	
	
	
}
