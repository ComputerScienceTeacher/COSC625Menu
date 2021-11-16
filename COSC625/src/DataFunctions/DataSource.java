package DataFunctions;

/**
 * This class sets up the database file and imports data for Students and courses from csv files stored in the location as the project
 * 
 * 
 * @author Chad Whiteley, Jacob Facemire 
 * @Version 0.9
 * 
 * 
 * 
 * 
 * 
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteDataSource;

public class DataSource {
	public SQLiteDataSource ds = new SQLiteDataSource();
	Connection conn;
	Statement smt;

        /**
        * This is the constructor for Datasource
        */
	
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
	    
		try {
			conn = ds.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.exit( 0 );
		}
 
	
	    System.out.println( "Created database successfully" );
	}

        /**
        * This is method closes the connection with ds
        */
	
	public void closeConnection() {
		try {
			conn.close();
			System.out.println("Database connection closed.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit( 0 );
		}
	}

       /**
        * This function executes a query into the RAP database
        */
	
	public void newQuery(String query) {
		try ( Statement stmt = conn.createStatement(); ) {
	            int rv = stmt.executeUpdate( query );
	            System.out.println( "executeUpdate() returned " + rv );
	        
		} catch ( SQLException e ) {
	            e.printStackTrace();
	            System.exit( 0 );
	    }
	}


        
        /**
        * This method adds a courses table into the RAP database
        */
	
	public void addCourseTable()
	{
           
		try { Statement smt = conn.createStatement();
		
		smt.executeUpdate("DROP TABLE COURSE");
		
		String sql = "CREATE TABLE  IF NOT EXISTS COURSE (Name VARCHAR(255), CourseID VARCHAR(255), NumberOfCredits FLOAT, "
				   + "CourseLevel INTEGER, NumberOfStudents INTEGER, CoursePreRequisite VARCHAR(255), CoursePreRequisiteTwo VARCHAR(255), CourseCoRequisite VARCHAR(255), "
				   + "ProgramOfStudy VARCHAR(255), ProgramOfStudyTwo VARCHAR(255), ProgramOfStudyThree VARCHAR(255), ProgramOfStudyFour VARCHAR(255))";
		
		smt.executeUpdate(sql);
		System.out.println("Course Table Created");
		
		} catch (SQLException e) {
			System.out.println("Unhandled SQL Exception");
			e.printStackTrace();
		}
	}


        /**
        * This method adds a student table into the RAP database
        */
	
	public void addStudentTable()
	{
		ds.setUrl("jdbc:sqlite:test.db");
		newQuery("DROP TABLE STUDENTS");
		try { Statement smt = conn.createStatement();
		String sql = "CREATE TABLE IF NOT EXISTS STUDENTS (LastName VARCHAR(255), FirstName VARCHAR(255),"+ 
					"MiddleName VARCHAR(255), StudentID VARCHAR(255), INTERNALID VARCHAR(255), GRADE INTEGER," +
					"PHONENUM VARCHAR(255), GENDER VARCHAR(255), GRADYEAR INTEGER, ADDRESS VARCHAR(255), SECONDPHONE VARCHAR(255)," +
					"BIRTHDATE VARCHAR(255),  THIRDPHONE VARCHAR(255), EMAILADDRESS VARCHAR(255), DUALCREDIT VARCHAR(255)," +
					"PROGRAMOFSTUDY VARCHAR(255), HISTORY VARCHAR(255))";

		
		smt.executeUpdate(sql);
		System.out.println("Student Table Created");
		
		} catch (SQLException e) {
			System.out.println("Unhandled SQL Exception");
			e.printStackTrace();
		}
	}

        /**
        * This method adds a history table into the RAP database
        */

	public void addHistoryTable()
	{
		ds.setUrl("jdbc:sqlite:test.db");
		newQuery("DROP TABLE HISTORY");
		try { Statement smt = conn.createStatement();
		String sql = "CREATE TABLE IF NOT EXISTS HISTORY (LastName VARCHAR(255), FirstName VARCHAR(255),"+ 
					"MiddleName VARCHAR(255), StudentID VARCHAR(255), GRADE INTEGER, PROGRAMOFSTUDY VARCHAR(255), " +
					"HIST VARCHAR(1255))";

		
		smt.executeUpdate(sql);
		System.out.println("History Table Created");
		
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
		ds.setUrl("jdbc:sqlite:test.db");
		String csvPath = "Courses.csv";
		
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

        /**
        * This method imports the course_history.csv into the RAP database
        */
	
	public void historyImport() {
		//Set relative paths for DB and CSV
		ds.setUrl("jdbc:sqlite:test.db");
		String csvPath = "Course_History.csv";
		
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
        * This method imports the students.csv into the RAP database
        */
	
	
	public void studentImport() {
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
		String History;
		
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
									"PROGRAMOFSTUDY, HISTORY) VALUES"
							+ "(\'" + LastName + "\'," + "\'" + FirstName + "\'," + "\'" + MiddleName + "\'," + "\'" + StudentID + "\'," + "\'" + InternalID 
							+ "\'," + "\'" + Grade + "\'," + "\'" + PhoneNum + "\'," + "\'" + Gender + "\'," + "\'" + GradYear +"\'," + "\'" + Address  
							+ "\'," + "\'" + SecondPhone + "\'," + "\'" + BirthDate + "\'," + "\'" + ThirdPhone + "\'," + "\'" + EmailAddress   
							+ "\'," + "\'"+ DualCredit + "\',"  + "\'" + ProgramOfStudy +"\',"  + "\'" + History + "\')";
									
					
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

        

	public void StudentHistoryGen() {
		{
			/*
			 * This block of code makes a new table if it has yet to be created
			 * and imports student IDs and Grades through Student Table
			 * 
			 * All of This is outdated and shouyld be removed once Schedule Assignment is possible
			 * 
			 * 
			 * 
			 * 
			 */
			SQLiteDataSource ds;
			ds = new SQLiteDataSource();
			ds.setUrl("jdbc:sqlite:test.db");
			try { Statement smt = conn.createStatement();
			String sql2 = "DROP TABLE HISTORY";
			
			smt.execute(sql2);
			
			String sql = "CREATE TABLE  IF NOT EXISTS SCHEDULE (StudentID VARCHAR(255), Grade INTEGER, CourseIDs VARCHAR(255))";
			
			smt.addBatch(sql);
			System.out.println("Course Table Created");

			
			/**
			 * Because our test data does not have course history, we are going to randomly generate it
			 * 
			 */			
			
			//Set relative paths for DB and CSV
			ds.setUrl("jdbc:sqlite:test.db");
			String csvPath = "Students.csv";
			
			//These Vars are for all the columns in the Students Table
			String StudentID;
			int Grade;
		
			@SuppressWarnings("resource")
			BufferedReader lineReader = new BufferedReader(new FileReader(csvPath));
			//This skips the header line
			String line = lineReader.readLine();

			String temp = null;
				// This while loop goes through the whole CSV Iteratively
				while ((line=lineReader.readLine())!=null) {
					if(!line.contains(",,,")) {
						String currentLine[] = line.split(",");
					
						StudentID = currentLine[3];
						Grade = Integer.valueOf(currentLine[5]);
						
						if(Grade == 9) 
							temp = "3220100,3100500,3010200,3320100";
						if (Grade == 10) 
							temp = "3220200,3100700,3040000,3340400";
						if (Grade == 11) {
							int r = (int)(Math.random() * 2 + 1);
							if(r == 1)
								temp = "A3220100,3100600,3050000,3340100";
							else 
								temp = "3220300,3100600,3050000,3340100";
						}
						if (Grade == 12) {
							int r = (int)(Math.random() * 3 + 1);	
							System.out.println("r is : " + r);
							if(r == 1)
								temp = "A3220200,3101100,13002100,3340100";
							if(r == 2)
								temp = "A3220100,A3100101,13000700,3310300";
							if(r == 3)
								temp = "3220400,A3100200,13020600,A3330100";		
						}
						
						sql = "INSERT INTO SCHEDULE (StudentID, GRADE, CourseIDs) VALUES (\'" + StudentID + "\', \'" + Grade +  "\',\'" + temp + "\') ";
						System.out.println(sql);
						smt.addBatch(sql);
					}
				}
				smt.executeBatch();
				System.out.println("SQL Executed Properly");
						
			} catch (SQLException | NumberFormatException | IOException e) {
				System.out.println("Unhandled SQL Exception");
				e.printStackTrace();
			}
		}
	}

}

