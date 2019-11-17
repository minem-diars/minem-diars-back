package com.minem.diars.app.core;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.minem.diars.app.model.api.request.ChronogramRegisterRequest;
import com.minem.diars.app.model.api.response.CheckChronogramResponse;
import com.minem.diars.app.model.bean.Chronogram;
import com.minem.diars.app.model.bean.ChronogramDatail;
import com.minem.diars.app.model.bean.ChronogramInformation;
import com.minem.diars.app.model.common.ChronogramModel;
import com.minem.diars.app.model.entity.ChronogramDetailEntity;
import com.minem.diars.app.model.entity.ChronogramEntity;
import com.minem.diars.app.model.entity.EmployeeEntity;
import com.minem.diars.app.repository.ChronogramDetailRepository;
import com.minem.diars.app.repository.ChronogramRepository;
import com.minem.diars.app.repository.EmployeeRepository;
import com.minem.diars.app.util.constants.ChronogramConstants;
import com.minem.diars.app.util.constants.MinemConstants;

@Component(ChronogramConstants.CORE)
public class ChronogramCore {
	
	@Autowired
	@Qualifier(MinemConstants.EMPLOYEE_REPOSITORY)
	private EmployeeRepository employeeRepository;
	
	@Autowired
	@Qualifier(MinemConstants.CHRONOGRAM_REPOSITORY)
	private ChronogramRepository chronogramRepository;
	
	@Autowired
	@Qualifier(MinemConstants.CHRONOGRAM_DETAIL_REPOSITORY)
	private ChronogramDetailRepository chronogramDetailRepository;
	
	public ChronogramModel saveChronogram(ChronogramRegisterRequest request) {
		
		ChronogramModel response = null;
		
		EmployeeEntity employee = employeeRepository.findById(Integer.parseInt(request.getCommissioner().getEmployeeCode())).get();
		
		if (employee != null) {
			
			ChronogramEntity chronogram = buildChronogramEntity(request.getChronogram());
			
			chronogram = buildChronogramDetailEntity(chronogram, request.getChronogramDatails());
			
			chronogram.setEmployee(employee);
			
			employee.getChronograms().add(chronogram);
			
			employeeRepository.save(employee);
			
			response = new ChronogramModel();
			
			response.setStatus(MinemConstants.RESPONSE_OK);
			
			return response;
			
		} else {
			
			response = new ChronogramModel();
			
			response.setStatus(MinemConstants.RESPONSE_KO);
			
			return response;
			
		}
		
	}

	private ChronogramEntity buildChronogramDetailEntity(ChronogramEntity chronogram,
			List<ChronogramDatail> chronogramDatails) {
		
		for(ChronogramDatail obj : chronogramDatails) {
			ChronogramDetailEntity ent = null;
			String[] activities = obj.getActivities().split(MinemConstants.COMMA);
			
			for (int i = 0; i < activities.length; i++) {
				ent = new ChronogramDetailEntity();
				ent.setDay(String.valueOf(obj.getDay()));
				ent.setActivity(activities[i]);
				
				ent.setChronogram(chronogram);
				
				chronogram.getChronogramDetails().add(ent);
			}
			
		}
		
		return chronogram;
	}

	private ChronogramEntity buildChronogramEntity(ChronogramInformation chronogram) {
		ChronogramEntity response = new ChronogramEntity();
		
		response.setNameService(chronogram.getNameService());
		response.setInitialDate(chronogram.getInitialDate());
		response.setFinalDate(chronogram.getFinalDate());
		response.setDays(chronogram.getDays());
		response.setMiningCode(chronogram.getMiningCode());
		
		return response;
	}
	
	public CheckChronogramResponse findChronograms(String employeeCode) {
		EmployeeEntity employee = employeeRepository.findById(Integer.parseInt(employeeCode)).get();
		List<Chronogram> listChronograms = new ArrayList<Chronogram>();
		Iterator<ChronogramEntity> itr = employee.getChronograms().iterator();
		while (itr.hasNext()) {
			Chronogram chronogram = filterChronogram(itr.next());
			if(chronogram != null) {
				listChronograms.add(chronogram);
			}
		}
		return buildResponse(employee, listChronograms);
	}
	
	private CheckChronogramResponse buildResponse(EmployeeEntity employee, List<Chronogram> listChronograms) {
		
		CheckChronogramResponse response = new CheckChronogramResponse();
		
		response.setEmployeeName(employee.getName());
		response.setChronograms(listChronograms);
		
		return response;
		
	}

	private Chronogram filterChronogram(ChronogramEntity ent) {
		String[] iDate = ent.getInitialDate().split(MinemConstants.SLASH);
		LocalDate iLocalDate = LocalDate.of(Integer.parseInt(iDate[2]), Integer.parseInt(iDate[1]), Integer.parseInt(iDate[0]));
		LocalDate currentDate = LocalDate.now();
		
		if (iLocalDate.isAfter(currentDate) || iLocalDate.isEqual(currentDate)) {
			return buildChronogram(ent);
		}else {
			return null;
		}
		
	}

	private Chronogram buildChronogram(ChronogramEntity ent) {
		
		Chronogram response = new Chronogram();
		
		response.setChronogramCode(String.valueOf(ent.getIdChronogram()));
		response.setMiningEntity(ent.getMiningCode());
		response.setInitialDate(ent.getInitialDate());
		response.setFinalDate(ent.getFinalDate());
		
		return response;
		
	}

}
