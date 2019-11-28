package com.minem.diars.app.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.minem.diars.app.model.entity.FileEntity;
import com.minem.diars.app.repository.FileRepository;
import com.minem.diars.app.service.FileService;
import com.minem.diars.app.util.constants.MinemConstants;

@RestController
@RequestMapping("/travel/file/v1/")
@CrossOrigin("http://localhost:4200")
public class FileController {
	
	@Autowired
	@Qualifier(MinemConstants.FILE_SERVICE)
	private FileService fileService;
	
	@Autowired
	@Qualifier(MinemConstants.FILE_REPOSITORY)
	private FileRepository fileRepository;
	
	@PostMapping(value = "upload",
				consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void attachFiles(@RequestParam("file") MultipartFile file) throws IOException {
		System.out.println(file.getOriginalFilename());
		this.fileService.uploadFile(file);
	}
	
//	@GetMapping("/download/{id}")
//	public ResponseEntity<byte[]> downloadFile(@PathVariable("id") Integer id) throws IOException {
//		FileEntity dFile = this.fileRepository.findById(id).get();
//		return ResponseEntity.ok()
//					.contentType(MediaType.IMAGE_JPEG)
//					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = " + dFile.getFileName())
//					.body(dFile.getData());
//	}

}
