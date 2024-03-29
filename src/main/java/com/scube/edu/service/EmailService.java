
package com.scube.edu.service;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;
import com.lowagie.text.pdf.draw.VerticalPositionMark;
import com.scube.edu.model.BranchMasterEntity;
import com.scube.edu.model.DocumentMaster;
import com.scube.edu.model.PassingYearMaster;
import com.scube.edu.model.SemesterEntity;
import com.scube.edu.model.StreamMaster;
import com.scube.edu.model.UserMasterEntity;
import com.scube.edu.model.VerificationRequest;
import com.scube.edu.repository.VerificationRequestRepository;
import com.scube.edu.request.SendQueryFromHomeRequest;
import com.scube.edu.response.UserResponse;
import com.scube.edu.util.FileStorageService;

@Service
public class EmailService {

	Base64.Encoder baseEncoder = Base64.getEncoder();

	@Autowired
	VerificationRequestRepository verificationReqRepository;

	@Autowired
	UserService userService;

	@Autowired
	BranchMasterService branchService;

	@Autowired
	SemesterService semService;

	@Autowired
	StreamService streamService;

	@Autowired
	YearOfPassingService yearOfPassService;

	@Autowired
	DocumentService documentService;

	private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

	@Value("${file.Email-Files}")
    private String emailFileLocation;
	
	@Value("${file.imagepath-dir}")
    private String logoimageLocation;
	
//	@Value("${file.Email-Files-test}")
//    private String emailFileLocationTest;
	
//	@Value("${file.imagepathtest-dir}")
//    private String logoimageLocationTest;
	
	@Value("${file.awsORtest}")
    private String awsORtest;

	@Value("${from.mail.id}")
    private String fromMailID;
	
	@Value("${to.mail.id}")
    private String toMailId;
	
	@Value("${CC.Mail.id}")
    private String CCMailid;
	
	@Value("${file.url-dir}")
    private String loginUrl;
	
	
	@Value("${new.from.mail.id}")
    private String newfromMailID;
	
	@Value("${email.pwd}")
    private String emailPwd;
	
	@Value("${email.smtp}")
    private String emailSmtp;
	
	
	
	public void sendRequestEditMail(VerificationRequest entt,String email, String subject) throws MessagingException,Exception{
		logger.info("*****emailService sendRequestEditMail*****"+ email);
		String to = email;
	//	String from = "support@educred.co.in";
	//	String host = "mail.educred.co.in";
		
		String name = entt.getFirstName();
		Long appId = entt.getApplicationId();
		String temp = entt.getEditReason();
		String[] list = temp.split("%");
		String reason = list[list.length - 1];
		
		Properties properties = System.getProperties();

		properties.put("mail.smtp.host", emailSmtp);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");

		
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {

//	                return new PasswordAuthentication("universityscube@gmail.com", "edu@1234");
			//	return new PasswordAuthentication("support@educred.co.in", "EduCred$2021$");
				return new PasswordAuthentication(newfromMailID, emailPwd);

			}

		});
		session.setDebug(true);
		
		try {
			
			MimeMessage message = new MimeMessage(session);

			MimeBodyPart textBodyPart = new MimeBodyPart();

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(newfromMailID));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.addRecipient(RecipientType.CC, new InternetAddress(
		            "help@educred.co.in"));
			// Set Subject: header field
				message.setSubject(subject);
				
			String vmFileContent = "Dear "+name+", <br><br>"
					+ "Your request (Application ID: "+appId+") has been rejected, please login again and Fulfill the requirements by clicking on the Edit button against your record. <br><br>"
					+ "Rejection Details: "+reason+". <br><br>"
					+ "If you have any query you can writeback to help@educred.co.in <br><br>"
					+ "Click "+"<a href='https://"+loginUrl+"/University/verification'><strong>Here</strong></a>"+" to Login. <br><br>"
					+ "Team Educred.";

			
			message.setText(vmFileContent,"UTF-8", "html");

			Transport.send(message);
			System.out.println("Sent message successfully....");
			
		} catch (MessagingException e) {
			throw new RuntimeException(e);

		}   
		
	}
	
	void sendQueryMailFromLandingPage(SendQueryFromHomeRequest sendQueryFromHomeRequest) throws MessagingException,Exception{
		
		//String to = "support@educred.co.in";
		String to = "help@educred.co.in";
		String from = "support@educred.co.in";
		String host = "mail.educred.co.in";
		
		Properties properties = System.getProperties();

		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");

		properties.put("mail.smtp.auth", "true");
		
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {

//	                return new PasswordAuthentication("universityscube@gmail.com", "edu@1234");
				return new PasswordAuthentication("support@educred.co.in", "EduCred$2021$");

			}

		});
		session.setDebug(true);
		
		try {
			
			MimeMessage message = new MimeMessage(session);

			MimeBodyPart textBodyPart = new MimeBodyPart();

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject: header field
			message.setSubject(sendQueryFromHomeRequest.getEmailid());
			
			String vmFileContent = "Emailid:- "+sendQueryFromHomeRequest.getEmailid()+"\r"
					+ "Query:-"+ sendQueryFromHomeRequest.getQuery();
			
			message.setText(vmFileContent);
			Transport.send(message);
			System.out.println("Sent message successfully....");
			
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	
	void sendQueryMail(UserMasterEntity user, String query) throws MessagingException,Exception{
		
		//String to = "support@educred.co.in";
		String to = "help@educred.co.in";
		String from = "support@educred.co.in";
		String host = "mail.educred.co.in";
		
		Properties properties = System.getProperties();

		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");

		properties.put("mail.smtp.auth", "true");
		
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {

//	                return new PasswordAuthentication("universityscube@gmail.com", "edu@1234");
				return new PasswordAuthentication("support@educred.co.in", "EduCred$2021$");

			}

		});
		session.setDebug(true);
		
		try {
			
			MimeMessage message = new MimeMessage(session);

			MimeBodyPart textBodyPart = new MimeBodyPart();

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject: header field
			message.setSubject(user.getUsername());
			
			String vmFileContent = "Name: "+user.getFirstName()+" "+ user.getLastName()+"\r"+
									"Phone Number: "+user.getPhoneNo()+"\r"+
									"Email: "+user.getUsername()+"\r"+
									"Query:- "+ query;
			
			message.setText(vmFileContent);
			Transport.send(message);
			System.out.println("Sent message successfully....");
			
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
	}
	

	void sendEmail(String emailId, String encodeEmail, String url) throws MessagingException, Exception {

		String to = emailId;

		logger.info("-------->1"+emailId);

		// Sender's email ID needs to be mentioned
//	        String from = "universityscube@gmail.com";
	//	String from = "verify@educred.co.in";

		// Assuming you are sending email from through gmails smtp
//	        String host = "smtp.gmail.com";
//		String host = "mail.educred.co.in";

		Properties properties = System.getProperties();

		
		properties.put("mail.smtp.host", emailSmtp);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");
		

		// Get the Session object.// and pass username and password
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {

//				return new PasswordAuthentication("verify@educred.co.in", "EduCred$2021$");
				return new PasswordAuthentication(newfromMailID, emailPwd);


			}

		});
		logger.info("------>2");
		// Used to debug SMTP issues
		session.setDebug(true);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			MimeBodyPart textBodyPart = new MimeBodyPart();

			// Set From: header field of the header.
			//message.setFrom(new InternetAddress(from));
			message.setFrom(new InternetAddress(newfromMailID));
			

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject: header field
			message.setSubject("Password Reset Link!!!");

			// Now set the actual message

			logger.info("URL---------->" + url);
			
			  String vmFileContent =
			  "Hello User, <br><br> We have received your reset password request .Please click link below to reset  your password. <br> "
			  +"<a href='https://"+url+"/University/resetPassword?emailId="+
			  encodeEmail+"'><strong>Reset Link</strong></a>"+
			  "<br><br> Thanks, <br> Team University";
			
//                		"Hello User, <br><br> We have received your reset password request .Please click link below to reset  your password.<br><a href='http://localhost:4200/resetPassword?emailId="+encodeEmail+"'><strong>Reset Link</strong></a> "+
//                        "<br><br><br> Thanks,<br>Team University";
			logger.info("------>3");
			message.setText(vmFileContent,"UTF-8", "html");

//			String file = null;
//			if(awsORtest.equalsIgnoreCase("AWS")) {
//				file=emailFileLocation+"PasswordReset.txt";     //Live
//			}if(awsORtest.equalsIgnoreCase("TEST")) {
//				file = emailFileLocation+ "PasswordReset.txt"; // test
////				file = "./EmailFiles/PasswordReset.txt"; o
//			}if(awsORtest.equalsIgnoreCase("LOCAL")) {
//				file = emailFileLocation+"./EmailFiles/PasswordReset.txt";          //local
//			}
//
//			BufferedReader reader = new BufferedReader(new FileReader(file));
//			String vmFileContent = reader.readLine();
//			vmFileContent = vmFileContent.replaceFirst("#url", url);
//			vmFileContent = vmFileContent.replaceFirst("#encodeEmail", encodeEmail);
//
//			MimeBodyPart imagePart = new MimeBodyPart();
//
//			if(awsORtest.equalsIgnoreCase("AWS")) {
//			 imagePart.attachFile(logoimageLocation+"logo.png"); //For live
//			}if(awsORtest.equalsIgnoreCase("TEST")) {
//				imagePart.attachFile(logoimageLocation+ "logo.png"); // For test
////				imagePart.attachFile("./logo.png");
//			}else {
//				imagePart.attachFile(logoimageLocation+"logo.png"); // For local Testing
//			}
//			imagePart.setDisposition(MimeBodyPart.INLINE);
//			Map<String, String> inlineImages = new HashMap<String, String>();
//			
//			if(awsORtest.equalsIgnoreCase("AWS")) {
//				inlineImages.put("image",logoimageLocation+"logo.png"); //For live
//			}if(awsORtest.equalsIgnoreCase("TEST")) {
//				inlineImages.put("image",logoimageLocation+"logo.png"); //For test
////				inlineImages.put("image", "./logo.png");
//			}else {
//				inlineImages.put("image", logoimageLocation+"logo.png"); // For local Testing
//			}
//
//			if (inlineImages != null && inlineImages.size() > 0) {
//				Set<String> setImageID = inlineImages.keySet();
//
//				for (String contentId : setImageID) {
//					// MimeBodyPart imagePart = new MimeBodyPart();
//					imagePart.setHeader("Content-ID", "<" + contentId + ">");
//					imagePart.setDisposition(MimeBodyPart.INLINE);
//
//					String imageFilePath = inlineImages.get(contentId);
//					try {
//						imagePart.attachFile(imageFilePath);
//					} catch (IOException ex) {
//						ex.printStackTrace();
//					}
//				}
//			}
//			textBodyPart.setText(vmFileContent, "utf-8", "html");
			//mimeMessage.saveChanges();

			//logger.info("fileContent=" + vmFileContent);
			// Send the complete message parts
