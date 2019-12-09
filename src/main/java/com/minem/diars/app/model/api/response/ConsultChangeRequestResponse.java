package com.minem.diars.app.model.api.response;

import java.util.List;

import com.minem.diars.app.model.bean.Program;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ConsultChangeRequestResponse extends RestResponse {
	
	private String employeeFullName;
	private List<Program> programs;

}
