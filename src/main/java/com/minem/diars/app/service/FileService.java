package com.minem.diars.app.service;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.minem.diars.app.model.api.response.ConsultAttachedFileResponse;

public interface FileService {
	
	void uploadFile(MultipartFile file) throws IOException;
	
	void init();

	ConsultAttachedFileResponse consultAttachedFiles(Integer programCode);

	Resource getAttachedFile(Integer programCode, String fileName);

}
