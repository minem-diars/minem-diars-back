package com.minem.diars.app.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "change_request")
public class ChangeRequestEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_change_request")
	private Integer idChangeRequest;
	
	@Column(name = "observations")
	private String observations;
	
	@Column(name = "state")
	private int state; // 0 -> SIN ATENDER ; 1 -> ATENDIDO
	
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_program", nullable = false)
	private ProgramEntity program;

}
