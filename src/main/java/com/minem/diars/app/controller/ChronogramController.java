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

import com.minem.diars.app.model.api.request.ChronogramRegisterRequest;
import com.minem.diars.app.model.api.response.CheckChronogramResponse;
import com.minem.diars.app.model.api.response.ChronogramRegisterResponse;
import com.minem.diars.app.model.api.response.FindChronogramResponse;
import com.minem.diars.app.service.ChronogramService;
import com.minem.diars.app.util.constants.MinemConstants;

@RestController
@RequestMapping("/travel/chronogram/v1/")
@CrossOrigin("http://localhost:4200")
public class ChronogramController {
	
	@Autowired
	@Qualifier(MinemConstants.CHRONOGRAM_SERVICE)
	private ChronogramService chronogramService;
	
	@PostMapping("register")
	public ChronogramRegisterResponse postRegister(@RequestBody ChronogramRegisterRequest request) {
		return this.chronogramService.registerChronogram(request);
	}
	
	@GetMapping("consult/{employeeCode}")
	public CheckChronogramResponse getCheckChronogram(@PathVariable("employeeCode") String employeeCode) {
		return this.chronogramService.checkChronogram(employeeCode);
	}
	
	@GetMapping("consult/findone/{chronogramCode}")
	public FindChronogramResponse getChronogram(@PathVariable("chronogramCode") String chronogramCode) {
		return this.chronogramService.findChronogram(chronogramCode);
	}

}
