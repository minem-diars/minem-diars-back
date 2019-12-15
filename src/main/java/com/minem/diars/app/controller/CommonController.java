package com.minem.diars.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minem.diars.app.model.api.response.FindAirlineResponse;
import com.minem.diars.app.model.api.response.FindMiningResponse;
import com.minem.diars.app.model.api.response.FindRolesResponse;
import com.minem.diars.app.service.CommonService;
import com.minem.diars.app.util.constants.MinemConstants;

@RestController
@RequestMapping("/travel/common/v1/")
@CrossOrigin(origins = {"http://localhost:4200", "https://minem-diars.github.io"})
public class CommonController {
	
	@Autowired
	@Qualifier(MinemConstants.COMMON_SERVICE)
	private CommonService commonService;
	
	@GetMapping("consult/minings")
	public FindMiningResponse getMinings() {
		return this.commonService.findMinings();
	}
	
	@GetMapping("consult/airlines")
	public FindAirlineResponse getAirlines() {
		return this.commonService.findAirlines();
	}
	
	@GetMapping("consult/roles")
	public FindRolesResponse getRoles() {
		return this.commonService.findRoles();
	}

}
