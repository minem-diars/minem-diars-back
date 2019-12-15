package com.minem.diars.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minem.diars.app.model.api.request.EmployeeRegisterRequest;
import com.minem.diars.app.model.api.response.EmployeeRegisterResponse;
import com.minem.diars.app.service.EmployeeService;
import com.minem.diars.app.util.constants.MinemConstants;

@RestController
@RequestMapping("/travel/employee/v1/")
@CrossOrigin(origins = {"http://localhost:4200", "https://minem-diars.github.io"})
public class EmployeeController {

	@Autowired
	@Qualifier(MinemConstants.EMPLOYEE_SERVICE)
	private EmployeeService employeeService;
	
	@PostMapping("register")
	public EmployeeRegisterResponse registerEmployee(@RequestBody EmployeeRegisterRequest request) {
		
		return this.employeeService.registerEmployee(request);
		
	}
	
	
	
	
}
