package com.minem.diars.app.model.api.response;

import java.util.List;

import com.minem.diars.app.model.bean.TicketPurchase;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ConsultTicketPurchaseResponse {
	
	private String employeeName;
	private List<TicketPurchase> tickets;

}
