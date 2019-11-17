package com.minem.diars.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.minem.diars.app.model.entity.ChronogramDetailEntity;
import com.minem.diars.app.util.constants.MinemConstants;

@Repository(MinemConstants.CHRONOGRAM_DETAIL_REPOSITORY)
public interface ChronogramDetailRepository extends JpaRepository<ChronogramDetailEntity, Integer> {

}
