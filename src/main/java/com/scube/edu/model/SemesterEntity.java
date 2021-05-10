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
@Getter @Setter
@Table(	name = "semester_Master")
public class SemesterEntity extends CreateUpdate{

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	

	@Column(name = "universityId")
	private Long universityId;
	

	@Column(name = "Semester")
	private String semester;
	
	@Column(name = "stream_id")
	private Long streamId;
}

