package com.minem.diars.app.model.api.response;

import java.util.List;

import com.minem.diars.app.model.bean.Mining;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FindMiningResponse extends RestResponse {
	
	private List<Mining> minings;	

}
