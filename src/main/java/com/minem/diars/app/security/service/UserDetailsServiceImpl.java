package com.minem.diars.app.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.minem.diars.app.model.entity.CredentialEntity;
import com.minem.diars.app.repository.CredentialRepository;
import com.minem.diars.app.util.constants.MinemConstants;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	@Qualifier(MinemConstants.CREDENTIAL_REPOSITORY)
	private CredentialRepository credentialRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		CredentialEntity user = this.credentialRepository.findByUsername(username).orElseThrow(
				() -> new UsernameNotFoundException("User Not Found with -> username or email : " + username)
				);
		
		return UserPrinciple.build(user);
	}

}
