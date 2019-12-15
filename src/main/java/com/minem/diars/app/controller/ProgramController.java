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
import com.minem.diars.app.model.api.request.UpdateProgramRequest;
import com.minem.diars.app.model.api.request.UpdateStateRequest;
import com.minem.diars.app.model.api.response.AcceptedProgramResponse;
import com.minem.diars.app.model.api.response.CheckProgramResponse;
import com.minem.diars.app.model.api.response.ConsultUpdateProgramResponse;
import com.minem.diars.app.model.api.response.FindForEvaluateResponse;
import com.minem.diars.app.model.api.response.ProgramRegisterResponse;
import com.minem.diars.app.model.api.response.UpdateProgramResponse;
import com.minem.diars.app.model.api.response.UpdateStateResponse;
import com.minem.diars.app.model.api.response.VerifyProgramResponse;
import com.minem.diars.app.service.ProgramService;
import com.minem.diars.app.util.constants.ProgramConstant;

@RestController
@RequestMapping("/travel/program/v1/")
@CrossOrigin(origins = {"http://localhost:4200", "https://minem-diars.github.io"})
public class ProgramController {
	
	@Autowired
	@Qualifier(ProgramConstant.SERVICE)
	private ProgramService programService;
	
	@PostMapping("register")
	public ProgramRegisterResponse postRegisterProgram(@RequestBody ProgramRegisterRequest request) {
		return this.programService.registerProgram(request);
	}
	
	@GetMapping("consult/{employeeCode}/{role}/{flag}")
	public CheckProgramResponse getCheckProgram(@PathVariable("employeeCode") Integer employeeCode,
												@PathVariable("role") String role,
												@PathVariable("flag") Integer flag) {
		return this.programService.checkProgram(employeeCode, role, flag);
	}
	
	@GetMapping("consult/evaluate/{programCode}")
	public FindForEvaluateResponse getProgram(@PathVariable("programCode") Integer programCode) {
		return this.programService.findProgram(programCode);
	}
	
	@PostMapping("update/state")
	public UpdateStateResponse updateState(@RequestBody UpdateStateRequest request) {
		return this.programService.updateProgramState(request);
	}
	
	@GetMapping("verify/{idProgram}")
	public VerifyProgramResponse verifyProgram(@PathVariable("idProgram") Integer idProgram) {
		return this.programService.verifyProgram(idProgram);
	}
	
	@GetMapping("consult/accepted/{dlogCode}/{empCode}")
	public AcceptedProgramResponse consultAcceptedPrograms(@PathVariable("dlogCode") Integer dlogCode,
															@PathVariable("empCode") Integer empCode) {
		return this.programService.consultAcceptedPrograms(dlogCode, empCode);
	}
	
	@GetMapping("consult/update/{programCode}/{flag}")
	public ConsultUpdateProgramResponse consultForUpdateProgram(@PathVariable("programCode") Integer programCode,
																@PathVariable("flag") Integer flag) {
		return this.programService.consultForUpdateProgram(programCode, flag);
	}
	
	@PostMapping("update/program")
	public UpdateProgramResponse postUpdateProgram(@RequestBody UpdateProgramRequest request) {
		return this.programService.postUpdateProgram(request);
	}

}
