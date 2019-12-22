package com.minem.diars.app.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.minem.diars.app.model.api.request.CredentialInfoRequest;
import com.minem.diars.app.model.api.request.CredentialUpdateRequest;
import com.minem.diars.app.model.api.request.LoginRequest;
import com.minem.diars.app.model.api.response.CredentialInfoResponse;
import com.minem.diars.app.model.api.response.CredentialUpdateResponse;
import com.minem.diars.app.model.common.LoginModel;
import com.minem.diars.app.model.entity.CredentialEntity;
import com.minem.diars.app.model.entity.EmployeeEntity;
import com.minem.diars.app.repository.CredentialRepository;
import com.minem.diars.app.repository.EmployeeRepository;
import com.minem.diars.app.security.jwt.JwtProvider;
import com.minem.diars.app.util.constants.LoginConstants;
import com.minem.diars.app.util.constants.MinemConstants;

@Component(LoginConstants.CORE)
public class LoginCore {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	@Qualifier(MinemConstants.CREDENTIAL_REPOSITORY)
	private CredentialRepository credentialRepository;
	
	@Autowired
	@Qualifier(MinemConstants.EMPLOYEE_REPOSITORY)
	private EmployeeRepository employeeRepository;
	
	
	public LoginModel findCredentials(LoginRequest request) {
		
		Authentication authentication = this.authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = jwtProvider.generateJwtToken(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		
		CredentialEntity credential = this.credentialRepository.findByUsername(request.getUsername()).get();
		
		return this.buildResponse(jwt, userDetails, credential);
	}

	private LoginModel buildResponse(String jwt, UserDetails userDetails, CredentialEntity credential) {
		List<String> roles = obtainRoles(userDetails.getAuthorities());
		
		LoginModel response = new LoginModel();
		
		response.setEmployeeCode(credential.getEmployee().getIdEmployee().toString());
		response.setEmployeeUsername(userDetails.getUsername());
		response.setEmployeeFullName(credential.getEmployee().getFullname());
		response.setToken(jwt);
		response.setEmployeeRol(roles);
		
		return response;
	}

	private List<String> obtainRoles(Collection<? extends GrantedAuthority> collection) {
		List<String> response = new ArrayList<String>();
		Iterator<? extends GrantedAuthority> itr = collection.iterator();
		while (itr.hasNext()) {
			response.add(itr.next().getAuthority());			
		}
		return response;
	}

	public CredentialInfoResponse infoValidate(CredentialInfoRequest request) {
		CredentialInfoResponse response = new CredentialInfoResponse();
		EmployeeEntity employeeEnt = this.employeeRepository.findByEmail(request.getEmail());
		if (employeeEnt != null) {
			if (employeeEnt.getCredential().getUsername().equals(request.getUsername())) {
				response.setStatus(MinemConstants.RESPONSE_OK);
				response.setEmployeeCode(employeeEnt.getIdEmployee());
				return response;
			} else {
				response.setStatus(MinemConstants.RESPONSE_KO);
				response.setErrorCode("LE-0003");
				response.setErrorMessage("El usuario especificado es incorrecto.");
				return response;
			}
		} else {
			response.setStatus(MinemConstants.RESPONSE_KO);
			response.setErrorCode("LE-0002");
			response.setErrorMessage("No existe usuario afiliado al correo especificado.");
			return response;
		}
	}

	public CredentialUpdateResponse passwordUpdate(CredentialUpdateRequest request) {
		CredentialUpdateResponse response = new CredentialUpdateResponse();
		try {
			EmployeeEntity employeeEnt = this.employeeRepository.findById(request.getEmployeeCode()).get();
			CredentialEntity credentialEnt = employeeEnt.getCredential();
			credentialEnt.setPassword(this.encoder.encode(request.getPassword()));
			this.credentialRepository.save(credentialEnt);
			response.setStatus(MinemConstants.RESPONSE_OK);
			response.setMessage("Se modifico la contraseña correctamente.");
			return response;
		} catch (Exception e) {
			response.setStatus(MinemConstants.RESPONSE_KO);
			response.setErrorCode("LE-0004");
			response.setErrorMessage("Falló actualización de contraseña, intentelo nuevamente.");
			return response;
		}
	}

}
