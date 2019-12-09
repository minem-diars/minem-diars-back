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
@Table(name = "program")
public class ProgramEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_program")
	private Integer idProgram;
	
	@Column(name = "viatic_flag")
	private Integer viaticFlag;
	
	@Column(name = "loging_cost")
	private String lodgingCost;
	
	@Column(name = "transport_cost")
	private String transportCost;
	
	@Column(name = "state")
	private Integer state;
	
	@Column(name = "derv_dg")
	private int derv_dg;
	
	@Column(name = "derv_ol")
	private int derv_ol;
	
	@Column(name = "state_dl")
	private int state_dl;
	
	@Column(name = "st_register_file")
	private int st_register_file;
	
	@Column(name = "st_ticket_purchase")
	private int st_ticket_purchase;
	
	@Column(name = "id_acceptedBy")
	private Integer acceptedBy;
	
	@Column(name = "change_request_flag")
	private int changeRequestFlag;
	
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_chronogram", nullable = false)
	private ChronogramEntity chronogram;
	
	@OneToMany(fetch = FetchType.LAZY,
			cascade = CascadeType.ALL,
			mappedBy = "program")
	private Set<FileEntity> files = new HashSet<FileEntity>();
	
	@OneToOne(fetch = FetchType.LAZY,
			cascade = CascadeType.ALL,
			mappedBy = "program")
	private TicketPurchaseEntity ticketPurchase;
	
	@OneToOne(fetch = FetchType.LAZY,
			cascade = CascadeType.ALL,
			mappedBy = "program")
	private ChangeRequestEntity changeRequest;

}
