package com.minem.diars.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.minem.diars.app.model.entity.FileEntity;
import com.minem.diars.app.util.constants.MinemConstants;

@Repository(MinemConstants.FILE_REPOSITORY)
public interface FileRepository extends JpaRepository<FileEntity, Integer>{

}
