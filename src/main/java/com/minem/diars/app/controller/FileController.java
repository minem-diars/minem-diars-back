package com.minem.diars.app.controller;

import java.io.File;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/travel/file/v1/")
@CrossOrigin("http://localhost:4200")
public class FileController {
	
	@PostMapping(value = "upload",
				consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String attachFiles(@RequestParam("file") MultipartFile file) {
		return "";
	}

}
