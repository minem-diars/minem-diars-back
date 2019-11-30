package com.minem.diars.app.model.api.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RegisterTicketPurchaseRequest {
	
	private Integer idProgram;
	private String goingDate;
	private String comebackDate;
	private Integer airline;

}
