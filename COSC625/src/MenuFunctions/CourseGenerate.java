package MenuFunctions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteDataSource;

import project.RAPMenu;

/**
 * This class contains the algorithm to automatically generate a plausible student schedule 
 * to a class depending on their ID and assigns the passed student to those classes. 
 * 
 * Then, it updates the courses to show how many students are in that course
 * 
 * @author Jacob Facemire, 11/12/2021, Ver 1.0.0
 *
 */
@SuppressWarnings("unused")
public class CourseGenerate {
	static SQLiteDataSource ds;
	static Connection conn;
	static Statement smt;
	static String[] courses;
	static Statement smt2;
	
	public static void CourseGenerate() {
		
		// Pull all relevant info for Student
		
		courses = new String[8];
		int count = 0;
		ds = new SQLiteDataSource();
		ds.setUrl("jdbc:sqlite:test.db");
				
		try {
			conn = ds.getConnection();
			smt = conn.createStatement();
			smt2 = conn.createStatement();
			

			String sql2 = "SELECT FirstName,StudentID FROM STUDENTS";
			ResultSet rs4 = smt2.executeQuery(sql2);
			ResultSet rs;
			ResultSet rs2;
			ResultSet rs3;

			String[] prog = new String[5];
			prog[0] = "English";
			prog[1] = "Math";
			prog[2] = "Science";
			prog[3] = "History";
			
			boolean req1;
			boolean req2;
			String requ1;
			String requ2;
			String courTemp;;
			String id;
			String sql;
			
			int grade;
			int newCount;

			
			
			while (rs4.next()) {
				
				req1 = false;
				req2 = false;
				requ1 = "";
				requ2 = "";
				courTemp = "";
				id = rs4.getString("StudentID");
				sql = "SELECT PROGRAMOFSTUDY, Grade, HISTORY FROM STUDENTS WHERE StudentID = \'" + id + "\';";
				rs = smt.executeQuery(sql);
				prog[4] = rs.getString("ProgramOfStudy");
				String[] hist = rs.getString("History").split(";");
				grade = rs.getInt("Grade");
				count = 0;
				//			//Print all pulled information for Debugging/Verification
				//			System.out.println(prog);
//				System.out.println("Grade: " + grade);
				//			for(int i = 0; i < hist.length; i++)
				//			{
				//				System.out.println(hist[i]);
				//			}
				for (int i = 0; i < prog.length; i++) {
					sql2 = "Select * From COURSE WHERE ProgramOfStudy = \'" + prog[i] + "\' AND CourseLevel = \'"
							+ grade + "\'";
					//				System.out.println(sql2);
					//				System.out.println("\nSearching for " + prog[i] + " Classes");
					rs2 = smt.executeQuery(sql2);
					//				System.out.println("list of courses for: " + prog[i]);
					boolean trigger = false;
					while (rs2.next() && trigger == false) {
//						System.out.println("Current Course Name: " + rs2.getString("Name"));
//						System.out.println("Course PreReq's: " + rs.getString("CoursePreRequisite") + ", "
//								+ rs2.getString("CoursePreRequisiteTwo"));
						requ1 = rs2.getString("CoursePreRequisite");
//						System.out.println("requ1 = " + requ1);
						requ2 = rs2.getString("CoursePreRequisiteTwo");
//						System.out.println("requ2 = " + requ2);
						if (requ1.equals("none")) {
							req1 = true;
						} else {
							for (int j = 0; j < hist.length; j++) {

								if (hist[j].equals(requ1)) {
//									System.out.println("Comparing: " + hist[j] + " " + requ1);
									req1 = true;
								}
							}
						}

						if (requ2.equals("none")) {
							req2 = true;
						} else {
							for (int j = 0; j < hist.length; j++) {
								if (hist[j].equals(requ2))
									req2 = true;
							}
						}

						if (req1 == true && req2 == true) {
							courses[count] = rs2.getString("Name");
							count++;
							trigger = true;
						}

//						System.out.println("req1 = " + req1 + " req2 = " + req2);
						req1 = false;
						req2 = false;

					}
//					System.out.println("-------------------------------------");

				}
//				System.out.println("Suggested Schedule for Student:");
				for (int i = 0; i < 8; i++) {
//					System.out.println(courses[i]);
					if (i == 7) {
						courTemp = courTemp + courses[i];
					} else {
						courTemp = courTemp + courses[i] + ";";
					}
				}
				sql = "UPDATE STUDENTS SET SCHEDULE =  (\'" + courTemp + "\') WHERE STUDENTID = \'" + id + "\'";
				for (int i = 0; i < count; i++) {
					rs = smt.executeQuery("SELECT NUMBEROFSTUDENTS FROM COURSE WHERE NAME = \'" + courses[i] + "\'");
					newCount = rs.getInt(1) + 1;
					//					System.out.println("New count: " + newCount);

					smt.executeUpdate("UPDATE COURSE SET NUMBEROFSTUDENTS = (" + newCount + ") WHERE NAME = \'"
							+ courses[i] + "\'");
					//					System.out.println("UPDATE COURSE SET NUMBEROFSTUDENTS = (" + newCount + ") WHERE NAME = \'" + courses[i] + "\'");
				}
				//			
				//			System.out.println(sql);
				smt.executeUpdate(sql);
//				System.out.println("\n\n" + rs4.isClosed() + "\n\n");
			}
		conn.close();
		System.out.print("Schedules Generated");
		
		}
		catch (SQLException e) 
		{
			System.out.println("Unhandled SQL Exception");
			e.printStackTrace();
		}

		
		
	}
}
