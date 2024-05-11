package com.agap.management;

import com.agap.management.domain.entities.Role;
import com.agap.management.domain.enums.RoleType;
import com.agap.management.infrastructure.adapters.persistence.IRoleRepository;
import com.agap.management.domain.entities.User;
import com.agap.management.infrastructure.adapters.persistence.IUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
@EnableAsync
public class AgapManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgapManagementApplication.class, args);
	}

	/*
	@Bean
	public CommandLineRunner commandLineRunner(IUserRepository userRepository, IRoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			Role roleFarmer = Role.builder().name(RoleType.FARMER).build();
			Role roleAdmin = Role.builder().name(RoleType.ADMIN).build();
			roleRepository.saveAll(List.of( roleFarmer, roleAdmin ));

			User sebas = User.builder().firstName("sebastian")
					.lastName("silva").email("sebas2@yopmail.com")
					.password(passwordEncoder.encode("password"))
					.roles(List.of(roleFarmer)).enabled(true).build();

			User sebas2 = User.builder().firstName("sebastian")
					.lastName("silva").email("sesilvavi2@gmail.com")
					.password(passwordEncoder.encode("password"))
					.roles(List.of(roleFarmer)).enabled(true).build();

			User admin = User.builder().firstName("admin")
					.lastName("admin").email("admin2@yopmail.com")
					.password(passwordEncoder.encode("password"))
					.roles(List.of(roleAdmin, roleFarmer)).enabled(true).build();

			userRepository.saveAll(List.of( sebas, sebas2, admin ));
			System.out.println("Roles roleFarmer and roleAdmin loaded into the database");
			System.out.println("Users sebas, sebas2 and Admin loaded into the database");
		};
	}
	 */
}
