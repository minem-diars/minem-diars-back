package com.minem.diars.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minem.diars.app.model.api.request.LoginRequest;
import com.minem.diars.app.model.api.response.LoginResponse;
import com.minem.diars.app.service.LoginService;
import com.minem.diars.app.util.constants.LoginConstants;

@RestController
@RequestMapping("/travel/login/v1/")
@CrossOrigin("http://localhost:4200")
public class LoginController {
	
	@Autowired
	@Qualifier(LoginConstants.SERVICE)
	private LoginService loginService;
	
	@PostMapping("access")
	public LoginResponse postAccess(@RequestBody LoginRequest request) {
		return this.loginService.accessValidate(request);
	}

}
