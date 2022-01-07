package com.scube.edu.util;

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
	FinancialYearRepository finYearRepo;
	
	@Autowired
	UserService userService;
	
	@Scheduled(cron = "*/10 * * * * ?")	
      public void getListofEditRequest() throws MessagingException, Exception {
		
		logger.info("********getListofEditRequest***********");
		
		Date currentdate=new Date();
		List <VerificationRequest> list=remainderMailtoEditRequestRepository.getListOfEditRequest();
          System.out.println("list="+list.size());	
          for(VerificationRequest vr: list) {
        	  Date requestdAte=vr.getUpdatedate();
              int diffInDays = (int) ((currentdate.getTime() - requestdAte.getTime()) / (1000 * 60 * 60 * 24));
              System.out.println("diffInDays"+diffInDays);
              if(diffInDays==2||diffInDays==4||diffInDays==6) {
            	UserResponse ent = userService.getUserInfoById(vr.getUserId());
      		  emailService.sendRequestEditMail(ent.getEmail());
        	  System.out.println("Reminder mail sent successfully to="+ent.getEmail());
              }
          }
		
		
	}
}