//			Multipart multipart = new MimeMultipart();
//			multipart.addBodyPart(imagePart);
//			multipart.addBodyPart(textBodyPart);
//			//message.setContent(vmFileContent, "text/html");
//			message.setContent(multipart);

			System.out.println("sending...");
			// Send message
			Transport.send(message);

			// javaMailSender.send(message);
			System.out.println("Sent message successfully....");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	void sendVerificationEmail(String emailId, String url) throws MessagingException, Exception {

		String encodeEmail = baseEncoder.encodeToString(emailId.getBytes(StandardCharsets.UTF_8));

		String to = emailId;
		logger.info("URL---------->" + url );
		logger.info("--newfromMailID--"+ newfromMailID);
		logger.info("--emailPwd--"+ emailPwd); 
		logger.info("--emailSmtp--"+ emailSmtp); 
				
				//+"+--pwd--"+emailPwd +"--emailSmtp--"+emailSmtp);
		// Sender's email ID needs to be mentioned

		//String from = "verify@educred.co.in";
		//String from = "verify@educred.co.in";

		// Assuming you are sending email from through gmails smtp
		//String host = "mail.educred.co.in";
		//String host = "mail.gmail.com";

		Properties properties = System.getProperties();

		properties.put("mail.smtp.host", emailSmtp);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");

		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {

			//	return new PasswordAuthentication("verify@educred.co.in", "EduCred$2021$");
				return new PasswordAuthentication(newfromMailID, emailPwd);

			}
		});

		// Used to debug SMTP issues
		session.setDebug(true);
		try {

			MimeMessage message = new MimeMessage(session);
			MimeBodyPart textBodyPart = new MimeBodyPart();

			//message.setFrom(new InternetAddress(from));
			message.setFrom(new InternetAddress(newfromMailID));

			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			message.setSubject("Email Verification Link!!!");

			// Now set the actual messageHello User,

			String vmFileContent1 = "Hello User, <br> We have received your registration request .Please click link below to verify your email account. <br><br> "
				+"<a href='https://"	+ url + "/University/emailVerification?emailId=" + encodeEmail + "' ><strong>Verification Link</strong></a> <br>"
					+ " If you do not use this link within 24 hours , it will expire. Post that you will need to register again. <br><br> Thanks, <br> Team University";

		//	message.setText(vmFileContent1,"UTF-8", "html");
		String file = null;
		if(awsORtest.equalsIgnoreCase("AWS")) {
			file=emailFileLocation+"VerficationLink.txt";     //Live
		}if(awsORtest.equalsIgnoreCase("TEST")) {
			file=emailFileLocation+"Verification.txt";	// test
//			file = "./EmailFiles/VerficationLink.txt";
		}if(awsORtest.equalsIgnoreCase("LOCAL")) {
			//file = emailFileLocation+"./EmailFiles/VerficationLink.txt";
			file = "./EmailFiles/VerficationLink.txt";
		}
		if(awsORtest.equalsIgnoreCase("InHouse")) {
			file=emailFileLocation+"VerficationLink.txt";     //Live
		}
		

		BufferedReader reader = new BufferedReader(new FileReader(file));
		String vmFileContent = reader.readLine();
		vmFileContent = vmFileContent.replaceFirst("#url", url);
		vmFileContent = vmFileContent.replaceFirst("#encodeEmail", encodeEmail);
			//vmFileContent = vmFileContent.replaceFirst("#url1", url);
			//vmFileContent = vmFileContent.replaceFirst("#encodeEmail1", encodeEmail);

			/*
			 * MimeBodyPart imagePart = new MimeBodyPart();
			 * if(awsORtest.equalsIgnoreCase("AWS")) {
			 * imagePart.attachFile(logoimageLocation+"logo.png"); //For live
			 * }if(awsORtest.equalsIgnoreCase("TEST")) {
			 * imagePart.attachFile(logoimageLocation+"logo.png"); // for test //
			 * imagePart.attachFile("./logo.png"); }else { //
			 * imagePart.attachFile(logoimageLocation+"logo.png"); // For local Testing }
			 * imagePart.setDisposition(MimeBodyPart.INLINE); Map<String, String>
			 * inlineImages = new HashMap<String, String>(); //
			 * if(awsORtest.equalsIgnoreCase("AWS")) {
			 * inlineImages.put("image",logoimageLocation+"logo.png"); //For live
			 * }if(awsORtest.equalsIgnoreCase("TEST")) {
			 * inlineImages.put("image",logoimageLocation+"logo.png"); //For live //
			 * inlineImages.put("image", "./logo.png"); }else { // inlineImages.put("image",
			 * logoimageLocation+"./logo.png"); // For local Testing } // if (inlineImages
			 * != null && inlineImages.size() > 0) { Set<String> setImageID =
			 * inlineImages.keySet(); // for (String contentId : setImageID) { //
			 * MimeBodyPart imagePart = new MimeBodyPart();
			 * imagePart.setHeader("Content-ID", "<" + contentId + ">");
			 * imagePart.setDisposition(MimeBodyPart.INLINE); // String imageFilePath =
			 * inlineImages.get(contentId); try { imagePart.attachFile(imageFilePath); }
			 * catch (IOException ex) { ex.printStackTrace(); } } }
			 */
		textBodyPart.setText(vmFileContent, "utf-8", "html");


			//logger.info("fileContent=" + vmFileContent);
			// Send the complete message parts
		Multipart multipart = new MimeMultipart();
	//	multipart.addBodyPart(imagePart);
		multipart.addBodyPart(textBodyPart);
			// Send the complete message parts
		message.setContent(multipart);
		message.setContent(vmFileContent, "text/html");  

			System.out.println("sending...");

			Transport.send(message);
			System.out.println("Sent message successfully....");

		} catch (MessagingException e) {

			throw new RuntimeException(e);
		}
	}

	  public void sendStatusMail(String altEmail, String emailId, Long id, String status, String imageLocation) throws Exception {

//		   String encodeEmail = baseEncoder.encodeToString(emailId.getBytes(StandardCharsets.UTF_8)) ;
		logger.info("imageLocation---->" + imageLocation);
		Optional<VerificationRequest> vrr = verificationReqRepository.findById(id);
		VerificationRequest vr = vrr.get();

		PassingYearMaster year = yearOfPassService.getYearById(vr.getYearOfPassingId());

		DocumentMaster doc = documentService.getNameById(vr.getDocumentId());

		String to = emailId;
		String altTo = altEmail;
		Long Id = id;

		logger.info("email Ids----->" + to + Id);

		// Sender's email ID needs to be mentioned
//	        String from = "universityscube@gmail.com";
	//	String from = "verify@educred.co.in";
//	        String from = "resolution@educred.co.in";

		// Assuming you are sending email from through gmails smtp
	//	String host = "mail.educred.co.in";
//	        String host = "smtp.gmail.com";

		Properties properties = System.getProperties();

		properties.put("mail.smtp.host", emailSmtp);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");

		
		  String vmFileContent = "Dear Candidate/ Requestor,  \r\r" +
//		  "<img src=\"https://i.ibb.co/PDDDWrR/bar.jpg\">" +
		  " Your request for verification has been completed, attached is your verification report. \r\r"
		  +
		  " In case of any dispute you may log on to the site and raise a request through the Dispute option available. \r\r\r"
		  + "Team EduCred";

//		String file = null;
//		if(awsORtest.equalsIgnoreCase("AWS")) {
//			file=emailFileLocation+"StatusMail.txt"; //Live
//		}if(awsORtest.equalsIgnoreCase("TEST")){
//			file=emailFileLocation+"StatusMail.txt"; //test
////			file = "./EmailFiles/StatusMail.txt";
//		}if(awsORtest.equalsIgnoreCase("LOCAL")) {
//			file = emailFileLocation+"./EmailFiles/StatusMail.txt";
//		}
//		
//		BufferedReader reader = new BufferedReader(new FileReader(file));
//		String vmFileContent = reader.readLine();

		logger.info("fileContent=" + vmFileContent);

		String subject = "Verification Result";
		logger.info("subject of mail----->" + subject);
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {

				
				return new PasswordAuthentication(newfromMailID, emailPwd);

				//return new PasswordAuthentication("verify@educred.co.in", "EduCred$2021$");
//	                return new PasswordAuthentication("universityscube@gmail.com", "edu@1234");
//	                return new PasswordAuthentication("resolution@educred.co.in", "EduCred$2021$");

			}

		});
