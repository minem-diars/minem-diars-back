package com.minem.diars.app.controller;

import java.io.File;
import java.util.Map;

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
	
	@PostMapping("upload")
	public String attachFiles(@RequestBody Map<String, String> files) {
		System.out.println(files.toString());
		return "";
	}

}
