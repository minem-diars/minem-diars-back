package com.minem.diars.app.model.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "chronogram")
public class ChronogramEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_chronogram")
	private Integer idChronogram;
	
	@Column(name = "name_service")
	private String nameService;
	
	@Column(name = "initial_date")
	private String initialDate;

	@Column(name = "final_date")
	private String finalDate;
	
	@Column(name = "days")
	private Integer days;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_employee", nullable = false)
	private EmployeeEntity employee;
	
	@OneToMany(cascade = CascadeType.ALL,
			fetch = FetchType.LAZY,
			mappedBy = "chronogram")
	private Set<ChronogramDetailEntity> chronogramDetails = new HashSet<ChronogramDetailEntity>();
	
	@OneToOne(fetch = FetchType.LAZY,
			cascade = CascadeType.ALL,
			mappedBy = "chronogram")
	private ProgramEntity program;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_mining", nullable = true)
	private MiningEntity mining;
	

}