//	        Session session = Session.getDefaultInstance(properties, null);
		ByteArrayOutputStream outputStream = null;
		// Used to debug SMTP issues
		session.setDebug(true);
		try {

			MimeMessage mimeMessage = new MimeMessage(session);

			MimeBodyPart textBodyPart = new MimeBodyPart();

//			MimeBodyPart imagePart = new MimeBodyPart();
//
////			 imagePart.attachFile(imageLocation+"logo.png"); //For live
//			
//			if(awsORtest.equalsIgnoreCase("AWS")) {
//				imagePart.attachFile(logoimageLocation+"logo.png");
//			}if(awsORtest.equalsIgnoreCase("TEST")) {
//				imagePart.attachFile(logoimageLocation+"logo.png");
////				imagePart.attachFile("logo.png");
//			}else {
////				imagePart.attachFile(imageLocation+"logo.png");
//				imagePart.attachFile(logoimageLocation+"logo.png");
//			}

			//imagePart.attachFile("./logo.png"); // For local Testing
//			imagePart.setDisposition(MimeBodyPart.INLINE);

//			Map<String, String> inlineImages = new HashMap<String, String>();
//
//			if(awsORtest.equalsIgnoreCase("AWS")) {
//				inlineImages.put("image",logoimageLocation+"logo.png"); //For live
//			}if(awsORtest.equalsIgnoreCase("TEST")) {
//				inlineImages.put("image",logoimageLocation+"logo.png");
////				inlineImages.put("image","logo.png");
//			}else {
//				inlineImages.put("image",logoimageLocation+"logo.png");
//			}

			//inlineImages.put("image", "./logo.png"); // For local Testing

//			if (inlineImages != null && inlineImages.size() > 0) {
//				Set<String> setImageID = inlineImages.keySet();
//
//				for (String contentId : setImageID) {
//					// MimeBodyPart imagePart = new MimeBodyPart();
//					imagePart.setHeader("Content-ID", "<" + contentId + ">");
//					imagePart.setDisposition(MimeBodyPart.INLINE);
//
//					String imageFilePath = inlineImages.get(contentId);
//					try {
//						imagePart.attachFile(imageFilePath);
//					} catch (IOException ex) {
//						ex.printStackTrace();
//					}
//				}
//			}

//			textBodyPart.setText(vmFileContent, "utf-8", "html");
			 textBodyPart.setText(vmFileContent);
			outputStream = new ByteArrayOutputStream();
			logger.info("check if approved/rejected----->" + status);
			if (status.equalsIgnoreCase("Approved") || status.equalsIgnoreCase("SV_Approved")
					|| status.equalsIgnoreCase("Uni_Auto_Approved") || status.equalsIgnoreCase("UN_Approved_Pass")|| status.equalsIgnoreCase("UN_Approved_Fail")) {
				writeApprovalPdf(outputStream, Id, imageLocation);
				logger.info("writeApprovalPdf----->");
			}

			if (status.equalsIgnoreCase("Rejected") || status.equalsIgnoreCase("SV_Rejected")
					|| status.equalsIgnoreCase("Uni_Auto_Rejected") || status.equalsIgnoreCase("UN_Rejected")) {
				logger.info("writeRejectionPdf----->");

				writeRejectionPdf(outputStream, id, imageLocation);

			}

			byte[] bytes = outputStream.toByteArray();

			// construct the pdf body part
			DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
			MimeBodyPart pdfBodyPart = new MimeBodyPart();
			pdfBodyPart.setDataHandler(new DataHandler(dataSource));

			// construct the mime multi part
			MimeMultipart mimeMultipart = new MimeMultipart();
			mimeMultipart.addBodyPart(textBodyPart);
			mimeMultipart.addBodyPart(pdfBodyPart);
			// mimeMultipart.addBodyPart(imagePart);
			pdfBodyPart.setFileName(doc.getDocumentName() + "_" + year.getYearOfPassing() + ".pdf");

			mimeMessage.saveChanges();

			Message message = new MimeMessage(session);
			BodyPart messageBodyPart = new MimeBodyPart();
			Multipart multipart = new MimeMultipart();
			message.setFrom(new InternetAddress(newfromMailID));
            message.addRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            if(altTo != null) {
	            if(!altTo.equalsIgnoreCase("")) {
		             message.addRecipients(Message.RecipientType.TO,
		                     InternetAddress.parse(altTo));
	            }
            }
//            message.setRecipients(Message.RecipientType.TO,
//                    InternetAddress.parse(emai));	
            messageBodyPart = new MimeBodyPart();
			messageBodyPart.setDataHandler(new DataHandler(dataSource));
			messageBodyPart.setFileName(doc.getDocumentName() + "_" + year.getYearOfPassing() + ".pdf");
			multipart.addBodyPart(textBodyPart);
			multipart.addBodyPart(messageBodyPart);
//			multipart.addBodyPart(imagePart);
			message.setContent(multipart);

			// create the sender/recipient addresses
			InternetAddress iaSender = new InternetAddress(newfromMailID);
			InternetAddress iaRecipient = new InternetAddress(to);

			// construct the mime message
//               MimeMessage mimeMessage = new MimeMessage(session);
			mimeMessage.setSender(iaSender);
			message.setSubject(subject);
			mimeMessage.setRecipient(Message.RecipientType.TO, iaRecipient);
			mimeMessage.setContent(mimeMultipart);

			// send off the email

			System.out.println("sending...");
			
			try {
				Transport.send(message);

			}
			catch(Exception e)
			{
				System.out.println("exception in mail sending....");

			}
			
			
//	            Transport.send(message);
			System.out.println("Sent message successfully....");

		} catch (MessagingException e) {

			throw new RuntimeException(e);       
		}
	}



	private void writeRejectionPdf(ByteArrayOutputStream outputStream, Long id, String imageLocation) throws Exception {
		System.out.println("******EmailServiceImpl writeRejectionPdf*******");

		try {
			Optional<VerificationRequest> vrr = verificationReqRepository.findById(id);
			VerificationRequest vr = vrr.get();

			UserResponse ume = userService.getUserInfoById(vr.getUserId());
			
			String strEmbassyId=vr.getEmbassyid();
			String embassyName= vr.getEmbassyname();
			String embassyAddress= vr.getEmbassyadress();
			
			String strLogoString="logo.jpg";
			
			 if(strEmbassyId!=null) {
		        	
		        	if(strEmbassyId.equals("1")) {
		        		
		        		strLogoString="dubailogo.jpg";
		        	}
		        }
			

			Document document = new Document(PageSize.A4, 40, 40, 50, 7);
			// Set all required fonts here with appropriate names
			Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
			headFont.setSize(15);
			headFont.setColor(Color.BLACK);

			Font headAddrFont12 = FontFactory.getFont(FontFactory.HELVETICA);
			headAddrFont12.setSize(12);
			headAddrFont12.setColor(Color.BLACK);

			Font headAddrFont11 = FontFactory.getFont(FontFactory.HELVETICA);
			headAddrFont11.setSize(11);
			headAddrFont11.setColor(Color.BLACK);

			Font footerFont9 = FontFactory.getFont(FontFactory.HELVETICA);
			footerFont9.setSize(9);
			footerFont9.setColor(Color.BLACK);
			
			Font headingFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
			headingFont.setSize(13);
			headingFont.setColor(Color.BLACK);

			PdfWriter.getInstance(document, outputStream);

			/*
			 * HeaderFooter footer = new HeaderFooter( new
			 * Phrase("System generated document does not require signature.", footerFont9),
			 * false); footer.setAlignment(Element.ALIGN_CENTER); //
			 * footer.setBorder(Rectangle.NO_BORDER); document.setFooter(footer);
			 */
	        Image img;
	        if(awsORtest.equalsIgnoreCase("AWS")) {
	        	img = Image.getInstance(logoimageLocation+strLogoString); // live
	        }if(awsORtest.equalsIgnoreCase("TEST")) {
	        	img = Image.getInstance(logoimageLocation+strLogoString); // test
//	        	img = Image.getInstance("logo.jpg");
	        }else {
	        	img = Image.getInstance(logoimageLocation+strLogoString);
	        }
	       // Image img = Image.getInstance("logo.jpg");

			img.setAlignment(Element.ALIGN_CENTER);
			img.scaleToFit(400,350); // width, height
	        
	        document.open();
	        
	        document.add(img);
	        
			/*
			 * String strEmbassyId=vr.getEmbassyid(); String embassyName=
			 * vr.getEmbassyname(); String embassyAddress= vr.getEmbassyadress();
			 */
	        
	        LineSeparator ls = new LineSeparator();
	        document.add(new Chunk(ls));
	        int years = Calendar.getInstance().get(Calendar.YEAR);
	        String currentYear = String.valueOf(years);
	        
	        int currentYear1 = Integer.parseInt(currentYear);
	        
	        // Calculate the last year
	        int lastYear = currentYear1 - 1; 
	        
	        String lastTwoDigitsOfCurrentYear = currentYear.substring(currentYear.length() - 2);
	        
	        //Academic Year
	        String academicYear = lastYear + "-" + lastTwoDigitsOfCurrentYear;
	        
	        Chunk glue = new Chunk(new VerticalPositionMark());
	        
	        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");  
	        LocalDateTime now = LocalDateTime.now();  
	        System.out.println(dtf.format(now));  
	        
	        Paragraph refNo = new Paragraph();
	        refNo.setAlignment(Paragraph.ALIGN_LEFT);
	        refNo.setFont(footerFont9);
	        
	        if(strEmbassyId!=null) {
	        	
	        	if(strEmbassyId.equals("1")) {
	        		
	        		refNo.add("No.DBOEE/ICF/"+academicYear+ "/");
	        		
	        	}else {
	        		
	        		 refNo.add("Ref.: No.Exam./M&C Unit/"+ vr.getId()+"/"+currentYear);
					
				}
	        }

	        refNo.add(glue);
	        refNo.add("DATE:- "+dtf.format(now));
	        refNo.add(Chunk.NEWLINE);
	        refNo.add(Chunk.NEWLINE);
	        refNo.add(Chunk.NEWLINE);
	        
//	        Paragraph date = new Paragraph();
//			date.setAlignment(Paragraph.ALIGN_RIGHT);
//			date.setFont(footerFont9);
//			date.add("DATE:- "+dtf.format(now));
//			date.add(Chunk.NEWLINE);
//			date.add(Chunk.NEWLINE);
//			date.add(Chunk.NEWLINE);
			
			
			
			document.add(refNo);
//			document.add(date);
//	    EduCred_Logo.jpg

			

//			Paragraph head = new Paragraph();
//			head.setAlignment(Paragraph.ALIGN_RIGHT);
//			head.setFont(headFont);
//			head.add("Marks And Certification Unit");
//			document.add(head);
//
//			Paragraph headAddr = new Paragraph();
//			headAddr.setFont(headAddrFont12);
//			headAddr.setAlignment(Paragraph.ALIGN_RIGHT);
//			headAddr.add(Chunk.NEWLINE);
//			headAddr.add("Examinations Section, \r" + "M.J. Phule Bhavan, \r" + "Vidyanagari, Santacruz (East), \r"
//					+ "Mumbai- 400 098. \r" + "Date: " + java.time.LocalDate.now());
//			document.add(headAddr);
//
//			Paragraph Addr = new Paragraph();
//			Addr.setFont(headAddrFont12);
//			Addr.setAlignment(Paragraph.ALIGN_LEFT);
//			Addr.add(Chunk.NEWLINE);
//			Addr.add("To, \r" + ume.getFirst_name() + " " + ume.getLast_name() + "\r" + "Email Id: " + ume.getEmail()
//					+ ", \r" + "Phone No: " + ume.getPhone_no() + ", \r");
//			Addr.add(Chunk.NEWLINE);
//			Addr.add(Chunk.NEWLINE);
//			Addr.add(Chunk.NEWLINE);
//			document.add(Addr);

//			Paragraph greeting = new Paragraph();
//			greeting.setFont(headAddrFont11);
//			greeting.setAlignment(Paragraph.ALIGN_LEFT);
//			greeting.add("Sir/Madam, \r");
//
//			Paragraph para = new Paragraph();
//			para.setFont(headAddrFont11);
//			para.setAlignment(Paragraph.ALIGN_LEFT);
//			para.add(
//					"          As per Xerox copy of the Statement of Marks of the candidate received from you for verification. After verifying the office record it has been observed that the statement of marks is not issued by this University and hence the same is FAKE.");
//			para.add(Chunk.NEWLINE);
//			para.add(Chunk.NEWLINE);
//
//			document.add(greeting);
//			document.add(para);
			
//			Chunk heading = new Chunk();
//			heading.setFont(headingFont);
//			heading.setAlignment(Paragraph.ALIGN_CENTER);
//			
//			heading.add("Verification Certificate");
//			heading.add(Chunk.NEWLINE);
//			heading.add(Chunk.NEWLINE);
			

			
			System.out.println("----------------embassyName" + embassyName + "embassyAddress---------" + embassyAddress);
			
			if(strEmbassyId!=null)
			{
				if(strEmbassyId.equals("1") || strEmbassyId.equals("3"))
				{
					/*
					 * Paragraph emb = new Paragraph(); emb.setAlignment(Paragraph.ALIGN_LEFT);
					 * emb.setFont(footerFont9); emb.add("To,");
					 * 
					 * emb.add(Chunk.NEWLINE); emb.add("Dubai Embassy");
					 * 
					 * emb.add(Chunk.NEWLINE); emb.add(embassyName +",");
					 * 
					 * emb.add(Chunk.NEWLINE); emb.add(embassyAddress); emb.add(Chunk.NEWLINE);
					 * 
					 * document.add(emb);
					 */
					
		            PdfPTable table = new PdfPTable(2); // 2 columns
		            table.setHorizontalAlignment(PdfPTable.ALIGN_LEFT); // Set table alignment to left
		            
		            float leading = 12f;

		            PdfPCell cell1 = new PdfPCell(new Paragraph("To,", footerFont9));
		            cell1.setLeading(leading, 0);
		            
		            PdfPCell cell2 = null;
		            PdfPCell cell3 = null;
		            
		            if(strEmbassyId.equals("1")) {
		            	
			            cell2 = new PdfPCell(new Paragraph("")); // Empty cell with no content
			            cell3 = new PdfPCell(new Paragraph("Dubai Embassy", footerFont9));
			            cell3.setLeading(leading, 0);
		            	
		            }else {
						
		            	cell2 = new PdfPCell(new Paragraph("")); // Empty cell with no content
			            cell3 = new PdfPCell(new Paragraph(""));
			            cell3.setLeading(leading, 0);
					}
		            
		            PdfPCell cell4 = new PdfPCell(new Paragraph("")); // Empty cell with no content
		            PdfPCell cell5 = new PdfPCell(new Paragraph(embassyName + "," , footerFont9));
		            cell5.setLeading(leading, 0);
		            
		            PdfPCell cell6 = new PdfPCell(new Paragraph("")); // Empty cell with no content
		            PdfPCell cell7 = new PdfPCell(new Paragraph(embassyAddress, footerFont9));
		            cell7.setLeading(leading, 0);
		            
		            PdfPCell cell8 = new PdfPCell(new Paragraph("")); // Empty cell with no content

		            // Set border width to 0 for all cells and the table
		            cell1.setBorderWidth(0);
		            cell2.setBorderWidth(0);
		            cell3.setBorderWidth(0);
		            cell4.setBorderWidth(0);
		            cell5.setBorderWidth(0);
		            cell6.setBorderWidth(0);
		            cell7.setBorderWidth(0);
		            cell8.setBorderWidth(0);
		              
		            table.getDefaultCell().setBorderWidth(0);

		            table.addCell(cell1);
		            table.addCell(cell2);
		            table.addCell(cell3);
		            table.addCell(cell4);
		            table.addCell(cell5);
		            table.addCell(cell6);
		            table.addCell(cell7);
		            table.addCell(cell8);

		            document.add(table);

				}
			}
			
			if(strEmbassyId!=null) {
				
				if(strEmbassyId.equals("1")) {
					
					System.out.println("In Embassy-- no header");
									
					Paragraph forSirMadam  = new Paragraph();
					forSirMadam.setAlignment(Paragraph.ALIGN_LEFT);   
					forSirMadam.setFont(headAddrFont11);
					forSirMadam.add(Chunk.NEWLINE);
					forSirMadam.add("Sir/ Madam,");
					document.add(forSirMadam);
					
				}
			}
			
			
			
			if(strEmbassyId!=null) {
				
				if(strEmbassyId.equals("1")) {
					
					System.out.println("for dubai - no header");
					
				}else {
					
					Chunk chunk = new Chunk("VERIFICATION CERTIFICATE");
					chunk.setFont(headingFont);
					chunk.setUnderline(1.0f, -1f);
//					document.add(chunk);
					
					Phrase phrase = new Phrase();
					phrase.add(chunk);
					
					Paragraph certi = new Paragraph();
					certi.add(phrase);
					certi.setAlignment(Paragraph.ALIGN_CENTER);
					
					document.add(certi);
					
				}
			}
			


			Paragraph para = new Paragraph();
			para.setFont(headAddrFont11);
			para.setAlignment(Paragraph.ALIGN_LEFT);
//			if(vr.getDocStatus().equalsIgnoreCase("UN_Approved_Pass")||vr.getDocStatus().equalsIgnoreCase("SVD_Approved_Pass")) {
			para.add(Chunk.NEWLINE);
			para.add(Chunk.NEWLINE);
			
			if(strEmbassyId!=null) {
				
				if(strEmbassyId.equals("1")) {
					
					System.out.println("For dubai --");
					
					para.add(
							"This is to inform you that the candidate "+ vr.getFirstName() + " " + vr.getLastName() 
							+ " has submitted the following mentioned document for verification purpose. \r");
				}else {
					
					para.add(
							"This is to certify that the candidate "+ vr.getFirstName() + " " + vr.getLastName() 
							+ " has submitted the following mentioned document for verification purpose. \r");
				}
				
			}

			
			para.add(Chunk.NEWLINE);
			para.add(Chunk.NEWLINE);
//			}
//			if(vr.getDocStatus().equalsIgnoreCase("UN_Approved_Fail")||vr.getDocStatus().equalsIgnoreCase("SVD_Approved_Fail")) {
//				
//				para.add(
//						"This is to certify that the candidate "+ vr.getFirstName() + " " + vr.getLastName() + " has submitted the following mentioned document for verification purpose- \r"
//								+ "Details of Statement of Marks/Grade Card");
//				para.add(Chunk.NEWLINE);
//				para.add(Chunk.NEWLINE);
//				}
//			document.add(heading);
			document.add(para);
			
			Chunk chunk1 = new Chunk("Details of Statement of Marks/Grade Card");
			chunk1.setFont(headingFont);
			chunk1.setUnderline(1.0f, -1f);
			
			Phrase phrase1 = new Phrase();
			phrase1.add(chunk1);
			
			Paragraph certi1 = new Paragraph();
			certi1.add(phrase1);
			certi1.setAlignment(Paragraph.ALIGN_CENTER);
			
			document.add(certi1);
			
			Paragraph headd = new Paragraph();
			headd.setFont(headingFont);
			headd.setAlignment(Paragraph.ALIGN_CENTER);
//			headd.add("Details of Statement of Marks/Grade Card");
			headd.add(Chunk.NEWLINE);
			headd.add(Chunk.NEWLINE);
			headd.add(Chunk.NEWLINE);
			document.add(headd);

		    PdfPTable detailsTable = new PdfPTable(6);
		    detailsTable.setWidthPercentage(100);
		    detailsTable.setWidths(new int[] {20,20,20,15,25,20});
		    
//		    PdfPCell cell1 = new PdfPCell(new Paragraph("Serial No"));
		    PdfPCell cell1 = new PdfPCell(new Paragraph("Name of the Examination"));
		    PdfPCell cell2 = new PdfPCell(new Paragraph("Seat No"));
//		    PdfPCell cell3 = new PdfPCell(new Paragraph("Document Name"));
		    PdfPCell cell4 = new PdfPCell(new Paragraph("Month & Year Of Examination"));
		    PdfPCell cell5 = new PdfPCell(new Paragraph("Status"));
//		    PdfPCell cell6 = new PdfPCell(new Paragraph("Branch"));
		    PdfPCell cell7 = new PdfPCell(new Paragraph("Class/CGPA/CGPI"));
//		    PdfPCell cell8 = new PdfPCell(new Paragraph("Semester"));
		    PdfPCell cell8 = new PdfPCell(new Paragraph("Mode of Study"));

		    
		    detailsTable.addCell(cell1);
		    detailsTable.addCell(cell2);
//		    detailsTable.addCell(cell3);
		    detailsTable.addCell(cell4);
		    detailsTable.addCell(cell5);
		    detailsTable.addCell(cell7);
		    detailsTable.addCell(cell8);

//		    detailsTable.addCell(cell6);

//	    for(VerificationRequest ent: vr) {

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String strDate = formatter.format(vr.getCreatedate());

			PassingYearMaster year = yearOfPassService.getYearById(vr.getYearOfPassingId());

			DocumentMaster doc = documentService.getNameById(vr.getDocumentId());

			BranchMasterEntity branch = branchService.getbranchById(vr.getBranchId());

			SemesterEntity sem = semService.getSemById(vr.getSemId());

			StreamMaster stream = streamService.getNameById(vr.getStreamId());

			String modeofstudyinfo;

			if(vr.getModeOfStudy()!=null){
		    	 modeofstudyinfo=vr.getModeOfStudy();
		    	}
		    	else
		    	{
		    		modeofstudyinfo="-";
		    	}
			
			
			PdfPCell nameOfExamCell = new PdfPCell(new Paragraph(stream.getStreamName()+"("+branch.getBranchName()+")"));
	    	PdfPCell seatNoCell = new PdfPCell(new Paragraph(vr.getEnrollmentNumber()));
//	    	PdfPCell docCell = new PdfPCell(new Paragraph(doc.getDocumentName()));
	    	PdfPCell yearCell = new PdfPCell(new Paragraph(vr.getMonthOfPassing()+" "+year.getYearOfPassing()));
	    	PdfPCell statusCell = new PdfPCell(new Paragraph("Fake"));
//	    	PdfPCell branchCell = new PdfPCell(new Paragraph(branch.getBranchName()));
	    	PdfPCell cgpiCell = new PdfPCell(new Paragraph(vr.getCgpi()));
//	    	PdfPCell semCell = new PdfPCell(new Paragraph(sem.getSemester()));
	    	PdfPCell modeofstudycell = new PdfPCell(new Paragraph(modeofstudyinfo));

	    	
	    	detailsTable.addCell(nameOfExamCell);
	    	detailsTable.addCell(seatNoCell);
//	    	detailsTable.addCell(docCell);
	    	detailsTable.addCell(yearCell);
	    	detailsTable.addCell(statusCell);
	    	detailsTable.addCell(cgpiCell);
	    	detailsTable.addCell(modeofstudycell);

//	    	detailsTable.addCell(branchCell);
	    	
//	    	detailsTable.addCell(semCell);
	    	
	    
	    
	    	document.add(detailsTable);
	    	

//			Paragraph para1 = new Paragraph();
//			para1.setFont(headAddrFont11);
//			para1.setAlignment(Paragraph.ALIGN_LEFT);
//			para1.add(
//					"          You are, requested to inform the Sr. Inspector Of Police of your nearby Police Station for investigating in the matter and the same maybe informed to this office accordingly.");
//			para1.add(Chunk.NEWLINE);
//			para1.add(Chunk.NEWLINE);
//			para1.add("Thanking you");
//			para1.add(Chunk.NEWLINE);
//			para1.add(Chunk.NEWLINE);
//			document.add(para1);

			// Add footer of PDF here
			
			Paragraph result = new Paragraph();
		    result.setFont(headAddrFont11);
		    result.setAlignment(Paragraph.ALIGN_LEFT);
		    result.add(Chunk.NEWLINE);
		    result.add(Chunk.NEWLINE);
		    result.add(Chunk.NEWLINE);
		    result.add("The above details are verified from the office records. After verification it is observed that the above said document is issued by the university and details mentioned in the documents are found to be FAKE.");
		    result.add(Chunk.NEWLINE);
		    result.add(Chunk.NEWLINE);
		    result.add(Chunk.NEWLINE);
		    result.add("You are, requested to inform the Sr. Inspector of Police of your nearby Police Station for Investigating in the matter and the same maybe informed to this office accordingly.");
		    result.add(Chunk.NEWLINE);
		    result.add(Chunk.NEWLINE);
		    document.add(result);

//			Paragraph footer1 = new Paragraph();
//			footer1.setAlignment(Paragraph.ALIGN_RIGHT);
//			footer1.setFont(headAddrFont12);
//			footer1.add("Yours faithfully,");
//			document.add(footer1);

//		    Image img2 = Image.getInstance("logo.jpg");
////		    img2.scaleAbsolute(107, 107);
////		    img2.setAlignment(20);
////		    img2.setAbsolutePosition(243, 720);
//		    img2.setAlignment(Image.ALIGN_RIGHT);
//		    img2.scaleAbsolute(75, 75);
//		    document.add(img2);
		    
			if(strEmbassyId!=null) {
				
				if(strEmbassyId.equals("1")) {
					
					System.out.println("In Embassy-- no header");
					
					 Paragraph forYoursFaithfully  = new Paragraph();
					    forYoursFaithfully.setAlignment(Paragraph.ALIGN_RIGHT);   
					    forYoursFaithfully.setFont(headAddrFont11);
					    forYoursFaithfully.add(Chunk.NEWLINE);
					    forYoursFaithfully.add("Yours faithfully,");
						document.add(forYoursFaithfully);
					
				}
			}
			
		   
		    
		    
		    Image signImg;
	        if(awsORtest.equalsIgnoreCase("AWS")) {
	        	signImg = Image.getInstance(logoimageLocation+"signn.jpg");
	        }if(awsORtest.equalsIgnoreCase("TEST")){
	        	signImg = Image.getInstance(logoimageLocation+"signn.jpg");
//	        	signImg = Image.getInstance("signn.jpg");
	        }else {
	        	signImg = Image.getInstance(logoimageLocation+"signn.jpg");
	        }
//		    Image signImg = Image.getInstance(imageLocation+ "/signn.jpg");

		 //   Image signImg = Image.getInstance("signn.jpg");
		    signImg.setAlignment(Element.ALIGN_RIGHT);
		    signImg.scaleToFit(70,40);
		    
		    document.add(signImg);
		    
		    Paragraph foot = new Paragraph();
		    foot.setAlignment(Paragraph.ALIGN_RIGHT);
		    foot.setFont(headAddrFont12);
		   // foot.add("Dr. Vinod P. Patil");
		    foot.add("Dr. Prasad Karande.");
		    
		    
		    document.add(foot);
		    
		    
		    Paragraph foot1 = new Paragraph();
		    foot1.setAlignment(Paragraph.ALIGN_RIGHT);
		    foot1.setFont(headAddrFont12);
		    foot1.add("Director \r"
		    		+ "Board of Examinations & Evaluation");
		    
		   
		    		    
  		    document.add(foot1);

  		// Image signImg = Image.getInstance("signature.png");
//		    signImg.setAlignment(Element.ALIGN_RIGHT);
//		    signImg.scaleToFit(150, 120);
//		    
//		    document.add(signImg);
			//HeaderFooter footer = new HeaderFooter( new Phrase("System generated document does not require signature.", footerFont9), true);
//	        footer.setAlignment(Element.ALIGN_CENTER);
////	        footer.setBorder(Rectangle.NO_BORDER);
//	        document.setFooter(footer);

			document.close();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}

	private void writeApprovalPdf(OutputStream outputStream, Long id, String imageLocation) throws Exception {

		System.out.println("******EmailServiceImpl writeApprovalPdf*******");

		System.out.println("--------------" + java.time.LocalDate.now());

		try {

			logger.info("imageLocation----->" + imageLocation);

			Optional<VerificationRequest> vrr = verificationReqRepository.findById(id);
			VerificationRequest vr = vrr.get();

			UserResponse ume = userService.getUserInfoById(vr.getUserId());
			
			
			String strEmbassyId=vr.getEmbassyid();
			String embassyName= vr.getEmbassyname();
			String embassyAddress= vr.getEmbassyadress();

			String strLogoString="logo.jpg";
			
			 if(strEmbassyId!=null) {
		        	
		        	if(strEmbassyId.equals("1")) {
		        		
		        		strLogoString="dubailogo.jpg";
		        	}
		        }
			

			Document document = new Document(PageSize.A4, 40, 40, 50, 7);
			// Set all required fonts here with appropriate names
			Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
			headFont.setSize(15);
			headFont.setColor(Color.BLACK);

			Font headAddrFont12 = FontFactory.getFont(FontFactory.HELVETICA);
			headAddrFont12.setSize(12);
			headAddrFont12.setColor(Color.BLACK);

			Font headAddrFont11 = FontFactory.getFont(FontFactory.HELVETICA);
			headAddrFont11.setSize(11);
			headAddrFont11.setColor(Color.BLACK);

			Font footerFont9 = FontFactory.getFont(FontFactory.HELVETICA);
			footerFont9.setSize(9);
			footerFont9.setColor(Color.BLACK);
			
			Font headingFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
			headingFont.setSize(13);
			headingFont.setColor(Color.BLACK);

			PdfWriter.getInstance(document, outputStream);

			/*
			 * HeaderFooter footer = new HeaderFooter( new
			 * Phrase("System generated document does not require signature.", footerFont9),
			 * false); footer.setAlignment(Element.ALIGN_CENTER); //
			 * footer.setBorder(Rectangle.NO_BORDER); document.setFooter(footer);
			 */

			// left, right, top, bottom
			logger.info("headerFooter set here--->just before document.open()");

			Image img;
	        if(awsORtest.equalsIgnoreCase("AWS")) {
	        	img = Image.getInstance(logoimageLocation+strLogoString); // live
	        }if(awsORtest.equalsIgnoreCase("TEST")){
	        	img = Image.getInstance(logoimageLocation+strLogoString); // test
//	        	img = Image.getInstance("logo.jpg");
	        }else {
	        	img = Image.getInstance(logoimageLocation+strLogoString);
	        }

			img.setAlignment(Element.ALIGN_CENTER);
			img.scaleToFit(400, 350); // width, height

			document.open();
//	    EduCred_Logo.jpg

			document.add(img);
			
		
			
			 // insert horizontal line
	        LineSeparator ls = new LineSeparator();
	        document.add(new Chunk(ls));
	        int years = Calendar.getInstance().get(Calendar.YEAR);
	        String currentYear = String.valueOf(years);
	        
	     
	        int currentYear1 = Integer.parseInt(currentYear);
	        
	        // Calculate the last year
	        int lastYear = currentYear1 - 1; 
	        
	        String lastTwoDigitsOfCurrentYear = currentYear.substring(currentYear.length() - 2);
	        
	        //Academic Year
	        String academicYear = lastYear + "-" + lastTwoDigitsOfCurrentYear;
	        
	        
	        Chunk glue = new Chunk(new VerticalPositionMark());
	        
	        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");  
	        LocalDateTime now = LocalDateTime.now();  
	        System.out.println(dtf.format(now));  
	        
	        Paragraph refNo = new Paragraph();
	        refNo.setAlignment(Paragraph.ALIGN_LEFT);
	        refNo.setFont(footerFont9);
	        
	        if(strEmbassyId!=null) {
	        	
	        	if(strEmbassyId.equals("1")) {
	        		
	        		refNo.add("No.DBOEE/ICF/"+academicYear+ "/");
	        		
	        	}else {
	        		
	        		 refNo.add("Ref.: No.Exam./M&C Unit/"+ vr.getId()+"/"+currentYear);
					
				}
	        }
	        
	      	        
	        refNo.add(glue);
	        refNo.add("DATE:- "+dtf.format(now));
	        refNo.add(Chunk.NEWLINE);
	        refNo.add(Chunk.NEWLINE);
	        refNo.add(Chunk.NEWLINE);
			
			document.add(refNo);
			

			if(strEmbassyId!=null)
			{
				if(strEmbassyId.equals("1") || strEmbassyId.equals("3"))
				{
					/*
					 * Paragraph emb = new Paragraph(); emb.setAlignment(Paragraph.ALIGN_LEFT);
					 * emb.setFont(footerFont9); emb.add("To,");
					 * 
					 * emb.add(Chunk.NEWLINE); emb.add("Dubai Embassy");
					 * 
					 * emb.add(Chunk.NEWLINE); emb.add(embassyName +",");
					 * 
					 * emb.add(Chunk.NEWLINE); emb.add(embassyAddress); emb.add(Chunk.NEWLINE);
					 * 
					 * document.add(emb);
					 */
					
					 PdfPTable table = new PdfPTable(2); // 2 columns
			            table.setHorizontalAlignment(PdfPTable.ALIGN_LEFT); // Set table alignment to left
			            
			            float leading = 12f;

			            PdfPCell cell1 = new PdfPCell(new Paragraph("To,", footerFont9));
			            cell1.setLeading(leading, 0);
			            
			            PdfPCell cell2 = null;
			            PdfPCell cell3 = null;
			            
			            if(strEmbassyId.equals("1")) {
			            	
				            cell2 = new PdfPCell(new Paragraph("")); // Empty cell with no content
				            cell3 = new PdfPCell(new Paragraph("Dubai Embassy", footerFont9));
				            cell3.setLeading(leading, 0);
			            	
			            }else {
							
			            	cell2 = new PdfPCell(new Paragraph("")); // Empty cell with no content
				            cell3 = new PdfPCell(new Paragraph(""));
				            cell3.setLeading(leading, 0);
						}
			            
			            PdfPCell cell4 = new PdfPCell(new Paragraph("")); // Empty cell with no content
			            PdfPCell cell5 = new PdfPCell(new Paragraph(embassyName + "," , footerFont9));
			            cell5.setLeading(leading, 0);
			            
			            PdfPCell cell6 = new PdfPCell(new Paragraph("")); // Empty cell with no content
			            PdfPCell cell7 = new PdfPCell(new Paragraph(embassyAddress, footerFont9));
			            cell7.setLeading(leading, 0);
			            
			            PdfPCell cell8 = new PdfPCell(new Paragraph("")); // Empty cell with no content

			            // Set border width to 0 for all cells and the table
			            cell1.setBorderWidth(0);
			            cell2.setBorderWidth(0);
			            cell3.setBorderWidth(0);
			            cell4.setBorderWidth(0);
			            cell5.setBorderWidth(0);
			            cell6.setBorderWidth(0);
			            cell7.setBorderWidth(0);
			            cell8.setBorderWidth(0);
			              
			            table.getDefaultCell().setBorderWidth(0);

			            table.addCell(cell1);
			            table.addCell(cell2);
			            table.addCell(cell3);
			            table.addCell(cell4);
			            table.addCell(cell5);
			            table.addCell(cell6);
			            table.addCell(cell7);
			            table.addCell(cell8);

			            document.add(table);
					 

				}
			}
			
			if(strEmbassyId!=null) {
				
				if(strEmbassyId.equals("1")) {
					
					System.out.println("In Embassy-- no header");
									
					Paragraph forSirMadam  = new Paragraph();
					forSirMadam.setAlignment(Paragraph.ALIGN_LEFT);   
					forSirMadam.setFont(headAddrFont11);
					forSirMadam.add(Chunk.NEWLINE);
					forSirMadam.add("Sir/ Madam,");
					document.add(forSirMadam);
					
				}
			}
			
//			document.add(date);
	        
//			Paragraph head = new Paragraph();
//			head.setAlignment(Paragraph.ALIGN_RIGHT);
//			head.setFont(headFont);
//			head.add("Marks And Certification Unit");
//			document.add(head);
//
//			Paragraph headAddr = new Paragraph();
//			headAddr.setFont(headAddrFont12);
//			headAddr.setAlignment(Paragraph.ALIGN_RIGHT);
//			headAddr.add(Chunk.NEWLINE);
//			headAddr.add("Examinations Section, \r" + "M.J. Phule Bhavan, \r" + "Vidyanagari, Santacruz (East), \r"
//					+ "Mumbai- 400 098. \r" + "Date: " + java.time.LocalDate.now());
//			document.add(headAddr);
//
//			Paragraph Addr = new Paragraph();
//			Addr.setFont(headAddrFont12);
//			Addr.setAlignment(Paragraph.ALIGN_LEFT);
//			Addr.add(Chunk.NEWLINE);
//			Addr.add("To, \r" + ume.getFirst_name() + " " + ume.getLast_name() + "\r" + "Email Id: " + ume.getEmail()
//					+ ", \r" + "Phone No: " + ume.getPhone_no() + ", \r");
//			Addr.add(Chunk.NEWLINE);
//			Addr.add(Chunk.NEWLINE);
//			Addr.add(Chunk.NEWLINE);
//			document.add(Addr);

			logger.info("greeting set below here--->");
			
			if(strEmbassyId!=null) {
				
				if(strEmbassyId.equals("1")) {
					
					System.out.println("In Embassy-- no header");
				}else {
					
					System.out.println("hii");
					
					Chunk chunk = new Chunk("VERIFICATION CERTIFICATE");
					chunk.setFont(headingFont);
					chunk.setUnderline(1.0f, -1f);
					
					Phrase phrase = new Phrase();
					phrase.add(chunk);
					
					Paragraph certi = new Paragraph();
					certi.add(phrase);
					certi.setAlignment(Paragraph.ALIGN_CENTER);
					
					document.add(certi);
					
				}
			}
			


//			Paragraph heading = new Paragraph();
//			heading.setFont(headingFont);
//			heading.setAlignment(Paragraph.ALIGN_CENTER);
//			heading.add("Verification Certificate");
//			heading.add(Chunk.NEWLINE);
//			heading.add(Chunk.NEWLINE);

			Paragraph para = new Paragraph();
			para.setFont(headAddrFont11);
			para.setAlignment(Paragraph.ALIGN_LEFT);
//			if(vr.getDocStatus().equalsIgnoreCase("UN_Approved_Pass")||vr.getDocStatus().equalsIgnoreCase("SVD_Approved_Pass")) {
			para.add(Chunk.NEWLINE);
			para.add(Chunk.NEWLINE);
			
			if(strEmbassyId!=null) {
				
				if(strEmbassyId.equals("1")) {
					
					System.out.println("For dubai --");
					
					para.add(
							"This is to inform you that the candidate "+ vr.getFirstName() + " " + vr.getLastName() 
							+ " has submitted the following mentioned document for verification purpose. \r");
				}else {
					
					para.add(
							"This is to certify that the candidate "+ vr.getFirstName() + " " + vr.getLastName() 
							+ " has submitted the following mentioned document for verification purpose. \r");
				}
				
			}

			
			para.add(Chunk.NEWLINE);
			para.add(Chunk.NEWLINE);
//			}
//			if(vr.getDocStatus().equalsIgnoreCase("UN_Approved_Fail")||vr.getDocStatus().equalsIgnoreCase("SVD_Approved_Fail")) {
//				
//				para.add(
//						"This is to certify that the candidate "+ vr.getFirstName() + " " + vr.getLastName() + " has submitted the following mentioned document for verification purpose- \r"
//								+ "Details of Statement of Marks/Grade Card");
//				para.add(Chunk.NEWLINE);
//				para.add(Chunk.NEWLINE);
//				}
//			document.add(heading);
			document.add(para);
			
			Chunk chunk1 = new Chunk("Details of Statement of Marks/Grade Card");
			chunk1.setFont(headingFont);
			chunk1.setUnderline(1.0f, -1f);
			
			Phrase phrase1 = new Phrase();
			phrase1.add(chunk1);
			
			Paragraph certi1 = new Paragraph();
			certi1.add(phrase1);
			certi1.setAlignment(Paragraph.ALIGN_CENTER);
			certi1.add(Chunk.NEWLINE);
			certi1.add(Chunk.NEWLINE);
			certi1.add(Chunk.NEWLINE);
			
			document.add(certi1);
			
//			document.add(chunk1);
			
			
			
//			Paragraph headd = new Paragraph();
//			headd.setFont(headingFont);
//			headd.setAlignment(Paragraph.ALIGN_CENTER);
//			headd.add("Details of Statement of Marks/Grade Card");
//			headd.add(Chunk.NEWLINE);
//			headd.add(Chunk.NEWLINE);
//			headd.add(Chunk.NEWLINE);
//			document.add(headd);

			 PdfPTable detailsTable = new PdfPTable(6);
			    detailsTable.setWidthPercentage(100);
			   detailsTable.setWidths(new int[] {20,20,20,15,18,20});
			    
//	    PdfPCell cell1 = new PdfPCell(new Paragraph("Serial No"));
	    PdfPCell cell1 = new PdfPCell(new Paragraph("Name of the Examination"));
	    PdfPCell cell2 = new PdfPCell(new Paragraph("Seat No"));
//	    PdfPCell cell3 = new PdfPCell(new Paragraph("Document Name"));
	    PdfPCell cell4 = new PdfPCell(new Paragraph("Month & Year Of Examination"));
	    PdfPCell cell5 = new PdfPCell(new Paragraph("Status"));
//	    PdfPCell cell6 = new PdfPCell(new Paragraph("Branch"));
	    PdfPCell cell7 = new PdfPCell(new Paragraph("Class/CGPA/CGPI"));
	    PdfPCell cell8 = new PdfPCell(new Paragraph("Mode of Study"));
//	    PdfPCell cell8 = new PdfPCell(new Paragraph("Semester"));
	    
	    
	    
	    detailsTable.addCell(cell1);
	    detailsTable.addCell(cell2);
	    detailsTable.addCell(cell4);
	    detailsTable.addCell(cell5);
	    detailsTable.addCell(cell7);
	    detailsTable.addCell(cell8);
//	    detailsTable.addCell(cell6);
	    
//	    detailsTable.addCell(cell8);

	    
	    
//	    for(VerificationRequest ent: vr) {
			logger.info("record values set here--->");

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String strDate = formatter.format(vr.getCreatedate());

			PassingYearMaster year = yearOfPassService.getYearById(vr.getYearOfPassingId());

			DocumentMaster doc = documentService.getNameById(vr.getDocumentId());

			BranchMasterEntity branch = branchService.getbranchById(vr.getBranchId());

			SemesterEntity sem = semService.getSemById(vr.getSemId());

			StreamMaster stream = streamService.getNameById(vr.getStreamId());
			String modeofstudyinfo;

			if(vr.getModeOfStudy()!=null){
		    	 modeofstudyinfo=vr.getModeOfStudy();
		    	}
		    	else
		    	{
		    		modeofstudyinfo="-";
		    	}
			PdfPCell nameOfExamCell = new PdfPCell(new Paragraph(stream.getStreamName()+"("+branch.getBranchName()+")"));
	    	PdfPCell seatNoCell = new PdfPCell(new Paragraph(vr.getEnrollmentNumber()));
//	    	PdfPCell docCell = new PdfPCell(new Paragraph(doc.getDocumentName()));
	    	PdfPCell yearCell = new PdfPCell(new Paragraph(vr.getMonthOfPassing()+" "+year.getYearOfPassing()));
	    	PdfPCell statusCell = new PdfPCell();
	    	if(vr.getDocStatus().equalsIgnoreCase("UN_Approved_Pass")) {
	    		statusCell = new PdfPCell(new Paragraph("Successful "));
	    	}if(vr.getDocStatus().equalsIgnoreCase("UN_Approved_Fail")) {
	    		statusCell = new PdfPCell(new Paragraph("Fail"));
	    	}
	    	
//	    	PdfPCell branchCell = new PdfPCell(new Paragraph(branch.getBranchName()));
	    	PdfPCell cgpiCell = new PdfPCell(new Paragraph(vr.getCgpi()));
//	    	PdfPCell semCell = new PdfPCell(new Paragraph(sem.getSemester()));
	    	
	    	PdfPCell modeofstudycell = new PdfPCell(new Paragraph(modeofstudyinfo));
	    	detailsTable.addCell(nameOfExamCell);
	    	detailsTable.addCell(seatNoCell);
//	    	detailsTable.addCell(docCell);
	    	detailsTable.addCell(yearCell);
	    	detailsTable.addCell(statusCell);
	    	detailsTable.addCell(cgpiCell);
//	    	detailsTable.addCell(branchCell);
	    	detailsTable.addCell(modeofstudycell);
//	    	detailsTable.addCell(semCell);
	    	
	    	

	    document.add(detailsTable);
	    
	    Paragraph result = new Paragraph();
	    result.setFont(headAddrFont11);
	    result.setAlignment(Paragraph.ALIGN_LEFT);
	    result.add(Chunk.NEWLINE);
	    result.add(Chunk.NEWLINE);
	    result.add(Chunk.NEWLINE);
	    if(vr.getDocStatus().equalsIgnoreCase("UN_Approved_Pass")||vr.getDocStatus().equalsIgnoreCase("SVD_Approved_Pass")) {
	    	result.add("The above details are verified from the office records. After verification it is observed that the above said document is issued by the university and details mentioned in the documents are found to be correct.");
	    	result.add(Chunk.NEWLINE);
	    	result.add(Chunk.NEWLINE);
	    	result.add(Chunk.NEWLINE);
	    }
	    if(vr.getDocStatus().equalsIgnoreCase("UN_Approved_Fail")||vr.getDocStatus().equalsIgnoreCase("SVD_Approved_Fail")) {
	    	result.add("The above details are verified from the office records. After verification it is observed that the above said document is issued by the university and details mentioned in the documents are found to be correct.");
	    	result.add(Chunk.NEWLINE);
	    	result.add(Chunk.NEWLINE);
	    	result.add(Chunk.NEWLINE);
	    }
	    document.add(result);
	    logger.info("detail table added--->");
	    //Add footer of PDF here
	    
//	    Paragraph footer1 = new Paragraph();
//	    footer1.setAlignment(Paragraph.ALIGN_RIGHT);
//	    footer1.setFont(headAddrFont12);
//	    footer1.add("Yours faithfully,");
//	    document.add(footer1);
	    
		if(strEmbassyId!=null) {
			
			if(strEmbassyId.equals("1")) {
				
				System.out.println("In Embassy-- no header");
				
				 Paragraph forYoursFaithfully  = new Paragraph();
				    forYoursFaithfully.setAlignment(Paragraph.ALIGN_RIGHT);   
				    forYoursFaithfully.setFont(headAddrFont11);
				    forYoursFaithfully.add(Chunk.NEWLINE);
				    forYoursFaithfully.add("Yours faithfully,");
					document.add(forYoursFaithfully);
				
			}
		}
	    
	   
	    
	   
		
	    
	 //   Image signImg = Image.getInstance("signn.jpg");
//    Image signImg = Image.getInstance(imageLocation+ "/signn.jpg");
	    
	    Image signImg;
        if(awsORtest.equalsIgnoreCase("AWS")) {
        	signImg = Image.getInstance(logoimageLocation+"signn.jpg");
        }if(awsORtest.equalsIgnoreCase("TEST")){
        	signImg = Image.getInstance(logoimageLocation+"signn.jpg");
//        	signImg = Image.getInstance("signn.jpg");
        }else {
        	signImg = Image.getInstance(logoimageLocation+"signn.jpg");
        }
	    
	    signImg.setAlignment(Element.ALIGN_RIGHT);
	    signImg.scaleToFit(70, 40);
	    
	    document.add(signImg);
	    
	    
	    Paragraph foot = new Paragraph();
		foot.setAlignment(Paragraph.ALIGN_RIGHT);   
		foot.setFont(headAddrFont12);
	//	foot.add("Dr. Vinod P. Patil");
		foot.add("Dr. Prasad Karande.");
		document.add(foot);
			
			
			
			  Paragraph foot1 = new Paragraph();
			    foot1.setAlignment(Paragraph.ALIGN_RIGHT);
			    foot1.setFont(headAddrFont12);
			    foot1.add("Director \r"
			    		+ "Board of Examinations & Evaluation");
	  		    document.add(foot1);

//        HeaderFooter footer = new HeaderFooter( new Phrase("System generated document does not require signature.", footerFont9), true);
//        footer.setAlignment(Element.ALIGN_CENTER);
////        footer.setBorder(Rectangle.NO_BORDER);
//        document.setFooter(footer);
			logger.info("before document.close() here--->");

			document.close();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}

	void sendDisputeSaveMail(String emailId, long appId, long id) throws MessagingException, Exception {

		String to = emailId;

		// Sender's email ID needs to be mentioned
	//	String from = "resolution@educred.co.in";
//		   String from = "universityscube@gmail.com";

		// Assuming you are sending email from through gmails smtp
//		String host = "mail.educred.co.in";
//	        String host = "smtp.gmail.com";

		Properties properties = System.getProperties();

		properties.put("mail.smtp.host", emailSmtp);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");

		// Get the Session object.// and pass username and password
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication(newfromMailID, emailPwd);				
		//		return new PasswordAuthentication("resolution@educred.co.in", "EduCred$2021$");
//	            	return new PasswordAuthentication("universityscube@gmail.com", "edu@1234");

			}

		});

		// Used to debug SMTP issues
		session.setDebug(true);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);
			MimeBodyPart textBodyPart = new MimeBodyPart();

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(newfromMailID));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject: header field
			message.setSubject("Dispute Confirmation");

			// Now set the actual message

			String vmFileContent1 = "Dear Candidate/ Requestor, \r\r "
					+ "We have recieved the dispute that you raised. We will get back to you in 15 working days. \r "
					+ "For reference your dispute reference id is:" + id + ". \r\r"
					+ "Thanks, \r\r Team EduCred";

