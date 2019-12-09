package com.minem.diars.app.model.api.response;

import java.util.List;

import com.minem.diars.app.model.bean.AttachedFile;

import lombok.Getter;
import lombok.ToString;
import lombok.Setter;

@Getter
@Setter
@ToString
public class ConsultAttachedFileResponse extends RestResponse {
	
	private String employeeFullName;
	private String miningName;
	private List<AttachedFile> files;

}
