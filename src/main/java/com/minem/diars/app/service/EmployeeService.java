package com.minem.diars.app.service;

import com.minem.diars.app.model.api.request.EmployeeRegisterRequest;
import com.minem.diars.app.model.api.response.EmployeeRegisterResponse;

public interface EmployeeService {

	EmployeeRegisterResponse registerEmployee(EmployeeRegisterRequest request);

}