//			String file = null;
//			if(awsORtest.equalsIgnoreCase("AWS")) {
//				file=emailFileLocation+"DisputeRaised.txt";     //Live
//			}if(awsORtest.equalsIgnoreCase("TEST")) {
//				file=emailFileLocation+"DisputeRaised.txt";     //Test
////				file = "./EmailFiles/DisputeRaised.txt";
//			}if(awsORtest.equalsIgnoreCase("LOCAL")) {
//
//				file = emailFileLocation+"./EmailFiles/DisputeRaised.txt";
//			}
//			
//			BufferedReader reader = new BufferedReader(new FileReader(file));
//			String vmFileContent = reader.readLine();
//			vmFileContent = vmFileContent.replaceFirst("#id", Long.toString(id));
//
//			MimeBodyPart imagePart = new MimeBodyPart();
//			if(awsORtest.equalsIgnoreCase("AWS")) {
//				imagePart.attachFile(logoimageLocation+"logo.png"); //For live
//			}if(awsORtest.equalsIgnoreCase("TEST")) {
//				imagePart.attachFile(logoimageLocation+"logo.png"); //For Test
////				imagePart.attachFile("./logo.png");
//			}else {
//				imagePart.attachFile(logoimageLocation+"logo.png"); // For local Testing
//			}
//			
//			imagePart.setDisposition(MimeBodyPart.INLINE);
//			Map<String, String> inlineImages = new HashMap<String, String>();
//
//			if(awsORtest.equalsIgnoreCase("AWS")) {
//				inlineImages.put("image",logoimageLocation+"logo.png"); //For live
//			}if(awsORtest.equalsIgnoreCase("TEST")) {
//				inlineImages.put("image",logoimageLocation+"logo.png"); //For Test
////				inlineImages.put("image", "./logo.png");
//			}else {
//				inlineImages.put("image",logoimageLocation+ "logo.png"); // For local Testing
//			}
//
//			if (inlineImages != null && inlineImages.size() > 0) {
//				Set<String> setImageID = inlineImages.keySet();
//
//				for (String contentId : setImageID) {
//					// MimeBodyPart imagePart = new MimeBodyPart();
//					imagePart.setHeader("Content-ID", "<" + contentId + ">");
//					imagePart.setDisposition(MimeBodyPart.INLINE);
//
//					String imageFilePath = inlineImages.get(contentId);
//					try {
//						imagePart.attachFile(imageFilePath);
//					} catch (IOException ex) {
//						ex.printStackTrace();
//					}
//				}
//			}
//			textBodyPart.setText(vmFileContent1, "utf-8", "html");
			textBodyPart.setText(vmFileContent1);


			logger.info("fileContent=" + vmFileContent1);
			// Send the complete message parts
