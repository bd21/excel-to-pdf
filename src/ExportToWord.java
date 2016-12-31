import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;

public class ExportToWord {
	private List<Order> list;
	private String filename;
	public ExportToWord(List<Order> orders, String filename) throws InvalidFormatException, IOException {
		this.list = orders;
	    //Template Document
	    this.filename = filename;
	}

	
	//goes through the orders and creates new certificate documents based on the order info
	public void replaceOrders() throws IOException, InvalidFormatException {
	      //Write the Document in file system
	      String basePath = new File("").getAbsolutePath();
	      for(Order o : this.list) {
	    	  XWPFDocument document = new XWPFDocument(OPCPackage.open(this.filename));
	    	  System.out.println("currently working on " + o.getName());
	    	  FileOutputStream out = new FileOutputStream(
		    		  new File(basePath + "/output/" + o.getName() + ".docx"));

	    	  replaceFields(o, document);
		      document.write(out);
		      out.close();
		      document.close();
	      }
	}
	
	private void replaceFields(Order o, XWPFDocument document) {
		if(o.binary) {
			replace(document, "#coordinate_1", o.coordinates1);
			replace(document, "#coordinate_2", o.coordinates2);
		} else {
			replace(document, "#coordinate_1", "");
			replace(document, "&", o.coordinates1);//set size to larger
			replace(document, "#coordinate_2", "");
		}

		replace(document, "#insert_name", o.starName);
		
		//this chunk supports unlimited message lines
		for(int i = 0; i < o.personalMessage.length; i++) {
			replace(document, "msg_" + i, o.personalMessage[i]);
		}


		replace(document, "#registration_number", o.registrationNumber);
		replace(document, "#current_date", o.date);
	}
	
	//replaces the before's with afters in all word tables and paragraphs
	private void replace(XWPFDocument document, String before, String after) {
		//iterate through paragraphs
		boolean flag = false;//remove later and next line
		System.out.println("Currently trying to replace " + before + " with " + after + ".");
		for (XWPFParagraph p : document.getParagraphs()) {
		    List<XWPFRun> runs = p.getRuns();
		    if (runs != null) {
		        for (XWPFRun r : runs) {
		            String text = r.getText(0);
		            if (text != null && text.contains(before)) {
		                text = text.replace(before, after);
		                r.setText(text, 0);
		                flag = true;
		                System.out.println("Replaced " + before + " with " + after + ".");
		                
		            }
		        }
		    }
		}
		
		//through tables 
 		for (XWPFTable tbl : document.getTables()) {
			   for (XWPFTableRow row : tbl.getRows()) {
			      for (XWPFTableCell cell : row.getTableCells()) {
			         for (XWPFParagraph p : cell.getParagraphs()) {
			            for (XWPFRun r : p.getRuns()) {
			              String text = r.getText(0);
			              if (text.contains(before)) {
			                text = text.replace(before, after);
			                r.setText(text, 0);
			                flag = true;
			              }
			            }
			         }
			      }
			   }
		}
 		
 		System.out.println(flag);//remove later
		//through textboxes
		//turns out textboxes are a bitch to implement....fuck that
	}
}