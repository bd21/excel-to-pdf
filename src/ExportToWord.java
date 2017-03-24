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
	private String inputFilename;
	public ExportToWord(List<Order> orders, String inputFilename) throws InvalidFormatException, IOException {
		this.list = orders;
	    //Template Document
	    this.inputFilename = inputFilename;
	}

	
	//goes through the orders and creates new certificate documents based on the order info
	public void replaceOrders() throws IOException, InvalidFormatException {
	      //Write the Document in file system
	      String basePath = new File("").getAbsolutePath();
	      
	      //We need to
	      //create a copy of the file
	      //rename it
	      //replace fields
	      for(Order o : this.list) {
	    	  XWPFDocument document = new XWPFDocument(OPCPackage.open(this.inputFilename));
	    	  //so we're working on the original that we don't want to touch
	    	  
	    	  //System.out.println("currently working on " + o.getName());
	    	  //File output stream to create the new document as an exact copy as before
	    	  
	    	  String outputPath = basePath + "/" + o.getName() + ".docx";
	    	  FileOutputStream out = new FileOutputStream(new File(outputPath));
	    	  //write and close out the copy
		      document.write(out);
		      out.close();
		      document.close();
	      }
	      for(Order o : this.list) {//i think this maintains the same ordering
	    	  String outputPath = basePath + "/" + o.getName() + ".docx";
	    	  XWPFDocument document = new XWPFDocument(OPCPackage.open(outputPath));
	    	  //so we're working on the original that we don't want to touch
	    	  

	    	  FileOutputStream out = new FileOutputStream(new File(basePath + "/output/" + o.getName() + ".docx"));
	    	  //write and close out the copy
	    	  
	    	  replaceFields(o, document);
		      document.write(out);
		      out.close();
		      document.close();
	      }
	}
	
	private void replaceFields(Order o, XWPFDocument document) throws FileNotFoundException, IOException {
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
	private void replace(XWPFDocument document, String before, String after) throws FileNotFoundException, IOException {
		//iterate through paragraphs
		boolean flag = false;//remove later and next line
		System.out.println("Currently trying to replace " + before + " with " + after + ".");
		TextReplacer tr = new TextReplacer(before, after);
		tr.replace(document);
 		
		//through textboxes
		//turns out textboxes are a bitch to implement....fuck that
	}
}
