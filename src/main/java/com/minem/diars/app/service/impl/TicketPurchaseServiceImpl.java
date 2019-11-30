package com.minem.diars.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.minem.diars.app.core.TicketPurchaseCore;
import com.minem.diars.app.model.api.request.RegisterTicketPurchaseRequest;
import com.minem.diars.app.model.api.response.RegisterTicketPurchaseResponse;
import com.minem.diars.app.service.TicketPurchaseService;
import com.minem.diars.app.util.constants.MinemConstants;

@Repository(MinemConstants.TICKET_PURCHASE_SERVICE)
public class TicketPurchaseServiceImpl implements TicketPurchaseService {
	
	@Autowired
	@Qualifier(MinemConstants.TICKET_PURCHASE_CORE)
	private TicketPurchaseCore ticketPurchaseCore;

	@Override
	public RegisterTicketPurchaseResponse registerTicketPurchase(RegisterTicketPurchaseRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
