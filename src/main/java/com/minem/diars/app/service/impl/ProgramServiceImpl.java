package com.minem.diars.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.minem.diars.app.core.ProgramCore;
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
			return validateLodgingAndTransport(request);
		} else {
			ProgramRegisterResponse response = new ProgramRegisterResponse();
			response.setStatus(MinemConstants.RESPONSE_KO);
			response.setErrorCode(ErrorConstant.DATA_IS_MISSING_CODE);
			response.setErrorMessage(ProgramConstant.DATA_IS_MISSING);
			
			return response;
		}
	}

	private ProgramRegisterResponse validateLodgingAndTransport(ProgramRegisterRequest request) {
		if (request.getViaticFlag() == 1) {
			ProgramModel model = this.programCore.saveProgram(request);
			return validateRegisterStatus(model);
		} else {
			if (validateInvalidNumber(request.getLodgingCost()) == null && validateInvalidNumber(request.getTransportCost()) == null) {
				ProgramModel model = this.programCore.saveProgram(request);
				return validateRegisterStatus(model);
			} else {
				ProgramRegisterResponse response = new ProgramRegisterResponse();
				response = new ProgramRegisterResponse();
				response.setStatus(MinemConstants.RESPONSE_KO);
				response.setErrorCode("PG-0004");
				response.setErrorMessage("Debe ingresar un valor correcto en Hospedaje y Transporte.");
				return response;
			}
		}
	}
	
	private ProgramRegisterResponse validateInvalidNumber(String input) {
		try {
			@SuppressWarnings("unused")
			Integer logding = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			ProgramRegisterResponse response = new ProgramRegisterResponse();
			System.out.println("Error al validar hospedaje y transporte.");
			return response;
		}
		return null;
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
	public CheckProgramResponse checkProgram(Integer employeeCode, String role, Integer flag) {
		
		CheckProgramResponse response = this.programCore.findPrograms(employeeCode, role, flag);

		if (response != null) {
			return response;
		} else {
			response = new CheckProgramResponse();
			response.setStatus(MinemConstants.RESPONSE_KO);
			response.setErrorCode("CG-0003");
			response.setErrorMessage("Error al obtener lista de programaciones de viaje.");
			return response;			
		}
		
		
	}

	@Override
	public FindForEvaluateResponse findProgram(Integer programCode) {
		FindForEvaluateResponse response = this.programCore.obtainProgram(programCode); 
		if (response != null) {
			return response;
		} else {
			response = new FindForEvaluateResponse();
			response.setStatus(MinemConstants.RESPONSE_KO);
			response.setErrorCode("CG-0004");
			response.setErrorMessage("Error al obtener programación de viaje.");
			return response;	
		}
		
	}

	@Override
	public UpdateStateResponse updateProgramState(UpdateStateRequest request) {
		UpdateStateResponse response = this.programCore.updateProgram(request);
		return response;
	}

	@Override
	public VerifyProgramResponse verifyProgram(Integer idProgram) {
		VerifyProgramResponse response = this.programCore.getForVerifyProgram(idProgram);
		if (response != null) {
			response.setStatus(MinemConstants.RESPONSE_OK);
			response.setMessage("La programación de viaje se derivo correctamente.");
			return response;
		} else {
			response = new VerifyProgramResponse();
			response.setStatus(MinemConstants.RESPONSE_KO);
			response.setErrorCode("CG-0004");
			response.setErrorMessage("Error al obtener programación de viaje.");
			return response;
		}
	}

	@Override
	public AcceptedProgramResponse consultAcceptedPrograms(Integer dlogCode, Integer empCode) {
		return this.programCore.consultAcceptedPrograms(dlogCode, empCode);
	}

	@Override
	public ConsultUpdateProgramResponse consultForUpdateProgram(Integer programCode, Integer flag) {
		return this.programCore.consultForUpdateProgram(programCode, flag);
	}

	@Override
	public UpdateProgramResponse postUpdateProgram(UpdateProgramRequest request) {
		return this.programCore.postUpdateProgram(request);
	}

}
