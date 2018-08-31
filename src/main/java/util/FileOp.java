package util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FileOp {

	public static InputStream getFile() {
		InputStream input = null;
		String filename = "PBNEW_24_8_18.xlsx";
		input = FileOp.class.getClassLoader().getResourceAsStream(filename);
		return input;
	}

	public static List<List<String>> execute() {
		DataFormatter dataFormatter = new DataFormatter();
		List<List<String>> test = new ArrayList<List<String>>();
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(getFile());
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				XSSFSheet sheet = workbook.getSheetAt(i);
				Iterator<?> rows = sheet.rowIterator();
				while (rows.hasNext()) {
					XSSFRow row = (XSSFRow) rows.next();
					Iterator<?> cells = row.cellIterator();
					List<String> data = new ArrayList<String>();
					while (cells.hasNext()) {
						XSSFCell cell = (XSSFCell) cells.next();
						data.add(dataFormatter.formatCellValue(cell));
					}
					test.add(data);
				}
				workbook.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return test;
	}

	public static void main(String args[]) throws IOException {
		handleData();
	}
	
	
	static void handleData() throws IOException {
		List<List<String>> test = execute();
		test.remove(0);
		
		String[] temp;
		String delimiter = ",";
		String registrationNumber = "";
		String firstName = "";
		
		for(int i=0;i<test.size();i++) {
			String record = String.join(",", test.get(i));
			temp = record.split(delimiter); 
			registrationNumber = temp[0]; 
			firstName = temp[1];
			
			if(firstName.equals("") || firstName.isEmpty()) {
				firstName = "NULL";
			}
			
			String sqlQuery = "select FirstName from patientregistration where RegistrationNo = "+ registrationNumber;
			String result = DatabaseConnector.executeSQLQuery("QA", sqlQuery);
			if(firstName.contains(result)) {
				System.out.println("Matched:" + registrationNumber+ "::" + firstName + "=>" +  result);
				System.out.println("\n");
			}else {
				log("Unmatched:" + registrationNumber+ "::" + firstName + "=>" +  result);
				log("\n");
			}
			
			System.out.println("\n");
		}
		System.out.println("Success");
		
	}
	
	
	public static void log(String message) throws IOException 
	{ 
	     PrintWriter out = new PrintWriter(new FileWriter("/home/nikhil/JavaEE/Data-Verification/src/main/resources/file.txt", true), true);
	     out.write(message);
	     out.close();
	}
	
}
