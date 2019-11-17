package com.minem.diars.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minem.diars.app.model.api.request.ProgramRegisterRequest;
import com.minem.diars.app.model.api.response.CheckProgramResponse;
import com.minem.diars.app.model.api.response.ProgramRegisterResponse;
import com.minem.diars.app.service.ProgramService;
import com.minem.diars.app.util.constants.ProgramConstant;

@RestController
@RequestMapping("/travel/program/v1/")
@CrossOrigin("http://localhost:4200")
public class ProgramController {
	
	@Autowired
	@Qualifier(ProgramConstant.SERVICE)
	private ProgramService programService;
	
	@PostMapping(value = "register")
	public ProgramRegisterResponse postRegisterProgram(@RequestBody ProgramRegisterRequest request) {
		return this.programService.registerProgram(request);
	}
	
	@GetMapping("consult/{employeeCode}")
	public CheckProgramResponse getCheckProgram(@PathVariable("employeeCode") Integer employeeCode) {
		return this.programService.checkProgram(employeeCode);
	}
	
	

}
