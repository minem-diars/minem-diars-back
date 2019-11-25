package com.minem.diars.app.model.api.response;

import com.minem.diars.app.model.bean.Chronogram;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FindChronogramResponse extends RestResponse {
	
	private String employeeName;
	private Chronogram chronogram;

}
