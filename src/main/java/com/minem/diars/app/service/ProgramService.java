package com.minem.diars.app.service;

import com.minem.diars.app.model.api.request.ProgramRegisterRequest;
import com.minem.diars.app.model.api.response.CheckProgramResponse;
import com.minem.diars.app.model.api.response.ProgramRegisterResponse;

public interface ProgramService {
	
	ProgramRegisterResponse registerProgram(ProgramRegisterRequest request);

	CheckProgramResponse checkProgram(Integer employeeCode);

}
