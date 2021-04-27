package com.scube.edu.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

import com.lowagie.text.BadElementException;
import com.scube.edu.model.CollegeMaster;
import com.scube.edu.model.DocumentMaster;
import com.scube.edu.model.PassingYearMaster;
import com.scube.edu.model.RequestTypeMaster;
import com.scube.edu.model.StreamMaster;
import com.scube.edu.model.UniversityStudentDocument;
import com.scube.edu.model.UserMasterEntity;
import com.scube.edu.model.VerificationRequest;
import com.scube.edu.repository.RequestTypeRepository;
import com.scube.edu.repository.StreamRepository;
import com.scube.edu.repository.UserRepository;
import com.scube.edu.repository.VerificationRequestRepository;
import com.scube.edu.request.StatusChangeRequest;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.CollegeResponse;
import com.scube.edu.response.JwtResponse;
import com.scube.edu.response.RequestTypeResponse;
import com.scube.edu.response.StreamResponse;
import com.scube.edu.response.StudentVerificationDocsResponse;
import com.scube.edu.response.UserResponse;
import com.scube.edu.response.VerificationResponse;
import com.scube.edu.security.JwtUtils;
import com.scube.edu.util.StringsUtils;


@Service
public class VerifierServiceImpl implements VerifierService{
	
	private static final Logger logger = LoggerFactory.getLogger(VerifierServiceImpl.class);
	
	Base64.Encoder baseEncoder = Base64.getEncoder();
	
	BaseResponse	baseResponse	= null;  
      
	 BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	 
	 @Autowired
	 VerificationRequestRepository verificationReqRepository;
	 
	 @Autowired 
	 StreamService streamService;
	 
	 @Autowired
	 YearOfPassingService yearOfPassService;
	 
	 @Autowired
	 DocumentService	documentService;
	 
	 @Autowired
	 UserRepository userRepository;
	 
	 @Autowired
	 StreamRepository  streamRespository;
	 
	 @Autowired 
	 CollegeSevice collegeService;
	 
	 @Autowired
	 UniversityStudentDocService  stuDocService;
	 
	 @Autowired
	 RequestTypeService reqTypeService;
	 
		@Autowired
		EmailService emailService;
		
		@Autowired
		UserService userService;
	 
	 @Override
	 public List<VerificationResponse> getVerifierRequestList() throws Exception {
		 
		 logger.info("********VerifierServiceImpl getVerifierRequestList********");
		 
		 List<VerificationResponse> List = new ArrayList<>();
		 
		 List<VerificationRequest> verReq = verificationReqRepository.getVerifierRecords();
		 
		 for(VerificationRequest veriReq: verReq) {
			 
			
			 VerificationResponse resp = new VerificationResponse();
			  
			  PassingYearMaster year =
			  yearOfPassService.getYearById(veriReq.getYearOfPassingId());
			  
			  System.out.println(veriReq.getDocumentId());
			  
			  DocumentMaster doc = documentService.getNameById(veriReq.getDocumentId());
			  
			  Optional<UserMasterEntity> user = userRepository.findById(veriReq.getUserId());
			  UserMasterEntity userr = user.get();
			  
			  Optional<StreamMaster> stream = streamRespository.findById(veriReq.getStreamId()); 
			  StreamMaster str = stream.get();
			  
			  RequestTypeResponse reqMaster = reqTypeService.getNameById(veriReq.getRequestType());
			  
			  resp.setId(veriReq.getId());
			  resp.setApplication_id(veriReq.getApplicationId());
			  resp.setCollege_name_id(veriReq.getCollegeId());
			  resp.setDoc_name(doc.getDocumentName());
			  resp.setEnroll_no(veriReq.getEnrollmentNumber());
			  resp.setFirst_name(veriReq.getFirstName());
			  resp.setLast_name(veriReq.getLastName());
			  resp.setStream_name(str.getStreamName());
			  resp.setUni_id(veriReq.getUniversityId());
			  resp.setUpload_doc_path(veriReq.getUploadDocumentPath());
			  resp.setUser_id(veriReq.getUserId());
			  resp.setVer_req_id(veriReq.getVerRequestId());
			  resp.setYear(year.getYearOfPassing());
			  resp.setCompany_name(userr.getCompanyName());
			  resp.setRequest_type_id(reqMaster.getRequestType());
			 
			 // run query here which will update 'assigned_to' column with userId value
			 // for now assign any value other than 0 (assign 1)
			 Long a = (long) 1;
			 veriReq.setAssignedTo(a);
			 verificationReqRepository.save(veriReq);
			 
			 // ON logout, change 'assignedTo' field back to 0
			 
			  List.add(resp);
		 }
		 
		 System.out.println("----------"+ verReq);
		 
		 return List;
		 
	 }

	 
	 
	@Override
	public List<StudentVerificationDocsResponse> verifyDocument(Long id) {

		List<StudentVerificationDocsResponse> verificationDataList = new ArrayList<StudentVerificationDocsResponse>();
		
		
		 List<VerificationRequest> verifierData = verificationReqRepository.getDataByIdToVerify(id);
				 
		        for(VerificationRequest veriReq: verifierData) {
					 
					 StudentVerificationDocsResponse resEntity = new StudentVerificationDocsResponse();
					 
					 PassingYearMaster year = yearOfPassService.getYearById(veriReq.getYearOfPassingId());
				
					 
					 StreamMaster stream = streamService.getNameById(veriReq.getStreamId());
					 
					 CollegeResponse college = collegeService.getNameById(veriReq.getCollegeId());
					 
					 
					 resEntity.setId(veriReq.getId());
					 resEntity.setEnroll_no(veriReq.getEnrollmentNumber());
					 resEntity.setUpload_doc_path("/verifier/getimage/VR/"+veriReq.getId());
					 resEntity.setYear(year.getYearOfPassing());
					 
					 UniversityStudentDocument doc = stuDocService.getDocDataBySixFields(veriReq.getEnrollmentNumber(), 
							 veriReq.getFirstName(), veriReq.getLastName(), stream.getStreamName(), year.getYearOfPassing()
							 , college.getCollegeName());
					 
					 resEntity.setOriginalDocUploadFilePath("/verifier/getimage/U/"+doc.getId());
					 verificationDataList.add(resEntity);
		        }
				 
		return verificationDataList;
	}



	@Override
	public List<StudentVerificationDocsResponse> setStatusForVerifierDocument(StatusChangeRequest statusChangeRequest) throws BadElementException, MessagingException, IOException {
		
			System.out.println("******VerifierServiceImpl setStatusForVerifierDocument******" +statusChangeRequest.getRemark());
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
		    Date date = new Date();  
		    String currentDate = formatter.format(date);  
			
			VerificationRequest entt =  verificationReqRepository.findById(statusChangeRequest.getId());
//			VerificationRequest entt = ent.get();
			System.out.println("------------"+ entt.getDocStatus() + entt.getApplicationId());
			
			entt.setDocStatus(statusChangeRequest.getStatus());
			entt.setVerifiedBy(statusChangeRequest.getVerifiedby());
			entt.setRemark("VR_"+currentDate+"-"+statusChangeRequest.getRemark());
			verificationReqRepository.save(entt);
			
			if(statusChangeRequest.getStatus().equalsIgnoreCase("Approved") || 
					statusChangeRequest.getStatus().equalsIgnoreCase("SV_Approved")) {
				
				UserResponse ume = userService.getUserInfoById(entt.getUserId());
				emailService.sendStatusMail(ume.getEmail(), entt.getId() , statusChangeRequest.getStatus());
				
				}
		
		return null;
	}

	
}
