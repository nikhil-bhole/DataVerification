package util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Execute {
	
	public static void main(String args[]) throws IOException {
		run();
	}
	
	public static void run() throws IOException {
		//String sqlQuery = "select PatientRegistrationID,FirstName from patientregistration";
		String regno = "9999";
		String sqlQuery = "select FirstName from patientregistration where RegistrationNo = "+ regno;
		
		/*List<String> results = DatabaseConnector.executeSQLQuery_List("QA", sqlQuery);
		
		System.out.println("No of records:" + results.size()+"\n");
		for(int i=0;i<results.size();i++) {
			log(results.get(i));
			//System.out.println(results.get(i));
		}*/
		
		String result = DatabaseConnector.executeSQLQuery("QA", sqlQuery);
		System.out.println(result);
	}
	
	public static void log(String message) throws IOException 
	{ 
	     PrintWriter out = new PrintWriter(new FileWriter("/home/nikhil/JavaEE/Data-Verification/src/main/resources/file.txt", true), true);
	     out.write(message);
	     out.close();
	}
	
}
