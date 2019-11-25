package com.minem.diars.app.model.api.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FindForEvaluateResponse extends RestResponse {
	
	private String employeeName;
	private String miningName;
	private Integer viatics;
	private Integer days;

}
