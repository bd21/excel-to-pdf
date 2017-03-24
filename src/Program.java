import java.io.File;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import java.util.*;
import java.io.IOException;


public class Program {

	public static void main(String[] args) throws IOException, InvalidFormatException {
		// TODO Auto-generated method stub
		System.out.println("Starting...");
		System.out.println("Reading excel file...");
		ExcelProcessing e = new ExcelProcessing();
		File file = e.getFile();
		//create a list of orders
		List<Order> entries = e.getInfo(file);
		System.out.println("Info successfully retrieved from excel file.");
		System.out.println("Exporting all entries to word documents...");
		//plug in the list of orders into the word exporter
		ExportToWord export = new ExportToWord(entries, "cert_test.docx");//input filename
		export.replaceOrders();
		System.out.println("document written to word");
		
		System.out.println("converting to pdf...");
		
		
		
	}

}
