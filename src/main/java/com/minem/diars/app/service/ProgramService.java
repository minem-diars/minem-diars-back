package com.minem.diars.app.service;

import com.minem.diars.app.model.api.request.ProgramRegisterRequest;
import com.minem.diars.app.model.api.request.UpdateStateRequest;
import com.minem.diars.app.model.api.response.CheckProgramResponse;
import com.minem.diars.app.model.api.response.FindForEvaluateResponse;
import com.minem.diars.app.model.api.response.ProgramRegisterResponse;
import com.minem.diars.app.model.api.response.UpdateStateResponse;
import com.minem.diars.app.model.api.response.VerifyProgramResponse;

public interface ProgramService {
	
	ProgramRegisterResponse registerProgram(ProgramRegisterRequest request);

	CheckProgramResponse checkProgram(Integer employeeCode, String role, Integer flag);

	FindForEvaluateResponse findProgram(Integer programCode);

	UpdateStateResponse updateProgramState(UpdateStateRequest request);

	VerifyProgramResponse verifyProgram(Integer idProgram);

}
