package com.minem.diars.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.minem.diars.app.model.entity.MiningEntity;
import com.minem.diars.app.util.constants.MinemConstants;

@Repository(MinemConstants.MINING_REPOSITORY)
public interface MiningRepository extends JpaRepository<MiningEntity, Integer> {

}
