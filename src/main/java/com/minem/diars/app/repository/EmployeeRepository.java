package com.minem.diars.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.minem.diars.app.model.entity.EmployeeEntity;
import com.minem.diars.app.util.constants.MinemConstants;

@Repository(MinemConstants.EMPLOYEE_REPOSITORY)
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer>{
	
	EmployeeEntity findByEmail(String email);

}
