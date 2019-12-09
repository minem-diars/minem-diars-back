package com.minem.diars.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.minem.diars.app.model.entity.ChangeRequestEntity;
import com.minem.diars.app.util.constants.MinemConstants;

@Repository(MinemConstants.CHANGE_REQUEST_REPOSITORY)
public interface ChangeRequestRepository extends JpaRepository<ChangeRequestEntity, Integer> {

}
