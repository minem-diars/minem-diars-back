package com.minem.diars.app.model.api.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginResponse extends RestResponse {
	
	private String description;
	private String employeeCode;
	private String employeeName;
//	private String userRol;

}
