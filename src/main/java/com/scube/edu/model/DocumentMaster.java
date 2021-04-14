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
@Table(name = "documentMaster")
@Getter @Setter
public class DocumentMaster extends CreateUpdate {
	

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	
	
	@Column(name = "universityId")
	private Long universityId;
	
	@NotBlank
	@Size(max = 100)
	@Column(name = "documentName")
	private String documentName;
	
	
	
	



}
