package DataFunctions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteDataSource;

public class DataSource {
	SQLiteDataSource ds;
	
	public DataSource(){
		ds = null;
	
	    try {
	        ds = new SQLiteDataSource();
	        ds.setUrl("jdbc:sqlite:test.db");
	    } catch ( Exception e ) {
	        e.printStackTrace();
	        System.exit(0);
	    }
	    System.out.println( "Opened database successfully" );
	
	    try ( Connection conn = ds.getConnection() ) {
	    } catch ( SQLException e ) {
	        e.printStackTrace();
	        System.exit( 0 );
	    }
	
	    System.out.println( "Created database successfully" );
	}
	
	public void newQuery(String query) {
		try ( Connection conn = ds.getConnection();
	            Statement stmt = conn.createStatement(); ) {
	            int rv = stmt.executeUpdate( query );
	            System.out.println( "executeUpdate() returned " + rv );
	        
		} catch ( SQLException e ) {
	            e.printStackTrace();
	            System.exit( 0 );
	    }
	}
	
	public void addCourseTable()
	{
		ds.setUrl("jdbc:sqlite:test.db");
		try { Connection conn = ds.getConnection();
		Statement smt = conn.createStatement();
		String sql = "CREATE TABLE  IF NOT EXISTS COURSE (Name VARCHAR(255), CourseID VARCHAR(255), NumberOfCredits FLOAT, "
				   + "CourseLevel INTEGER, NumberOfStudents INTEGER, CoursePreRequisite VARCHAR(255), CourseCoRequisite VARCHAR(255) )";
		
		smt.executeUpdate(sql);
		System.out.println("Course Table Created");
		
		} catch (SQLException e) {
			System.out.println("Unhandled SQL Exception");
			e.printStackTrace();
		}
	}
	
