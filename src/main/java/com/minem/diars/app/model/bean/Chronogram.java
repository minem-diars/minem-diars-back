package com.minem.diars.app.model.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Chronogram {
	
	private String chronogramCode;
	private String miningEntity;
	private String initialDate;
	private String finalDate;

}
