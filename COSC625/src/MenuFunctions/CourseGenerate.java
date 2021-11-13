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
		Connection conn;
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
			System.out.println(sql);
			ResultSet rs = smt.executeQuery(sql);	
			boolean req1 = false;
			boolean req2 = false;
			String requ1;
			String requ2;
			
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
			for(int i = 0; i < hist.length; i++)
			{
				System.out.println(hist[i]);
			}
			
			for(int i = 0; i< prog.length; i++)
			{
				sql2 = "Select * From COURSE WHERE ProgramOfStudy = \'" + prog[i] + "\' AND CourseLevel = \'" + grade + "\'";
//				System.out.println(sql2);
				System.out.println("\nSearching for " + prog[i] + " Classes");
				ResultSet rs2 = smt.executeQuery(sql2);
//				System.out.println("list of courses for: " + prog[i]);
				while(rs2.next()) 
				{
					System.out.println("Current Course Name: " + rs2.getString("Name"));
					System.out.println("Course PreReq's: " + rs.getString("CoursePreRequisite") + ", " + rs2.getString("CoursePreRequisiteTwo"));
					requ1 = rs2.getString("CoursePreRequisite");
					System.out.println("requ1 = " + requ1);
					requ2 = rs2.getString("CoursePreRequisiteTwo");
					System.out.println("requ2 = " + requ2);
					if(requ1.equals("none")) 
					{
						req1 = true;
					}
					else 
					{
						for(int j = 0; j < hist.length; j++)
						{
							
							if(hist[j].equals(requ1)) {
								System.out.println("Comparing: " + hist[j] + " " + requ1);
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
						
					System.out.println("req1 = " + req1 + " req2 = " + req2);
					req1 = false;
					req2 = false;
					
				}
				System.out.println("-------------------------------------");
			}
			
			for(int i = 0; i < courses.length; i++)
				System.out.println(courses[i]);
			
//			courses[0] = getEnglish(grade, hist);
//			courses[1] = getMath(grade, hist);
//			courses[2] = getScience(grade, hist);
//			courses[3] = getHistory(grade, hist);
//			
//			String[] temp = getProgramReq(grade, hist, prog);
			
			} catch (SQLException e) 
				{
					System.out.println("Unhandled SQL Exception");
					e.printStackTrace();
				}
		

		
		return courses;
	}
	
//	private static String[] getProgramReq(int grade, String[] hist, String prog) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public static String getEnglish(int grade, String[] hist) {
//		if(grade == 9) 
//			return "English I";
//		
//		if(grade == 10) 
//		{
//			/*
//			 * This loop sees which of the possible English courses have been taken
//			 * 
//			 * For a 10th grader, the only options are English I and Pre AP English I
//			 * 
//			 */
//			for(int i = 0; i < hist.length; i++) {
//				if(hist[i] == "English I")
//					return "English II";
//				if(hist[i] == "Pre-AP English I")
//					return "Pre-AP English II";
//			}
//			//If neither is there, the student needs to re-take Eng I
//			return "English I";
//		}
//		
//		if(grade == 11)
//		{
//			/*
//			 * This loop sees which of the possible English courses have been taken
//			 * 
//			 * For an 11th grader, the only options are English II and Pre AP English II
//			 * 
//			 */
//			for(int i = 0; i < hist.length; i++)
//			{
//				if(hist[i] == "English II")
//					return "English III";
//				if(hist[i] == "Pre-AP English II")
//					return "AP English III Language and Composition";
//			}	
//			//If Neither is there, the student needs to re-take Eng II
//			return "English II";
//		}
//		
//		if(grade == 12)
//		{
//			/*
//			 * This loop sees which of the possible English courses have been taken
//			 * 
//			 * For a 12th grader, the only options are English III and AP Eng III
//			 * 
//			 */
//			for(int i = 0; i < hist.length; i++) 
//			{
//				if(hist[i] == "AP English III Language and Composition")
//					return "AP English IV Literature and Composition";
//				if(hist[i] == "English III")
//					return "English IV";
//			}
//			//If Neither Junior Level Course is there, they need to re-take Eng III
//			return "English III";
//		}
//		
//		
//		return "";
//	}
//	/**
//	 * This method looks at a students history to determine appropriate math courses
//	 * 
//	 * General Math Path
//	 * 
//	 * Algebra I -> Geometry -> Algebra II -> Pre-Calculus -> 
//	 * 									   -> Algebra III/Dual Credit College Algebra
//	 * 									   -> College Preparatory Course Mathematics
//	 * 									   -> Univ. Texas On Ramps College Algebra
//	 * 									   -> Statistics and Business Making Decisions
//	 * 						 -> Mathematical Models and Applications		    
//	 * 			 -> Pre-AP Geometry -> Pre-AP Algebra II -> Pre-Calculus -> AP Calculus AB
//	 * 													 				 -> AP Statistics 
//	 * 
//	 * 
//	 * The algorithm sets student on an appropriate path, but defaults to the non-adv 
//	 * standard path. Setting a student on an advanced path must be done manually by a counselor,
//	 * if the student is not already on an advanced path.
//	 * 
//	 * 
//	 * 
//	 * @param grade		Students Grade
//	 * @param hist		Students Course History
//	 * @return			Selected Course
//	 */
//	public static String getMath(int grade, String[] hist) {
//		if(grade == 9) 
//			return "Algebra I";
//		
//		if(grade == 10) {
//			for(int i = 0; i< hist.length; i++)
//			{
//				if(hist[i] == "Geometry")
//					return("Algebra II");
//			}
//			return "Geometry";
//		}
//		if(grade == 11) {
//			for(int i = 0; i < hist.length; i++) 
//			{
//				if(hist[i] == "Pre-AP Geometry")
//					return "Pre-AP Algebra II";
//				if(hist[i] == "Geometry")
//					return "Algebra II";
//				if(hist[i] == "Algebra II")
//					return "Pre-Calculus";
//			}
//		}
//		
//		if(grade == 12) {
//			for(int i = 0; i < hist.length; i++) 
//			{
//				if(hist[i] == "Pre-AP Algebra II")
//					return "AP Statistics";
//				if(hist[i] == "Algebra II")
//					return "Pre-Calculus";
//				if(hist[i] == "Pre-Calculus")
//					return "Ap Calculus AB";
//			}
//		}
//		
//		return "";	
//	}
//	
//	/**
//	 * Biology -> Chemistry -> Physics -> Any AP or Elective
//	 * 
//	 * 
//	 * @param grade
//	 * @param hist
//	 * @return
//	 */
//	public static String getScience(int grade, String[] hist) {
//		int r = (int)(Math.random() * 3 + 1);
//		
//		if(grade == 9) 
//			return "Biology";
//		
//		if(grade == 10) {
//			for(int i = 0; i < hist.length; i++) 
//			{
//				if(hist[i] == "Pre-AP Biology" || hist[i] == "Biology")
//				{
//					for(int j = 0; j < hist.length; j++)
//					{
//						if(hist[j] == "Algebra I")
//							return "Chemistry";
//					}
//					
//					return "Integrated Physics and Chemistry (IPC)";
//				}
//			}
//		}
//		
//		if(grade == 11) {
//			for(int i = 0; i < hist.length; i++) {
//				if(hist[i] == "Chemistry")
//					return "Physics";
//			}
//		}
//		
//		/*
//		 * At this point, if the student has taken Pre-AP Bio, they can take any of the three
//		 * main AP Science courses, this algorithm chooses one at random to assign, can be overridden by a counselor
//		 * 
//		 */
//		if(grade == 12) {
//			for(int i = 0; i < hist.length; i++) {
//				if(hist[i] == "Pre-AP Biology")
//				{
//					if(r == 1)
//						return "AP Physics I";
//					if(r == 2)
//						return "AP Biology";
//					if(r == 3) 
//						return "AP Chemistry";
//				}
//			}
//		}
//			
//		return "";	
//	}
//	public static String getHistory(int grade, String[] hist) {
//		if(grade == 9) 
//			return "World Geography";		
//		
//		return "";	
//	}
	

}