//			Multipart multipart = new MimeMultipart();
//			multipart.addBodyPart(imagePart);
//			multipart.addBodyPart(textBodyPart);
			// Send the complete message parts
//			message.setContent(multipart);
			// Send the complete message parts
			//message.setContent(vmFileContent, "text/html");
			message.setText(vmFileContent1);
			System.out.println("sending...");
			// Send message
			Transport.send(message);

			// javaMailSender.send(message);
			System.out.println("Sent message successfully....");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	void sendNoStatusChangeMail(String emailId, Long id) throws MessagingException, Exception {

		String to = emailId;

		// Sender's email ID needs to be mentioned
	   //     String from = "resolution@educred.co.in";
//		String from = "universityscube@gmail.com";
		// Assuming you are sending email from through gmails smtp
	    //    String host = "mail.educred.co.in";
	//	String host = "smtp.gmail.com";

		Properties properties = System.getProperties();

		properties.put("mail.smtp.host", emailSmtp);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");

		// Get the Session object.// and pass username and password
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication(newfromMailID, emailPwd);

               // return new PasswordAuthentication("resolution@educred.co.in", "EduCred$2021$");
//				return new PasswordAuthentication("universityscube@gmail.com", "edu@1234");
			}

		});

		// Used to debug SMTP issues
		session.setDebug(true);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);
			MimeBodyPart textBodyPart = new MimeBodyPart();

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(newfromMailID));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject: header field
			message.setSubject("Raised Dispute Result");

			// Now set the actual message

			String vmFileContent1 = "Dear Candidate/ Requestor, \r\r This is in relation with the dispute ref no.:" + id
					+ ". \r"
					+ "As mentioned in the dispute, we have cross checked the document and we believe that our earlier assessment does not need any correction. The status of your verification remains the same. \r\r "
