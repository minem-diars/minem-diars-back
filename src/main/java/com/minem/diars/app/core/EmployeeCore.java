package com.minem.diars.app.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.minem.diars.app.model.api.request.EmployeeRegisterRequest;
import com.minem.diars.app.model.bean.CredentialInformation;
import com.minem.diars.app.model.bean.EmployeeInformation;
import com.minem.diars.app.model.common.EmployeeModel;
import com.minem.diars.app.model.entity.CredentialEntity;
import com.minem.diars.app.model.entity.EmployeeEntity;
import com.minem.diars.app.model.entity.RoleEntity;
import com.minem.diars.app.repository.CredentialRepository;
import com.minem.diars.app.repository.EmployeeRepository;
import com.minem.diars.app.repository.RoleRepository;
import com.minem.diars.app.util.constants.MinemConstants;

@Component(MinemConstants.EMPLOYEE_CORE)
public class EmployeeCore {
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	@Qualifier(MinemConstants.EMPLOYEE_REPOSITORY)
	private EmployeeRepository employeeRepository;
	
	@Autowired
	@Qualifier(MinemConstants.CREDENTIAL_REPOSITORY)
	private CredentialRepository credentialRepository;
	
	@Autowired
	@Qualifier(MinemConstants.ROLE_REPOSITORY)
	private RoleRepository roleRepository;
	
	public EmployeeModel employeeRegister(EmployeeRegisterRequest request) {
		
		EmployeeModel model = new EmployeeModel();
		
		try {
			
			buildFullName(request);
			
			EmployeeEntity employee = buildEmployeeEntity(request.getEmployee());
			
			CredentialEntity credential = buildCredentialEntity(request.getCredential());
			
			RoleEntity role = this.roleRepository.findById(request.getRole().getIdRole()).get();
			
			role.getCredentials().add(credential);
			
			credential.getRoles().add(role);
			
			credential.setEmployee(employee);
			
			employee.setCredential(credential);
			
			this.credentialRepository.save(credential);
			
			model.setStatus(MinemConstants.RESPONSE_OK);
			
			return model;
		} catch (Exception e) {
			model.setStatus(MinemConstants.RESPONSE_KO);
			return model;
		}
		
	}

	private CredentialEntity buildCredentialEntity(CredentialInformation credential) {
		CredentialEntity credentialEnt = new CredentialEntity();
		credentialEnt.setUsername(credential.getUsername());
		credentialEnt.setPassword(encoder.encode(credential.getPassword()));
		
		return credentialEnt;
	}
	
	private void buildFullName(EmployeeRegisterRequest request) {
		request.getEmployee().setFullname(request.getEmployee().getName().toUpperCase().concat(" ")
				.concat(request.getEmployee().getLastname().toUpperCase()));
	}

	private EmployeeEntity buildEmployeeEntity(EmployeeInformation employee) {
		EmployeeEntity employeeEnt = new EmployeeEntity();
		employeeEnt.setDni(employee.getDni());
		employeeEnt.setFullname(employee.getFullname());
		employeeEnt.setAddress(employee.getAddress());
		employeeEnt.setEmail(employee.getEmail());
		employeeEnt.setLastname(employee.getLastname().toUpperCase());
		employeeEnt.setName(employee.getName().toUpperCase());
		employeeEnt.setPhone(employee.getPhone());
		return employeeEnt;
	}

}
