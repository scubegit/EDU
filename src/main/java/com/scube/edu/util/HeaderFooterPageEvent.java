package com.scube.edu.util;


import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;



public class HeaderFooterPageEvent extends PdfPageEventHelper {

    private PdfTemplate t;
    private Image total;
    private HttpServletRequest request = null ;

    public void onOpenDocument(PdfWriter writer, Document document) {
        t = writer.getDirectContent().createTemplate(530, 16);
        try {
            total = Image.getInstance(t);
 //           total.setRole(PdfName.ARTIFACT);
        } catch (DocumentException de) {
            throw new ExceptionConverter(de);
        }
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        addHeader(writer);
        addFooter(writer);
    }

    private void addHeader(PdfWriter writer){
        PdfPTable header = new PdfPTable(1);
        try {
        	
           	String letterheadHeader = "";


            // set defaults
         	header.setWidths(new int[]{27});
            header.setTotalWidth(527);
            header.setLockedWidth(true);
            header.getDefaultCell().setFixedHeight(140);
            header.getDefaultCell().setBorder(0);
            /*header.getDefaultCell().setBorder(Rectangle.BOTTOM);
            header.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);*/

            // add image
			/*
			 * Image logo = Image.getInstance("123.png");
			 * System.out.println("-------------logo--------"+logo);
			 * logo.setWidthPercentage(100);
			 * header.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			 * header.addCell(logo);
			 */
            // add text
  /*          PdfPCell text = new PdfPCell();
            text.setPaddingBottom(15);
            text.setPaddingLeft(10);
            text.setBorder(Rectangle.BOTTOM);
            text.setBorderColor(BaseColor.LIGHT_GRAY);
            text.addElement(new Phrase("iText PDF Header Footer Example", new Font(Font.FontFamily.HELVETICA, 12)));
            text.addElement(new Phrase("https://memorynotfound.com", new Font(Font.FontFamily.HELVETICA, 8)));
            header.addCell(text);
 */ 
            // write content
            header.writeSelectedRows(0, -1, 34, 803, writer.getDirectContent());
      } catch(DocumentException de) {
            throw new ExceptionConverter(de);
      }
    }

    private void addFooter(PdfWriter writer){
        PdfPTable footer = new PdfPTable(1);
        try {
            // set defaults
            footer.setWidths(new int[]{27});
            footer.setTotalWidth(527);
            footer.setLockedWidth(true);
            footer.getDefaultCell().setFixedHeight(140);
            footer.getDefaultCell().setBorder(0);
/*          footer.getDefaultCell().setBorder(Rectangle.TOP);
            footer.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);
            // add copyright
            footer.addCell(new Phrase("\u00A9 Memorynotfound.com", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
            // add current page count
            footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            footer.addCell(new Phrase(String.format("Page %d of", writer.getPageNumber()), new Font(Font.FontFamily.HELVETICA, 8)));
            // add placeholder for total page count
            PdfPCell totalPageCount = new PdfPCell(total);
            totalPageCount.setBorder(Rectangle.TOP);
            totalPageCount.setBorderColor(BaseColor.LIGHT_GRAY);
            footer.addCell(totalPageCount);
*/
         
            
         

         	 // add image
//            Image logo = Image.getInstance("ha-img.jpg");
//            System.out.println("-------------logo--------"+logo);
//            logo.setWidthPercentage(100);
            footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            
//            footer.addCell(logo);
         	
         	
            // write page
            PdfContentByte canvas = writer.getDirectContent();
 //           canvas.beginMarkedContentSequence(PdfName.ARTIFACT);
            footer.writeSelectedRows(0, -1, 34, 80, canvas);
            canvas.endMarkedContentSequence();
        } catch(DocumentException de) {
            throw new ExceptionConverter(de);
        }
    }

    public void onCloseDocument(PdfWriter writer, Document document) {
        int totalLength = String.valueOf(writer.getPageNumber()).length();
        int totalWidth = totalLength * 5;
     //   ColumnText.showTextAligned(t, Element.ALIGN_RIGHT,new Phrase(String.valueOf(writer.getPageNumber()), new Font(Font.FontFamily.HELVETICA, 8)),totalWidth, 6, 0);
    }
}