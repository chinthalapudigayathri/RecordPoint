package com.gayathri.projects;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.gayathri.projects.repository")
@EntityScan(basePackages = "com.gayathri.projects.entity")
@ComponentScan(basePackages = "com.gayathri.projects")
@EnableCaching
public class EmployeemgmtApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeemgmtApplication.class, args);
	}

}
