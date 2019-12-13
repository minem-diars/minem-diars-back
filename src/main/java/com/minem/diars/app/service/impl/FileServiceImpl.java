package com.minem.diars.app.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.minem.diars.app.core.FileCore;
import com.minem.diars.app.model.api.response.ConsultAttachedFileResponse;
import com.minem.diars.app.model.api.response.FileUploadResponse;
import com.minem.diars.app.service.FileService;
import com.minem.diars.app.util.constants.MinemConstants;

@Service(MinemConstants.FILE_SERVICE)
public class FileServiceImpl implements FileService {
	
	private final Path rootLocation = Paths.get("\\livelihoodFiles");
	//livelihoodFiles
	@Autowired
	@Qualifier(MinemConstants.FILE_CORE)
	private FileCore fileCore;

	@Override
	public FileUploadResponse uploadFile(MultipartFile file) throws IOException {
		FileUploadResponse response = new FileUploadResponse();
		if (file != null) {
			try {
				this.fileCore.saveFile(rootLocation, file);
				response.setStatus(MinemConstants.RESPONSE_OK);
				response.setMessage("Se guardó correctamente: "+ file.getOriginalFilename().split("\\+")[1]);
				return response;
			} catch (Exception e) {
				response.setStatus(MinemConstants.RESPONSE_KO);
				response.setErrorCode("FA-0001");
				response.setMessage("No se guardó correctamente: "+ file.getOriginalFilename().split("\\+")[1]);
				return response;
			}
		} else {
			response.setStatus(MinemConstants.RESPONSE_KO);
			response.setErrorCode("FA-0002");
			response.setMessage("No se recuperó el archivo adjuntado.");
			return response;
		}
	}
	
	public void init() {
		try {
			if (!Files.isExecutable(rootLocation)) {
				Files.createDirectory(rootLocation);
			}
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize storage!");
		}
	}

	@Override
	public ConsultAttachedFileResponse consultAttachedFiles(Integer programCode) {
		return this.fileCore.consultAttachedFiles(programCode);
	}

	@Override
	public Resource getAttachedFile(Integer programCode, String fileName) {
		return this.fileCore.downloadAttachedFile(programCode, fileName);
	}

}
