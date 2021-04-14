
package com.scube.edu.util;

import java.awt.Color;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.TabStop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lowagie.text.Cell;
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
import com.lowagie.text.alignment.HorizontalAlignment;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfDocument;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import com.scube.edu.model.PassingYearMaster;
import com.scube.edu.model.UserMasterEntity;
import com.scube.edu.repository.UserRepository;
import com.scube.edu.request.StudentDocVerificationRequest;
import com.scube.edu.response.StudentVerificationDocsResponse;


public class InvoicePDFExporter {
	
       private static final Logger logger = LoggerFactory.getLogger(InvoicePDFExporter.class);
    
	    private   HashMap<Object, Object> studentVerificationPdfData ;
	
	    
	    String applicationId = "";
	    
	

	    public InvoicePDFExporter(HashMap<Object, Object> pdfData, String applicationId )
	    {
                     this.studentVerificationPdfData = pdfData;
                    this.applicationId =applicationId;
	   
	    }

		
	   

	public void export(HttpServletResponse response) {
		    
	    	try {
	    		
	    	List<StudentVerificationDocsResponse> studentDocList =	(List<StudentVerificationDocsResponse>) studentVerificationPdfData.get("doclist");
	    	UserMasterEntity userMasterEntity  =  (UserMasterEntity) studentVerificationPdfData.get("userEntity");
	    	
	 
	    	
	    	
	        Document document = new Document(PageSize.A4, 30, 30, 50, 50);
	        
	       // document = new Document(PageSize.A4, 36, 36, 150, 80);
	        
	        document.setMargins(10, 10, 10, 10);
	        
	        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());

	        

            
	       // HeaderFooterPageEvent  event = new HeaderFooterPageEvent ();
		   //  writer.setPageEvent(event);
	        
	      
	        Font headingFont15 = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	        headingFont15.setSize(15);
	        headingFont15.setColor(Color.BLACK);
	        
	        Font paraFont10 = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE);
	        paraFont10.setSize(10);
	        paraFont10.setColor(Color.BLACK);
	        
	        Font addrFont8 = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	        addrFont8.setSize(8);
	        addrFont8.setColor(Color.BLACK);
	        
	        Font f6 = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	        f6.setSize(6);
	        f6.setColor(Color.BLACK);
	        
	        Font f10 = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	        f10.setSize(10);
	        f10.setColor(Color.BLACK);
	        
	        Font ft10 = FontFactory.getFont(FontFactory.TIMES_ROMAN);
	        ft10.setSize(10);
	        ft10.setColor(Color.BLACK);
	        
	        Font ft12 = FontFactory.getFont(FontFactory.TIMES_ROMAN);
	        ft12.setSize(10);
	        ft12.setColor(Color.BLACK);
	        
	         Font font9b = FontFactory.getFont(FontFactory.TIMES_BOLD);
	 	     font9b.setSize(11);
	        
	

            HeaderFooter footer =    new HeaderFooter( new Phrase("System generated document does not require signature.", f10), false);
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setBorder(Rectangle.NO_BORDER);
            document.setFooter(footer);
			/*
			 * document.open(); Image logo1 = Image.getInstance("ha-img.jpg");
			 * logo1.setAbsolutePosition(100,900);
			 * 
			 * writer.getDirectContent().addImage(logo1);
			 */
            
            //document.setHeader();
            
            
            
               Image logo = Image.getInstance("ha-img.jpg");
               logo.setAbsolutePosition(10, 300);
                Phrase phrase1 = new Phrase(logo.HEADER);
               HeaderFooter header =new HeaderFooter(phrase1,false);
               document.setHeader(header);
            
			
			
			  Image img = Image.getInstance("ha-img.jpg"); //
			  img.setAlignment(Element.ALIGN_CENTER);
			  
			  PdfPCell ImageCell1 = new PdfPCell(); ImageCell1.addElement(img);
			  
			  PdfPTable tab1 = new PdfPTable(1); tab1.setWidthPercentage(100);
			  
			  
			  tab1.addCell(ImageCell1); 
			  document.open();
			  document.add(tab1);
			
			 
			PdfPTable  recpttable = new PdfPTable(2);
	        recpttable.setWidthPercentage(100);
	        recpttable.setWidths(new int[]{50, 50});
	        
	        PdfPCell recNocell = new PdfPCell(new Paragraph("Receipt No :"+applicationId+"",f10));
            // Po_NOcell.addElement(PoNo_ph);
	        recNocell.setPaddingBottom(20);
	        recNocell.setBorderWidthTop(0);
	        recNocell.setBorderWidthRight(0);
	        recNocell.setBorderWidthBottom(0);
         
	        
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy ");
            LocalDateTime now = LocalDateTime.now();
            System.out.println(dtf.format(now));
       
             
          PdfPCell datecell = new PdfPCell(new Paragraph("Date:"+dtf.format(now),f10));
	              // Po_NOcell.addElement(PoNo_ph);
             datecell.setPaddingBottom(20);
             datecell.setBorderWidthTop(0);
             datecell.setBorderWidthBottom(0); 
             datecell.setBorderWidthLeft(0);
             
