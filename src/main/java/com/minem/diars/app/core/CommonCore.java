package com.minem.diars.app.core;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.minem.diars.app.model.bean.Airline;
import com.minem.diars.app.model.bean.Mining;
import com.minem.diars.app.model.bean.Role;
import com.minem.diars.app.model.common.CommonModel;
import com.minem.diars.app.model.entity.AirlineEntity;
import com.minem.diars.app.model.entity.MiningEntity;
import com.minem.diars.app.model.entity.RoleEntity;
import com.minem.diars.app.repository.AirlineRepository;
import com.minem.diars.app.repository.MiningRepository;
import com.minem.diars.app.repository.RoleRepository;
import com.minem.diars.app.util.constants.MinemConstants;

@Component(MinemConstants.COMMON_CORE)
public class CommonCore {
	
	@Autowired
	@Qualifier(MinemConstants.MINING_REPOSITORY)
	private MiningRepository miningRepository;
	
	@Autowired
	@Qualifier(MinemConstants.AIRLINE_REPOSITORY)
	private AirlineRepository airlineRepository;
	
	@Autowired
	@Qualifier(MinemConstants.ROLE_REPOSITORY)
	private RoleRepository roleRepository;
	
	public CommonModel listMinings() {
		List<MiningEntity> miningsEnt = this.miningRepository.findAll();
		if (miningsEnt != null) {
			return buildMiningResponse(miningsEnt);
		} else {
			return buildModelError();
		}
	}

	private CommonModel buildModelError() {
		CommonModel response = new CommonModel();
		response.setStatus(MinemConstants.RESPONSE_KO);
		return response;
	}

	private CommonModel buildMiningResponse(List<MiningEntity> miningsEnt) {
		CommonModel response = new CommonModel();
		response.setStatus(MinemConstants.RESPONSE_OK);
		response.setMinings(converMiningtList(miningsEnt));
		return response;
	}

	private List<Mining> converMiningtList(List<MiningEntity> miningsEnt) {
		List<Mining> response = new ArrayList<Mining>();
		for(MiningEntity ent : miningsEnt) {
			Mining mining = new Mining();
			mining.setCode(ent.getIdMining());
			mining.setName(ent.getName());
			response.add(mining);
		}
		return response;
	}

	public CommonModel listAirlines() {
		List<AirlineEntity> airlinesEnt = this.airlineRepository.findAll();
		if (airlinesEnt != null) {
			return buildAirlineResponse(airlinesEnt);
		} else {
			return buildModelError();
		}
	}

	private CommonModel buildAirlineResponse(List<AirlineEntity> airlinesEnt) {
		CommonModel response = new CommonModel();
		response.setStatus(MinemConstants.RESPONSE_OK);
		response.setAirlines(convertAirlineList(airlinesEnt));
		return response;
	}

	private List<Airline> convertAirlineList(List<AirlineEntity> airlinesEnt) {
		List<Airline> response = new ArrayList<Airline>();
		for(AirlineEntity ent : airlinesEnt) {
			Airline mining = new Airline();
			mining.setCode(ent.getIdAirline());
			mining.setName(ent.getName());
			response.add(mining);
		}
		return response;
	}

	public CommonModel listRoles() {
		List<RoleEntity> rolesEnt = this.roleRepository.findAll();
		if (rolesEnt != null) {
			return buildRoleResponse(rolesEnt);
		} else {
			return buildModelError();
		}
	}

	private CommonModel buildRoleResponse(List<RoleEntity> rolesEnt) {
		CommonModel response = new CommonModel();
		response.setStatus(MinemConstants.RESPONSE_OK);
		response.setRoles(convertRoleList(rolesEnt));
		return response;
	}

	private List<Role> convertRoleList(List<RoleEntity> rolesEnt) {
		List<Role> response = new ArrayList<Role>();
		for (RoleEntity ent : rolesEnt) {
			Role role = new Role();
			role.setCode(ent.getIdRole());
			role.setName(ent.getName());
			response.add(role);
		}
		return response;
	}

}
