package com.minem.diars.app.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.minem.diars.app.core.ChronogramCore;
import com.minem.diars.app.model.api.request.ChronogramRegisterRequest;
import com.minem.diars.app.model.api.response.CheckChronogramResponse;
import com.minem.diars.app.model.api.response.ChronogramRegisterResponse;
import com.minem.diars.app.model.common.ChronogramModel;
import com.minem.diars.app.service.ChronogramService;
import com.minem.diars.app.util.constants.ChronogramConstants;
import com.minem.diars.app.util.constants.ErrorConstant;
import com.minem.diars.app.util.constants.MinemConstants;

@Service(ChronogramConstants.SERVICE)
public class ChronogramServiceImpl implements ChronogramService {
	
	@Autowired
	@Qualifier(ChronogramConstants.CORE)
	private ChronogramCore chronogramCore;

	@Override
	public ChronogramRegisterResponse registerChronogram(ChronogramRegisterRequest request) {
		
		if (!request.isNull()) {
			ChronogramModel model = chronogramCore.saveChronogram(request);
			return validateRegisterStatus(model);
		}else {
			ChronogramRegisterResponse response = new ChronogramRegisterResponse();
			response.setStatus(MinemConstants.RESPONSE_KO);
			response.setErrorCode(ErrorConstant.DATA_IS_MISSING_CODE);
			response.setErrorMessage(ChronogramConstants.DATA_IS_MISSING);
			
			return response;
		}
		
		
	}
	
	private ChronogramRegisterResponse validateRegisterStatus(ChronogramModel model) {
		ChronogramRegisterResponse response = null;
		if (MinemConstants.RESPONSE_OK.equals(model.getStatus())) {
			
			response = new ChronogramRegisterResponse();
			response.setMessage(ChronogramConstants.CHRONOGRAM_REGISTERED);
			response.setStatus(MinemConstants.RESPONSE_OK);
			
			return response;
		} else{
			
			response = new ChronogramRegisterResponse();
			response.setStatus(MinemConstants.RESPONSE_KO);
			response.setErrorCode(ErrorConstant.CHRONOGRAM_UNREGISTERED_CODE);
			response.setErrorMessage(ChronogramConstants.CHRONOGRAM_UNREGISTERED);
			
			return response;
		}
	}

	@Override
	public CheckChronogramResponse checkChronogram(String employeeCode) {
		CheckChronogramResponse response =  new CheckChronogramResponse();
		if (employeeCode == null || employeeCode.equals("")) {
			//error
			return response;
		}else {
			return chronogramCore.findChronograms(employeeCode);
		}
	}
	
}