             datecell.setHorizontalAlignment(Element.ALIGN_RIGHT);
             
             recpttable.addCell(recNocell);
             recpttable.addCell(datecell);
             
             PdfPCell InvoiceCell =new PdfPCell(new Paragraph("\nINVOICE\n\n",headingFont15));
         
             InvoiceCell.setColspan(2);
             InvoiceCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        
             
             recpttable.addCell(InvoiceCell);
             
             document.add(recpttable); 
            
	         
	         PdfPTable  studentinfotable = new PdfPTable(2);
	         studentinfotable.setWidthPercentage(100);
	         studentinfotable.setWidths(new int[]{50, 50});
	         
	          PdfPCell reqcell = new PdfPCell(new Paragraph("Requested By",f10));
	              // Po_NOcell.addElement(PoNo_ph);
	               reqcell.setPaddingBottom(10);
	               reqcell.setBorderWidthTop(0);
	               reqcell.setBorderWidthRight(0);
	               reqcell.setBorderWidthBottom(0);
	               reqcell.setBackgroundColor(Color.gray);
	               
	            PdfPCell verifiercell = new PdfPCell(new Paragraph("Verifier Details",f10));
		              // Po_NOcell.addElement(PoNo_ph);
	               verifiercell.setPaddingBottom(10);
	               verifiercell.setBorderWidthTop(0);
	               verifiercell.setBorderWidthBottom(0);
	               verifiercell.setBackgroundColor(Color.gray);
	             
	               
           studentinfotable.addCell(reqcell);
           studentinfotable.addCell(verifiercell);
           
           
           
           
 	      PdfPCell studentinfoCell = new PdfPCell();
 	      studentinfoCell.setBorderWidthTop(0);
 	     
	 	     studentinfoCell.addElement(new Paragraph("Name      :"+userMasterEntity.getFirstName(),font9b));
	 	     studentinfoCell.addElement(new Paragraph("Mobile    :"+userMasterEntity.getPhoneNo(),font9b));
	 	     studentinfoCell.addElement(new Paragraph("Email ID :"+userMasterEntity.getEmailId(),font9b));
	 	     studentinfoCell.addElement(new Paragraph("Country   :India",font9b));
	 	     studentinfoCell.addElement(Chunk.NEWLINE);
	 	     studentinfoCell.addElement(Chunk.NEWLINE);
	 	     studentinfoCell.addElement(Chunk.NEWLINE);
 	 
 	     
	 	    PdfPCell verifierCell = new PdfPCell();
	 	    verifierCell.setBorderWidthTop(0);
	 	     
	 	    verifierCell.addElement(new Paragraph("Institue    :St.Joseph",font9b));
	 	    verifierCell.addElement(new Paragraph("Name       :omni",font9b));
	 	    verifierCell.addElement(new Paragraph("Email ID  :ab90c@gmail.com",font9b));
	 	    verifierCell.addElement(new Paragraph("GSTIN     :gst58652585824586",font9b));
	 	 
		 	
 	       studentinfotable.addCell(studentinfoCell);
	 	   studentinfotable.addCell(verifierCell);
	 	    
	 	     document.add(Chunk.NEWLINE);
 	         document.add(studentinfotable);
           
           
           
 	      PdfPTable studentDocTable = new PdfPTable(8);
 	      studentDocTable.setWidthPercentage(100);
 	   
 	   
 	     
 	     studentDocTable.addCell(getCellH("SR NO", Element.ALIGN_CENTER, font9b));
 	     studentDocTable.addCell(getCellH("Name", Element.ALIGN_CENTER, font9b));
 	     studentDocTable.addCell(getCellH("Enrollment NO", Element.ALIGN_CENTER, font9b));
 	     studentDocTable.addCell(getCellH("Document Name", Element.ALIGN_CENTER, font9b));
 	     studentDocTable.addCell(getCellH("Passing Year", Element.ALIGN_CENTER, font9b));
         studentDocTable.addCell(getCellH("Amount", Element.ALIGN_CENTER, font9b));
 	     studentDocTable.addCell(getCellH("GST", Element.ALIGN_CENTER, font9b));
 	     studentDocTable.addCell(getCellH("Total", Element.ALIGN_CENTER, font9b));
 
 	    Long totalAmt = (long) 0;
 	    Long totalGst = (long) 0;
 	     
