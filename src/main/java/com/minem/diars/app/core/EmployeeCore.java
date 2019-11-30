package com.minem.diars.app.core;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.minem.diars.app.model.api.request.EmployeeRegisterRequest;
import com.minem.diars.app.model.api.response.EmployeeRegisterResponse;
import com.minem.diars.app.model.bean.CredentialInformation;
import com.minem.diars.app.model.bean.EmployeeInformation;
import com.minem.diars.app.model.entity.ChronogramEntity;
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
	@Qualifier(MinemConstants.EMPLOYEE_REPOSITORY)
	private EmployeeRepository employeeRepository;
	
	@Autowired
	@Qualifier(MinemConstants.CREDENTIAL_REPOSITORY)
	private CredentialRepository credentialRepository;
	
	@Autowired
	@Qualifier(MinemConstants.ROLE_REPOSITORY)
	private RoleRepository roleRepository;
	
	public EmployeeRegisterResponse employeeRegister(EmployeeRegisterRequest request) {
		
		EmployeeEntity employee = buildEmployeeEntity(request.getEmployee());
		
		CredentialEntity credential = buildCredentialEntity(request.getCredential());
		
		RoleEntity role = this.roleRepository.findById(request.getRole().getIdRole()).get();
		
		role.getCredentials().add(credential);
		
		credential.getRoles().add(role);
		
		credential.setEmployee(employee);
		
		employee.setCredential(credential);
		
		this.credentialRepository.save(credential);
		
		return null;
	}

	private CredentialEntity buildCredentialEntity(CredentialInformation credential) {
		// TODO Auto-generated method stub
		CredentialEntity credentialEnt = new CredentialEntity();
		credentialEnt.setUsername(credential.getUsername());
		credentialEnt.setPassword(credential.getPassword());
		
		return credentialEnt;
	}

	private EmployeeEntity buildEmployeeEntity(EmployeeInformation employee) {
		// TODO Auto-generated method stub
		EmployeeEntity employeeEnt = new EmployeeEntity();
		employeeEnt.setDni(employee.getDni());
		employeeEnt.setFullname(employee.getFullname());
		employeeEnt.setAddress(employee.getAddress());
		employeeEnt.setEmail(employee.getEmail());
		employeeEnt.setLastname(employee.getLastname());
		employeeEnt.setName(employee.getName());
		employeeEnt.setPhone(employee.getPhone());
		return employeeEnt;
	}

}
