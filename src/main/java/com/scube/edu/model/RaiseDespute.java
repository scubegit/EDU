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
@Table (name ="raiseDespute")
@Getter @Setter
public class RaiseDespute {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotBlank
	@Size(max = 100)
	@Column(name = "applicationId")
	private Long applicationId;
	
	
	@NotBlank
	@Size(max = 200)
	@Column(name = "contactPersonName")
	private String contactPersonName;
	
	@NotBlank
	@Size(max = 100)
	@Column(name = "contactPersonPhone")
	private String contactPersonPhone;
	
	@NotBlank
	@Size(max = 100)
	@Column(name = "contactPersonEmail")
	private String contactPersonEmail;
	
	@NotBlank
	@Size(max = 100)
	@Column(name = "reasonForDispute")
	private String reasonForDispute;
	
	@NotBlank
	@Size(max = 100)
	@Column(name = "status")
	private String status;
	
	@NotBlank
	@Size(max = 100)
	@Column(name = "comment")
	private String comment;


}
