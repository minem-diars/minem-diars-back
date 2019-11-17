package com.minem.diars.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.minem.diars.app.model.entity.CredentialEntity;
import com.minem.diars.app.util.constants.MinemConstants;

@Repository(MinemConstants.CREDENTIAL_REPOSITORY)
public interface CredentialRepository extends JpaRepository<CredentialEntity, Integer>{
	
	CredentialEntity findByUsername(String username);

}
