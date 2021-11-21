package DataFunctions;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteDataSource;

public class AddTables {
	public SQLiteDataSource ds = new SQLiteDataSource();
	Connection conn;
	Statement smt;
	
	
	public AddTables() {
		ds = null;
				
	    try {
	        ds = new SQLiteDataSource();
	        ds.setUrl("jdbc:sqlite:test.db");
			conn = ds.getConnection();
		    addCourseTable();
		    addHistoryTable();
		    addProgramTable();
		    addStudentTable();
		    conn.close();
	    } catch ( Exception e ) {
	        e.printStackTrace();
	        System.exit(0);
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
	* This method adds a teacher table into the RAP database
	*/
	public void addTeacherTable()
	{
		ds.setUrl("jdbc:sqlite:test.db");
		try { Statement smt = conn.createStatement();
		String sql = "CREATE TABLE IF NOT EXISTS TEACHERS (Name VARCHAR(255), Phone VARCHAR(255),"+ 
					"Email VARCHAR(255), Address VARCHAR(255), Zip VARCHAR(255), ID VARCHAR(255))";
	
		
		smt.executeUpdate(sql);
		System.out.println("Teacher Table Created");
		
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
		try { Statement smt = conn.createStatement();
		String sql = "CREATE TABLE IF NOT EXISTS STUDENTS (LastName VARCHAR(255), FirstName VARCHAR(255),"+ 
					"MiddleName VARCHAR(255), StudentID VARCHAR(255), INTERNALID VARCHAR(255), GRADE INTEGER," +
					"PHONENUM VARCHAR(255), GENDER VARCHAR(255), GRADYEAR INTEGER, ADDRESS VARCHAR(255), SECONDPHONE VARCHAR(255)," +
					"BIRTHDATE VARCHAR(255),  THIRDPHONE VARCHAR(255), EMAILADDRESS VARCHAR(255), DUALCREDIT VARCHAR(255)," +
					"PROGRAMOFSTUDY VARCHAR(255), HISTORY VARCHAR(255), SCHEDULE VARCHAR(255))";
	
		
		smt.executeUpdate(sql);
		System.out.println("Student Table Created");
		
		} catch (SQLException e) {
			System.out.println("Unhandled SQL Exception");
			e.printStackTrace();
		}
	}

	/**
	 * This Method builds the SQL table used to store info regarding programs of study and their requirements list, The following imports a csv into the table
	 * 
	 * @author Jacob Facemire
	 */
	public void addProgramTable()
	{
		ds.setUrl("jdbc:sqlite:test.db");
		try { Statement smt = conn.createStatement();
		smt.executeUpdate("Drop Table Program");
		String sql = "CREATE TABLE IF NOT EXISTS PROGRAM (Name VARCHAR(255), Requirements VARCHAR(255))";
		
		smt.executeUpdate(sql);
		System.out.println("Program Table Created");
		
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

}