 	    for(int i=0 ;i<studentDocList.size();i++)
        {
 	    	
     	   StudentVerificationDocsResponse responseObj = studentDocList.get(i);
 	    	
     	   String srno = ""+(i+1);
     	   
     	 
		     	   PdfPCell srcell=new PdfPCell(new Phrase(srno,ft10));
		     	   srcell.setHorizontalAlignment(Element.ALIGN_CENTER);
     	   
				   PdfPCell namevCell =new PdfPCell(new  Paragraph(responseObj.getFirst_name(),ft12));
				   namevCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				   
				   PdfPCell enrollCell=new PdfPCell(new Paragraph(responseObj.getEnroll_no(),ft12)); //-------------------------//
				   enrollCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				   
				   PdfPCell docNamecell=new PdfPCell(new Paragraph(responseObj.getDoc_name(),ft12)); //-------------------------//
				   docNamecell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				   
				   PdfPCell yearcell=new PdfPCell(new Paragraph(responseObj.getYear(),ft12)); //-------------------------//
				   yearcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				   
				   PdfPCell amtCell=new PdfPCell(new Paragraph(responseObj.getDocAmt().toString(),ft12)); 
				   amtCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				   
				   
				   
				   Long gstval = responseObj.getDocAmtWithGST() - responseObj.getDocAmt();
				   
				   totalGst = totalGst +gstval;
				   
				   PdfPCell gstAmtCell=new PdfPCell(new Paragraph(gstval.toString(),ft12)); 
				   gstAmtCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				   
				   PdfPCell totalCell=new PdfPCell(new Paragraph(responseObj.getDocAmtWithGST().toString(),ft12)); 
				   totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				   
				   totalAmt = totalAmt + responseObj.getDocAmtWithGST();
				   
				   
     	    studentDocTable.addCell(srcell);
     	    studentDocTable.addCell(namevCell);
     	    studentDocTable.addCell(enrollCell);
     	    studentDocTable.addCell(docNamecell);
     	    studentDocTable.addCell(yearcell);
          
            
     	    studentDocTable.addCell(amtCell); 
     	    studentDocTable.addCell(gstAmtCell); 
     	    studentDocTable.addCell(totalCell); 
           
     }
        
 	   
		    
		    PdfPCell totAMTCell =new PdfPCell(new Paragraph("Total Value:",font9b));
		    totAMTCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		    totAMTCell.setColspan(7);
		    
		    studentDocTable.addCell(totAMTCell);
	        
	        PdfPCell totAmtVCell = new PdfPCell(new Paragraph(totalAmt.toString() ,font9b));
	        totAmtVCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	        totAmtVCell.setPaddingBottom(5);
	        
	        studentDocTable.addCell(totAmtVCell);
	        
	        PdfPCell GstAMTCell =new PdfPCell(new Paragraph("Total GST:",font9b));
	        GstAMTCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	        GstAMTCell.setColspan(7);
		    
		    studentDocTable.addCell(GstAMTCell);
	        
	        PdfPCell GstAmtVCell = new PdfPCell(new Paragraph(totalGst.toString(),font9b));
	        GstAmtVCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	        GstAmtVCell.setPaddingBottom(5);
	        
	        studentDocTable.addCell(GstAmtVCell);
	        
	        PdfPCell grandAMTCell =new PdfPCell(new Paragraph("Grand Total:",font9b));
	        grandAMTCell.setColspan(7);
	        grandAMTCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		    
		    studentDocTable.addCell(grandAMTCell);
		    
		    Long grandTotal = totalAmt + totalGst;
	        
	        PdfPCell grandAmtVCell = new PdfPCell(new Paragraph(grandTotal.toString(),font9b));
	        grandAmtVCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	        grandAmtVCell.setPaddingBottom(5);
	        
            studentDocTable.addCell(grandAmtVCell);
        
 	 
 	      document.add(studentDocTable);
 	     
 	     
 	     
 	     
 	    
          PdfPTable contentTable = new PdfPTable(1);
          contentTable.setWidthPercentage(100);
       
	 
	    
	       PdfPCell  contentCell = new PdfPCell(); 
	       
	       contentCell.addElement(new Paragraph("   GENERAL FUND - 1 ",ft12));
	       contentCell.addElement(new Paragraph("Received from SECURE Credential ltd the sum of rupees ",ft12));
	       contentCell.addElement(new Paragraph(NumberToWordConverter1.convertToWords(totalAmt)+" only",ft12));
	       contentCell.addElement(new Paragraph("by Online transfer  being the amount of",ft12));
	       contentCell.addElement(new Paragraph("FEE  FOR  VER  OF  CERT  AS  PER  LIST",ft12));
	     
	       contentCell.setHorizontalAlignment(Element.ALIGN_CENTER);
	       contentCell.setVerticalAlignment(Element.ALIGN_CENTER);
	       contentCell.setPaddingLeft(50);
	       contentCell.setPaddingBottom(10);
	       contentCell.setPaddingTop(15);
	        
	       contentTable.addCell(contentCell);
	      
 	  //   document.add(contentTable);
 	

	        
	       document.close();
	        
	    } catch (Exception ex) {
	    	ex.printStackTrace();
	        System.err.println(ex.getMessage());
	    }
	    
		}
	    
	
	
	 
	 public static PdfPCell getCellH(String value, int alignment, Font font) {
	        PdfPCell cell = new PdfPCell();
	        cell.setUseAscender(true);
	        cell.setUseDescender(true);
	     //   cell.setBackgroundColor(BaseColor.DARK_GRAY);
	        Paragraph p = new Paragraph(value, font);
	        p.setAlignment(alignment);
	        cell.addElement(p);
	        cell.setPaddingBottom(5);
	        cell.setPaddingTop(5);
	        return cell;
	    }
	
		

}

