package com.minem.diars.app.model.api.response;

import java.util.List;

import com.minem.diars.app.model.bean.Chronogram;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CheckChronogramResponse {
	
	private String employeeName;
	private List<Chronogram> chronograms;
	
}