//             		+ "Sorry for the inconvenience caused. \r\n "
					+ "\r\r Thanks, \r\r Team EduCred";

//			String file = null;
//			if(awsORtest.equalsIgnoreCase("AWS")) {
//				file=emailFileLocation+"DisputeNoChangeStatus.txt";     //Live
//			}if(awsORtest.equalsIgnoreCase("TEST")) {
//				file=emailFileLocation+"DisputeNoChangeStatus.txt";     //Test
////				file = "./EmailFiles/DisputeNoChangeStatus.txt";
//			}if(awsORtest.equalsIgnoreCase("LOCAL")) {
//
//				file = emailFileLocation+"./EmailFiles/DisputeNoChangeStatus.txt";
//			}
//
//			BufferedReader reader = new BufferedReader(new FileReader(file));
//			String vmFileContent = reader.readLine();
//			vmFileContent = vmFileContent.replaceFirst("#id", Long.toString(id));
//
//			MimeBodyPart imagePart = new MimeBodyPart();
//
//			if(awsORtest.equalsIgnoreCase("AWS")) {
//				imagePart.attachFile(logoimageLocation+"logo.png"); //For live
//			}if(awsORtest.equalsIgnoreCase("TEST")) {
//				imagePart.attachFile(logoimageLocation+"logo.png"); //For Test
////				imagePart.attachFile("./logo.png");
//			}else {
//				imagePart.attachFile(logoimageLocation+"logo.png"); // For local Testing
//			}
//
//			
//			imagePart.setDisposition(MimeBodyPart.INLINE);
//			Map<String, String> inlineImages = new HashMap<String, String>();
//
//			if(awsORtest.equalsIgnoreCase("AWS")) {
//				inlineImages.put("image",logoimageLocation+"logo.png"); //For live
//			}if(awsORtest.equalsIgnoreCase("TEST")) {
//				inlineImages.put("image",logoimageLocation+"logo.png"); //For Test
////				inlineImages.put("image", "./logo.png");
//			}else {
//
//				inlineImages.put("image", logoimageLocation+"logo.png"); // For local Testing
//			}
//			
//			if (inlineImages != null && inlineImages.size() > 0) {
//				Set<String> setImageID = inlineImages.keySet();
//
//				for (String contentId : setImageID) {
//					// MimeBodyPart imagePart = new MimeBodyPart();
//					imagePart.setHeader("Content-ID", "<" + contentId + ">");
//					imagePart.setDisposition(MimeBodyPart.INLINE);
//
//					String imageFilePath = inlineImages.get(contentId);
//					try {
//						imagePart.attachFile(imageFilePath);
//					} catch (IOException ex) {
//						ex.printStackTrace();
//					}
//				}
//			}
//			textBodyPart.setText(vmFileContent, "utf-8", "html");


