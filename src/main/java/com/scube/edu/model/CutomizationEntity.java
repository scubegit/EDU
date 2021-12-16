package com.scube.edu.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Cutomization")
@Getter @Setter
public class CutomizationEntity {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotBlank
	@Size(max = 100)
	@Column(name = "role_Id")
	private Long roleId;
	
	@Column(name = "university_Id")
	private Long universityId;
	
	
	@Column(name = "Email_Flag")
	private String EmailFlag;
	
	@Column(name = "service_flag")
	private String serviceFlag;


}
