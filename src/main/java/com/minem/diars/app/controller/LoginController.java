package com.minem.diars.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minem.diars.app.model.api.request.CredentialInfoRequest;
import com.minem.diars.app.model.api.request.CredentialUpdateRequest;
import com.minem.diars.app.model.api.response.CredentialInfoResponse;
import com.minem.diars.app.model.api.response.CredentialUpdateResponse;
import com.minem.diars.app.service.LoginService;
import com.minem.diars.app.util.constants.LoginConstants;
import com.minem.diars.app.util.constants.MinemConstants;

@RestController
@RequestMapping("/travel/login/v1/")
@CrossOrigin(origins = {
		MinemConstants.BASE_URL_DESA, 
		MinemConstants.BASE_URL_HOST
		})
public class LoginController {
	
	@Autowired
	@Qualifier(LoginConstants.SERVICE)
	private LoginService loginService;
	
	@PostMapping("validateInfo")
	public CredentialInfoResponse postValidateInfo(@RequestBody CredentialInfoRequest request) {
		return this.loginService.infoValidate(request);
	}
	
	@PostMapping("updatePassword")
	public CredentialUpdateResponse postUpdatePassword(@RequestBody CredentialUpdateRequest request) {
		return this.loginService.passwordUpdate(request);
	}

}
