package com.minem.diars.app.model.api.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateTicketPurchaseRequest {
	
	private Integer ticketCode;
	private Integer newState;

}
