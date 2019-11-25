package com.minem.diars.app.service;

import com.minem.diars.app.model.api.request.ChronogramRegisterRequest;
import com.minem.diars.app.model.api.response.CheckChronogramResponse;
import com.minem.diars.app.model.api.response.ChronogramRegisterResponse;
import com.minem.diars.app.model.api.response.FindChronogramResponse;

public interface ChronogramService {
	
	ChronogramRegisterResponse registerChronogram(ChronogramRegisterRequest request);	
	
	CheckChronogramResponse checkChronogram(String employeeCode);

	FindChronogramResponse findChronogram(String chronogramCode);

}
