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
@Table (name ="verifiedDocument")
@Getter @Setter
public class VerifiedDocument {
	
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
	@Column(name = "empId")
	private Long empId;
	
	@NotBlank
	@Size(max = 100)
	@Column(name = "firstName")
	private String firstName;
	
	@NotBlank
	@Size(max = 100)
	@Column(name = "lastName")
	private String lastName;
	
	@NotBlank
	@Size(max = 100)
	@Column(name = "streamId")
	private Long streamId;
	
	@NotBlank
	@Size(max = 100)
	@Column(name = "collegeId")
	private Long collegeId;
	
	@NotBlank
	@Size(max = 100)
	@Column(name = "universityId")
	private Long universityId;
	
	@NotBlank
	@Size(max = 100)
	@Column(name = "documentName")
	private String documentName;
	
	@NotBlank
	@Size(max = 100)
	@Column(name = "enrollmentNo")
	private String enrollmentNo;
	
	@NotBlank
	@Size(max = 100)
	@Column(name = "yearOfPassingId")
	private String yearOfPassingId;
	
	@NotBlank
	@Size(max = 100)
	@Column(name = "uploadDocumentPath")
	private String uploadDocumentPath;
	
	@NotBlank
	@Size(max = 100)
	@Column(name = "docStatus")
	private String docStatus;
	
	


}
