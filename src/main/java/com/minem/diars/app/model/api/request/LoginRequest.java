package com.minem.diars.app.model.api.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginRequest {
	
	private String username;
	private String password;
	
	public boolean isNull() {
		return (this.username == null && this.password == null)?false:true;
	}

}
