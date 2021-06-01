package com.scube.edu.util;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.scube.edu.model.UserMasterEntity;
import com.scube.edu.repository.UserRepository;



@Component
public class UnregisterUser {
	
	private static final Logger logger = LoggerFactory.getLogger(UnregisterUser.class);

	@Autowired
	UserRepository userRepository;
	
	
	
	@Scheduled(cron = "${UnregisterUser.cronTime}")	
	//@Scheduled(cron = "*/60 * * * * *")
	public void removeUnverifiedUser() {
		
		    //  This scheduler deletes record from user table after every 5 min
		   //Criteria for deletion is : records for which email veri. is not done in 24 hours.
		
		 System.out.println("Method executed at every 5 seconds. Current time is :: "+ new Date());
		 
		 List<UserMasterEntity> userEntities  = userRepository.findByEmailVerificationStatus("N");
		 System.out.println("LISTT-------   size-----"+userEntities.size());
		 
          for(UserMasterEntity entity:userEntities) {
        	 
        	    int minutes = userRepository.gethoursToValidateTheLink(entity.getId());
        	    logger.info("---------minutes-------"+minutes);
   		  
   		         //24 hrs  = 1440 minutes
   		  
   		          if(minutes > 1440 ) 
   		          {
   		        	 logger.info("---------inside if-------"+minutes);
   		        	 userRepository.deleteById(entity.getId());
	              }
          }   		          
   		          
       }
	
	
	
	
}
