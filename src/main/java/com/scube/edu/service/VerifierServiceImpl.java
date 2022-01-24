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
import com.lowagie.text.DocumentException;
import com.lowagie.text.ExceptionConverter;
import com.scube.edu.model.BranchMasterEntity;
import com.scube.edu.model.CollegeMaster;
import com.scube.edu.model.CutomizationEntity;
import com.scube.edu.model.DocumentMaster;
import com.scube.edu.model.PassingYearMaster;
import com.scube.edu.model.RequestTypeMaster;
import com.scube.edu.model.SemesterEntity;
import com.scube.edu.model.StreamMaster;
import com.scube.edu.model.UniversityStudentDocument;
import com.scube.edu.model.UserMasterEntity;
import com.scube.edu.model.VerificationRequest;
import com.scube.edu.repository.CustomizationRepository;
import com.scube.edu.repository.RequestTypeRepository;
import com.scube.edu.repository.StreamRepository;
import com.scube.edu.repository.UserRepository;
import com.scube.edu.repository.VerificationRequestRepository;
import com.scube.edu.request.StatusChangeRequest;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.CollegeResponse;
import com.scube.edu.response.EditReasonResponse;
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
		CustomizationRepository customizationRepository;
	 
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
		
		@Autowired
		SemesterService semesterService;
		
		@Autowired
		BranchMasterService branchMasterService;
	 
	 @Override
	 public List<VerificationResponse> getVerifierRequestList( long id) throws Exception {
		 
		 logger.info("********VerifierServiceImpl getVerifierRequestList********");
		 
		 
		 List<VerificationResponse> List = new ArrayList<>();
		 try {
		 List<VerificationRequest> verReq = verificationReqRepository.getVerifierRecords(id);
		 
		 logger.info("------>1"+verReq.toString());
		 
		 for(VerificationRequest veriReq: verReq) {
			 
			 logger.info("------>2");
			 VerificationResponse resp = new VerificationResponse();
			  
			  PassingYearMaster year =
			  yearOfPassService.getYearById(veriReq.getYearOfPassingId());
			  logger.info("------>3"+ year.getYearOfPassing());
			  System.out.println(veriReq.getDocumentId());
			  
			  DocumentMaster doc = documentService.getNameById(veriReq.getDocumentId());
			  logger.info("------>4"+ doc.getDocumentName());
			  Optional<UserMasterEntity> user = userRepository.findById(veriReq.getUserId());
			  UserMasterEntity userr = user.get();
			  logger.info("------>5"+ userr.getEmailId());
			  Optional<StreamMaster> stream = streamRespository.findById(veriReq.getStreamId()); 
			  StreamMaster str = stream.get();
			  logger.info("------>6"+ str.getStreamName());
			  RequestTypeResponse reqMaster = reqTypeService.getNameById(veriReq.getRequestType());
			  logger.info("------>7"+ reqMaster.getRequestType());
			  
			  SemesterEntity sem=semesterService.getSemById(veriReq.getSemId());
				
			  BranchMasterEntity branch=branchMasterService.getbranchById(veriReq.getBranchId());
				
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
			  resp.setRequest_type_id(reqMaster.getId());
			  resp.setRequest_type(reqMaster.getRequestType());
			  resp.setBranch_nm(branch.getBranchName());
			  resp.setSemester(sem.getSemester());
			  resp.setMonthOfPassing(veriReq.getMonthOfPassing());
			 // run query here which will update 'assigned_to' column with userId value
			 // for now assign any value other than 0 (assign 1)
			// Long a = (long) 1;
			 veriReq.setAssignedTo(id);
			 verificationReqRepository.save(veriReq);
			 logger.info("------>saved--->8");
			 // ON logout, change 'assignedTo' field back to 0
			 
			  List.add(resp);
		 }
		 
		 System.out.println("----------"+ verReq);
		 
		 return List;
		 }catch(Exception e) {
			 throw new Exception(e.getMessage());
		 }
		 
	 }

	 
	 
	@Override
	public List<StudentVerificationDocsResponse> verifyDocument(Long id) {

		List<StudentVerificationDocsResponse> verificationDataList = new ArrayList<StudentVerificationDocsResponse>();
		logger.info("verifyDocument");
		
		try {
		 List<VerificationRequest> verifierData = verificationReqRepository.getDataByIdToVerify(id);
				 logger.info("just outside for");
				 if(!verifierData.isEmpty()) {
		        for(VerificationRequest veriReq: verifierData) {
		        	logger.info("just inside for");
					 StudentVerificationDocsResponse resEntity = new StudentVerificationDocsResponse();
					 
					 PassingYearMaster year = yearOfPassService.getYearById(veriReq.getYearOfPassingId());
				
					 
					 StreamMaster stream = streamService.getNameById(veriReq.getStreamId());
					 
					 CollegeResponse college = collegeService.getNameById(veriReq.getCollegeId());
					 
					 
					 resEntity.setId(veriReq.getId());
					 resEntity.setEnroll_no(veriReq.getEnrollmentNumber());
					 resEntity.setUpload_doc_path("/verifier/getimage/VR/"+veriReq.getId());
					 resEntity.setYear(year.getYearOfPassing());
					 
					 logger.info("VR upload set just above");
					 
					 UniversityStudentDocument doc = stuDocService.getDocDataByFourFields(veriReq.getEnrollmentNumber(), veriReq.getYearOfPassingId(), veriReq.getSemId(),
							 veriReq.getStreamId(), veriReq.getMonthOfPassing());
					 
					 if(doc != null) {
					 resEntity.setOriginalDocUploadFilePath("/verifier/getimage/U/"+doc.getId());
					 }
					 logger.info("University upload path set just above");
					 verificationDataList.add(resEntity);
		        }
				 }else {
					 StudentVerificationDocsResponse resEntity = new StudentVerificationDocsResponse();
					 
					 UniversityStudentDocument doc = stuDocService.getUniversityDocDataById(id);
					 
					 if(doc != null) {
					 resEntity.setOriginalDocUploadFilePath("/verifier/getimage/U/"+doc.getId());
					 }
					 logger.info("University upload path set just above");
					 verificationDataList.add(resEntity);
				 }
		} catch(Exception e) {
            throw new ExceptionConverter(e);
      }
				 
		return verificationDataList;
	}



	@Override
	public List<StudentVerificationDocsResponse> setStatusForVerifierDocument(StatusChangeRequest statusChangeRequest) throws Exception {
		
			System.out.println("******VerifierServiceImpl setStatusForVerifierDocument******" +statusChangeRequest.getRemark());
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
		    Date date = new Date();  
		    String currentDate = formatter.format(date);  
			
			VerificationRequest entt =  verificationReqRepository.findById(statusChangeRequest.getId());
//			VerificationRequest entt = ent.get();
			System.out.println("------------"+ entt.getDocStatus() + entt.getApplicationId());
//			Long roleId = Long.parseLong(statusChangeRequest.getRoleid());
			entt.setDocStatus(statusChangeRequest.getStatus());
			entt.setVerifiedBy(statusChangeRequest.getVerifiedby());
			//entt.setClosedDate(date);
			entt.setVerId(statusChangeRequest.getVerifiedby());
			entt.setVerifierStatus(statusChangeRequest.getStatus());
			entt.setVerActionDate(new Date());
			
			if(statusChangeRequest.getCgpi() != null) {
				entt.setCgpi(statusChangeRequest.getCgpi());
			}
//			gets reason for rejection in remark
			if (statusChangeRequest.getRemark() != null) {
				entt.setRemark("VR_"+currentDate+"-"+statusChangeRequest.getRemark());
			}
			if(statusChangeRequest.getEditreason() != null) {
				if(entt.getEditReason() == null) {
					entt.setEditReason("Verifier%"+currentDate+"%"+statusChangeRequest.getEditreason());
				}else {
					entt.setEditReason(entt.getEditReason()+"#Verifier%"+currentDate+"%"+statusChangeRequest.getEditreason());
				}
			}
			entt.setRemDate(null);
			entt.setRemEmailCount((long)0);
			verificationReqRepository.save(entt);
			
			UserResponse ent = userService.getUserInfoById(entt.getUserId());
			
//			send mail to candidate requesting him to edit his verification request
			if(statusChangeRequest.getStatus().equalsIgnoreCase("Ver_Request_Edit")) {
//				send mail to candidate to edit his request
				emailService.sendRequestEditMail(ent.getEmail(), "Edit Verification Request.");
				
			}
			
//			if(statusChangeRequest.getStatus().equalsIgnoreCase("Approved") || 
//					statusChangeRequest.getStatus().equalsIgnoreCase("SV_Approved")) {
//				
//				UserResponse ume = userService.getUserInfoById(entt.getUserId());
//				CutomizationEntity cutomizationEntity=customizationRepository.findByRoleId(roleId);
//				if(cutomizationEntity!=null)
//				{
//					if(cutomizationEntity.getEmailFlag().equals("Y")) {
//				emailService.sendStatusMail(ume.getEmail(), entt.getId() , statusChangeRequest.getStatus());
//					}
//				}
//				}
		
		return null;
	}



	@Override
	public Integer updateListonLogout(long id) throws Exception {
		System.out.println("******VerifierServiceImpl updateListonLogout******" );
		logger.info("id"+id);
		Integer rowcnt=verificationReqRepository.updateList(id);

		logger.info("List"+rowcnt);
		return rowcnt;
	}



	@Override
	public List<VerificationResponse> getRejectedRequests() {
		
		System.out.println("******VerifierServiceImpl getRejectedRequests******" );
		
		List<VerificationResponse> List = new ArrayList<>();
//		 try {
		 List<VerificationRequest> verReq = verificationReqRepository.findByDocStatus("Ver_Request_Edit");
		 
		 logger.info("------>1"+verReq.toString());
		 
		 for(VerificationRequest veriReq: verReq) {
			 
			 logger.info("------>2");
			 VerificationResponse resp = new VerificationResponse();
			  
			  PassingYearMaster year =
			  yearOfPassService.getYearById(veriReq.getYearOfPassingId());
			  logger.info("------>3"+ year.getYearOfPassing());
			  System.out.println(veriReq.getDocumentId());
			  
			  DocumentMaster doc = documentService.getNameById(veriReq.getDocumentId());
			  logger.info("------>4"+ doc.getDocumentName());
			  Optional<UserMasterEntity> user = userRepository.findById(veriReq.getUserId());
			  UserMasterEntity userr = user.get();
			  logger.info("------>5"+ userr.getEmailId());
			  Optional<StreamMaster> stream = streamRespository.findById(veriReq.getStreamId()); 
			  StreamMaster str = stream.get();
			  logger.info("------>6"+ str.getStreamName());
			  RequestTypeResponse reqMaster = reqTypeService.getNameById(veriReq.getRequestType());
			  logger.info("------>7"+ reqMaster.getRequestType());
			  
			  SemesterEntity sem=semesterService.getSemById(veriReq.getSemId());
				
			  BranchMasterEntity branch=branchMasterService.getbranchById(veriReq.getBranchId());
				
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
			  resp.setRequest_type_id(reqMaster.getId());
			  resp.setRequest_type(reqMaster.getRequestType());
			  resp.setBranch_nm(branch.getBranchName());
			  resp.setSemester(sem.getSemester());
			  resp.setMonthOfPassing(veriReq.getMonthOfPassing());
			 
			  List.add(resp);
		 }
		 
		 System.out.println("----------"+ verReq);
		 
		 return List;
//		 }catch(Exception e) {
//			 throw new Exception(e.getMessage());
//		 }
		
//		return null;
	}



	@Override
	public List<EditReasonResponse> getRejectionTrailById(Long id) {
		
		System.out.println("*****VerifierServiceImpl getRejectionTrailById*****"+ String.valueOf(id));
		
		Optional<VerificationRequest> verReqq = verificationReqRepository.findById(id);
		VerificationRequest verReq = verReqq.get();
		
		List<EditReasonResponse> list = new ArrayList<>();
		
		String str = verReq.getEditReason();
		if(str != null) {
			String[] listToSplit;
			
			listToSplit = str.split("#");
			
			for(int i = 0; i<listToSplit.length; i++) {
			logger.info(listToSplit[i]);
			
			EditReasonResponse editResp = new EditReasonResponse();
			
			String[] listToSet = listToSplit[i].split("%");
			
			editResp.setRole(listToSet[0]);
			editResp.setDate(listToSet[1]);
			editResp.setReason(listToSet[2]);
			
			list.add(editResp);
			
			}
			return list;
		}
		return null;
	}

	
}
