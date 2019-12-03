package com.minem.diars.app.service;

import com.minem.diars.app.model.api.response.FindAirlineResponse;
import com.minem.diars.app.model.api.response.FindMiningResponse;
import com.minem.diars.app.model.api.response.FindRolesResponse;

public interface CommonService {

	FindMiningResponse findMinings();
	
	FindAirlineResponse findAirlines();

	FindRolesResponse findRoles();
	
}
