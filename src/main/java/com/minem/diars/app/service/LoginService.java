package com.minem.diars.app.service;

import com.minem.diars.app.model.api.request.LoginRequest;
import com.minem.diars.app.model.api.response.LoginResponse;

public interface LoginService {
	
	LoginResponse accessValidate(LoginRequest request);

}
