package com.minem.diars.app.model.api.request;

import com.minem.diars.app.model.bean.CredentialInformation;
import com.minem.diars.app.model.bean.EmployeeInformation;
import com.minem.diars.app.model.bean.RoleInformation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmployeeRegisterRequest {
	
	private EmployeeInformation employee;
	private CredentialInformation credential;
	private RoleInformation role;

}
