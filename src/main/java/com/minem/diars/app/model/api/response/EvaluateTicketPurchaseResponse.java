package com.minem.diars.app.model.api.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EvaluateTicketPurchaseResponse extends RestResponse {
	
	private String employeeName;
	private String miningName;
	private String airlineName;

}
