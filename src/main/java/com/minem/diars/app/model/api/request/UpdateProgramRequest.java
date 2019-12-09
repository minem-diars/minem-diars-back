package com.minem.diars.app.model.api.request;

import lombok.ToString;

import lombok.Setter;

import lombok.Getter;

@Getter
@Setter
@ToString
public class UpdateProgramRequest {
	
	private Integer programCode;
	private String initialDate;
	private String finalDate;
	private Integer viaticFlag;
	private String lodgingCost;
	private String transportCost;

}
