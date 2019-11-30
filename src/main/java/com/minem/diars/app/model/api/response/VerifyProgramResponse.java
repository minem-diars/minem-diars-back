package com.minem.diars.app.model.api.response;

import java.util.List;

import com.minem.diars.app.model.bean.ActivityResponse;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VerifyProgramResponse extends RestResponse {
	
	private String message;
	private String employeeName;
	private String miningName;
	private List<ActivityResponse> activities;
	private Integer state;
	
}
