package com.minem.diars.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minem.diars.app.model.api.request.ChangeRequestRequest;
import com.minem.diars.app.model.api.response.ChangeRequestResponse;
import com.minem.diars.app.model.api.response.ConsultChangeRequestResponse;
import com.minem.diars.app.service.ChangeRequestService;
import com.minem.diars.app.util.constants.MinemConstants;

@RestController
@RequestMapping("/travel/change/request/v1/")
@CrossOrigin("http://localhost:4200")
public class ChangeRequestController {
	
	@Autowired
	@Qualifier(MinemConstants.CHANGE_REQUEST_SERVICE)
	private ChangeRequestService changeRequestService;
	
	@PostMapping("register")
	public ChangeRequestResponse postRegister(@RequestBody ChangeRequestRequest request) {
		return this.changeRequestService.registerChangeRequest(request);
	}
	
	@GetMapping("consult/{empCode}")
	public ConsultChangeRequestResponse getRequestsChange(@PathVariable("empCode") Integer empCode) {
		return this.changeRequestService.getRequestsChange(empCode);
	}

}
