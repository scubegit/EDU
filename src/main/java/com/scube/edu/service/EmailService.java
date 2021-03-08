package com.scube.edu.service;

import java.sql.Date;
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
	
	   @Autowired
	    private JavaMailSender javaMailSender;
	   
	   void sendEmail() throws MessagingException {

		/*
		 * SimpleMailMessage msg = new SimpleMailMessage();
		 * 
		 * msg.setTo("manishapopalghat315@gmail.com");
		 * 
		 * msg.setSubject("Testing from Spring Boot");
		 * msg.setText("Hello World \n Spring Boot Email");
		 * 
		 * javaMailSender.send(msg);
		 */
	        
	        
		/*
		 * MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		 * 
		 * MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,
		 * true);
		 * 
		 * mimeMessageHelper.setSubject("Testing from Spring Boot");
		 * 
		 * mimeMessageHelper.setTo("manishapopalghat315@gmail.com");
		 * mimeMessageHelper.setText("Hello World \\n Spring Boot Email",true);
		 * 
		 * javaMailSender.send(mimeMessageHelper.getMimeMessage());
		 */
		   
		   
		   
		/*
		 * Properties props = new Properties(); props.put("mail.smtp.auth", "true");
		 * props.put("mail.smtp.starttls.enable", "true"); props.put("mail.smtp.host",
		 * "smtp.gmail.com"); props.put("mail.smtp.port", "587");
		 * 
		 * 
		 * Session session = Session.getInstance(props, new javax.mail.Authenticator() {
		 * protected PasswordAuthentication getPasswordAuthentication() { return new
		 * PasswordAuthentication("universityscube@gmail.com", "edu@1234"); } });
		 * Message msg = new MimeMessage(session); msg.setFrom(new
		 * InternetAddress("universityscube@gmail.com"));
		 * 
		 * msg.setRecipients(Message.RecipientType.TO,
		 * InternetAddress.parse("manishapopalghat315@gmail.com"));
		 * msg.setSubject("Tutorials point email");
		 * msg.setContent("Tutorials point email", "text/html");
		 * 
		 * 
		 * MimeBodyPart messageBodyPart = new MimeBodyPart();
		 * messageBodyPart.setContent("Tutorials point email", "text/html");
		 * 
		 * Multipart multipart = new MimeMultipart();
		 * multipart.addBodyPart(messageBodyPart); // MimeBodyPart attachPart = new
		 * MimeBodyPart();
		 * 
		 * //attachPart.attachFile("/var/tmp/image19.png"); //
		 * multipart.addBodyPart(attachPart); msg.setContent(multipart);
		 * Transport.send(msg);
		 */
		   
		   
		   Properties props = new Properties();
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.starttls.required","true");

	        String host = "smtp.gmail.com";
	        int port = 25;
	        String userName = "universityscube@gmail.com";
	        String password = "edu@1234";

	        String mailTo = "scubeuser8@gmail.com";
	        String subject = "Hello my friend~";

	        JavaMailSenderImpl sender = new JavaMailSenderImpl();
	        sender.setJavaMailProperties(props);
	        sender.setHost(host);
	        sender.setPort(port);
	        sender.setUsername(userName);
	        sender.setPassword(password);
	    
	        sender.setProtocol("smtp");

	        MimeMessage message = sender.createMimeMessage();
	        MimeMessageHelper helper;
	        try {
	            helper = new MimeMessageHelper(message, true);
	            helper.setTo(mailTo);
	            helper.setSubject(subject);
	            helper.setText("test test");
	        } catch (MessagingException e) {
	            throw new RuntimeException(e);
	        }

	        sender.send(message);
		   
		   
		   
		   
		   
		   
		   
	    }
}
