package com.gayathri.projects.employeemgmt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EmployeemgmtApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeemgmtApplication.class, args);
	}

}
