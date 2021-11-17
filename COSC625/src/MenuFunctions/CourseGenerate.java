package MenuFunctions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteDataSource;

import project.RAPMenu;

/**
 * This class contains the algorithm to automatically generate a plausible student schedule 
 * to a class depending on their ID
 * 
 * TODO:		Build new Table of Program of Study req's to make pulling more simple
 * 				Write
 * 
 * 
 * FINISHED:	
 * 
 * 
 * 
 * @author Jacob Facemire, 11/10/2021, Ver 0.0.1
 *
 */
public class CourseGenerate {

	public static String[] CourseGenerate(String rp1) {
		
		// Pull all relevant info for Student
		
		SQLiteDataSource ds = new SQLiteDataSource();
		Connection conn = null ;
		Statement smt;
		String[] courses = new String[8];
		int count = 0;
		
		
		ds.setUrl("jdbc:sqlite:test.db");
		
		
		try {
			conn = ds.getConnection();
			smt = conn.createStatement();
			//String sql = "Select (ProgramOfStudy, History) FROM STUDENTS WHERE StudentID = \'" + rp1.getStudentID() + "\';";
			String sql = "SELECT PROGRAMOFSTUDY, Grade, HISTORY FROM STUDENTS WHERE StudentID = \'" + rp1 + "\';";
			String sql2;
//			System.out.println(sql);
			ResultSet rs = smt.executeQuery(sql);	
			boolean req1 = false;
			boolean req2 = false;
			String requ1;
			String requ2;
			String courTemp = "";
			
			String[] prog = new String[5];
			prog[0] = "English";
			prog[1] = "Math";
			prog[2] = "Science";
			prog[3] = "History";
			prog[4] = rs.getString("ProgramOfStudy");
			String[] hist = rs.getString("History").split(";");
			int grade = rs.getInt("Grade");
			
//			//Print all pulled information for Debugging/Verification
//			System.out.println(prog);
			System.out.println("Grade: " + grade);
//			for(int i = 0; i < hist.length; i++)
//			{
//				System.out.println(hist[i]);
//			}
			
			for(int i = 0; i< prog.length; i++)
			{
				sql2 = "Select * From COURSE WHERE ProgramOfStudy = \'" + prog[i] + "\' AND CourseLevel = \'" + grade + "\'";
//				System.out.println(sql2);
//				System.out.println("\nSearching for " + prog[i] + " Classes");
				ResultSet rs2 = smt.executeQuery(sql2);
//				System.out.println("list of courses for: " + prog[i]);
				while(rs2.next()) 
				{
//					System.out.println("Current Course Name: " + rs2.getString("Name"));
//					System.out.println("Course PreReq's: " + rs.getString("CoursePreRequisite") + ", " + rs2.getString("CoursePreRequisiteTwo"));
					requ1 = rs2.getString("CoursePreRequisite");
//					System.out.println("requ1 = " + requ1);
					requ2 = rs2.getString("CoursePreRequisiteTwo");
//					System.out.println("requ2 = " + requ2);
					if(requ1.equals("none")) 
					{
						req1 = true;
					}
					else 
					{
						for(int j = 0; j < hist.length; j++)
						{
							
							if(hist[j].equals(requ1)) {
//								System.out.println("Comparing: " + hist[j] + " " + requ1);
								req1 = true;}
						}
					}
					
					if(requ2.equals("none")) 
					{
						req2 = true;
					}
					else 
					{
						for(int j = 0; j < hist.length; j++)
						{
							if(hist[j].equals(requ2))
								req2 = true;
						}	
					}
					
					if(req1 == true && req2 == true) 
					{
						courses[count] = rs2.getString("Name");
						count++;
					}	
						
//					System.out.println("req1 = " + req1 + " req2 = " + req2);
					req1 = false;
					req2 = false;
					
				}
//				System.out.println("-------------------------------------");
			
			}
			
			System.out.println("Suggested Schedule for Student:");
			

			for(int i = 0; i < 8; i++) 
			{
				System.out.println(courses[i]);
				if(i == 7) 
				{
					courTemp =  courTemp + courses[i];
				}
				else 
				{
					courTemp = courTemp + courses[i] + ";";	
				}
			}
			
			sql = "UPDATE STUDENTS SET SCHEDULE =  (\'" + courTemp + "\') WHERE STUDENTID = \'" + rp1 + "\'";
			
			System.out.println(sql);
			smt.executeUpdate(sql);
			
			} catch (SQLException e) 
				{
					System.out.println("Unhandled SQL Exception");
					e.printStackTrace();
				}
		
	try {
		conn.close();
		System.out.println("Database connection closed.");
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		System.exit( 0 );
	}
		
		return courses;
	}
}
