package MenuFunctions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import project.RAPMenu;

import org.sqlite.SQLiteDataSource;

public class ProgramOfStudy {

	public ProgramOfStudy(RAPMenu rp1) {
		SQLiteDataSource ds;
		ds = new SQLiteDataSource();
		ds.setUrl("jdbc:sqlite:test.db");
		rp1.getMainLabel().setText("Success! Program of Study works!");
		
		try 
		{ 
			Connection conn = ds.getConnection();
			Statement smt = conn.createStatement();
			String sql = "SELECT ProgramOfStudy FROM STUDENTS WHERE StudentID = \'" + rp1.getStudentID() + "\';";
		
			ResultSet rs = smt.executeQuery(sql);
			
			String prog = rs.getString("PROGRAMOFSTUDY");
			
			//return prog;
			  
		} 
		catch (SQLException e) {
		System.out.println("Unhandled SQL Exception");
		e.printStackTrace();
		}
			
		//return null;
	}

}
