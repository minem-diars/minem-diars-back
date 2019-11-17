package com.minem.diars.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.minem.diars.app.core.ProgramCore;
import com.minem.diars.app.model.api.request.ProgramRegisterRequest;
import com.minem.diars.app.model.api.response.CheckProgramResponse;
import com.minem.diars.app.model.api.response.ProgramRegisterResponse;
import com.minem.diars.app.model.common.ProgramModel;
import com.minem.diars.app.service.ProgramService;
import com.minem.diars.app.util.constants.ErrorConstant;
import com.minem.diars.app.util.constants.MinemConstants;
import com.minem.diars.app.util.constants.ProgramConstant;

@Service(ProgramConstant.SERVICE)
public class ProgramServiceImpl implements ProgramService {
	
	@Autowired
	@Qualifier(ProgramConstant.CORE)
	private ProgramCore programCore;
	
	@Override
	public ProgramRegisterResponse registerProgram(ProgramRegisterRequest request) {

		if (!request.isNull()) {
			ProgramModel model = programCore.saveProgram(request);
			return validateRegisterStatus(model);
		} else {
			ProgramRegisterResponse response = new ProgramRegisterResponse();
			response.setStatus(MinemConstants.RESPONSE_KO);
			response.setErrorCode(ErrorConstant.DATA_IS_MISSING_CODE);
			response.setErrorMessage(ProgramConstant.DATA_IS_MISSING);
			
			return response;
		}
	}

	private ProgramRegisterResponse validateRegisterStatus(ProgramModel model) {
		ProgramRegisterResponse response = null;
		if (MinemConstants.RESPONSE_OK.equals(model.getStatus())) {
			
			response = new ProgramRegisterResponse();
			response.setMessage(ProgramConstant.PROGRAM_REGISTERED);
			response.setStatus(MinemConstants.RESPONSE_OK);
			
			return response;
			
		}else {
			
			response = new ProgramRegisterResponse();
			response.setStatus(MinemConstants.RESPONSE_KO);
			response.setErrorCode(ErrorConstant.CHRONOGRAM_UNREGISTERED_CODE);
			response.setErrorMessage(ProgramConstant.PROGRAM_UNREGISTERED);
			
			return response;
			
		}
	}

	@Override
	public CheckProgramResponse checkProgram(Integer employeeCode) {
		
		CheckProgramResponse response = programCore.findPrograms(employeeCode);
		
		return response;
	}

}
