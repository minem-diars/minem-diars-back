package com.minem.diars.app.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "chronogram_detail")
public class ChronogramDetailEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_chronogram_detail")
	private Integer idChronogramDetail;
	
	@Column(name = "day")
	private String day;
	
	@Column(name = "activity")
	private String activity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_chronogram", nullable = false)
	private ChronogramEntity chronogram;

}
