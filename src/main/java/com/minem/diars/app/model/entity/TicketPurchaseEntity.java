package com.minem.diars.app.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "ticket_purchase")
public class TicketPurchaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_ticket_purchase")
	private Integer idTicketPurchase;
	
	@Column(name = "going_date")
	private String goingDate;
	
	@Column(name = "comeback_date")
	private String comebackDate;
	
	@Column(name = "state")
	private Integer state;
	
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_program", nullable = false)
	private ProgramEntity program;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_airline", nullable = true)
	private AirlineEntity airline;

}
