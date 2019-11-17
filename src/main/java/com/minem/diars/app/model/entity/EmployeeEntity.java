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
@Table(name = "employee")
public class EmployeeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_employee")
	private Integer idEmployee;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "lastname")
	private String lastname;
	
	@Column(name = "dni")
	private String dni;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "phone")
	private Integer phone;
	
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_credential", nullable = false)
	private CredentialEntity credential;
	
	@OneToMany(cascade = CascadeType.ALL,
				fetch = FetchType.LAZY,
				mappedBy = "employee")
	private Set<ChronogramEntity> chronograms = new HashSet<ChronogramEntity>();

}
