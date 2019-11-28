package com.minem.diars.app.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	
	void uploadFile(MultipartFile file) throws IOException;
	
	void init();

}
