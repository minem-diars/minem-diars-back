package com.minem.diars.app.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.minem.diars.app.model.api.request.RegisterTicketPurchaseRequest;
import com.minem.diars.app.model.api.response.RegisterTicketPurchaseResponse;
import com.minem.diars.app.model.entity.AirlineEntity;
import com.minem.diars.app.model.entity.ProgramEntity;
import com.minem.diars.app.model.entity.TicketPurchaseEntity;
import com.minem.diars.app.repository.AirlineRepository;
import com.minem.diars.app.repository.ProgramRepository;
import com.minem.diars.app.repository.TicketPurchaseRepository;
import com.minem.diars.app.util.constants.MinemConstants;

@Component(MinemConstants.TICKET_PURCHASE_CORE)
public class TicketPurchaseCore {
	
	@Autowired
	@Qualifier(MinemConstants.PROGRAM_REPOSITORY)
	private ProgramRepository programRepository;
	
	@Autowired
	@Qualifier(MinemConstants.AIRLINE_REPOSITORY)
	private AirlineRepository airlineRepository;
	
	@Autowired
	@Qualifier(MinemConstants.TICKET_PURCHASE_REPOSITORY)
	private TicketPurchaseRepository ticketPurchaseRepository;
	
	public RegisterTicketPurchaseResponse registerTickerPurchase(RegisterTicketPurchaseRequest request) {
		
		ProgramEntity programEnt = this.programRepository.findById(request.getIdProgram()).get();
		
		TicketPurchaseEntity ticketEnt = buildTicketPurchaseEntity(request);
		
		AirlineEntity airlineEnt = this.airlineRepository.findById(request.getAirline()).get(); 
		
		ticketEnt.setAirline(airlineEnt);
		airlineEnt.getTickets().add(ticketEnt);
		
		ticketEnt.setProgram(programEnt);
		programEnt.setTicketPurchase(ticketEnt);
		
		this.ticketPurchaseRepository.save(ticketEnt);
		
		return null;
	}

	private TicketPurchaseEntity buildTicketPurchaseEntity(RegisterTicketPurchaseRequest request) {
		TicketPurchaseEntity ticketEnt = new TicketPurchaseEntity();
		ticketEnt.setGoingDate(request.getGoingDate());
		ticketEnt.setComebackDate(request.getComebackDate());
		return ticketEnt;
	}

}
