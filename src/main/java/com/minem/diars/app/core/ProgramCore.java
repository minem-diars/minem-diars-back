package com.minem.diars.app.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.minem.diars.app.model.api.request.ProgramRegisterRequest;
import com.minem.diars.app.model.api.response.CheckProgramResponse;
import com.minem.diars.app.model.bean.Program;
import com.minem.diars.app.model.common.ProgramModel;
import com.minem.diars.app.model.entity.ChronogramEntity;
import com.minem.diars.app.model.entity.EmployeeEntity;
import com.minem.diars.app.model.entity.ProgramEntity;
import com.minem.diars.app.repository.ChronogramRepository;
import com.minem.diars.app.repository.EmployeeRepository;
import com.minem.diars.app.util.constants.MinemConstants;
import com.minem.diars.app.util.constants.ProgramConstant;

@Component(ProgramConstant.CORE)
public class ProgramCore {
	
	@Autowired
	@Qualifier(MinemConstants.CHRONOGRAM_REPOSITORY)
	private ChronogramRepository chronogramRepository;
	
	@Autowired
	@Qualifier(MinemConstants.EMPLOYEE_REPOSITORY)
	private EmployeeRepository employeeRepository;
	
	public ProgramModel saveProgram(ProgramRegisterRequest request) {
		
		ProgramModel model = null;
		
		ChronogramEntity chronogram = chronogramRepository.findById(request.getChronogramCode()).get();
		
		if (chronogram != null) {
			
			ProgramEntity program = buildProgramEntity(request);
			
			chronogram.setProgram(program);
			
			program.setChronogram(chronogram);
			
			chronogramRepository.save(chronogram);
			
			model = new ProgramModel();
			
			model.setStatus(MinemConstants.RESPONSE_OK);
			
			return model;
		} else {

			model = new ProgramModel();

			model.setStatus(MinemConstants.RESPONSE_KO);

			return model;

		}
		
	}

	private ProgramEntity buildProgramEntity(ProgramRegisterRequest request) {
		ProgramEntity program = new ProgramEntity();
		program.setViaticFlag(request.getViaticFlag());
		program.setLodgingCost(request.getLodgingCost());
		program.setTransportCost(request.getTransportCost());
		program.setState(ProgramConstant.WAITING_STATE);
		return program;
	}
	
	public CheckProgramResponse findPrograms(Integer employeeCode) {
		
		EmployeeEntity employee = employeeRepository.findById(employeeCode).get();
		Iterator<ChronogramEntity> chronograms = employee.getChronograms().iterator();
		List<Program> programs = new ArrayList<Program>();
		
		while (chronograms.hasNext()) {
			Program program = new Program();
			program = buildProgram(chronograms.next());
			if (program != null) {
				programs.add(program);
			}
		}
		return buildResponse(employee, programs);
	}
	
	private CheckProgramResponse buildResponse(EmployeeEntity employee, List<Program> programs) {
		CheckProgramResponse response = new CheckProgramResponse();
		response.setEmployeeName(employee.getName());
		response.setPrograms(programs);
		return response;
	}

	private Program buildProgram(ChronogramEntity ent) {
		ProgramEntity programEnt = ent.getProgram();
		if (programEnt != null) {
			return validateState(programEnt, ent);
		}else {
			return null;
		}
	}
	
	private Program validateState(ProgramEntity programEnt, ChronogramEntity ent) {
		if (programEnt.getState() == 2) {
			Program response = new Program();
			response.setProgramCode(programEnt.getIdProgram());
			response.setMiningEntity(ent.getMiningCode());
			response.setViaticFlag(programEnt.getViaticFlag());
			response.setState(programEnt.getState());
			return response;
		} else {
			return null;
		}
	}

}
