package com.minem.diars.app.model.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResponse {
	
	private String status;
	private String errorCode;
	private String errorMessage;

}
