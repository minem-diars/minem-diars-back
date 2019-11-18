package com.minem.diars.app.core;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.minem.diars.app.model.bean.Mining;
import com.minem.diars.app.model.common.CommonModel;
import com.minem.diars.app.model.entity.MiningEntity;
import com.minem.diars.app.repository.MiningRepository;
import com.minem.diars.app.util.constants.MinemConstants;

@Component(MinemConstants.COMMON_CORE)
public class CommonCore {
	
	@Autowired
	@Qualifier(MinemConstants.MINING_REPOSITORY)
	private MiningRepository miningRepository;
	
	public CommonModel listMinings() {
		List<MiningEntity> miningsEnt = miningRepository.findAll();
		if (miningsEnt != null) {
			return buildModelResponse(miningsEnt);
		} else {
			return buildModelError();
		}
	}

	private CommonModel buildModelError() {
		CommonModel response = new CommonModel();
		response.setStatus(MinemConstants.RESPONSE_KO);
		return response;
	}

	private CommonModel buildModelResponse(List<MiningEntity> miningsEnt) {
		CommonModel response = new CommonModel();
		response.setStatus(MinemConstants.RESPONSE_OK);
		response.setMinings(convertList(miningsEnt));
		return response;
	}

	private List<Mining> convertList(List<MiningEntity> miningsEnt) {
		List<Mining> response = new ArrayList<Mining>();
		for(MiningEntity ent : miningsEnt) {
			Mining mining = new Mining();
			mining.setCode(ent.getIdMining());
			mining.setName(ent.getName());
			response.add(mining);
		}
		return response;
	}

}
