package com.minem.diars.app.service;

import com.minem.diars.app.model.api.request.RegisterTicketPurchaseRequest;
import com.minem.diars.app.model.api.response.RegisterTicketPurchaseResponse;

public interface TicketPurchaseService {
	
	RegisterTicketPurchaseResponse registerTicketPurchase(RegisterTicketPurchaseRequest request);

}
