package MenuFunctions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteDataSource;

public class CurrentCourses {

	public static String[] CurrentCourses(String id) {
		SQLiteDataSource ds;
		ds = new SQLiteDataSource();
		ds.setUrl("jdbc:sqlite:test.db");
		
		try 
		{ 
			Connection conn = ds.getConnection();
			Statement smt = conn.createStatement();
			String sql = "SELECT CourseIDs FROM HISTORY WHERE StudentID = \'" + id + "\';";
		
			ResultSet rs; 
			ResultSet rs2;
		
			rs = smt.executeQuery(sql);
			String[] classes = rs.getString("CourseIDs").split(",");
			String[] hist = new String[8];
			for(int i = 0; i < classes.length; i++) {
				sql = "SELECT Name FROM COURSE WHERE CourseID LIKE \"" + classes[i] + "\"";
				rs2 = smt.executeQuery(sql);
				hist[i] = rs2.getString("Name");
			}
		
			System.out.println("Student " + id + "'s Schedule:\n--------------------------------------");
			for(int i = 0; i < hist.length; i++)
				System.out.println(hist[i]);
		
			return hist;    
		} 
		catch (SQLException e) {
		System.out.println("Unhandled SQL Exception");
		e.printStackTrace();
		}
			
		return null;
	}
	
	//Print Method used for testing, keeping it in case it is useful for further development
	public static void printTable() {
		SQLiteDataSource ds;
		ds = new SQLiteDataSource();
		ds.setUrl("jdbc:sqlite:test.db");
		try { Connection conn = ds.getConnection();
		Statement smt = conn.createStatement();
		
		/**
		 * This was testing code, do not bother
		 * 
		 */
		ResultSet rs3;
		
		String[] temp = new String[100];
		String[] t2 = new String[100];
		int count = 0;
		
		rs3 = smt.executeQuery("Select * From COURSE");
	
		while (rs3.next()) {
			
		temp[count]  = rs3.getString("Name");
		t2[count]    = rs3.getString("CourseID");
		count++;
		}
		
		for(int i = 0; i < 100; i++)
			System.out.println("Course: " + temp[i] + "\t\t\t\t\t\t\t" + t2[i]);
		
	} catch (SQLException e) {
		System.out.println("Unhandled SQL Exception");
		e.printStackTrace();
	}}

	
	
	
	

}
