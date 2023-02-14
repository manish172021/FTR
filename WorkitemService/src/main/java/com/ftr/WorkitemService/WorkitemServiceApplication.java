package com.ftr.WorkitemService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class WorkitemServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkitemServiceApplication.class, args);
	}

}
