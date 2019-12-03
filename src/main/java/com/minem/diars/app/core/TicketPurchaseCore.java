package com.minem.diars.app.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.minem.diars.app.model.api.request.RegisterTicketPurchaseRequest;
import com.minem.diars.app.model.api.response.ConsultTicketPurchaseResponse;
import com.minem.diars.app.model.api.response.EvaluateTicketPurchaseResponse;
import com.minem.diars.app.model.api.response.RegisterTicketPurchaseResponse;
import com.minem.diars.app.model.bean.TicketPurchase;
import com.minem.diars.app.model.entity.AirlineEntity;
import com.minem.diars.app.model.entity.ChronogramEntity;
import com.minem.diars.app.model.entity.EmployeeEntity;
import com.minem.diars.app.model.entity.ProgramEntity;
import com.minem.diars.app.model.entity.TicketPurchaseEntity;
import com.minem.diars.app.repository.AirlineRepository;
import com.minem.diars.app.repository.EmployeeRepository;
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
	
	@Autowired
	@Qualifier(MinemConstants.EMPLOYEE_REPOSITORY)
	private EmployeeRepository employeeRepository;
	
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
		ticketEnt.setState(1);
		return ticketEnt;
	}

	public ConsultTicketPurchaseResponse consultTicketPurchase(Integer employeeCode) {
		ConsultTicketPurchaseResponse response = null;
		EmployeeEntity employeeEnt = this.employeeRepository.findById(employeeCode).get();
		if (employeeEnt != null) {
			response = new ConsultTicketPurchaseResponse();
			response.setEmployeeName(employeeEnt.getFullname());
			response.setTickets(buildTicketList(employeeEnt.getChronograms().iterator()));
		}
		return null; // No existe empleado
	}

	private List<TicketPurchase> buildTicketList(Iterator<ChronogramEntity> iterator) {
		List<TicketPurchase> response = null;
		if (iterator != null) {
			response = new ArrayList<TicketPurchase>();
			while (iterator.hasNext()) {
				ChronogramEntity chronogramEnt = iterator.next();
				if (chronogramEnt.getProgram() != null && chronogramEnt.getProgram().getState() == 4) {
					ProgramEntity programEnt = chronogramEnt.getProgram();
					if (programEnt.getTicketPurchase() != null) {
						TicketPurchase ticket = new TicketPurchase();
						ticket.setTicketCode(programEnt.getTicketPurchase().getIdTicketPurchase());
						ticket.setAirlineName(programEnt.getTicketPurchase().getAirline().getName());
						response.add(ticket);
					}
				}
			}
			return response;
		}
		return null; // NO TIENE CRONOGRAMAS
	}

	public EvaluateTicketPurchaseResponse evaluateTicketPurchase(Integer ticketCode) {
		EvaluateTicketPurchaseResponse response = null;
		TicketPurchaseEntity ticketEnt = this.ticketPurchaseRepository.findById(ticketCode).get();
		if (ticketEnt != null) {
			response = new EvaluateTicketPurchaseResponse();
			response.setAirlineName(ticketEnt.getAirline().getName());
			response.setEmployeeName(ticketEnt.getProgram().getChronogram().getEmployee().getFullname());
			response.setDays(String.valueOf(ticketEnt.getProgram().getChronogram().getDays()));
			response.setMiningName(ticketEnt.getProgram().getChronogram().getMining().getName());
			return response;
		}
		return null;
	}

}
