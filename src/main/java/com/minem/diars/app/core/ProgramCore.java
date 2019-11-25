package com.minem.diars.app.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.minem.diars.app.model.api.request.ProgramRegisterRequest;
import com.minem.diars.app.model.api.request.UpdateStateRequest;
import com.minem.diars.app.model.api.response.CheckProgramResponse;
import com.minem.diars.app.model.api.response.FindForEvaluateResponse;
import com.minem.diars.app.model.api.response.UpdateStateResponse;
import com.minem.diars.app.model.bean.Program;
import com.minem.diars.app.model.common.ProgramModel;
import com.minem.diars.app.model.entity.ChronogramEntity;
import com.minem.diars.app.model.entity.EmployeeEntity;
import com.minem.diars.app.model.entity.ProgramEntity;
import com.minem.diars.app.repository.ChronogramRepository;
import com.minem.diars.app.repository.EmployeeRepository;
import com.minem.diars.app.repository.ProgramRepository;
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
	
	@Autowired
	@Qualifier(MinemConstants.PROGRAM_REPOSITORY)
	private ProgramRepository programRepository;
	
	public ProgramModel saveProgram(ProgramRegisterRequest request) {
		
		ProgramModel model = null;
		
		ChronogramEntity chronogram = this.chronogramRepository.findById(request.getChronogramCode()).get();
		
		if (chronogram != null) {
			
			ProgramEntity program = buildProgramEntity(request);
			
			chronogram.setProgram(program);
			
			program.setChronogram(chronogram);
			
			this.chronogramRepository.save(chronogram);
			
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
	
	public CheckProgramResponse findPrograms(Integer employeeCode, String role) {
		
		EmployeeEntity employee = this.employeeRepository.findById(employeeCode).get();
		Integer state = getRoleOfUser(role);
		List<Program> programs = new ArrayList<Program>();
		
		if (state ==  null || state == 0) {
			return null;
		} else {
			Iterator<ChronogramEntity> chronograms = employee.getChronograms().iterator();
			while (chronograms.hasNext()) {
				Program program = new Program();
				program = buildProgram(chronograms.next(), state);
				if (program != null) {
					programs.add(program);
				}
			}
			return buildResponse(employee, programs);
		}
		
	}
	
	private Integer getRoleOfUser(String role) {
		switch (role) {
		case MinemConstants.ROLE_D_ADMIN:
			return 2;
		case MinemConstants.ROLE_V_ADMIN:
			return 3;
		default:
			return 0;
		}
	}
	private CheckProgramResponse buildResponse(EmployeeEntity employee, List<Program> programs) {
		CheckProgramResponse response = new CheckProgramResponse();
		response.setEmployeeName(employee.getFullname());
		response.setPrograms(programs);
		return response;
	}

	private Program buildProgram(ChronogramEntity ent, Integer state) {
		ProgramEntity programEnt = ent.getProgram();
		if (programEnt != null) {
			return validateState(programEnt, ent, state);
		}else {
			return null;
		}
	}
	
	private Program validateState(ProgramEntity programEnt, ChronogramEntity ent, Integer state) {
		if (programEnt.getState() == state) {
			Program response = new Program();
			response.setProgramCode(programEnt.getIdProgram());
			response.setMiningEntity(ent.getMining().getName());
			response.setViaticFlag(programEnt.getViaticFlag());
			response.setState(programEnt.getState());
			return response;
		} else {
			return null;
		}
	}

	public FindForEvaluateResponse obtainProgram(Integer programCode) {
		try {
			ProgramEntity programEnt = this.programRepository.findById(programCode).get();
			if (programEnt != null) {
				return buildEvaluateResponse(programEnt);
			}
		} catch (NoSuchElementException e) {
			FindForEvaluateResponse response = new FindForEvaluateResponse();
			response.setStatus(MinemConstants.RESPONSE_KO);
			response.setErrorCode("CG-0005");
			response.setErrorMessage("No se encontro la programación.");
			return response;
		}
		return null;
	}

	private FindForEvaluateResponse buildEvaluateResponse(ProgramEntity programEnt) {
		FindForEvaluateResponse response = new FindForEvaluateResponse();
		buildWithChronogramInformation(response, programEnt);
		response.setViatics(programEnt.getViaticFlag());
		return response;
	}

	private void buildWithChronogramInformation(FindForEvaluateResponse response, ProgramEntity programEnt) {
		ChronogramEntity chronogram = programEnt.getChronogram();
		response.setEmployeeName(chronogram.getEmployee().getFullname());
		response.setMiningName(chronogram.getMining().getName());
		response.setDays(chronogram.getDays());
	}

	public UpdateStateResponse updateProgram(UpdateStateRequest request) {
		try {
			ProgramEntity programEnt = this.programRepository.findById(request.getProgramCode()).get();
			if (programEnt != null) {
				return buildUpdateState(programEnt, request);
			}
		} catch (NoSuchElementException e) {
			UpdateStateResponse response = new UpdateStateResponse();
			response.setStatus(MinemConstants.RESPONSE_KO);
			response.setErrorCode("CG-0006");
			response.setErrorMessage("No se encontro la programación.");
			return response;
		}
		return null;
	}

	private UpdateStateResponse buildUpdateState(ProgramEntity programEnt, UpdateStateRequest request) {
		programEnt.setState(request.getState());
		this.programRepository.save(programEnt);
		return buildUpdateResponse();
	}

	private UpdateStateResponse buildUpdateResponse() {
		UpdateStateResponse response = new UpdateStateResponse();
		response.setStatus(MinemConstants.RESPONSE_OK);
		response.setMessage("Estado de programación actualizado.");
		return response;
	}

}
