package com.minem.diars.app.model.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Itinerary {
	
	private String destination;
	private Integer days;
	private String lodging;
	private String lodgingPhone;

}
