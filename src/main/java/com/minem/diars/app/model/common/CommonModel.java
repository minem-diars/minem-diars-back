package com.minem.diars.app.model.common;

import java.util.List;

import com.minem.diars.app.model.bean.Airline;
import com.minem.diars.app.model.bean.Mining;
import com.minem.diars.app.model.bean.Role;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommonModel {
	
	private String status;
	private List<Mining> minings;
	private List<Airline> airlines;
	private List<Role> roles;

}
