package com.minem.diars.app.util.commons;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.minem.diars.app.security.service.UserPrinciple;

public class SecurityAuditorAware implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication == null || !authentication.isAuthenticated()) {
			return Optional.empty();
		}
		
		return Optional.of(((UserPrinciple) authentication.getPrincipal()).getName());
	}

}
