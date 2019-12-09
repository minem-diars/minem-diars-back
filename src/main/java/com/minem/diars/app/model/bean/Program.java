package com.minem.diars.app.model.bean;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Program {
	
	private Integer programCode;
	private String miningEntity;
	private Integer viaticFlag;
	private Integer state;

}
