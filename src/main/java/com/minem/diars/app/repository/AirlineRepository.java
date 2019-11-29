package com.minem.diars.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.minem.diars.app.model.entity.AirlineEntity;
import com.minem.diars.app.util.constants.MinemConstants;

@Repository(MinemConstants.AIRLINE_REPOSITORY)
public interface AirlineRepository extends JpaRepository<AirlineEntity, Integer>{

}
