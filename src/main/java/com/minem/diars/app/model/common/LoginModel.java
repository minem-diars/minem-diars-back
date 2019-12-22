package com.minem.diars.app.model.common;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginModel {
	
	private String employeeCode;
	private String employeeUsername;
	private String employeeFullName;
	private String token;
	private List<String> employeeRol;

}
