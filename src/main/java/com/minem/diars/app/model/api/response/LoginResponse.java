package com.minem.diars.app.model.api.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginResponse extends RestResponse {
	
	private String description;
	private String employeeCode;
	private String employeeFullName;
	private String type = "Bearer";
	private String token;
	private List<String> userRol;

}
