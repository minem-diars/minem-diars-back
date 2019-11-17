package com.minem.diars.app.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.minem.diars.app.model.api.request.LoginRequest;
import com.minem.diars.app.model.common.LoginModel;
import com.minem.diars.app.model.entity.CredentialEntity;
import com.minem.diars.app.repository.CredentialRepository;
import com.minem.diars.app.util.constants.LoginConstants;
import com.minem.diars.app.util.constants.MinemConstants;

@Component(LoginConstants.CORE)
public class LoginCore {
	
	@Autowired
	@Qualifier(MinemConstants.CREDENTIAL_REPOSITORY)
	private CredentialRepository credentialRepository;
	
	public LoginModel findCredentials(LoginRequest request) {
		
		CredentialEntity entity = credentialRepository.findByUsername(request.getUsername());
		
		return buildResponse(entity);
	}

	private LoginModel buildResponse(CredentialEntity entity) {
		
		if(entity == null) {
			return null;
		}else {
			LoginModel response = new LoginModel();
			
			response.setEmployeeCode(entity.getEmployee().getIdEmployee().toString());
			response.setEmployeeName(entity.getEmployee().getName());
			
			return response;
		}
		
	}

}
