package com.minem.diars.app.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.minem.diars.app.model.api.request.CredentialInfoRequest;
import com.minem.diars.app.model.api.request.CredentialUpdateRequest;
import com.minem.diars.app.model.api.request.LoginRequest;
import com.minem.diars.app.model.api.response.CredentialInfoResponse;
import com.minem.diars.app.model.api.response.CredentialUpdateResponse;
import com.minem.diars.app.model.common.LoginModel;
import com.minem.diars.app.model.entity.CredentialEntity;
import com.minem.diars.app.model.entity.EmployeeEntity;
import com.minem.diars.app.model.entity.RoleEntity;
import com.minem.diars.app.repository.CredentialRepository;
import com.minem.diars.app.repository.EmployeeRepository;
import com.minem.diars.app.util.constants.LoginConstants;
import com.minem.diars.app.util.constants.MinemConstants;

@Component(LoginConstants.CORE)
public class LoginCore {
	
	@Autowired
	@Qualifier(MinemConstants.CREDENTIAL_REPOSITORY)
	private CredentialRepository credentialRepository;
	
	@Autowired
	@Qualifier(MinemConstants.EMPLOYEE_REPOSITORY)
	private EmployeeRepository employeeRepository;
	
	public LoginModel findCredentials(LoginRequest request) {
		
		CredentialEntity entity = credentialRepository.findByUsername(request.getUsername());
		
		if(entity!=null) {
			
			return validatePassword(entity, request);
		}
		return null;
		
	}

	private LoginModel validatePassword(CredentialEntity entity, LoginRequest request) {
		if(request.getPassword().equals(entity.getPassword())) {
			return buildResponse(entity);
			
		}
		return null;
	}

	private LoginModel buildResponse(CredentialEntity entity) {
		
		if(entity == null) {
			return null;
		}else {
			
			List<String> roles = obtainRoles(entity.getRoles());
			
			LoginModel response = new LoginModel();
			
			response.setEmployeeCode(entity.getEmployee().getIdEmployee().toString());
			response.setEmployeeFullName(entity.getEmployee().getFullname());
			response.setEmployeeRol(roles);
			
			return response;
		}
		
	}

	private List<String> obtainRoles(Set<RoleEntity> roles) {
		List<String> response = new ArrayList<String>();
		Iterator<RoleEntity> itr = roles.iterator();
		while (itr.hasNext()) {
			response.add(itr.next().getName());			
		}
		return response;
	}

	public CredentialInfoResponse infoValidate(CredentialInfoRequest request) {
		CredentialInfoResponse response = new CredentialInfoResponse();
		EmployeeEntity employeeEnt = this.employeeRepository.findByEmail(request.getEmail());
		if (employeeEnt != null) {
			if (employeeEnt.getCredential().getUsername().equals(request.getUsername())) {
				response.setStatus(MinemConstants.RESPONSE_OK);
				response.setEmployeeCode(employeeEnt.getIdEmployee());
				return response;
			} else {
				response.setStatus(MinemConstants.RESPONSE_KO);
				response.setErrorCode("LE-0003");
				response.setErrorMessage("El usuario especificado es incorrecto.");
				return response;
			}
		} else {
			response.setStatus(MinemConstants.RESPONSE_KO);
			response.setErrorCode("LE-0002");
			response.setErrorMessage("No existe usuario afiliado al correo especificado.");
			return response;
		}
	}

	public CredentialUpdateResponse passwordUpdate(CredentialUpdateRequest request) {
		CredentialUpdateResponse response = new CredentialUpdateResponse();
		try {
			EmployeeEntity employeeEnt = this.employeeRepository.findById(request.getEmployeeCode()).get();
			CredentialEntity credentialEnt = employeeEnt.getCredential();
			credentialEnt.setPassword(request.getPassword());
			this.credentialRepository.save(credentialEnt);
			response.setStatus(MinemConstants.RESPONSE_OK);
			response.setMessage("Se modifico la contraseña correctamente.");
			return response;
		} catch (Exception e) {
			response.setStatus(MinemConstants.RESPONSE_KO);
			response.setErrorCode("LE-0004");
			response.setErrorMessage("Falló actualización de contraseña, intentelo nuevamente.");
			return response;
		}
	}

}
