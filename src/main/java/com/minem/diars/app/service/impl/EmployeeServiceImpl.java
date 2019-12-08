package com.minem.diars.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.minem.diars.app.core.EmployeeCore;
import com.minem.diars.app.model.api.request.EmployeeRegisterRequest;
import com.minem.diars.app.model.api.response.EmployeeRegisterResponse;
import com.minem.diars.app.model.common.EmployeeModel;
import com.minem.diars.app.service.EmployeeService;
import com.minem.diars.app.util.constants.MinemConstants;

@Repository(MinemConstants.EMPLOYEE_SERVICE)
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	@Qualifier(MinemConstants.EMPLOYEE_CORE)
	private  EmployeeCore employeeCore;
	

	@Override
	public EmployeeRegisterResponse registerEmployee(EmployeeRegisterRequest request) {
		
		EmployeeRegisterResponse response = new EmployeeRegisterResponse();
		EmployeeModel model = this.employeeCore.employeeRegister(request);
		
		if (MinemConstants.RESPONSE_OK.equals(model.getStatus())) {
			response.setStatus(model.getStatus());
			response.setMessage("Empleado registrado correctamente.");
			return response;
		} else {
			response.setStatus(model.getStatus());
			response.setErrorCode("ER-0001");
			response.setErrorMessage("Ocurrio un error al registrar el empleado.");
			return response;
		}
		
	}

}
