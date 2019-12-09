package com.minem.diars.app.model.api.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChangeRequestRequest {
	
	private Integer programCode;
	private String observations;

}
