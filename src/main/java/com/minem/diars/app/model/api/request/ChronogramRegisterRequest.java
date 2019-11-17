package com.minem.diars.app.model.api.request;

import java.util.List;

import com.minem.diars.app.model.bean.ChronogramDatail;
import com.minem.diars.app.model.bean.ChronogramInformation;
import com.minem.diars.app.model.bean.CommissionerInformation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChronogramRegisterRequest {
	
	private CommissionerInformation	commissioner;
	private ChronogramInformation chronogram;
	private List<ChronogramDatail> chronogramDatails;
	
	public boolean isNull() {
		return ((this.commissioner == null && this.chronogram == null && this.chronogramDatails == null)||
				(this.commissioner == null || this.chronogram == null || this.chronogramDatails == null)) ? true : false;
	}

}
