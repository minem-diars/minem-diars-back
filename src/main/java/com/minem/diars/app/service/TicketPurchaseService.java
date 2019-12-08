package com.minem.diars.app.service;

import com.minem.diars.app.model.api.request.RegisterTicketPurchaseRequest;
import com.minem.diars.app.model.api.request.UpdateTicketPurchaseRequest;
import com.minem.diars.app.model.api.response.ConsultTicketPurchaseResponse;
import com.minem.diars.app.model.api.response.EvaluateTicketPurchaseResponse;
import com.minem.diars.app.model.api.response.RegisterTicketPurchaseResponse;
import com.minem.diars.app.model.api.response.UpdateTicketPurchaseResponse;

public interface TicketPurchaseService {
	
	RegisterTicketPurchaseResponse registerTicketPurchase(RegisterTicketPurchaseRequest request);

	EvaluateTicketPurchaseResponse evaluateTicketPurchase(Integer ticketCode);

	ConsultTicketPurchaseResponse consultTicketPurchase(Integer employee);

	UpdateTicketPurchaseResponse updateStateOfTicket(UpdateTicketPurchaseRequest request);

}
