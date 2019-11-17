package com.minem.diars.app.model.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChronogramInformation {
	
	private String nameService;
	private String initialDate;
	private String finalDate;
	private Integer days;
	private String miningCode;

}
