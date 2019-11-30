package com.minem.diars.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minem.diars.app.model.api.request.RegisterTicketPurchaseRequest;
import com.minem.diars.app.model.api.response.RegisterTicketPurchaseResponse;
import com.minem.diars.app.service.TicketPurchaseService;
import com.minem.diars.app.util.constants.MinemConstants;

@RestController
@RequestMapping("/travel/ticket/purchase/v1/")
@CrossOrigin("http://localhost:4200")
public class TicketPurchaseController {

	@Autowired
	@Qualifier(MinemConstants.TICKET_PURCHASE_SERVICE)
	private TicketPurchaseService ticketPurchaseService;

	@PostMapping("register")
	public RegisterTicketPurchaseResponse registerTicketPurchase(@RequestBody RegisterTicketPurchaseRequest request) {
		return this.ticketPurchaseService.registerTicketPurchase(request);
	}

}
