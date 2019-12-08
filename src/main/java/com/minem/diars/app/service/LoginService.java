package com.minem.diars.app.service;

import com.minem.diars.app.model.api.request.CredentialInfoRequest;
import com.minem.diars.app.model.api.request.CredentialUpdateRequest;
import com.minem.diars.app.model.api.request.LoginRequest;
import com.minem.diars.app.model.api.response.CredentialInfoResponse;
import com.minem.diars.app.model.api.response.CredentialUpdateResponse;
import com.minem.diars.app.model.api.response.LoginResponse;

public interface LoginService {
	
	LoginResponse accessValidate(LoginRequest request);

	CredentialInfoResponse infoValidate(CredentialInfoRequest request);

	CredentialUpdateResponse passwordUpdate(CredentialUpdateRequest request);

}
