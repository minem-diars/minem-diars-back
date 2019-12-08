package com.minem.diars.app.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.minem.diars.app.model.api.request.RegisterTicketPurchaseRequest;
import com.minem.diars.app.model.api.request.UpdateTicketPurchaseRequest;
import com.minem.diars.app.model.api.response.ConsultTicketPurchaseResponse;
import com.minem.diars.app.model.api.response.EvaluateTicketPurchaseResponse;
import com.minem.diars.app.model.api.response.RegisterTicketPurchaseResponse;
import com.minem.diars.app.model.api.response.UpdateTicketPurchaseResponse;
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
		RegisterTicketPurchaseResponse response = new RegisterTicketPurchaseResponse();
		try {
			ProgramEntity programEnt = this.programRepository.findById(request.getIdProgram()).get();
			
			TicketPurchaseEntity ticketEnt = buildTicketPurchaseEntity(request);
			
			AirlineEntity airlineEnt = this.airlineRepository.findById(request.getAirline()).get(); 
			
			ticketEnt.setAirline(airlineEnt);
			airlineEnt.getTickets().add(ticketEnt);
			
			ticketEnt.setProgram(programEnt);
			programEnt.setSt_ticket_purchase(1);
			programEnt.setTicketPurchase(ticketEnt);
			
			this.ticketPurchaseRepository.save(ticketEnt);
			
			response.setStatus(MinemConstants.RESPONSE_OK);
			response.setMessage("Se registro pedido de compra de pasaje correctamente.");
			
			return response;
		} catch (Exception e) {
			
			response.setStatus(MinemConstants.RESPONSE_KO);
			response.setErrorCode("TP-0001");
			response.setErrorMessage("Ocurrio un error al registrar el pedido de compra de pasaje.");

			return response;
		}
		
	}

	private TicketPurchaseEntity buildTicketPurchaseEntity(RegisterTicketPurchaseRequest request) {
		TicketPurchaseEntity ticketEnt = new TicketPurchaseEntity();
		ticketEnt.setGoingDate(request.getGoingDate());
		ticketEnt.setComebackDate(request.getComebackDate());
		ticketEnt.setState(2);
		return ticketEnt;
	}

	public ConsultTicketPurchaseResponse consultTicketPurchase(Integer employeeCode) {
		ConsultTicketPurchaseResponse response = null;
		EmployeeEntity employeeEnt = this.employeeRepository.findById(employeeCode).get();
		if (employeeEnt != null) {
			response = new ConsultTicketPurchaseResponse();
			response.setEmployeeName(employeeEnt.getFullname());
			response.setTickets(buildTicketList(employeeEnt.getChronograms().iterator()));
			return response;
		}
		return null; // No existe empleado
	}

	private List<TicketPurchase> buildTicketList(Iterator<ChronogramEntity> iterator) {
		List<TicketPurchase> response = null;
		if (iterator != null) {
			response = new ArrayList<TicketPurchase>();
			while (iterator.hasNext()) {
				ChronogramEntity chronogramEnt = iterator.next();
				if (chronogramEnt.getProgram() != null) {
					ProgramEntity programEnt = chronogramEnt.getProgram();
					if (programEnt.getTicketPurchase() != null && programEnt.getTicketPurchase().getState() == 2) {
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
			response.setMiningName(ticketEnt.getProgram().getChronogram().getMining().getName());
			return response;
		}
		return null;
	}

	public UpdateTicketPurchaseResponse updateStateOfTicket(UpdateTicketPurchaseRequest request) {
		UpdateTicketPurchaseResponse response = new UpdateTicketPurchaseResponse();
		try {
			TicketPurchaseEntity ticketEnt = this.ticketPurchaseRepository.findById(request.getTicketCode()).get();
			ticketEnt.setState(request.getNewState());
			this.ticketPurchaseRepository.save(ticketEnt);
			
			response.setStatus(MinemConstants.RESPONSE_OK);
			response.setMessage("Se actualizó el estado correctamente.");
			
			return response;
		} catch (Exception e) {
			
			response.setStatus(MinemConstants.RESPONSE_KO);
			response.setErrorCode("TP-0002");
			response.setErrorMessage("Ocurrio un error al intentar actualizar el estado, intentelo nuevamente.");
			
			return response;
		}
	}

}
