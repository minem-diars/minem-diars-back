package com.minem.diars.app.model.api.response;

import java.util.List;

import com.minem.diars.app.model.bean.Program;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CheckProgramResponse extends RestResponse {
	
	private String employeeName;
	private List<Program> programs;

}
