package com.minem.diars.app.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.minem.diars.app.model.api.request.ProgramRegisterRequest;
import com.minem.diars.app.model.api.request.UpdateProgramRequest;
import com.minem.diars.app.model.api.request.UpdateStateRequest;
import com.minem.diars.app.model.api.response.AcceptedProgramResponse;
import com.minem.diars.app.model.api.response.CheckProgramResponse;
import com.minem.diars.app.model.api.response.ConsultUpdateProgramResponse;
import com.minem.diars.app.model.api.response.FindForEvaluateResponse;
import com.minem.diars.app.model.api.response.UpdateProgramResponse;
import com.minem.diars.app.model.api.response.UpdateStateResponse;
import com.minem.diars.app.model.api.response.VerifyProgramResponse;
import com.minem.diars.app.model.bean.ActivityResponse;
import com.minem.diars.app.model.bean.Program;
import com.minem.diars.app.model.common.ProgramModel;
import com.minem.diars.app.model.entity.ChronogramDetailEntity;
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
		program.setDerv_dg(0);
		program.setDerv_ol(0);
		program.setState_dl(0);
		program.setSt_register_file(0);
		program.setSt_ticket_purchase(0);
		return program;
	}
	
	public CheckProgramResponse findPrograms(Integer employeeCode, String role, Integer flag) {
		
		EmployeeEntity employee = this.employeeRepository.findById(employeeCode).get();
		Integer state = getRoleOfUser(role);
		List<Program> programs = new ArrayList<Program>();
		
		if (state ==  null) {
			return null;
		} else {
			Iterator<ChronogramEntity> chronograms = employee.getChronograms().iterator();
			while (chronograms.hasNext()) {
				Program program = buildProgram(chronograms.next(), state, flag);
				if (program != null) {
					programs.add(program);
				}
			}
			return buildResponse(employee, programs);
		}
		
	}
	
	private Integer getRoleOfUser(String role) {
		Integer valueOfUser = null;
		switch (role) {
		case MinemConstants.ROLE_OLOG:
			valueOfUser = 1;
			break;
		case MinemConstants.ROLE_DGFM:
			valueOfUser = 2;
			break;
		case MinemConstants.ROLE_VICE:
			valueOfUser = 3;
			break;
		case MinemConstants.ROLE_DLOG:
			valueOfUser = 4;
			break;
		case MinemConstants.ROLE_COAD:
			valueOfUser = 5;
			break;
		case MinemConstants.ROLE_ENOF:
			valueOfUser = 6;
			break;
		default:
			return valueOfUser;
		}
		return valueOfUser;
	}
	
	private CheckProgramResponse buildResponse(EmployeeEntity employee, List<Program> programs) {
		CheckProgramResponse response = new CheckProgramResponse();
		response.setEmployeeName(employee.getFullname());
		response.setPrograms(programs);
		return response;
	}

	private Program buildProgram(ChronogramEntity ent, Integer state, Integer flag) {
		ProgramEntity programEnt = ent.getProgram();
		if (programEnt != null) {
			return validateState(programEnt, ent, state, flag);
		}else {
			return null;
		}
	}
	
	private Program validateState(ProgramEntity programEnt, ChronogramEntity ent, Integer state, Integer flag) {
		Program response = null;
		switch (state) {
		case 1:
			if (programEnt.getState() == 1 && programEnt.getDerv_ol() == 0 && programEnt.getSt_register_file() == 1 && programEnt.getSt_ticket_purchase() == 1) {
				response = new Program();
				response.setProgramCode(programEnt.getIdProgram());
				response.setMiningEntity(ent.getMining().getName());
				response.setViaticFlag(programEnt.getViaticFlag());
				response.setState(programEnt.getState());
			}
			break;
		case 2:
			if (programEnt.getState() == state) {
				response = new Program();
				response.setProgramCode(programEnt.getIdProgram());
				response.setMiningEntity(ent.getMining().getName());
				response.setViaticFlag(programEnt.getViaticFlag());
				response.setState(programEnt.getState());
			}
			break;
		case 3:
			if (programEnt.getDerv_dg() == 1 && programEnt.getState() == 2) {
				response = new Program();
				response.setProgramCode(programEnt.getIdProgram());
				response.setMiningEntity(ent.getMining().getName());
				response.setViaticFlag(programEnt.getViaticFlag());
				response.setState(programEnt.getState());
			}
			break;
		case 4:
			if (programEnt.getState_dl() == 0 && programEnt.getDerv_ol() == 1 && programEnt.getSt_register_file() == 1 && programEnt.getSt_ticket_purchase() == 1) {
				response = new Program();
				response.setProgramCode(programEnt.getIdProgram());
				response.setMiningEntity(ent.getMining().getName());
				response.setViaticFlag(programEnt.getViaticFlag());
				response.setState(programEnt.getState());
			}
			break;
		case 5:
			if (flag == 1) { // reg doc sustento
				if (programEnt.getState() == 1 && programEnt.getSt_register_file() == 0	&& programEnt.getSt_ticket_purchase() == 0) {
					response = new Program();
					response.setProgramCode(programEnt.getIdProgram());
					response.setMiningEntity(ent.getMining().getName());
					response.setViaticFlag(programEnt.getViaticFlag());
					response.setState(programEnt.getState());
				}
			} else if (flag == 2) { // reg ped compra pasaje
				if (programEnt.getState() == 1 && programEnt.getSt_register_file() == 1	&& programEnt.getSt_ticket_purchase() == 0) {
					response = new Program();
					response.setProgramCode(programEnt.getIdProgram());
					response.setMiningEntity(ent.getMining().getName());
					response.setViaticFlag(programEnt.getViaticFlag());
					response.setState(programEnt.getState());
				}
			}
			break;
		case 6:
			if (programEnt.getState_dl() == 1 && programEnt.getSt_register_file() == 1 && programEnt.getSt_ticket_purchase() == 1 && programEnt.getTicketPurchase().getState() == 2) {
				response = new Program();
				response.setProgramCode(programEnt.getIdProgram());
				response.setMiningEntity(ent.getMining().getName());
			}
			break;
		default:
			return null;
		}
		return response;
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
		
		switch (request.getRole()) {
		case MinemConstants.ROLE_DGFM:
			if (request.getDerv_dg() == 3) {
				programEnt.setDerv_dg(request.getDerv_dg());
				this.programRepository.save(programEnt);
			} else {
				programEnt.setState(request.getState());
				this.programRepository.save(programEnt);
			}
			break;
		case MinemConstants.ROLE_VICE:
			programEnt.setState(request.getState());
			this.programRepository.save(programEnt);
			break;
		case MinemConstants.ROLE_DLOG:
			programEnt.setState_dl(request.getState_dl());
			programEnt.setAcceptedBy(request.getIdUserDlog());
			this.programRepository.save(programEnt);
			break;
		case MinemConstants.ROLE_OLOG:
			if (request.getDerv_ol() == 1) {
				programEnt.setDerv_ol(request.getDerv_ol());
				this.programRepository.save(programEnt);
			}
			break;

		default:
			break;
		}
				
		return buildUpdateResponse();
	}

	private UpdateStateResponse buildUpdateResponse() {
		UpdateStateResponse response = new UpdateStateResponse();
		response.setStatus(MinemConstants.RESPONSE_OK);
		response.setMessage("Estado de programación actualizado.");
		return response;
	}

	public VerifyProgramResponse getForVerifyProgram(Integer idProgram) {
		VerifyProgramResponse response = new VerifyProgramResponse();
		ProgramEntity programEnt = this.programRepository.findById(idProgram).get();
		response.setState(programEnt.getState());
		buildResponse(response, programEnt.getChronogram());
		return response;
	}

	private void buildResponse(VerifyProgramResponse response, ChronogramEntity chronogramEnt) {
		buildEmployeeAndMining(response, chronogramEnt);
		buildActivities(response, chronogramEnt.getChronogramDetails());
	}

	//TERMINAR
	private void buildActivities(VerifyProgramResponse response, Set<ChronogramDetailEntity> chronogramDetails) {
		List<ActivityResponse> activityList = new ArrayList<ActivityResponse>();
		Iterator<ChronogramDetailEntity> itr = chronogramDetails.iterator();
		while (itr.hasNext()){
			ChronogramDetailEntity detailEnt = itr.next();
			ActivityResponse activityRs = new ActivityResponse();
			activityRs.setDay(detailEnt.getDay());
			activityRs.setActivity(detailEnt.getActivity());
			activityList.add(activityRs);
		}
		response.setActivities(activityList);
	}

	private void buildEmployeeAndMining(VerifyProgramResponse response, ChronogramEntity chronogramEnt) {
		response.setEmployeeName(chronogramEnt.getEmployee().getFullname());
		response.setMiningName(chronogramEnt.getMining().getName());
	}

	public AcceptedProgramResponse consultAcceptedPrograms(Integer dlogCode, Integer empCode) {
		AcceptedProgramResponse response = new AcceptedProgramResponse();
		try {
			EmployeeEntity employeeEnt = this.employeeRepository.findById(empCode).get();
			response.setEmployeeFullName(employeeEnt.getFullname());
			
			Iterator<ChronogramEntity> chronogramItr = employeeEnt.getChronograms().iterator();
			List<Program> listProgram = buildListResponse(dlogCode, chronogramItr);
			response.setPrograms(listProgram);
			response.setStatus(MinemConstants.RESPONSE_OK);
			return response;
		} catch (Exception e) {
			response.setStatus(MinemConstants.RESPONSE_KO);
			response.setErrorCode("PG-0009");
			response.setErrorMessage("Ocurrio un error al cargar la información solicitada.");
			return response;
		}
	}

	private List<Program> buildListResponse(Integer dlogCode, Iterator<ChronogramEntity> chronogramItr) {
		List<Program> listProgram = new ArrayList<>();
		while (chronogramItr.hasNext()) {
			ProgramEntity programEnt = chronogramItr.next().getProgram();
			if (programEnt.getAcceptedBy() == dlogCode && programEnt.getChangeRequestFlag() == 0) {
				Program program = new Program();
				program.setProgramCode(programEnt.getIdProgram());
				program.setMiningEntity(programEnt.getChronogram().getMining().getName());
				listProgram.add(program);
			}
		}
		return listProgram;
	}

	public ConsultUpdateProgramResponse consultForUpdateProgram(Integer programCode, Integer flag) {
		ConsultUpdateProgramResponse response = new ConsultUpdateProgramResponse();
		try {
			ProgramEntity programEnt = this.programRepository.findById(programCode).get();
			
			response.setEmployeeName(programEnt.getChronogram().getEmployee().getFullname());
			response.setMiningName(programEnt.getChronogram().getMining().getName());
			response.setInitialDate(programEnt.getChronogram().getInitialDate());
			response.setFinalDate(programEnt.getChronogram().getFinalDate());
			response.setViaticFlag(programEnt.getViaticFlag());
			response.setLodgingCost(programEnt.getLodgingCost());
			response.setTransportCost(programEnt.getTransportCost());
			
			if (flag != 0) {
				response.setObservations(programEnt.getChangeRequest().getObservations());	
			}
			
			response.setStatus(MinemConstants.RESPONSE_OK);
			
			return response;
		} catch (Exception e) {
			
			response.setStatus(MinemConstants.RESPONSE_KO);
			
			response.setErrorCode("PG-0010");
			response.setErrorMessage("Ocurrio un error al obtener informacion de programacion.");
			
			return response;
		}
	}

	public UpdateProgramResponse postUpdateProgram(UpdateProgramRequest request) {
		UpdateProgramResponse response = new UpdateProgramResponse();
		try {
			ProgramEntity programEnt = this.programRepository.findById(request.getProgramCode()).get();
			
			programEnt.getChangeRequest().setState(1);
			
			programEnt.setChangeRequestFlag(2);
			
			programEnt.setViaticFlag(request.getViaticFlag());
			programEnt.setLodgingCost(request.getLodgingCost());
			programEnt.setTransportCost(request.getTransportCost());
			
			ChronogramEntity chronogramEnt = programEnt.getChronogram();
			chronogramEnt.setInitialDate(request.getInitialDate());
			chronogramEnt.setFinalDate(request.getFinalDate());
			
			programEnt.setChronogram(chronogramEnt);
			chronogramEnt.setProgram(programEnt);
			
			this.chronogramRepository.save(chronogramEnt);
			
			response.setStatus(MinemConstants.RESPONSE_OK);
			response.setMessage("Se actualizó correctamente la programacion");
			
			return response;
		} catch (Exception e) {

			response.setStatus(MinemConstants.RESPONSE_KO);

			response.setErrorCode("PG-0011");
			response.setErrorMessage("Ocurrio un error al intentar actualizar programacion.");
			return response;
		}
	}

}
