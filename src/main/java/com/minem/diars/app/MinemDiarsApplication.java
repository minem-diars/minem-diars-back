package com.minem.diars.app;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.minem.diars.app.service.FileService;
import com.minem.diars.app.util.constants.MinemConstants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class MinemDiarsApplication implements CommandLineRunner {
	
	@Resource
	@Qualifier(MinemConstants.FILE_SERVICE)
	private FileService fileService;

	public static void main(String[] args) {
		SpringApplication.run(MinemDiarsApplication.class, args);
		log.info("\n	---------------------------------------\n"
				+ "		http://localhost:8080\n"
				+ "	---------------------------------------");
	}

	@Override
	public void run(String... args) throws Exception {
		this.fileService.init();
		
	}

}