	public void addStudentTable()
	{
		ds.setUrl("jdbc:sqlite:test.db");
		try { Connection conn = ds.getConnection();
		Statement smt = conn.createStatement();
		String sql = "CREATE TABLE  IF NOT EXISTS STUDENTS (LastName VARCHAR(255), FirstName VARCHAR(255),"+ 
					"MiddleName VARCHAR(255), StudentID VARCHAR(255), INTERNALID VARCHAR(255), GRADE INTEGER," +
					"PHONENUM VARCHAR(255), GENDER VARCHAR(255), GRADYEAR INTEGER, ADDRESS VARCHAR(255), SECONDPHONE VARCHAR(255)," +
					"BIRTHDATE VARCHAR(255),  THIRDPHONE VARCHAR(255), EMAILADDRESS VARCHAR(255), DUALCREDIT VARCHAR(255)," +
					"PROGRAMOFSTUDY VARCHAR(255))";

		
		smt.executeUpdate(sql);
		System.out.println("Student Table Created");
		
		} catch (SQLException e) {
			System.out.println("Unhandled SQL Exception");
			e.printStackTrace();
		}
	}

	
	public void courseImport() {
		newQuery("DELETE FROM COURSE");
		//Set relative paths for DB and CSV
		ds.setUrl("jdbc:sqlite:test.db");
		String csvPath = "Core_Courses.csv";
		
		//These Vars are for all the columns in the Core Courses Table
		String Name;
		String CourseID;
		float NumberOfCredits;
		int CourseLevel;
		int NumberOfStudents;
		String CoursePreRequisite;
		String CourseCoRequisite;
		
		//Set up Line Reader for CSV
		try {
			BufferedReader lineReader = new BufferedReader(new FileReader(csvPath));
			//This skips the header line
			String line = lineReader.readLine();
			Connection conn = ds.getConnection();
			Statement smt = conn.createStatement();	
			
			// This while loop goes through the whole CSV Iteratively
			while ((line=lineReader.readLine())!=null) {
				if(!line.contains(",,,")) {
					String currentLine[] = line.split(",");
					Name = currentLine[0];
					CourseID = currentLine[1];
					NumberOfCredits = Float.valueOf(currentLine[2]);
					CourseLevel = Integer.valueOf(currentLine[3]);
					NumberOfStudents = Integer.valueOf(currentLine[4]);
					CoursePreRequisite = currentLine[5];
					CourseCoRequisite = currentLine[6];				
					
					String sql =	"INSERT INTO COURSE (Name, CourseID, NumberOfCredits, CourseLevel, NumberOfStudents, CoursePreRequisite, CourseCoRequisite) VALUES"
							+ "(\'" + Name + "\'," + "\'" + CourseID + "\'," + "\'" + NumberOfCredits + "\'," + "\'" + CourseLevel + "\'," + "\'" + NumberOfStudents 
							+ "\'," + "\'" + CoursePreRequisite + "\'," + "\'" + CourseCoRequisite + "\')";
					
					smt.addBatch(sql);
					
				}
			}
						
			smt.executeBatch();
			
			System.out.println("Finished Importing Course CSV");
			lineReader.close();

		
		} catch (FileNotFoundException e) {
			System.out.println("Course List Not Found. Error");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IO Exception");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Unhandled SQL Exception");
			e.printStackTrace();
		}
		

	}
	
	public void studentImport() {
		newQuery("DELETE FROM STUDENTS");
		//Set relative paths for DB and CSV
		ds.setUrl("jdbc:sqlite:test.db");
		String csvPath = "Students.csv";
		
		//These Vars are for all the columns in the Students Table
		String FirstName;
		String LastName;
		String MiddleName;
		String StudentID;
		String InternalID;
		int Grade;
		String PhoneNum;
		String Gender;
		int GradYear;
		String Address;
		String SecondPhone;
		String BirthDate;
		String ThirdPhone;
		String EmailAddress;
		String DualCredit;
		String ProgramOfStudy;
		
		//Set up Line Reader for CSV
		try {
			BufferedReader lineReader = new BufferedReader(new FileReader(csvPath));
			//This skips the header line
			String line = lineReader.readLine();
			Connection conn = ds.getConnection();
			Statement smt = conn.createStatement();	
			
			// This while loop goes through the whole CSV Iteratively
			while ((line=lineReader.readLine())!=null) {
				if(!line.contains(",,,")) {
					String currentLine[] = line.split(",");
					LastName = currentLine[0];
					FirstName = currentLine[1];
					MiddleName = currentLine[2];
					StudentID = currentLine[3];
					InternalID = currentLine[4];
					Grade = Integer.valueOf(currentLine[5]);
					PhoneNum = currentLine[6];
					Gender = currentLine[7];
					GradYear = Integer.valueOf(currentLine[8]);
					Address = currentLine[9];
					SecondPhone = currentLine[10];
					BirthDate = currentLine[11];
					ThirdPhone = currentLine[12];
					EmailAddress = currentLine[13];
					DualCredit = currentLine[14];
					ProgramOfStudy = currentLine[15];				
					
					String sql =	"INSERT INTO STUDENTS (LastName, FirstName, MiddleName, StudentID, INTERNALID, GRADE, PHONENUM," +
									"GENDER, GRADYEAR, ADDRESS, SECONDPHONE, BIRTHDATE,  THIRDPHONE, EMAILADDRESS, DUALCREDIT," +
									"PROGRAMOFSTUDY) VALUES"
							+ "(\'" + LastName + "\'," + "\'" + FirstName + "\'," + "\'" + MiddleName + "\'," + "\'" + StudentID + "\'," + "\'" + InternalID 
							+ "\'," + "\'" + Grade + "\'," + "\'" + PhoneNum + "\'," + "\'" + Gender + "\'," + "\'" + GradYear +"\'," + "\'" + Address  
							+ "\'," + "\'" + SecondPhone + "\'," + "\'" + BirthDate + "\'," + "\'" + ThirdPhone + "\'," + "\'" + EmailAddress   
							+ "\'," + "\'"+ DualCredit + "\',"  + "\'" + ProgramOfStudy +"\')";
									
					
					smt.addBatch(sql);
					
				}
				
			}	
			smt.executeBatch();
			
			System.out.println("Finished Importing Students CSV");
			lineReader.close();

		
		} catch (FileNotFoundException e) {
			System.out.println("Students Not Found. Error");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IO Exception");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Unhandled SQL Exception");
			e.printStackTrace();
		}
		

	}
	
}

