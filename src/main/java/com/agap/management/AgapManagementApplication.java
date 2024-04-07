package com.agap.management;

import com.agap.management.user.Role;
import com.agap.management.user.RoleRepository;
import com.agap.management.user.User;
import com.agap.management.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class AgapManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgapManagementApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			Role roleUser = Role.builder().name("USER").build();
			Role roleAdmin = Role.builder().name("ADMIN").build();
			roleRepository.saveAll(List.of( roleUser, roleAdmin ));

			User sebas = User.builder()
					.firstName("sebastian")
					.lastName("silva")
					.email("sebas@gmail.com")
					.password(passwordEncoder.encode("password"))
					.roles(List.of(roleUser))
					.enabled(true)
					.build();

			User admin = User.builder()
					.firstName("admin")
					.lastName("admin")
					.email("admin@gmail.com")
					.password(passwordEncoder.encode("password"))
					.roles(List.of(roleAdmin))
					.enabled(true)
					.build();
			userRepository.saveAll(List.of( sebas, admin ));
			System.out.println("Roles roleUser and roleAdmin loaded into the database " +
					"Users sebas and Admin loaded into the database");
		};
	}
}
