package com.scube.edu.util;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Calendar;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
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
import com.scube.edu.model.VerificationRequest;
import com.scube.edu.repository.VerificationRequestRepository;
import com.scube.edu.response.UserResponse;
import com.scube.edu.service.BranchMasterService;
import com.scube.edu.service.DocumentService;
import com.scube.edu.service.EmailService;
import com.scube.edu.service.SemesterService;
import com.scube.edu.service.StreamService;
import com.scube.edu.service.UserService;
import com.scube.edu.service.YearOfPassingService;

@Service
public class VerificationPdf {
	

	
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
	
	@Value("${file.imagepath-dir}")
    private String logoimageLocation;
	
	@Value("${file.awsORtest}")
    private String awsORtest;
	
	public void writeRejectionPdf(HttpServletResponse response, Long id, String imageLocation) throws Exception {
		System.out.println("******EmailServiceImpl writeRejectionPdf*******");

		try {
			Optional<VerificationRequest> vrr = verificationReqRepository.findById(id);
			VerificationRequest vr = vrr.get();
			
			String strEmbassyId=vr.getEmbassyid();
			String embassyName= vr.getEmbassyname();
			String embassyAddress= vr.getEmbassyadress();
			
			String strLogoString="logo.jpg";
			
			 if(strEmbassyId!=null) {
		        	
		        	if(strEmbassyId.equals("1")) {
		        		
		        		strLogoString="dubailogo.jpg";
		        	}
		        }

			UserResponse ume = userService.getUserInfoById(vr.getUserId());

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

			PdfWriter.getInstance(document, response.getOutputStream());

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
	
	public void writeApprovalPdf( HttpServletResponse response, Long id, String imageLocation) throws Exception {

		System.out.println("******EmailServiceImpl writeApprovalPdf*******");

		System.out.println("--------------" + java.time.LocalDate.now());

		try {

			logger.info("imageLocation----->" + imageLocation);

			Optional<VerificationRequest> vrr = verificationReqRepository.findById(id);
			VerificationRequest vr = vrr.get();
			
			String strEmbassyId=vr.getEmbassyid();
			String embassyName= vr.getEmbassyname();
			String embassyAddress= vr.getEmbassyadress();
			
			String strLogoString="logo.jpg";
			
			 if(strEmbassyId!=null) {
		        	
		        	if(strEmbassyId.equals("1")) {
		        		
		        		strLogoString="dubailogo.jpg";
		        	}
		        }

			UserResponse ume = userService.getUserInfoById(vr.getUserId());

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

			PdfWriter.getInstance(document, response.getOutputStream());

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
	        	logger.info("Hii1");
	        }if(awsORtest.equalsIgnoreCase("TEST")){
	        	img = Image.getInstance(logoimageLocation+strLogoString); // test
//	        	img = Image.getInstance("logo.jpg");
	        	logger.info("Hii2");
	        }else {
	        	img = Image.getInstance(logoimageLocation+strLogoString);
	        	logger.info("Hii3");
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
					 * 
					 * emb.add(embassyAddress); emb.add(Chunk.NEWLINE); document.add(emb);
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
	    //Add footer of PDF here
	    
//	    Paragraph footer1 = new Paragraph();
//	    footer1.setAlignment(Paragraph.ALIGN_RIGHT);
//	    footer1.setFont(headAddrFont12);
//	    footer1.add("Yours faithfully,");
//	    document.add(footer1);
	    
	   
		
	    
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
	






}
