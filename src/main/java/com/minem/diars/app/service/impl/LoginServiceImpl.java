package com.minem.diars.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.minem.diars.app.core.LoginCore;
import com.minem.diars.app.model.api.request.CredentialInfoRequest;
import com.minem.diars.app.model.api.request.CredentialUpdateRequest;
import com.minem.diars.app.model.api.request.LoginRequest;
import com.minem.diars.app.model.api.response.CredentialInfoResponse;
import com.minem.diars.app.model.api.response.CredentialUpdateResponse;
import com.minem.diars.app.model.api.response.LoginResponse;
import com.minem.diars.app.model.common.LoginModel;
import com.minem.diars.app.service.LoginService;
import com.minem.diars.app.util.constants.ErrorConstant;
import com.minem.diars.app.util.constants.LoginConstants;
import com.minem.diars.app.util.constants.MinemConstants;

@Service(LoginConstants.SERVICE)
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	@Qualifier(LoginConstants.CORE)
	private LoginCore loginCore;

	@Override
	public LoginResponse accessValidate(LoginRequest request) {
		return (request.isNull()?validateUser(request):nullInformation());
	}

	private LoginResponse validateUser(LoginRequest request) {
		LoginModel model = loginCore.findCredentials(request);
		LoginResponse response = null;
		if(model != null) {
			
			response = new LoginResponse();
			response.setEmployeeCode(model.getEmployeeCode());
			response.setDescription(LoginConstants.VALID_INFORMATION.concat(model.getEmployeeFullName()));
			response.setEmployeeFullName(model.getEmployeeFullName());
			response.setUserRol(model.getEmployeeRol());
			response.setToken(model.getToken());
			response.setStatus(MinemConstants.RESPONSE_OK);
			
			return response;
		}else {
			
			response = new LoginResponse();
			response.setErrorCode(ErrorConstant.LOGIN_ERROR);
			response.setErrorMessage(LoginConstants.INVALID_INFORMATION);
			response.setStatus(MinemConstants.RESPONSE_KO);
			
			return response;
		}
	}

	private LoginResponse nullInformation() {
		LoginResponse response = new LoginResponse();
		
		response.setDescription(LoginConstants.NULL_INFORMATION);
		
		return response;
	}

	@Override
	public CredentialInfoResponse infoValidate(CredentialInfoRequest request) {
		return this.loginCore.infoValidate(request);
	}

	@Override
	public CredentialUpdateResponse passwordUpdate(CredentialUpdateRequest request) {
		return this.loginCore.passwordUpdate(request);
	}

}
