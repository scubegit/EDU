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
@Table (name ="requestAction")
@Getter @Setter

public class RequestAction extends CreateUpdate{
	
	

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
	@Column(name = "userId")
	private Long userId;
	
	@NotBlank
	@Size(max = 100)
	@Column(name = "status")
	private String status;
	
	@NotBlank
	@Size(max = 100)
	@Column(name = "date")
	private String date;
	
	@NotBlank
	@Size(max = 100)
	@Column(name = "comment")
	private String comment;


}
