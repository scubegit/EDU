
package com.scube.edu.service;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.Base64;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	Base64.Encoder baseEncoder = Base64.getEncoder();
	   
	   void sendEmail(String emailId, String encodeEmail) throws MessagingException {
		   

		   String to = emailId;

	        // Sender's email ID needs to be mentioned
	        String from = "universityscube@gmail.com";

	        // Assuming you are sending email from through gmails smtp
	        String host = "smtp.gmail.com";

	        Properties properties = System.getProperties();
		   
		    properties.put("mail.smtp.host", host);
	        properties.put("mail.smtp.port", "465");
	        properties.put("mail.smtp.ssl.enable", "true");
	        
	        properties.put("mail.smtp.auth", "true");



	        // Get the Session object.// and pass username and password
	        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

	            protected PasswordAuthentication getPasswordAuthentication() {

	                return new PasswordAuthentication("universityscube@gmail.com", "edu@1234");

	            }

	        });

	        // Used to debug SMTP issues
	        session.setDebug(true);

	        try {
	            // Create a default MimeMessage object.
	            MimeMessage message = new MimeMessage(session);

	            // Set From: header field of the header.
	            message.setFrom(new InternetAddress(from));

	            // Set To: header field of the header.
	            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

	            // Set Subject: header field
	            message.setSubject("Password Reset Link!!!");

	            // Now set the actual message
	                       
                String vmFileContent = "Hello User, <br><br> We have received your reset password request .Please click link below to reset  your password.<br><a href='http://192.168.0.225:4200/resetPassword?emailId="+encodeEmail+"'><strong>Reset Link</strong></a> "+
                                      "<br><br><br> Thanks,<br>Team University";

                //  Send the complete message parts
                message.setContent(vmFileContent,"text/html");

	            System.out.println("sending...");
	            // Send message
	             Transport.send(message);
	            
	           // javaMailSender.send(message);
	            System.out.println("Sent message successfully....");
	            

	        } catch (MessagingException e) {
	            throw new RuntimeException(e);
	        }

	     
	    }
	   

	   
	   
	   
	  void sendVerificationEmail(String emailId) throws MessagingException {
		  
		   String encodeEmail = baseEncoder.encodeToString(emailId.getBytes(StandardCharsets.UTF_8)) ;

		   String to = emailId;

	        // Sender's email ID needs to be mentioned
	        String from = "universityscube@gmail.com";

	        // Assuming you are sending email from through gmails smtp
	        String host = "smtp.gmail.com";

	        Properties properties = System.getProperties();
		   
		    properties.put("mail.smtp.host", host);
	        properties.put("mail.smtp.port", "465");
	        properties.put("mail.smtp.ssl.enable", "true");
	        properties.put("mail.smtp.auth", "true");


	        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
	                 protected PasswordAuthentication getPasswordAuthentication() {

	                return new PasswordAuthentication("universityscube@gmail.com", "edu@1234");
	            }

	        });
	        
	        // Used to debug SMTP issues
	        session.setDebug(true);
	        try {
	          
	            MimeMessage message = new MimeMessage(session);

	            message.setFrom(new InternetAddress(from));

	            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

	            message.setSubject("Email Verification Link!!!");

	            // Now set the actual messageHello User,
	         
                String vmFileContent = "Hello User, <br><br> We have received your registration request .Please click link below to verify your email account.<br><a href='http://192.168.0.225:4200/emailVerification?emailId="+encodeEmail+"'><strong>localhost:8081/EDU/api/auth/verifyEmail/"+encodeEmail+"</strong></a> "+
                                       " <br>If you do not use this link within 24 hours , it will expire. Post that you will need to register again. <br><br> Thanks,<br>Team University";

                //  Send the complete message parts
                message.setContent(vmFileContent,"text/html");

	            System.out.println("sending...");
	         
	            Transport.send(message);
	            System.out.println("Sent message successfully....");
	            
	        } catch (MessagingException e) {
	        	
	        	
	            throw new RuntimeException(e);
	        }
	   }
	  
	  

}

