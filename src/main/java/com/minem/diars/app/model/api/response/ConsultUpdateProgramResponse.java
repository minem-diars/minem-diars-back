package com.minem.diars.app.model.api.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ConsultUpdateProgramResponse extends RestResponse {
	
	private String miningName;
	private String employeeName;
	private String initialDate;
	private String finalDate;
	private Integer viaticFlag;
	private String lodgingCost;
	private String transportCost;
	private String observations;

}
