package com.minem.diars.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.minem.diars.app.core.EmployeeCore;
import com.minem.diars.app.model.api.request.EmployeeRegisterRequest;
import com.minem.diars.app.model.api.response.EmployeeRegisterResponse;
import com.minem.diars.app.service.EmployeeService;
import com.minem.diars.app.util.constants.MinemConstants;

@Repository(MinemConstants.EMPLOYEE_SERVICE)
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	@Qualifier(MinemConstants.EMPLOYEE_CORE)
	private  EmployeeCore employeeCore;
	

	@Override
	public EmployeeRegisterResponse registerEmployee(EmployeeRegisterRequest request) {
		return this.employeeCore.employeeRegister(request);
	}

}
