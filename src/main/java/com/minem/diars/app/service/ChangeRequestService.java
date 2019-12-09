package com.minem.diars.app.service;

import com.minem.diars.app.model.api.request.ChangeRequestRequest;
import com.minem.diars.app.model.api.response.ChangeRequestResponse;
import com.minem.diars.app.model.api.response.ConsultChangeRequestResponse;

public interface ChangeRequestService {

	ChangeRequestResponse registerChangeRequest(ChangeRequestRequest request);

	ConsultChangeRequestResponse getRequestsChange(Integer empCode);

}
