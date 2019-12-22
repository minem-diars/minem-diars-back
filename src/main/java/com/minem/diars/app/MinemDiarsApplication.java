package com.minem.diars.app;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.core.env.Environment;

import com.minem.diars.app.service.FileService;
import com.minem.diars.app.util.constants.MinemConstants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class MinemDiarsApplication extends SpringBootServletInitializer implements CommandLineRunner {
	
	@Autowired
	private Environment env;
	
	@Resource
	@Qualifier(MinemConstants.FILE_SERVICE)
	private FileService fileService;

	public static void main(String[] args) {
		SpringApplication.run(MinemDiarsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("\n	---------------------------------------\n"
				+ "		http://localhost:{}\n"
				+ "	---------------------------------------",
				env.getProperty("server.port"));
		this.fileService.init();
		
	}

}
