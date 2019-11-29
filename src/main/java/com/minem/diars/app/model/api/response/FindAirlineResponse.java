package com.minem.diars.app.model.api.response;

import java.util.List;

import com.minem.diars.app.model.bean.Airline;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FindAirlineResponse extends RestResponse {
	
	List<Airline> airlines;

}
