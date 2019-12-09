package com.minem.diars.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.minem.diars.app.core.ChangeRequestCore;
import com.minem.diars.app.model.api.request.ChangeRequestRequest;
import com.minem.diars.app.model.api.response.ChangeRequestResponse;
import com.minem.diars.app.model.api.response.ConsultChangeRequestResponse;
import com.minem.diars.app.service.ChangeRequestService;
import com.minem.diars.app.util.constants.MinemConstants;

@Service(MinemConstants.CHANGE_REQUEST_SERVICE)
public class ChangeRequestServiceImpl implements ChangeRequestService {
	
	@Autowired
	@Qualifier(MinemConstants.CHANGE_REQUEST_CORE)
	private ChangeRequestCore changeRequestCore;

	@Override
	public ChangeRequestResponse registerChangeRequest(ChangeRequestRequest request) {
		return this.changeRequestCore.registerChangeRequest(request);
	}

	@Override
	public ConsultChangeRequestResponse getRequestsChange(Integer empCode) {
		return this.changeRequestCore.getRequestsChange(empCode);
	}

}
