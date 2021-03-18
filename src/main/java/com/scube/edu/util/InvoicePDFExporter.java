
package com.scube.edu.util;



import java.awt.Color;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.TabStop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
import com.lowagie.text.pdf.PdfDocument;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.scube.edu.request.StudentDocVerificationRequest;
import com.scube.edu.response.StudentVerificationDocsResponse;

public class InvoicePDFExporter {
	
       private static final Logger logger = LoggerFactory.getLogger(InvoicePDFExporter.class);
    
	    private   StudentVerificationDocsResponse studentVerificationDocsResponse  ;

	    public InvoicePDFExporter(StudentVerificationDocsResponse studentVerificationDocsResponse)
	    {
                     this.studentVerificationDocsResponse = studentVerificationDocsResponse;
	   
	    }

		
	   

	public void export(HttpServletResponse response) {
		    
	    	try {
	    	
	        Document document = new Document(PageSize.A4, 30, 30, 50, 50);
	        document.setMargins(10, 10, 10, 10);
	      
	      
	        
	        
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
	        
	        
	        BaseFont bf_helv = BaseFont.createFont(BaseFont.HELVETICA, "Cp1252", false);
	        BaseFont bf_times = BaseFont.createFont(BaseFont.TIMES_ROMAN, "Cp1252", false);
	        BaseFont bf_courier = BaseFont.createFont(BaseFont.COURIER, "Cp1252", false);
	        BaseFont bf_symbol = BaseFont.createFont(BaseFont.SYMBOL, "Cp1252", false);
	        
	 
	        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
	  
	        document.open();
	        
	     
	        
	           Image img = Image.getInstance("logo.png");
	           img.setAlignment(Element.ALIGN_CENTER);
		   	   img.scaleToFit(100,100);
		
	          
		        
		        PdfPCell ImageCell = new PdfPCell();
			   	  
		   	   
		   	    
		        ImageCell.addElement(img);
		   	    ImageCell.setBorderWidthRight(0);
		   	    ImageCell.setPaddingTop(15);
	            ImageCell.setPaddingRight(5);
		        
		        Paragraph firstLinep= new Paragraph("       University Of Mumbai",headingFont15); 
		      
		   
		        
		        PdfPCell pdfWordCell = new PdfPCell();
		        pdfWordCell.addElement(firstLinep);
		        pdfWordCell.addElement(Chunk.NEWLINE);
		        pdfWordCell.addElement(new Paragraph("Mahatma Jotirao Phule Bhavan,Vidhyanagri ,Santacruz(E) ,Mumbai",f10));
		    
		        
		        pdfWordCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		        pdfWordCell.setVerticalAlignment(Element.ALIGN_CENTER);
		        pdfWordCell.setBorderWidthLeft(0);
		        pdfWordCell.setPaddingLeft(50);
		        pdfWordCell.setPaddingBottom(10);
		        pdfWordCell.setPaddingTop(15);
		      
		        
		      
	            
	            PdfPTable tab1 = new PdfPTable(2);
	            tab1.setWidthPercentage(100);
	            tab1.setWidths(new int[]{25, 75});
	            
	            tab1.addCell(ImageCell);
	            tab1.addCell(pdfWordCell);
	            
	         document.add(tab1);
	         
	         PdfPTable recptinfotable = new PdfPTable(2);
	         recptinfotable.setWidthPercentage(100);
	         recptinfotable.setWidths(new int[]{50, 50});
	               
	             
	               PdfPCell rcpt_NOcell = new PdfPCell(new Paragraph("Receipt No.:" + studentVerificationDocsResponse.getApplication_id() ,f10));
	              // Po_NOcell.addElement(PoNo_ph);
	               rcpt_NOcell.setPaddingBottom(5);
	               rcpt_NOcell.setBorderWidthTop(0);
	               rcpt_NOcell.setBorderWidthRight(0);
	               rcpt_NOcell.setBorderWidthBottom(0);
	               
	               DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy ");
	               LocalDateTime now = LocalDateTime.now();
	               System.out.println(dtf.format(now));
	               
	               //Phrase PoDate_ph =new Phrase(PODateH + PODateV+ chunk.NEWLINE,boldFont);
	               PdfPCell Datecell = new PdfPCell(new Paragraph("Date : " + dtf.format(now),f10));
	               Datecell.setHorizontalAlignment(Element.ALIGN_CENTER);
	               Datecell.setBorderWidthTop(0);
	               Datecell.setBorderWidthLeft(0);
		           Datecell.setBorderWidthBottom(0);
	             //  PoDatecell.addElement(PoDate_ph);
	        
	               
           recptinfotable.addCell(rcpt_NOcell);
           recptinfotable.addCell(Datecell);
           
           document.add(recptinfotable);
           
           
	        
           PdfPTable contentTable = new PdfPTable(1);
           contentTable.setWidthPercentage(100);
         
           
           
           Paragraph skuTitle = new Paragraph();
	     
	        skuTitle.add("   GENERAL FUND - 1 \n ");
	        skuTitle.add(Chunk.NEWLINE);
	        skuTitle.add("Received from SECURE 	credential ltd the sum of rupees \n \n by D.D.No. 58585368 HDFC     being the amount of \n\n FEE  FOR  VER  OF  CERT  AS  PER  LIST");
	        skuTitle.setFont(headingFont15);
	        skuTitle.setAlignment(Paragraph.ALIGN_CENTER);
	        
           
	       
	    
	       PdfPCell  contentCell = new PdfPCell(); 
	       
	       contentCell.addElement(skuTitle);
	       contentCell.addElement(Chunk.NEWLINE);
	     
	        
	       contentCell.setHorizontalAlignment(Element.ALIGN_CENTER);
	       contentCell.setVerticalAlignment(Element.ALIGN_CENTER);
	       contentCell.setPaddingLeft(50);
	       contentCell.setPaddingBottom(10);
	       contentCell.setPaddingTop(15);
	        
	       contentTable.addCell(contentCell);
	      
           document.add(contentTable);
           
			/*
			 * Paragraph skuTitle = new Paragraph(); skuTitle.add("\n");
			 * skuTitle.add("GENERAL FUND - 1"); skuTitle.add("\n");
			 * skuTitle.add("Received from SECURE 	credential ltd the sum of rupes");
			 * skuTitle.setFont(headingFont15);
			 * skuTitle.setAlignment(Paragraph.ALIGN_CENTER);
			 * 
			 * document.add(skuTitle);
			 */
	        
           
 	      PdfPTable documentTable = new PdfPTable(2);
 	      documentTable.setWidthPercentage(100);
 	      
 	         
 		 insertCell(documentTable, "Document Name", Element.ALIGN_CENTER,  f10);
 	     insertCell(documentTable, "Document Amount", Element.ALIGN_CENTER,  f10);
 	     insertCell(documentTable, "Document Amount With GST", Element.ALIGN_CENTER,  f10);
 	     
 	     
 	
 	    	
 	    	 insertCell(documentTable,studentVerificationDocsResponse.getDoc_name().toString() , Element.ALIGN_CENTER,  ft10);
 	 	     insertCell(documentTable,studentVerificationDocsResponse.getDocAmt().toString(), Element.ALIGN_CENTER,  ft10);
 	 	     insertCell(documentTable,studentVerificationDocsResponse.getDocAmtWithGST().toString(), Element.ALIGN_CENTER,  ft10);
 	 	    		
 	    
 	     
 	     
 	     
 
 	 
 	     document.add(documentTable);
 	      
 	      
 	      
 	      
	      PdfPTable footerTable = new PdfPTable(1);
	      footerTable.setWidthPercentage(100);
	         
	      
	      PdfPCell footerCell = new PdfPCell();
	      
	      footerCell.addElement(new Paragraph("1. Receipt subject to the realisation of Cheque/Draft etc., it tendered.",ft10));
	      footerCell.addElement(new Paragraph("2. No. of duplicate  receipt will be issued.",ft10));
	      footerCell.addElement(new Paragraph("3.The statement of marks should be collected from Ground Floor,Window No.2 after 15 working days on production of the receipt and if"+
	                                             "no collected within 3 months no responsibility lies on the unversity.",ft10));
	      footerCell.setBorderWidthTop(0);
	     
	    
	      footerTable.addCell(footerCell);
	   
	       document.add(footerTable);

	        
	       
	        
	        

	        document.close();
	        
	    } catch (Exception ex) {
	    	ex.printStackTrace();
	        System.err.println(ex.getMessage());
	    }
	    
		}
	    
	
	
	
	 private void insertCell(PdfPTable table, String text, int align, Font font){
		   
		  //create a new cell with the specified Text and Font
		  PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
		  //set the cell alignment
		  cell.setHorizontalAlignment(align);
		  //set the cell column span in case you want to merge two or more cells
	
		  //in case there is no text and you wan to create an empty row
		  if(text.trim().equalsIgnoreCase("")){
		   cell.setMinimumHeight(10f);
		  }
		  //add the call to the table
		  table.addCell(cell);
		   
		 }
		 
		

}

