package com.minem.diars.app.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.minem.diars.app.model.api.request.ChangeRequestRequest;
import com.minem.diars.app.model.api.response.ChangeRequestResponse;
import com.minem.diars.app.model.api.response.ConsultChangeRequestResponse;
import com.minem.diars.app.model.bean.Program;
import com.minem.diars.app.model.entity.ChangeRequestEntity;
import com.minem.diars.app.model.entity.ChronogramEntity;
import com.minem.diars.app.model.entity.EmployeeEntity;
import com.minem.diars.app.model.entity.ProgramEntity;
import com.minem.diars.app.repository.EmployeeRepository;
import com.minem.diars.app.repository.ProgramRepository;
import com.minem.diars.app.util.constants.MinemConstants;

@Component(MinemConstants.CHANGE_REQUEST_CORE)
public class ChangeRequestCore {
	
	@Autowired
	@Qualifier(MinemConstants.PROGRAM_REPOSITORY)
	private ProgramRepository programRepository;
	
	@Autowired
	@Qualifier(MinemConstants.EMPLOYEE_REPOSITORY)
	private EmployeeRepository employeeRepository;

	public ChangeRequestResponse registerChangeRequest(ChangeRequestRequest request) {
		ChangeRequestResponse response = new ChangeRequestResponse();
		try {
			ProgramEntity programEnt = this.programRepository.findById(request.getProgramCode()).get();
			
			ChangeRequestEntity changeEnt = buildChangeRequestEnt(request);
			
			programEnt.setChangeRequestFlag(1);
			
			changeEnt.setProgram(programEnt);
			
			programEnt.setChangeRequest(changeEnt);
			
			this.programRepository.save(programEnt);
			
			response.setStatus(MinemConstants.RESPONSE_OK);
			response.setMessage("Se registro la solicitud de cambio correctamente.");
			
			return response;
		} catch (Exception e) {
			
			response.setStatus(MinemConstants.RESPONSE_KO);
			response.setErrorCode("CR-0001");
			response.setErrorMessage("Ocurrio un error al registrar solicitud, intentelo nuevamente.");
			return response;
		}
	}

	private ChangeRequestEntity buildChangeRequestEnt(ChangeRequestRequest request) {
		ChangeRequestEntity response = new ChangeRequestEntity();
		response.setObservations(request.getObservations());
		return response;
	}

	public ConsultChangeRequestResponse getRequestsChange(Integer empCode) {
		ConsultChangeRequestResponse response = new ConsultChangeRequestResponse();
		try {
			EmployeeEntity employeeEnt = this.employeeRepository.findById(empCode).get();
			List<Program> listProgram = buildListReponse(employeeEnt.getChronograms().iterator());
			response.setEmployeeFullName(employeeEnt.getFullname());
			response.setPrograms(listProgram);
			response.setStatus(MinemConstants.RESPONSE_OK);
			return response;
		} catch (Exception e) {
			response.setStatus(MinemConstants.RESPONSE_KO);
			response.setErrorCode("CR-0002");
			response.setErrorMessage("Ocurrio un error al obtener informacion.");
			return response;
		}
	}

	private List<Program> buildListReponse(Iterator<ChronogramEntity> iterator) {
		List<Program> response = new ArrayList<Program>();
		while (iterator.hasNext()) {
			ProgramEntity programEnt = iterator.next().getProgram();
			if (programEnt != null && programEnt.getChangeRequestFlag() == 1) {
				Program program = new Program();
				program.setMiningEntity(programEnt.getChronogram().getMining().getName());
				program.setProgramCode(programEnt.getIdProgram());
				response.add(program);
			}
		}
		return response;
	}

}
