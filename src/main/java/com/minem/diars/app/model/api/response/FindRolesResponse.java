package com.minem.diars.app.model.api.response;

import java.util.List;

import com.minem.diars.app.model.bean.Role;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FindRolesResponse extends RestResponse {
	
	private List<Role> roles;

}
