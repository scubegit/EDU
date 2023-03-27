package com.scube.edu.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scube.edu.model.BranchMasterEntity;
import com.scube.edu.model.DocumentMaster;
import com.scube.edu.model.PassingYearMaster;
import com.scube.edu.model.SemesterEntity;
import com.scube.edu.model.StreamMaster;
import com.scube.edu.model.UserMasterEntity;
import com.scube.edu.model.VerificationRequest;
import com.scube.edu.repository.RaiseDisputeRepository;
import com.scube.edu.repository.UserRepository;
import com.scube.edu.repository.VerificationRequestRepository;
import com.scube.edu.response.MisResponse;
import com.scube.edu.response.RequestTypeResponse;
import com.scube.edu.response.VerificationResponse;

@Service
public class MisServiceImpl implements MisService {
	
	@Autowired
	VerificationRequestRepository verificationReqRepository;
	
	@Autowired 
	StreamService streamService;
	
	@Autowired
	YearOfPassingService yearOfPassService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired 
	SemesterService semesterService;
	
	@Autowired
	BranchMasterService branchMasterService;
	
	
	

	private static final Logger logger = LoggerFactory.getLogger(EmployerServiceImpl.class);

	public List<MisResponse> getMisList(String fromDate, String toDate)
	{
		
		logger.info("*******MisServiceImplCheck range Date*******"+fromDate+":"+toDate);
		logger.info("*******MisServiceImpl getMisList*******");
		
		List<MisResponse> responseList = new ArrayList<>();
		
		logger.info("*******MisServiceImplCheck responseList *******"+responseList);
		try
		{
		logger.info("Check 12===>>>>>"+fromDate+"   "+toDate);
		
			List<VerificationRequest> list = verificationReqRepository.getVerificationRequestByDateRange(fromDate, toDate);
			logger.info("*******MisServiceImplCheck range Date inside try catch*******"+fromDate+":"+toDate);
			for(VerificationRequest req: list) {
				
				logger.info("*******MisServiceImpl getMisList333*******"+req.getId());

				
			MisResponse resp = new MisResponse();
			
			PassingYearMaster year = yearOfPassService.getYearById(req.getYearOfPassingId());
			
			StreamMaster stream = streamService.getNameById(req.getStreamId());
			
			logger.info("*******MisServiceImpl stream*******"+stream.getId());
			
			logger.info("*******MisServiceImpl req.getStreamId()*******"+req.getStreamId());
			
			SemesterEntity semesterEntity=semesterService.getSemById(req.getSemId());
			
			logger.info("*******MisServiceImpl req.getSemId()*******"+semesterEntity);
			
			logger.info("*******MisServiceImpl semesterEntity*******"+req.getSemId());
			
			BranchMasterEntity branchMasterEntity = branchMasterService.getbranchById(req.getBranchId());
			
		//	Optional<UserMasterEntity> user = userRepository.findById(req.getVerifiedBy());
			
			Optional<UserMasterEntity> user = userRepository.findById(req.getUserId());
			
			logger.info("*******MisServiceImpl user*******"+req.getUserId());
			
			UserMasterEntity userr = user.get(); // verifier User
			
			resp.setId(req.getId());
			resp.setFirst_name(req.getFirstName());
			resp.setLast_name(req.getLastName());
			resp.setStream_name(stream.getStreamName());
			resp.setSemester(semesterEntity.getSemester());
			resp.setBranch_name(branchMasterEntity.getBranchName());
			resp.setYear(year.getYearOfPassing());
			resp.setEnroll_no(req.getEnrollmentNumber());
			resp.setRegisterdemailid(userr.getUsername());
			resp.setRegisteredcontactno(userr.getPhoneNo());
			resp.setCompany_name(userr.getCompanyName());
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
			String strDate= formatter.format(req.getCreatedate());
			
			resp.setPaymentdate(strDate);
			
			resp.setTotalamount(req.getDocAmt());
			resp.setSecurAmt(req.getDocSecurCharge());
			resp.setUnivamount(req.getDocUniAmt());
			
			Long gstval =req.getDosAmtWithGst()-req.getDocAmt();
			
			resp.setGst(gstval);
			
			resp.setTransactionId(req.getPaymentId());
			resp.setActualstatus(req.getDocStatus());
			resp.setMonthOfPassing(req.getMonthOfPassing());
			
			if(req.getRemark() != null) {
				resp.setRemark(req.getRemark());
			}else {
				resp.setRemark("");
			}
			
			String docSt=req.getDocStatus(); 
			String currSt="";
		    String bucket="";
			//'','','','',''
			
			
			  if (docSt.equals("UN_Approved_Pass") || docSt.equals("UN_Approved_Fail") ||
			  docSt.equals("Uni_Auto_Approved")||docSt.equals("UN_Rejected") ||
			  docSt.equals("Uni_Auto_Rejected") || docSt.equals("SVD_Approved_Pass") ||
			  docSt.equals("SVD_Approved_Fail") || docSt.equals("SVD_Rejected")) {
			  currSt="Closed"; } else currSt="Open";
			  
			  
			  if (docSt.equals("Requested"))
				  bucket="Verifier"; 
			  else if
			  (docSt.equals("Rejected") || docSt.equals("offline") || docSt.equals("Unable To Verify")||docSt.equals("SV_Offline") ||  docSt.equals("SV_Unable To Verify")) 
				  bucket="SuperVerifier";
			  else if
			  (docSt.equals("Approved_Pass") || docSt.equals("Approved_Fail") ||  docSt.equals("SV_Approved_Pass")||docSt.equals("SV_Approved_Fail") ||  docSt.equals("SV_Rejected"))
				  bucket="University";
			  else if
			  (docSt.equalsIgnoreCase("Ver_Request_Edit") || docSt.equalsIgnoreCase("Uni_Request_Edit"))
				  bucket = "Rejection";
			 
			resp.setDoc_status(currSt);
				
			resp.setBucket(bucket);
			
			String strCloseDate="";
						
			if(req.getClosedDate()!=null)
				strCloseDate=formatter.format(req.getClosedDate());

			
			
			resp.setClosedDate(strCloseDate);
			
			
			
			/*
			 * DocumentMaster doc = documentService.getNameById(req.getDocumentId());
			 * 
			 * Optional<UserMasterEntity> reqUser =
			 * userRepository.findById(req.getUserId()); UserMasterEntity reqUserr =
			 * reqUser.get(); // User that created the request
			 * 
			 * if(req.getRequestType() != null) { RequestTypeResponse request =
			 * reqTypeService.getNameById(req.getRequestType());
			 * resp.setRequest_type_id(request.getRequestType()); }
			 * 
			 * SemesterEntity sem=semesterService.getSemById(req.getSemId());
			 * 
			 * BranchMasterEntity
			 * branch=branchMasterService.getbranchById(req.getBranchId());
			 */
			
			//resp.setDoc_status(req.getDocStatus());
			/*
			 * resp.setId(req.getId()); resp.setApplication_id(req.getApplicationId());
			 * //resp.setCompany_name(reqUserr.getCompanyName()); //
			 * resp.setDoc_name(doc.getDocumentName()); //
			 * resp.setEnroll_no(req.getEnrollmentNumber());
			 * resp.setFirst_name(req.getFirstName()); resp.setLast_name(req.getLastName());
			 * resp.setRemark(req.getRemark()); resp.setUser_id(req.getUserId());
			 * resp.setVer_req_id(req.getVerRequestId());
			 * resp.setYear(year.getYearOfPassing());
			 * resp.setUpload_doc_path(req.getUploadDocumentPath());
			 * resp.setStream_name(stream.getStreamName()); resp.setReq_date(strDate);
			 * resp.setVerifier_name(userr.getFirstName() + " " + userr.getLastName());
			 * //resp.setBranch_nm(branch.getBranchName());
			 * //resp.setSemester(sem.getSemester());
			 * resp.setMonthOfPassing(req.getMonthOfPassing()); resp.setCgpi(req.getCgpi());
			 */
			
			
			
			
			
			responseList.add(resp);
			
		}
		
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
			e.printStackTrace();
			logger.error(e.getMessage());
			
		}
			
		return responseList;
		
		
		
		
		
	}
}
