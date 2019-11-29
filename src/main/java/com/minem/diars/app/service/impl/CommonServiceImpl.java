package com.minem.diars.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.minem.diars.app.core.CommonCore;
import com.minem.diars.app.model.api.response.FindAirlineResponse;
import com.minem.diars.app.model.api.response.FindMiningResponse;
import com.minem.diars.app.model.common.CommonModel;
import com.minem.diars.app.service.CommonService;
import com.minem.diars.app.util.constants.MinemConstants;

@Service(MinemConstants.COMMON_SERVICE)
public class CommonServiceImpl implements CommonService {
	
	@Autowired
	@Qualifier(MinemConstants.COMMON_CORE)
	private CommonCore commonCore;

	@Override
	public FindMiningResponse findMinings() {
		CommonModel model = commonCore.listMinings();
		FindMiningResponse response = null;
		if (MinemConstants.RESPONSE_OK.equals(model.getStatus())) {
			response = new FindMiningResponse();
			response.setStatus(model.getStatus());
			response.setMinings(model.getMinings());
			return response;
		} else {
			response = new FindMiningResponse();
			response.setStatus(model.getStatus());
			response.setErrorCode("");
			response.setErrorMessage("Error al obtener lista de mineras.");
			return response;
		}
	}

	@Override
	public FindAirlineResponse findAirlines() {
		CommonModel model = commonCore.listAirlines();
		FindAirlineResponse response = null;
		if (MinemConstants.RESPONSE_OK.equals(model.getStatus())) {
			response = new FindAirlineResponse();
			response.setStatus(model.getStatus());
			response.setAirlines(model.getAirlines());
			return response;
		} else {
			response = new FindAirlineResponse();
			response.setStatus(model.getStatus());
			response.setErrorCode("");
			response.setErrorMessage("Error al obtener lista de aerolineas.");
			return response;
		}
	}

}
