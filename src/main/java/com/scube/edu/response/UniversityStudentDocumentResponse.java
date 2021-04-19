package com.scube.edu.response;

import javax.persistence.Column;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
public class UniversityStudentDocumentResponse {
	  
			private Long id;
			private String enrollmentNo;
			private String firstName;			
			private String lastName;	
			private String stream;
			private String collegeName;
			private String OriginalDOCuploadfilePath;
			private Integer passingYear;

}
