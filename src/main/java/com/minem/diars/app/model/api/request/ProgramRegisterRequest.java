package com.minem.diars.app.model.api.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProgramRegisterRequest {
	
	private Integer chronogramCode;
	private Integer viaticFlag;
	private String lodgingCost;
	private String transportCost;
	
	public boolean isNull() {
		return ((this.chronogramCode == null && this.viaticFlag == null && this.lodgingCost == null && this.transportCost == null)||
				(this.chronogramCode == null || this.viaticFlag == null || this.lodgingCost == null || this.transportCost == null)) ? true : false;
	}
	
}
