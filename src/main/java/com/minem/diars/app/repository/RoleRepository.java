package com.minem.diars.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.minem.diars.app.model.entity.RoleEntity;
import com.minem.diars.app.util.constants.MinemConstants;

@Repository(MinemConstants.ROLE_REPOSITORY)
public interface RoleRepository extends JpaRepository<RoleEntity, Integer>{

}
