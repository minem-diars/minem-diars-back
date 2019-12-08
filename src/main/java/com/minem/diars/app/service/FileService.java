package com.minem.diars.app.service;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.minem.diars.app.model.api.response.ConsultAttachedFileResponse;
import com.minem.diars.app.model.api.response.FileUploadResponse;

public interface FileService {
	
	FileUploadResponse uploadFile(MultipartFile file) throws IOException;
	
	void init();

	ConsultAttachedFileResponse consultAttachedFiles(Integer programCode);

	Resource getAttachedFile(Integer programCode, String fileName);

}
