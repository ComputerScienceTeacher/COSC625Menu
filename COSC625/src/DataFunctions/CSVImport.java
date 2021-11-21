package DataFunctions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteDataSource;

public class CSVImport {
	public SQLiteDataSource ds = new SQLiteDataSource();
	Connection conn;
	Statement smt;
	String csvPath;
	
	public CSVImport() {
		ds = null;
				
	    try {
	        ds = new SQLiteDataSource();
	        ds.setUrl("jdbc:sqlite:test.db");
			conn = ds.getConnection();
		    programImport();
		    studentImport();
		    teacherImport();
		    historyImport();
		    courseImport();
		    conn.close();
	    } catch ( Exception e ) {
	        e.printStackTrace();
	        System.exit(0);
	    }

	}
	
	/**
	 * This method imports the programs of study into the RAP database
	 */
	public void programImport() {
		//Set relative paths for DB and CSV
		//ds.setUrl("jdbc:sqlite:test.db");
		csvPath = "ProgramOfStudy.csv";
		
		//These Vars are for all the columns in the Core Courses Table
		String name;
		String req;
		
		//Set up Line Reader for CSV
		try {
			BufferedReader lineReader = new BufferedReader(new FileReader(csvPath));
			//This skips the header line
			String line = lineReader.readLine();
			smt = conn.createStatement();	
			
			// This while loop goes through the whole CSV Iteratively
			while ((line=lineReader.readLine())!=null) {
				if(!line.contains(",,,")) {
					String currentLine[] = line.split(",");
					name = currentLine[0];
					req = currentLine[1];
					
					String sql =	"INSERT INTO Program (Name, Requirements) VALUES"
							+ "(\'" + name + "\'," + "\'" + req + "\')";		
					smt.addBatch(sql);	
				}
			}
						
			smt.executeBatch();
			
			System.out.println("Finished Importing Program CSV");
			lineReader.close();
	
		
		} catch (FileNotFoundException e) {
			System.out.println("Program List Not Found. Error");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IO Exception");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Unhandled SQL Exception");
			e.printStackTrace();
		}
		
	
	}

	/**
	 * This method imports the students.csv into the RAP database
	 */
	public void studentImport() {
		//Set relative paths for DB and CSV
		//ds.setUrl("jdbc:sqlite:test.db");
		csvPath = "Students.csv";
		
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
		String History;
		String Schedule;
		
		//Set up Line Reader for CSV
		try {
			BufferedReader lineReader = new BufferedReader(new FileReader(csvPath));
			//This skips the header line
			String line = lineReader.readLine();
			smt = conn.createStatement();	
			
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
					History = currentLine[16];
					Schedule = currentLine[17];
					
					
					
					/* Some students have names with apostrophes in them. This code flags every apostrophe imported so that
					 * it does not cause SQL errors when executing statements 
					 * 
					 * for example, turns [  O'Brian  ] -> [  O''Brian  ] which sql properly reads as [  O'Brian  ]
					 * 
					 */
					
					String[] mod;
							
					if (LastName.contains("'")) {
						mod = LastName.split("'");
						LastName = mod[0] + "''" + mod[1];
					}
					if (FirstName.contains("'")) {
						mod = LastName.split("'");
						FirstName = mod[0] + "''" + mod[1];
					}	
					if (MiddleName.contains("'")) {
						mod = LastName.split("'");
						MiddleName = mod[0] + "''" + mod[1];
					}		
					
					String sql =	"INSERT INTO STUDENTS (LastName, FirstName, MiddleName, StudentID, INTERNALID, GRADE, PHONENUM," +
									"GENDER, GRADYEAR, ADDRESS, SECONDPHONE, BIRTHDATE,  THIRDPHONE, EMAILADDRESS, DUALCREDIT," +
									"PROGRAMOFSTUDY, HISTORY, SCHEDULE) VALUES"
							+ "(\'" + LastName + "\'," + "\'" + FirstName + "\'," + "\'" + MiddleName + "\'," + "\'" + StudentID + "\'," + "\'" + InternalID 
							+ "\'," + "\'" + Grade + "\'," + "\'" + PhoneNum + "\'," + "\'" + Gender + "\'," + "\'" + GradYear +"\'," + "\'" + Address  
							+ "\'," + "\'" + SecondPhone + "\'," + "\'" + BirthDate + "\'," + "\'" + ThirdPhone + "\'," + "\'" + EmailAddress   
							+ "\'," + "\'"+ DualCredit + "\',"  + "\'" + ProgramOfStudy +"\',"  + "\'" + History + "\'," + "\'" + Schedule + "\')";
									
					
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

	/**
	 * This method imports the teachers.csv into the RAP database
	 */
	public void teacherImport() {
		//Set relative paths for DB and CSV
		//ds.setUrl("jdbc:sqlite:test.db");
		csvPath = "Teachers.csv";
		
		//These Vars are for all the columns in the Teachers Table
		String name;
		String phone;
		String address;
		String email;
		String zip;
		String ID;
		
		//Set up Line Reader for CSV
		try {
			BufferedReader lineReader = new BufferedReader(new FileReader(csvPath));
			//This skips the header line
			String line = lineReader.readLine();
			Statement smt = conn.createStatement();	
		
			// This while loop goes through the whole CSV Iteratively
			while ((line=lineReader.readLine())!=null) {
				if(!line.contains(",,,")) {
					String currentLine[] = line.split(",");
					name = currentLine[0];
					phone = currentLine[1];
					email = currentLine[2];
					address = currentLine[3];
					zip = currentLine[4];
					ID = currentLine[5];
					
					String sql =	"INSERT INTO TEACHERS (Name, Phone, Email, Address, Zip, ID ) VALUES"
							+ "(\'" + name + "\'," + "\'" + phone + "\'," + "\'" + email + "\'," + "\'" + address + "\'," + "\'" + zip 
							+ "\'," + "\'" + ID + "\')";
					
					smt.addBatch(sql);
					
				}
			}
						
			smt.executeBatch();
			
			System.out.println("Finished Importing Teacher CSV");
			lineReader.close();
	
		
		} catch (FileNotFoundException e) {
			System.out.println("Teachers Not Found. May be a teacher shortage. Otherwise, it is an error");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IO Exception");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Unhandled SQL Exception");
			e.printStackTrace();
		}
		
	
	}

	/**
	    * This method imports the course_history.csv into the RAP database
	    */
	
	public void historyImport() {
		//Set relative paths for DB and CSV
		//ds.setUrl("jdbc:sqlite:test.db");
		csvPath = "Course_History.csv";
		
		//These Vars are for all the columns in the Core Courses Table
		String lastName;
		String firstName;
		String middleName;
		String studentID;
		int grade;
		String programOfStudy;
		String history;
		
		//Set up Line Reader for CSV
		try {
			BufferedReader lineReader = new BufferedReader(new FileReader(csvPath));
			//This skips the header line
			String line = lineReader.readLine();
			Statement smt = conn.createStatement();	
			
			// This while loop goes through the whole CSV Iteratively
			while ((line=lineReader.readLine())!=null) {
				if(!line.contains(",,,")) {
					String currentLine[] = line.split(",");
					firstName = currentLine[0];
					lastName = currentLine[1];
					middleName = currentLine[2];
					studentID = currentLine[3];
					grade = Integer.valueOf(currentLine[4]);
					programOfStudy = currentLine[5];
					history = currentLine[6];
					
					String sql =	"INSERT INTO HISTORY (FirstName, LastName, MiddleName, StudentID, Grade, programOfStudy, hist) VALUES"
							+ "(\'" + firstName + "\'," + "\'" + lastName + "\'," + "\'" + middleName + "\'," + "\'" + studentID + "\'," + 
							"\'" + grade + "\'," + "\'" + programOfStudy + "\'," + "\'" + history + "\')";
					
					smt.addBatch(sql);
					
				}
			}
						
			smt.executeBatch();
			
			System.out.println("Finished Importing History CSV");
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

	/**
	    * This method imports the courses.csv into the RAP database
	    */
	
	public void courseImport() {
		//Set relative paths for DB and CSV
		//ds.setUrl("jdbc:sqlite:test.db");
		csvPath = "Courses.csv";
		
		//These Vars are for all the columns in the Core Courses Table
		String Name;
		String CourseID;
		float NumberOfCredits;
		int CourseLevel;
		int NumberOfStudents;
		String CoursePreRequisite;
		String CoursePreRequisiteTwo;
		String CourseCoRequisite;
		String ProgramOfStudy;
		String ProgramOfStudyTwo;
		String ProgramOfStudyThree;
		String ProgramOfStudyFour;
		
		//Set up Line Reader for CSV
		try {
			BufferedReader lineReader = new BufferedReader(new FileReader(csvPath));
			//This skips the header line
			String line = lineReader.readLine();
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
					CoursePreRequisiteTwo = currentLine[6];
					CourseCoRequisite = currentLine[7];
					ProgramOfStudy = currentLine[8];
					ProgramOfStudyTwo = currentLine[9];
					ProgramOfStudyThree = currentLine[10];
					ProgramOfStudyFour = currentLine[11];
					
					String sql =	"INSERT INTO COURSE (Name, CourseID, NumberOfCredits, CourseLevel, NumberOfStudents, CoursePreRequisite, CoursePreRequisiteTwo, CourseCoRequisite, "
							+ "ProgramOfStudy, ProgramOfStudyTwo, ProgramOfStudyThree, ProgramOfStudyFour) VALUES"
							+ "(\'" + Name + "\'," + "\'" + CourseID + "\'," + "\'" + NumberOfCredits + "\'," + "\'" + CourseLevel + "\'," + "\'" + NumberOfStudents 
							+ "\'," + "\'" + CoursePreRequisite + "\'," + "\'" + CoursePreRequisiteTwo + "\'," + "\'" + CourseCoRequisite + "\',"
							+ "\'" + ProgramOfStudy + "\'," + "\'" + ProgramOfStudyTwo + "\'," + "\'" + ProgramOfStudyThree + "\'," + "\'" + ProgramOfStudyFour + "\')";
					
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

}
