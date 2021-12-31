package com.scube.edu.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(	name = "collegeVerificationUrlEntity")
public class CollegeVerificationUrlEntity extends CreateUpdate{
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "url")
	private String url;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "random_key")
	private String randomKey;
	
	@Column(name = "mig_pri_key")
	private String migPriKey;
}
