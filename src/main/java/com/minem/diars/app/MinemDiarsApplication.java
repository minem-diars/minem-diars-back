package com.minem.diars.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class MinemDiarsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinemDiarsApplication.class, args);
		log.info("\n	---------------------------------------\n"
				+ "		http://localhost:8080\n"
				+ "	---------------------------------------");
	}

}
