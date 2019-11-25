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
	private String employeeFullName;
	private List<String> employeeRol;

}
