package com.minem.diars.app.service;

import com.minem.diars.app.model.api.response.FindAirlineResponse;
import com.minem.diars.app.model.api.response.FindMiningResponse;

public interface CommonService {

	FindMiningResponse findMinings();
	
	FindAirlineResponse findAirlines();
	
}
