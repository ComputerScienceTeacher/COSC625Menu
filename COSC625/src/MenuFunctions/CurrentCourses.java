package MenuFunctions;

import project.RAPMenu;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.sqlite.SQLiteDataSource;


public class CurrentCourses {


	public static void CurrentCourses(String rp1) { 
		Connection conn = null;
		Statement smt;		
		SQLiteDataSource ds = new SQLiteDataSource();
		ds.setUrl("jdbc:sqlite:test.db");

		
		try 
		{ 
			conn = ds.getConnection();
			smt = conn.createStatement();
			String sql = "SELECT Schedule FROM STUDENTS WHERE StudentID = \'" + rp1 + "\';";
		
			ResultSet rs = smt.executeQuery(sql);
			
			String[] prog = rs.getString("Schedule").split(";");
			
			System.out.println(rp1 + "'s Current Schedule\n----------------");
			for(int i = 0; i < prog.length; i++)
				System.out.println(prog[i]);
			  
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
