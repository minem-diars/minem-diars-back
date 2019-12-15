package com.minem.diars.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minem.diars.app.model.api.request.RegisterTicketPurchaseRequest;
import com.minem.diars.app.model.api.request.UpdateTicketPurchaseRequest;
import com.minem.diars.app.model.api.response.ConsultTicketPurchaseResponse;
import com.minem.diars.app.model.api.response.EvaluateTicketPurchaseResponse;
import com.minem.diars.app.model.api.response.RegisterTicketPurchaseResponse;
import com.minem.diars.app.model.api.response.UpdateTicketPurchaseResponse;
import com.minem.diars.app.service.TicketPurchaseService;
import com.minem.diars.app.util.constants.MinemConstants;

@RestController
@RequestMapping("/travel/ticket/purchase/v1/")
@CrossOrigin(origins = {"http://localhost:4200", "https://minem-diars.github.io"})
public class TicketPurchaseController {

	@Autowired
	@Qualifier(MinemConstants.TICKET_PURCHASE_SERVICE)
	private TicketPurchaseService ticketPurchaseService;

	@PostMapping("register")
	public RegisterTicketPurchaseResponse registerTicketPurchase(@RequestBody RegisterTicketPurchaseRequest request) {
		return this.ticketPurchaseService.registerTicketPurchase(request);
	}
	
	@GetMapping("consult/{employee}")
	public ConsultTicketPurchaseResponse consultTicketPurchase(@PathVariable("employee") Integer employee) {
		return this.ticketPurchaseService.consultTicketPurchase(employee);
	}
	
	@GetMapping("evaluate/{ticketCode}")
	public EvaluateTicketPurchaseResponse evaluateTicketPurchase(@PathVariable("ticketCode") Integer ticketCode) {
		return this.ticketPurchaseService.evaluateTicketPurchase(ticketCode);
	}
	
	@PostMapping("update/state")
	public UpdateTicketPurchaseResponse updateStateOfTicket(@RequestBody UpdateTicketPurchaseRequest request) {
		return this.ticketPurchaseService.updateStateOfTicket(request);
	}

}
