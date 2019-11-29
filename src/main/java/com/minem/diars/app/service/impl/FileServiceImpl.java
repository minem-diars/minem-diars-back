package com.minem.diars.app.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.minem.diars.app.core.FileCore;
import com.minem.diars.app.service.FileService;
import com.minem.diars.app.util.constants.MinemConstants;

@Service(MinemConstants.FILE_SERVICE)
public class FileServiceImpl implements FileService {
	
	private final Path rootLocation = Paths.get("C:\\Users\\asus\\Desktop\\livelihoodFiles");
	//livelihoodFiles
	@Autowired
	@Qualifier(MinemConstants.FILE_CORE)
	private FileCore fileCore;

	@Override
	public void uploadFile(MultipartFile file) throws IOException {

		if (file != null) {
			this.fileCore.saveFile(rootLocation, file);
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

}
