package com.minem.diars.app.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.minem.diars.app.model.entity.EmployeeEntity;
import com.minem.diars.app.model.entity.FileEntity;
import com.minem.diars.app.model.entity.ProgramEntity;
import com.minem.diars.app.repository.FileRepository;
import com.minem.diars.app.repository.ProgramRepository;
import com.minem.diars.app.util.constants.MinemConstants;

@Component(MinemConstants.FILE_CORE)
public class FileCore {
	
	@Autowired
	@Qualifier(MinemConstants.FILE_REPOSITORY)
	private FileRepository fileRepository;
	
	@Autowired
	@Qualifier(MinemConstants.PROGRAM_REPOSITORY)
	private ProgramRepository programRepository;
	
	public void saveFile(Path rootLocation, MultipartFile file) {
		Integer programId = Integer.parseInt(file.getOriginalFilename().split("\\+")[2]);
		ProgramEntity programEnt = this.programRepository.findById(programId).get();
		
		if (programEnt != null) {
			
			try {
				
				Path employeDirectory = createEmployeeDirectory(rootLocation, programEnt.getChronogram().getEmployee());
				
				Files.copy(file.getInputStream(), employeDirectory.resolve(file.getOriginalFilename().split("\\+")[0]));
				
				FileEntity fileEnt = new FileEntity();
				fileEnt.setFileName(file.getOriginalFilename().split("\\+")[1]);
				fileEnt.setFilePath(employeDirectory.toString() + file.getOriginalFilename().split("\\+")[0]);
				
				programEnt.getFiles().add(fileEnt);
				
				this.programRepository.save(programEnt);
				
			} catch (IOException e) {
				throw new RuntimeException("Error al guardar: " + file.getOriginalFilename());
			}
		}
	}
	
	Path createEmployeeDirectory(Path rootLocation, EmployeeEntity employee) throws IOException {
		String newDirectory = rootLocation.toString()+ "\\" + employee.getIdEmployee();
		Path employeeDirectory = Paths.get(newDirectory);
		if(!Files.isExecutable(employeeDirectory)) {
			return Files.createDirectory(employeeDirectory);
		}
		return employeeDirectory;
		
	}

}
