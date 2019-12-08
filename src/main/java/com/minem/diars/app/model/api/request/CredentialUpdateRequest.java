package com.minem.diars.app.model.api.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CredentialUpdateRequest {
	
	private Integer employeeCode;
	private String password;

}
