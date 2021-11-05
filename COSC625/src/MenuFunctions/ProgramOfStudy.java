package MenuFunctions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteDataSource;

public class ProgramOfStudy {

	public String ProgramOfStudy(String id) {
		SQLiteDataSource ds;
		ds = new SQLiteDataSource();
		ds.setUrl("jdbc:sqlite:test.db");
		
		try 
		{ 
			Connection conn = ds.getConnection();
			Statement smt = conn.createStatement();
			String sql = "SELECT ProgramOfStudy FROM STUDENTS WHERE StudentID = \'" + id + "\';";
		
			ResultSet rs = smt.executeQuery(sql);
			
			String prog = rs.getString("PROGRAMOFSTUDY");
			
			return prog;
			  
		} 
		catch (SQLException e) {
		System.out.println("Unhandled SQL Exception");
		e.printStackTrace();
		}
			
		return null;
	}

}
