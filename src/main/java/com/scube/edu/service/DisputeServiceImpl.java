package com.scube.edu.service;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.lowagie.text.BadElementException;
import com.scube.edu.model.RaiseDespute;
import com.scube.edu.model.VerificationRequest;
import com.scube.edu.repository.RaiseDisputeRepository;
import com.scube.edu.repository.VerificationRequestRepository;
import com.scube.edu.request.DisputeRequest;
import com.scube.edu.request.UpdateDisputeRequest;




@Service
public class DisputeServiceImpl implements DisputeService{

	private static final Logger logger = LoggerFactory.getLogger(DisputeServiceImpl.class);
	
	@Autowired
	RaiseDisputeRepository disputeRepo;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	VerificationRequestRepository verificationReqRepository;
	
	@Override
	public boolean saveDispute(DisputeRequest disputeReq, HttpServletRequest request) throws Exception {
		
		logger.info("********DisputeServiceImpl saveDispute********");
		
		RaiseDespute rd = new RaiseDespute();
		
//		RaiseDespute check = disputeRepo.findByApplicationId(disputeReq.getApplication_id());
		RaiseDespute check = disputeRepo.findByVerificationId(disputeReq.getId());
		
		if(Objects.isNull(check)) {
			System.out.println("NULL---");
		
		rd.setVerificationId(disputeReq.getId());
		rd.setApplicationId(disputeReq.getApplication_id());
		rd.setContactPersonEmail(disputeReq.getEmail());
		rd.setContactPersonPhone(disputeReq.getPhone_no());
		rd.setReasonForDispute(disputeReq.getReason());
		rd.setCreateby(disputeReq.getCreated_by());
		rd.setComment(disputeReq.getComment());
		rd.setContactPersonName(disputeReq.getContact_person_name()); 
		rd.setStatus(disputeReq.getStatus());
		// dummy values of comment, contact person name and status have been sent through postman right now!
		RaiseDespute raised = disputeRepo.save(rd);
		logger.info("------" + raised.getId());
		//send email to applicant from here
		emailService.sendDisputeSaveMail(disputeReq.getEmail(), disputeReq.getApplication_id(), raised.getId());
		
		return true;
		}
		return false;
		
	}
	
	 @Value("${file.imagepath-dir}")
     private String imageLocation;

	@Override
	public boolean updateDispute(UpdateDisputeRequest updateDisputeReq, HttpServletRequest request) throws Exception {
		
		logger.info("********DisputeServiceImpl updateDispute********"+ updateDisputeReq.getStatus());
		
		Optional<RaiseDespute> rd = disputeRepo.findById(updateDisputeReq.getId());
		RaiseDespute rdd = rd.get();
		logger.info(String.valueOf(rdd.getVerificationId()));
		
		Optional<VerificationRequest> vr = verificationReqRepository.findById(rdd.getVerificationId());
		VerificationRequest vrr = vr.get();
		
		if(vrr.getDocStatus().equalsIgnoreCase("UN_Rejected") || vrr.getDocStatus().equalsIgnoreCase("Uni_Auto_Rejected")) { 
				 
//			|| verr.getDocStatus().equalsIgnoreCase("UN_Rejected")
			if(updateDisputeReq.getStatus().equalsIgnoreCase("CL")) {
				
				rdd.setStatus(updateDisputeReq.getStatus());
				disputeRepo.save(rdd);
				
				try {
					emailService.sendStatusChangeMail(rdd.getContactPersonEmail(), rdd.getVerificationId(), rdd.getId(), imageLocation);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				vrr.setDocStatus(updateDisputeReq.getUpdatedstatus());
				//vrr.setClosedDate(new Date());
				verificationReqRepository.save(vrr);
				return true;
				// send mail saying that after checking dispute status has been changed + PDF 
				
			}
			if(updateDisputeReq.getStatus().equalsIgnoreCase("NCL")) {
				
				rdd.setStatus(updateDisputeReq.getStatus());
				disputeRepo.save(rdd);
				
				emailService.sendNoStatusChangeMail(rdd.getContactPersonEmail(), rdd.getId());
				
				vrr.setDocStatus("SVD_Rejected");
				//vrr.setClosedDate(new Date());
				verificationReqRepository.save(vrr);
				return true;
			}
		
		}
		
		if(vrr.getDocStatus().equalsIgnoreCase("UN_Approved_Pass") || vrr.getDocStatus().equalsIgnoreCase("Uni_Auto_Approved_Pass")||vrr.getDocStatus().equalsIgnoreCase("UN_Approved_Fail") || vrr.getDocStatus().equalsIgnoreCase("Uni_Auto_Approved_Fail") ) {

//			|| verr.getDocStatus().equalsIgnoreCase("UN_Approved")
			if(updateDisputeReq.getStatus().equalsIgnoreCase("CL")) {
				
				rdd.setStatus(updateDisputeReq.getStatus());
				disputeRepo.save(rdd);
				
				try {
					emailService.sendStatusChangeMail(rdd.getContactPersonEmail(), rdd.getVerificationId(), rdd.getId(), imageLocation);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				vrr.setDocStatus(updateDisputeReq.getUpdatedstatus());
				verificationReqRepository.save(vrr);
				return true;
				// send mail saying the previously approved record's status has been changed and also attach PDF
//				emailService.sendStatusChangeMail(rdd.getContactPersonEmail(), rdd.getVerificationId(), rdd.getId());
			}
			if(updateDisputeReq.getStatus().equalsIgnoreCase("NCL")) {
				
				rdd.setStatus(updateDisputeReq.getStatus());
				disputeRepo.save(rdd);
				
				emailService.sendNoStatusChangeMail(rdd.getContactPersonEmail(), rdd.getId());
				
				if(vrr.getDocStatus().equalsIgnoreCase("UN_Approved_Pass")||vrr.getDocStatus().equalsIgnoreCase("Uni_Auto_Approved_Pass"))
				{
					vrr.setDocStatus("SVD_Approved_Pass");
				}
				else {
					vrr.setDocStatus("SVD_Approved_Fail");
				}
				verificationReqRepository.save(vrr);
				return true;
			}
			
		}
		
		return false;
	}
	
}
