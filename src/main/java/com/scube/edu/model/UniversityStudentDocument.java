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
public class UniversityStudentDocument extends CreateUpdate {
		

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
		
		//@Size(max = 100)
		@Column(name = "Last_Name")
		private String lastName;
		
		//@Size(max = 100)
		@Column(name = "StreamId")
		private Long streamId;
		
		//@Size(max = 100)
		@Column(name = "CollegeId")
		private Long collegeId;
		
		//@NotBlank
		@Size(max = 100)
		@Column(name = "OriginalDOC_UploadfilePath")
		private String OriginalDOCuploadfilePath;
		
		//@NotBlank
	//	@Size(max = 100)
		@Column(name = "passingYearId")
		private Long passingYearId;
		
		@Column(name = "branch_Id")
		private Long branchId;
		
		@Column(name = "semester_Id")
		private Long semId;
		
		@Column(name = "month_of_passing")
		private String monthOfPassing;



		
	

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof UniversityStudentDocument) {
                obj = (UniversityStudentDocument) obj;
                if (this.enrollmentNo.equals(((UniversityStudentDocument) obj).getEnrollmentNo())&&
                		this.streamId.equals(((UniversityStudentDocument) obj).getStreamId())&&
                		this.passingYearId.equals(((UniversityStudentDocument) obj).getPassingYearId())&&
                		this.semId.equals(((UniversityStudentDocument) obj).getSemId())&&
                		this.monthOfPassing.equals(((UniversityStudentDocument) obj).getMonthOfPassing())) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }

        
        
	
	  @Override public int hashCode() {
		  return enrollmentNo.hashCode()+streamId.hashCode()+passingYearId.hashCode()+semId.hashCode()+monthOfPassing.hashCode(); 
		  }
	 



}