//			logger.info("fileContent=" + vmFileContent);
			// Send the complete message parts
//			Multipart multipart = new MimeMultipart();
//			multipart.addBodyPart(imagePart);
//			multipart.addBodyPart(textBodyPart);
			// Send the complete message parts
//			message.setContent(multipart);
			// Send the complete message parts
			//message.setContent(vmFileContent, "text/html");
			message.setText(vmFileContent1);
			System.out.println("sending...");
			// Send message
			Transport.send(message);

			// javaMailSender.send(message);
			System.out.println("Sent message successfully....");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	public void sendStatusChangeMail(String emailId, Long verificationId, long disputeId, String imageLocation,String Status)
			throws Exception {

//		   String encodeEmail = baseEncoder.encodeToString(emailId.getBytes(StandardCharsets.UTF_8)) ;
		logger.info("imageLocation----> " + imageLocation);
		Optional<VerificationRequest> vrr = verificationReqRepository.findById(verificationId);
		VerificationRequest vr = vrr.get();

		PassingYearMaster year = yearOfPassService.getYearById(vr.getYearOfPassingId());

		DocumentMaster doc = documentService.getNameById(vr.getDocumentId());

		Long Id = verificationId;

		String to = emailId;

		// Sender's email ID needs to be mentioned
//		String from = "resolution@educred.co.in";
//		    String from = "universityscube@gmail.com";
		// Assuming you are sending email from through gmails smtp
//		String host = "mail.educred.co.in";
//	        String host = "smtp.gmail.com";
		Properties properties = System.getProperties();

		properties.put("mail.smtp.host", emailSmtp);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");

		String vmFileContent1 = "Hello User, \r\r This is in relation with the dispute ref no.:" + disputeId + ". \r\r"
				+ "As mentioned in the dispute, we have cross checked the document and the corrected result can be found in the attached document. \r\r "
				+ "Sorry for the inconvenience caused. \r\r " + "Thanks, \r\n Team EduCred";

		String subject = "Raised Dispute Result";
//		String file = null;
//		
//		if(awsORtest.equalsIgnoreCase("AWS")) {
//			file=emailFileLocation+"DisputeChangeStatus.txt";     //Live
//		}if(awsORtest.equalsIgnoreCase("TEST")) {
//			file=emailFileLocation+"DisputeChangeStatus.txt";     //Test
////			file = "./EmailFiles/DisputeChangeStatus.txt";
//		}if(awsORtest.equalsIgnoreCase("LOCAL")) {
//
//			file = emailFileLocation+"./EmailFiles/DisputeChangeStatus.txt";
//		}
//		
//		BufferedReader reader = new BufferedReader(new FileReader(file));
//		String vmFileContent = reader.readLine();
//		vmFileContent = vmFileContent.replaceFirst("#id", Long.toString(disputeId));


		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication(newfromMailID, emailPwd);				
		//		return new PasswordAuthentication("resolution@educred.co.in", "EduCred$2021$");
//	                return new PasswordAuthentication("universityscube@gmail.com", "edu@1234");
			}

		});
