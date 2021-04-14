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
@Table (name = "universityStudentdocument")
@Getter @Setter
public class UniversityStudentDocument {
		

		@Id
		@Column(name = "id")
		@GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
		
		
		@Size(max = 100)
		@Column(name = "enrollmentNo")
		private String enrollmentNo;
		
		@Size(max = 100)
		@Column(name = "First_Name")
		private String firstName;
		
		@Size(max = 100)
		@Column(name = "Last_Name")
		private String lastName;
		
		@Size(max = 100)
		@Column(name = "Stream")
		private String stream;
		
		@Size(max = 100)
		@Column(name = "College_Name")
		private String collegeName;
		
		//@NotBlank
		@Size(max = 100)
		@Column(name = "OriginalDOC_UploadfilePath")
		private String OriginalDOCuploadfilePath;
		
		//@NotBlank
	//	@Size(max = 100)
		@Column(name = "passingYear")
		private Integer passingYear;




}
