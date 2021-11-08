package MenuFunctions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import project.RAPMenu;

import org.sqlite.SQLiteDataSource;

public class ProgramOfStudy {

	Connection conn;
	Statement smt; 
	
	public ProgramOfStudy(RAPMenu rp1) {
		SQLiteDataSource ds = new SQLiteDataSource();
		ds.setUrl("jdbc:sqlite:test.db");
		rp1.getMainLabel().setText("Success! Program of Study works!");
		
		try 
		{ 
			conn = ds.getConnection();
			smt = conn.createStatement();
			String sql = "SELECT ProgramOfStudy FROM STUDENTS WHERE StudentID = \'" + rp1.getStudentID() + "\';";
		
			ResultSet rs = smt.executeQuery(sql);
			
			String prog = rs.getString("PROGRAMOFSTUDY");
			
			//return prog;
			  
		} 
		catch (SQLException e) {
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
	}

}
