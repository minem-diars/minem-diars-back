package com.minem.diars.app.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.minem.diars.app.model.api.request.LoginRequest;
import com.minem.diars.app.model.common.LoginModel;
import com.minem.diars.app.model.entity.CredentialEntity;
import com.minem.diars.app.model.entity.RoleEntity;
import com.minem.diars.app.repository.CredentialRepository;
import com.minem.diars.app.util.constants.LoginConstants;
import com.minem.diars.app.util.constants.MinemConstants;

@Component(LoginConstants.CORE)
public class LoginCore {
	
	@Autowired
	@Qualifier(MinemConstants.CREDENTIAL_REPOSITORY)
	private CredentialRepository credentialRepository;
	
	public LoginModel findCredentials(LoginRequest request) {
		
		CredentialEntity entity = credentialRepository.findByUsername(request.getUsername());
		
		if(entity!=null) {
			
			return validatePassword(entity, request);
		}
		return null;
		
	}

	private LoginModel validatePassword(CredentialEntity entity, LoginRequest request) {
		if(request.getPassword().equals(entity.getPassword())) {
			return buildResponse(entity);
			
		}
		return null;
	}

	private LoginModel buildResponse(CredentialEntity entity) {
		
		if(entity == null) {
			return null;
		}else {
			
			List<String> roles = obtainRoles(entity.getRoles());
			
			LoginModel response = new LoginModel();
			
			response.setEmployeeCode(entity.getEmployee().getIdEmployee().toString());
			response.setEmployeeFullName(entity.getEmployee().getFullname());
			response.setEmployeeRol(roles);
			
			return response;
		}
		
	}

	private List<String> obtainRoles(Set<RoleEntity> roles) {
		List<String> response = new ArrayList<String>();
		Iterator<RoleEntity> itr = roles.iterator();
		while (itr.hasNext()) {
			response.add(itr.next().getName());			
		}
		return response;
	}

}
