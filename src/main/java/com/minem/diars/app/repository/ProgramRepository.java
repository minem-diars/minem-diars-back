package com.minem.diars.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.minem.diars.app.model.entity.ProgramEntity;
import com.minem.diars.app.util.constants.MinemConstants;

@Repository(MinemConstants.PROGRAM_REPOSITORY)
public interface ProgramRepository extends JpaRepository<ProgramEntity, Integer> {

}
