package com.minem.diars.app.model.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "credential")
public class CredentialEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_credential")
	private Integer idCredential;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "create_date")
	@CreatedDate
	private String createdDate;
	
	@Column(name = "last_modified_date")
	@LastModifiedDate
	private String lastModifiedDate;
	
	@Column(name = "create_by")
	@CreatedBy
	private String createdDateBy;
	
	@Column(name = "last_modified_by")
	@LastModifiedBy
	private String lastModifiedDateBy;
	
	@OneToOne(fetch = FetchType.LAZY,
				cascade = CascadeType.ALL,
				mappedBy = "credential")
	private EmployeeEntity employee;
	
	@ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            })
    @JoinTable(name = "credential_role",
            joinColumns = {
            		@JoinColumn(name = "id_credential")
            },
            inverseJoinColumns = {
            		@JoinColumn(name = "id_role")
            })
	private Set<RoleEntity> roles = new HashSet<>();

}
