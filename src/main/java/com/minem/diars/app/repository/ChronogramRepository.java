package com.minem.diars.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.minem.diars.app.model.entity.ChronogramEntity;
import com.minem.diars.app.util.constants.MinemConstants;

@Repository(MinemConstants.CHRONOGRAM_REPOSITORY)
public interface ChronogramRepository extends JpaRepository<ChronogramEntity, Integer> {

}
