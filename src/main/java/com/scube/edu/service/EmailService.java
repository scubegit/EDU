
package com.scube.edu.service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
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
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.scube.edu.model.DocumentMaster;
import com.scube.edu.model.PassingYearMaster;
import com.scube.edu.model.UserMasterEntity;
import com.scube.edu.model.VerificationRequest;
import com.scube.edu.repository.VerificationRequestRepository;
import com.scube.edu.response.UserResponse;

@Service
public class EmailService {
	
	Base64.Encoder baseEncoder = Base64.getEncoder();
	
	@Autowired
	VerificationRequestRepository verificationReqRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired 
	StreamService streamService;
	
	@Autowired
	YearOfPassingService yearOfPassService;
	
	@Autowired
	DocumentService	documentService;
	   
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
	  
	  
	  public void sendStatusMail(String emailId, Long id, String status) throws MessagingException, BadElementException, IOException {
		  
//		   String encodeEmail = baseEncoder.encodeToString(emailId.getBytes(StandardCharsets.UTF_8)) ;
		  
		  	Optional<VerificationRequest> vrr = verificationReqRepository.findById(id);
			VerificationRequest vr = vrr.get();
			
			PassingYearMaster year = yearOfPassService.getYearById(vr.getYearOfPassingId());
	    	
	    	DocumentMaster doc = documentService.getNameById(vr.getDocumentId());

		   String to = emailId;
		   
		   Long Id = id;

		   
	        // Sender's email ID needs to be mentioned
	        String from = "universityscube@gmail.com";

	        // Assuming you are sending email from through gmails smtp
	        String host = "smtp.gmail.com";

	        Properties properties = System.getProperties();
		   
		    properties.put("mail.smtp.host", host);
	        properties.put("mail.smtp.port", "465");
	        properties.put("mail.smtp.ssl.enable", "true");
	        properties.put("mail.smtp.auth", "true");

	        String vmFileContent = "Hello User, Your record has been verified by MU. Please find the PDF with verification result and details attached below.";
	        
	        String subject = "Verification Result";

	        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
	                 protected PasswordAuthentication getPasswordAuthentication() {

	                return new PasswordAuthentication("universityscube@gmail.com", "edu@1234");
	            }

	        });
