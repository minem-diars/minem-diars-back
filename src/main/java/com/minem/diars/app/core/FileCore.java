package com.minem.diars.app.core;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.minem.diars.app.model.api.response.ConsultAttachedFileResponse;
import com.minem.diars.app.model.bean.AttachedFile;
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
				
				if (Files.isExecutable(employeDirectory)) {
					FileEntity fileEnt = new FileEntity();
					fileEnt.setFileName(file.getOriginalFilename().split("\\+")[1]);
					fileEnt.setFilePath(employeDirectory.toString() + "\\" + file.getOriginalFilename().split("\\+")[0]);
					
					programEnt.getFiles().add(fileEnt);
					fileEnt.setProgram(programEnt);
					
					ProgramEntity ent = this.programRepository.save(programEnt);
					
					if (ent != null) {
						Files.copy(file.getInputStream(), employeDirectory.resolve(file.getOriginalFilename().split("\\+")[0]));
					}
				}
				
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

	public ConsultAttachedFileResponse consultAttachedFiles(Integer programCode) {
		ConsultAttachedFileResponse response = null;
		ProgramEntity programEnt = this.programRepository.findById(programCode).get();
		if (programEnt != null) {
			response = new ConsultAttachedFileResponse();
			Iterator<FileEntity> itr = programEnt.getFiles().iterator();
			List<AttachedFile> files = new ArrayList<AttachedFile>();
			while (itr.hasNext()) {
				AttachedFile file = new AttachedFile();
				FileEntity fileEnt = itr.next();
				file.setFileName(fileEnt.getFileName());
				files.add(file);
			}
			response.setFiles(files);
			return response;
		} else {
			return null;
		}
	}

	public Resource downloadAttachedFile(Integer programCode, String fileName) {
		
		ProgramEntity programEnt = this.programRepository.findById(programCode).get();
		
		if (programEnt.getFiles() != null) {
			
			return buildResourceResponse(programEnt.getFiles().iterator(), fileName);
			
		}
		return null; // Sin archivos adjuntos
	}

	private Resource buildResourceResponse(Iterator<FileEntity> iterator, String fileName) {
		while (iterator.hasNext()) {
			FileEntity fileEnt = iterator.next();
			if (fileName.equals(fileEnt.getFileName())) {
				try {
					Resource resource = new UrlResource("file:///" + fileEnt.getFilePath());
					if (resource.exists() || resource.isReadable()) {
						return resource;
					} else {
						throw new RuntimeException("FAIL!");
					}
				} catch (MalformedURLException e) {
					throw new RuntimeException("FAIL!");
				}
			}
		}
		return null;
	}

}
