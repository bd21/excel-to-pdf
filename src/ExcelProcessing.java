import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class ExcelProcessing {
	public ExcelProcessing() {
		
	}
	
	public File getFile() {
		File file = new File("sant_csv.txt");
		return file;
	}
	public List<Order> getInfo(File file) {
		//safely initialize scanner
		Scanner scanner = new Scanner(System.in);
		try {
			scanner = new Scanner(file);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//get first line
		scanner.nextLine().split("\t");
		
		List<Order> entries = new ArrayList<Order>();
		int order = 1;
		while(scanner.hasNextLine()) {
			String[] temp = scanner.nextLine().split("\t");
			if(temp.length == 10) {//personal message
				entries.add(new Order(temp[0], temp[1], temp[2], temp[3],
									  temp[4], temp[5], temp[6], temp[7], 
									  temp[8], temp[9], order));
				System.out.println("added order with personal message");
			} else {
				System.out.println("Error! the line of input was of length "
									+ temp.length + " and not 10 as expected.");
			}
			order++;
		}
		//so now we have an arraylist of orders with the first one being the headers

		System.out.println();
		System.out.println("Check that the following entries are correct.");
		System.out.println();
		for(Order o : entries) {
			o.print();
		}
		
		System.out.println("Everything correct? Press enter to continue, or anything else to exit the program"
						    + " and fix your file.");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String response = "";
	/*	try {
			response = reader.readLine();

		} catch(IOException e) {
			e.printStackTrace();
		}*///add in later
		
		if(!response.equalsIgnoreCase("")) {
			System.out.println("Exiting...");
			System.exit(0);
		}

		
		
		scanner.close();
		return entries;
	}

}

