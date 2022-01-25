package com.scube.edu.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.scube.edu.model.VerificationRequest;
import com.scube.edu.repository.FinancialYearRepository;
import com.scube.edu.repository.RemainderMailtoEditRequestRepository;
import com.scube.edu.repository.VerificationRequestRepository;
import com.scube.edu.response.UserResponse;
import com.scube.edu.service.EmailService;
import com.scube.edu.service.UserService;

@Component
public class ReminderMailToEditRequest {
	
	private static final Logger logger = LoggerFactory.getLogger(UnregisterUser.class);

	@Autowired
	RemainderMailtoEditRequestRepository remainderMailtoEditRequestRepository;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	VerificationRequestRepository verificationReqRepo;
	
	@Autowired
	FinancialYearRepository finYearRepo;
	
	@Autowired
	UserService userService;
	
	@Scheduled(cron = "0 0 10 * * ?")	
      public void getListofEditRequest() throws MessagingException, Exception {
		
		logger.info("********getListofEditRequest***********");
		
		Date currentdate=new Date();
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		String currentDate = formatter.format(date);
		
		List <VerificationRequest> list=remainderMailtoEditRequestRepository.getListOfEditRequest();
          System.out.println("list="+list.size());	
          for(VerificationRequest vr: list) {
        	  try {
        		  UserResponse ent = userService.getUserInfoById(vr.getUserId());
        		  if(vr.getRemDate() == null) {
        			  emailService.sendRequestEditMail(vr,ent.getEmail(), "Verification Request  - Rejected  - Request ID – Reminder "+vr.getRemEmailCount()+1);
        			  vr.setRemDate(currentDate);
	            	  vr.setRemEmailCount(vr.getRemEmailCount() + 1);
        		  }else {
        		  
	        		  SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	            	  Date d1 = sdf.parse(vr.getRemDate());
	        		  
	//	        	  Date requestdAte=vr.getUpdatedate();
		              int diffInDays = (int) ((currentdate.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24))%365;
		              System.out.println("diffInDays"+diffInDays);
		              if(diffInDays >= 2) {
		            	  
			      			  if(vr.getRemEmailCount() < 3) { // count less than 3
			      				  emailService.sendRequestEditMail(vr,ent.getEmail(), "Verification Request  - Rejected  - Request ID - "+vr.getApplicationId()+" – Reminder "+vr.getRemEmailCount()+1);
			      				  vr.setRemDate(currentDate);
				            	  vr.setRemEmailCount(vr.getRemEmailCount() + 1);
				            	  System.out.println("Reminder mail sent successfully to="+ent.getEmail());
			      			 }
		              }
        		  }
        		  remainderMailtoEditRequestRepository.save(vr);
        	  }catch(Exception ex) {
	            	  ex.printStackTrace();
	              }
              }
          }
		
		
	}