//	        Session session = Session.getDefaultInstance(properties, null);
		ByteArrayOutputStream outputStream = null;
		// Used to debug SMTP issues
		session.setDebug(true);
		try {

			MimeMessage mimeMessage = new MimeMessage(session);

			MimeBodyPart textBodyPart = new MimeBodyPart();
			
//			MimeBodyPart imagePart = new MimeBodyPart();
//			
//			if(awsORtest.equalsIgnoreCase("AWS")) {
//				imagePart.attachFile(logoimageLocation+"logo.png"); //For live
//			}if(awsORtest.equalsIgnoreCase("TEST")) {
//				imagePart.attachFile(logoimageLocation+"logo.png"); //For Test
////				imagePart.attachFile(imageLocation+"logo.png");
//			}else {
//				 imagePart.attachFile(logoimageLocation+"logo.png"); //For live
//
//			}
			
			

			//imagePart.attachFile("./logo.png"); // For local Testing
//			imagePart.setDisposition(MimeBodyPart.INLINE);

//			Map<String, String> inlineImages = new HashMap<String, String>();
//
//			if(awsORtest.equalsIgnoreCase("AWS")) {
//				inlineImages.put("image",logoimageLocation+"logo.png"); //For live
//			}if(awsORtest.equalsIgnoreCase("TEST")) {
//				inlineImages.put("image",logoimageLocation+"logo.png"); //For Test
////				inlineImages.put("image", "./logo.png");
//			}else {
//
//				inlineImages.put("image", logoimageLocation+"logo.png"); // For local Testing
//			}
			
			
//			inlineImages.put("image",imageLocation+"logo.png"); //For live

			//inlineImages.put("image", "./logo.png"); // For local Testing

//			if (inlineImages != null && inlineImages.size() > 0) {
//				Set<String> setImageID = inlineImages.keySet();
//
//				for (String contentId : setImageID) {
//					// MimeBodyPart imagePart = new MimeBodyPart();
//					imagePart.setHeader("Content-ID", "<" + contentId + ">");
//					imagePart.setDisposition(MimeBodyPart.INLINE);
//
//					String imageFilePath = inlineImages.get(contentId);
//					try {
//						imagePart.attachFile(imageFilePath);
//					} catch (IOException ex) {
//						ex.printStackTrace();
//					}
//				}
//			}

//			textBodyPart.setText(vmFileContent, "utf-8", "html");
			textBodyPart.setText(vmFileContent1);

			outputStream = new ByteArrayOutputStream();

			if (Status.equalsIgnoreCase("SVD_Approved_Pass")||Status.equalsIgnoreCase("SVD_Approved_Fail")) {
				writeApprovalPdf(outputStream, Id, imageLocation);
			}
			if (vr.getDocStatus().equalsIgnoreCase("SVD_Rejected")) {
				writeRejectionPdf(outputStream, Id, imageLocation);
			}
			byte[] bytes = outputStream.toByteArray();

			// construct the pdf body part
			DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
			MimeBodyPart pdfBodyPart = new MimeBodyPart();
			pdfBodyPart.setDataHandler(new DataHandler(dataSource));

			// construct the mime multi part
			MimeMultipart mimeMultipart = new MimeMultipart();
			mimeMultipart.addBodyPart(textBodyPart);
			mimeMultipart.addBodyPart(pdfBodyPart);
			pdfBodyPart.setFileName(doc.getDocumentName() + "_" + year.getYearOfPassing() + ".pdf");

			Message message = new MimeMessage(session);
			BodyPart messageBodyPart = new MimeBodyPart();
			Multipart multipart = new MimeMultipart();
			message.setFrom(new InternetAddress(newfromMailID));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			messageBodyPart = new MimeBodyPart();
			messageBodyPart.setDataHandler(new DataHandler(dataSource));
			messageBodyPart.setFileName(doc.getDocumentName() + "_" + year.getYearOfPassing() + ".pdf");
			multipart.addBodyPart(textBodyPart);
			multipart.addBodyPart(messageBodyPart);
//			multipart.addBodyPart(imagePart);
			message.setContent(multipart);
			message.setSubject(subject);

			// create the sender/recipient addresses
			InternetAddress iaSender = new InternetAddress(newfromMailID);
			InternetAddress iaRecipient = new InternetAddress(to);

			// construct the mime message
//            MimeMessage mimeMessage = new MimeMessage(session);
			mimeMessage.setSender(iaSender);
			mimeMessage.setSubject(subject);
			mimeMessage.setRecipient(Message.RecipientType.TO, iaRecipient);
			mimeMessage.setContent(mimeMultipart);

			// send off the email

			System.out.println("sending...");
			Transport.send(message);
//	            Transport.send(message);
			System.out.println("Sent message successfully....");

		} catch (MessagingException e) {

			throw new RuntimeException(e);
		}
	}
	 public void sendRejectedDatamail(File Filepath) throws Exception {

			logger.info("*******sendRejectedDatamail--"+Filepath);


		String from =fromMailID;
//	        String from = "resolution@educred.co.in";

		
	      //  String host = "smtp.gmail.com";
			String host = "mail.educred.co.in";

		Properties properties = System.getProperties();

		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");

	
		
		String vmFileContent = "PFA, excel/csv of rejected requests <br><br> This is an Auto generated mail.  <br><br>  Thanks";
		
		
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication(from, "EduCred$2021$");
//	                return new PasswordAuthentication("universityscube@gmail.com", "edu@1234");
//	                return new PasswordAuthentication("resolution@educred.co.in", "EduCred$2021$");

			}

		});
		// Used to debug SMTP issues
		session.setDebug(true);
		try {
			java.util.Date date=java.util.Calendar.getInstance().getTime();
			SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy");
			String strdate = formatter.format(date);
			String subject = "Rejected records in scan of dated- "+strdate;
			MimeMessage message = new MimeMessage(session);
			MimeBodyPart textBodyPart = new MimeBodyPart();

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));
			String to=toMailId;			
			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			if(CCMailid!=null&&!CCMailid.equals("")){
			message.addRecipients(Message.RecipientType.CC, 
                    InternetAddress.parse(CCMailid));
			}
			message.setSubject(subject); 
			MimeBodyPart attachmentPart = new MimeBodyPart();
			logger.info("*******22sendRejectedDatamail--"+Filepath);
			
			attachmentPart.attachFile(Filepath);

			logger.info("*******3333sendRejectedDatamail--"+Filepath);
			
		//	BodyPart messageBodyPart = new MimeBodyPart(); 
			textBodyPart.setText(vmFileContent,"utf-8","html");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(textBodyPart);
			multipart.addBodyPart(attachmentPart);
			message.setContent(multipart);
			
			logger.info("*******444sendRejectedDatamail--"+Filepath);
			
			Transport.send(message);
		} catch (MessagingException e) {
			
			logger.info("*******ex in mail send--"+e.toString());
			throw new RuntimeException(e);
		}
	}


	 
	 
	 
}
