package com.minem.diars.app.model.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Chronogram {
	
	private String chronogramCode;
	private String miningEntity;
	private String initialDate;
	private String finalDate;

}