//	        Session session = Session.getDefaultInstance(properties, null);
	        ByteArrayOutputStream outputStream = null;
	        // Used to debug SMTP issues
	        session.setDebug(true);
	        try {
	          
	        	
	        	
	        	
	            MimeMessage mimeMessage = new MimeMessage(session);
	            
	            MimeBodyPart textBodyPart = new MimeBodyPart();
	            textBodyPart.setText(vmFileContent);
               
               outputStream = new ByteArrayOutputStream();
               
               if(status.equalsIgnoreCase("Approved") || status.equalsIgnoreCase("SV_Approved") || status.equalsIgnoreCase("Uni_Auto_Approved")) {
            	   
               writeApprovalPdf(outputStream, Id);
               
               }
               if(status.equalsIgnoreCase("Rejected") || status.equalsIgnoreCase("SV_Rejected") || status.equalsIgnoreCase("Uni_Auto_Rejected")) {
            	   
            	   writeRejectionPdf(outputStream , id);
            	   
               }
               
               byte[] bytes = outputStream.toByteArray();
               
               //construct the pdf body part
               DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
               MimeBodyPart pdfBodyPart = new MimeBodyPart();
               pdfBodyPart.setDataHandler(new DataHandler(dataSource));
               
             //construct the mime multi part
               MimeMultipart mimeMultipart = new MimeMultipart();
               mimeMultipart.addBodyPart(textBodyPart);
               mimeMultipart.addBodyPart(pdfBodyPart);
               pdfBodyPart.setFileName(doc.getDocumentName()+"_"+year.getYearOfPassing()+".pdf");
               
               
               
               
               //create the sender/recipient addresses
               InternetAddress iaSender = new InternetAddress(from);
               InternetAddress iaRecipient = new InternetAddress(to);
               
               //construct the mime message
//               MimeMessage mimeMessage = new MimeMessage(session);
               mimeMessage.setSender(iaSender);
               mimeMessage.setSubject(subject);
               mimeMessage.setRecipient(Message.RecipientType.TO, iaRecipient);
               mimeMessage.setContent(mimeMultipart);

               //send off the email
               
               

	            System.out.println("sending...");
	            Transport.send(mimeMessage);
//	            Transport.send(message);
	            System.out.println("Sent message successfully....");
	            
	        } catch (MessagingException e) {
	        	
	        	
	            throw new RuntimeException(e);
	        }
	   }





	private void writeRejectionPdf(ByteArrayOutputStream outputStream, Long id) throws BadElementException, IOException {
		System.out.println("******EmailServiceImpl writeRejectionPdf*******");
		
		Optional<VerificationRequest> vrr = verificationReqRepository.findById(id);
		VerificationRequest vr = vrr.get();
		
		Document document = new Document(PageSize.A4,40, 40, 50, 7);
		//Set all required fonts here with appropriate names
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
		
		PdfWriter.getInstance(document, outputStream);
		
		HeaderFooter footer = new HeaderFooter( new Phrase("System generated document does not require signature.", footerFont9), false);
        footer.setAlignment(Element.ALIGN_CENTER);
//        footer.setBorder(Rectangle.NO_BORDER);
        document.setFooter(footer);
        
        document.open();
	    
	    Image img = Image.getInstance("logo.png");
	    img.scaleAbsolute(107, 107);
	    img.setAlignment(20);
	    img.setAbsolutePosition(243, 720);
	    
	    
	    document.add(img);
	    
	    Paragraph head = new Paragraph();
	    head.setAlignment(Paragraph.ALIGN_RIGHT);
	    head.setFont(headFont);
	    head.add("Marks And Certification Unit");
	    document.add(head);
	    
	    Paragraph headAddr = new Paragraph();
	    headAddr.setFont(headAddrFont12);
	    headAddr.setAlignment(Paragraph.ALIGN_RIGHT);
	    headAddr.add(Chunk.NEWLINE);
	    headAddr.add("Examinations Section, \r"
	    		+ "M.J. Phule Bhavan, \r"
	    		+ "Vidyanagari, Santacruz (East), \r"
	    		+ "Mumbai- 400 098. \r"
	    		+ "Date: "+java.time.LocalDate.now());
	    document.add(headAddr);
	    
	    Paragraph Addr = new Paragraph();
	    Addr.setFont(headAddrFont12);
	    Addr.setAlignment(Paragraph.ALIGN_LEFT);
	    Addr.add(Chunk.NEWLINE);
	    Addr.add("To, \r"
	    		+ "Harshal Thorat, \r"
	    		+ "Verification Analyst - Education, \r"
	    		+ "SecUR Credentials Ltd, \r"
	    		+ "MIDC Cross Road A, \r"
	    		+ "Near Marol Depot, \r"
	    		+ "Andheri (East), Mumbai- 400 093.");
	    Addr.add(Chunk.NEWLINE);
	    Addr.add(Chunk.NEWLINE);
	    Addr.add(Chunk.NEWLINE);
	    document.add(Addr);
	    
	    Paragraph greeting = new Paragraph();
	    greeting.setFont(headAddrFont11);
	    greeting.setAlignment(Paragraph.ALIGN_LEFT);
	    greeting.add("Sir/Madam, \r");
	    
	    Paragraph para = new Paragraph();
	    para.setFont(headAddrFont11);
	    para.setAlignment(Paragraph.ALIGN_LEFT);
	    para.add("          As per Xerox copy of the Statement of Marks of the candidate received from you for verification. After verifying the office record it has been observed that the statement of marks is not issued by this University and hence the same if FAKE.");
	    para.add(Chunk.NEWLINE);
	    para.add(Chunk.NEWLINE);
	    
	    document.add(greeting);
	    document.add(para);
	    
	    
	    PdfPTable detailsTable = new PdfPTable(5);
	    detailsTable.setWidthPercentage(100);
	    detailsTable.setWidths(new int[] {20,20,20,20,20});
	    
//	    PdfPCell cell1 = new PdfPCell(new Paragraph("Serial No"));
	    PdfPCell cell1 = new PdfPCell(new Paragraph("Date"));
	    PdfPCell cell2 = new PdfPCell(new Paragraph("Name Of Candidate"));
	    PdfPCell cell3 = new PdfPCell(new Paragraph("Document Name"));
	    PdfPCell cell4 = new PdfPCell(new Paragraph("Year Of Exam"));
	    PdfPCell cell5 = new PdfPCell(new Paragraph("Enrollment No"));
	    
	    
	    
	    detailsTable.addCell(cell1);
	    detailsTable.addCell(cell2);
	    detailsTable.addCell(cell3);
	    detailsTable.addCell(cell4);
	    detailsTable.addCell(cell5);
	    
	    
//	    for(VerificationRequest ent: vr) {
	    
	    	UserResponse ume = userService.getUserInfoById(vr.getUserId());
	    	
	    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
	    	 String strDate= formatter.format(vr.getCreatedate());
	    	
	    	PassingYearMaster year = yearOfPassService.getYearById(vr.getYearOfPassingId());
	    	
	    	DocumentMaster doc = documentService.getNameById(vr.getDocumentId());
	    	
	    	PdfPCell dateCell = new PdfPCell(new Paragraph(strDate));
	    	PdfPCell nameCell = new PdfPCell(new Paragraph(ume.getName()));
	    	PdfPCell docCell = new PdfPCell(new Paragraph(doc.getDocumentName()));
	    	PdfPCell yearCell = new PdfPCell(new Paragraph(year.getYearOfPassing()));
	    	PdfPCell enrollCell = new PdfPCell(new Paragraph(vr.getEnrollmentNumber()));
	    	
	    	detailsTable.addCell(dateCell);
	    	detailsTable.addCell(nameCell);
	    	detailsTable.addCell(docCell);
	    	detailsTable.addCell(yearCell);
	    	detailsTable.addCell(enrollCell);
	    
	    
	    	document.add(detailsTable);
	    	
	    	Paragraph para1 = new Paragraph();
		    para1.setFont(headAddrFont11);
		    para1.setAlignment(Paragraph.ALIGN_LEFT);
		    para1.add("          You are, requested to inform the Sr. Inspector Of Police of your nearby Police Station for investigating in the matter and the same maybe informed to this office accordingly.");
		    para1.add(Chunk.NEWLINE);
		    para1.add(Chunk.NEWLINE);
		    para1.add("Thanking you,");
		    para1.add(Chunk.NEWLINE);
		    para1.add(Chunk.NEWLINE);
		    document.add(para1);
		    
		    //Add footer of PDF here
		    
		    Paragraph footer1 = new Paragraph();
		    footer1.setAlignment(Paragraph.ALIGN_RIGHT);
		    footer1.setFont(headAddrFont12);
		    footer1.add("Yours faithfully,");
		    document.add(footer1);
		    
//		    Image img2 = Image.getInstance("logo.png");
////		    img2.scaleAbsolute(107, 107);
////		    img2.setAlignment(20);
////		    img2.setAbsolutePosition(243, 720);
//		    img2.setAlignment(Image.ALIGN_RIGHT);
//		    img2.scaleAbsolute(75, 75);
//		    document.add(img2);
		    
		    Paragraph foot = new Paragraph();
		    foot.setAlignment(Paragraph.ALIGN_RIGHT);
		    foot.setFont(headAddrFont12);
		    foot.add("Dr. Vinod P Patil \r"
		    		+ "Director \r"
		    		+ "Board Of Examination & Evaluation");
		    document.add(foot);
		    
//	        HeaderFooter footer = new HeaderFooter( new Phrase("System generated document does not require signature.", footerFont9), true);
//	        footer.setAlignment(Element.ALIGN_CENTER);
////	        footer.setBorder(Rectangle.NO_BORDER);
//	        document.setFooter(footer);

		    
		    document.close();
		
	}





	private void writeApprovalPdf(OutputStream outputStream, Long id) throws BadElementException, IOException {
		
		System.out.println("******EmailServiceImpl writeApprovalPdf*******");
		
		System.out.println("--------------"+java.time.LocalDate.now());   
		
		Optional<VerificationRequest> vrr = verificationReqRepository.findById(id);
		VerificationRequest vr = vrr.get();
		
		Document document = new Document(PageSize.A4,40, 40, 50, 7);
		//Set all required fonts here with appropriate names
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
		
		PdfWriter.getInstance(document, outputStream);
		
		HeaderFooter footer = new HeaderFooter( new Phrase("System generated document does not require signature.", footerFont9), false);
        footer.setAlignment(Element.ALIGN_CENTER);
//        footer.setBorder(Rectangle.NO_BORDER);
        document.setFooter(footer);
		
		
		// left, right, top, bottom
		
	    
	    document.open();
	    
	    Image img = Image.getInstance("logo.png");
	    img.scaleAbsolute(107, 107);
	    img.setAlignment(20);
	    img.setAbsolutePosition(243, 720);
	    
	    
	    document.add(img);
	    
	    Paragraph head = new Paragraph();
	    head.setAlignment(Paragraph.ALIGN_RIGHT);
	    head.setFont(headFont);
	    head.add("Marks And Certification Unit");
	    document.add(head);
	    
	    Paragraph headAddr = new Paragraph();
	    headAddr.setFont(headAddrFont12);
	    headAddr.setAlignment(Paragraph.ALIGN_RIGHT);
	    headAddr.add(Chunk.NEWLINE);
	    headAddr.add("Examinations Section, \r"
	    		+ "M.J. Phule Bhavan, \r"
	    		+ "Vidyanagari, Santacruz (East), \r"
	    		+ "Mumbai- 400 098. \r"
	    		+ "Date: "+java.time.LocalDate.now());
	    document.add(headAddr);
	    
	    Paragraph Addr = new Paragraph();
	    Addr.setFont(headAddrFont12);
	    Addr.setAlignment(Paragraph.ALIGN_LEFT);
	    Addr.add(Chunk.NEWLINE);
	    Addr.add("To, \r"
	    		+ "Nagesh Madhur, \r"
	    		+ "Verification Analyst - Education, \r"
	    		+ "SecUR Credentials, \r"
	    		+ "SecUR House \r"
	    		+ "MIDC Cross Road A, \r"
	    		+ "Near Marol Depot, \r"
	    		+ "Andheri (East), Mumbai- 400 093.");
	    Addr.add(Chunk.NEWLINE);
	    Addr.add(Chunk.NEWLINE);
	    Addr.add(Chunk.NEWLINE);
	    document.add(Addr);
	    
//	    Paragraph para = new Paragraph();
//	    para.setFont(headAddrFont10);
//	    para.setAlignment(Paragraph.ALIGN_CENTER);
//	    para.add("Sir/Madam, \r"
//	    		+ "       With reference to your application of Verification Document, this \r"
//	    		+ "is to inform you that the contents of the photocopy of the statement of marks of the \r"
//	    		+ "below mentioned candidate received along with your letter have been duly verified \r"
//	    		+ "and found correct.");
//	    document.add(para);
	    
	    Paragraph greeting = new Paragraph();
	    greeting.setFont(headAddrFont11);
	    greeting.setAlignment(Paragraph.ALIGN_LEFT);
	    greeting.add("Sir/Madam, \r");
	    
	    Paragraph para = new Paragraph();
	    para.setFont(headAddrFont11);
	    para.setAlignment(Paragraph.ALIGN_LEFT);
	    para.add("          With reference to your application for Verification of the educational document , this is to inform you that the contents of the photocopy of the Statement Of Marks of the below mentioned candidate received along with your letter have been verified and found correct.");
	    para.add(Chunk.NEWLINE);
	    para.add(Chunk.NEWLINE);
	    
	    document.add(greeting);
	    document.add(para);
	    
	    PdfPTable detailsTable = new PdfPTable(5);
	    detailsTable.setWidthPercentage(100);
	    detailsTable.setWidths(new int[] {20,20,20,20,20});
	    
//	    PdfPCell cell1 = new PdfPCell(new Paragraph("Serial No"));
	    PdfPCell cell1 = new PdfPCell(new Paragraph("Date"));
	    PdfPCell cell2 = new PdfPCell(new Paragraph("Name Of Candidate"));
	    PdfPCell cell3 = new PdfPCell(new Paragraph("Document Name"));
	    PdfPCell cell4 = new PdfPCell(new Paragraph("Year Of Exam"));
	    PdfPCell cell5 = new PdfPCell(new Paragraph("Enrollment No"));
	    
	    
	    
	    detailsTable.addCell(cell1);
	    detailsTable.addCell(cell2);
	    detailsTable.addCell(cell3);
	    detailsTable.addCell(cell4);
	    detailsTable.addCell(cell5);
	    
	    
//	    for(VerificationRequest ent: vr) {
	    
	    	UserResponse ume = userService.getUserInfoById(vr.getUserId());
	    	
	    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
	    	 String strDate= formatter.format(vr.getCreatedate());
	    	
	    	PassingYearMaster year = yearOfPassService.getYearById(vr.getYearOfPassingId());
	    	
	    	DocumentMaster doc = documentService.getNameById(vr.getDocumentId());
	    	
	    	PdfPCell dateCell = new PdfPCell(new Paragraph(strDate));
	    	PdfPCell nameCell = new PdfPCell(new Paragraph(ume.getName()));
	    	PdfPCell docCell = new PdfPCell(new Paragraph(doc.getDocumentName()));
	    	PdfPCell yearCell = new PdfPCell(new Paragraph(year.getYearOfPassing()));
	    	PdfPCell enrollCell = new PdfPCell(new Paragraph(vr.getEnrollmentNumber()));
	    	
	    	detailsTable.addCell(dateCell);
	    	detailsTable.addCell(nameCell);
	    	detailsTable.addCell(docCell);
	    	detailsTable.addCell(yearCell);
	    	detailsTable.addCell(enrollCell);
	    	
	    	
	    	

	    document.add(detailsTable);
	    
	    
	    //Add footer of PDF here
	    
	    Paragraph footer1 = new Paragraph();
	    footer1.setAlignment(Paragraph.ALIGN_RIGHT);
	    footer1.setFont(headAddrFont12);
	    footer1.add("Yours faithfully,");
	    document.add(footer1);
	    
//	    Image img2 = Image.getInstance("logo.png");
////	    img2.scaleAbsolute(107, 107);
////	    img2.setAlignment(20);
////	    img2.setAbsolutePosition(243, 720);
//	    img2.setAlignment(Image.ALIGN_RIGHT);
//	    img2.scaleAbsolute(75, 75);
//	    document.add(img2);
	    
	    Paragraph foot = new Paragraph();
	    foot.setAlignment(Paragraph.ALIGN_RIGHT);
	    foot.setFont(headAddrFont12);
	    foot.add("(Narendra G. Khalane \r"
	    		+ "Assistant Registrar");
	    document.add(foot);
	    
//        HeaderFooter footer = new HeaderFooter( new Phrase("System generated document does not require signature.", footerFont9), true);
//        footer.setAlignment(Element.ALIGN_CENTER);
////        footer.setBorder(Rectangle.NO_BORDER);
//        document.setFooter(footer);

	    
	    document.close();
		
	}
	  
	  

}

