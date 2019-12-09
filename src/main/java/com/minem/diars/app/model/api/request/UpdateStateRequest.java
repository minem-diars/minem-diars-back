package com.minem.diars.app.model.api.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateStateRequest {
	
	private Integer programCode;
	private String role;
	private Integer idUserDlog;
	private Integer state;
	private Integer derv_dg;
	private Integer derv_ol;
	private Integer state_dl;

}
