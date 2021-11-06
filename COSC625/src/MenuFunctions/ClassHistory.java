package MenuFunctions;

import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import project.RAPMenu;
import org.sqlite.SQLiteDataSource;

public class ClassHistory {

	public ClassHistory(RAPMenu rp1) {
		rp1.getMainLabel().setText("Success! Class History works!");
		SQLiteDataSource ds;
		ds = new SQLiteDataSource();
		ds.setUrl("jdbc:sqlite:test.db");
		
		try 
		{ 
			Connection conn = ds.getConnection();
			Statement smt = conn.createStatement();
			String sql = "SELECT HISTORY FROM STUDENTS WHERE StudentID = \'" + rp1.getStudentID() + "\';";
		
			ResultSet rs; 
		
			rs = smt.executeQuery(sql);
			String[] classes = rs.getString("HISTORY").split(";");			
			System.out.println("Student " + rp1.getStudentID() + "'s Schedule:\n--------------------------------------");
			
			JPanel panel = new JPanel(new GridLayout(8,1));
			for(int i = 0; i<classes.length; i++) {
				JTextArea label = new JTextArea(classes[i]);
				panel.add(label);
				System.out.println(classes[i]);
			}
			rp1.getF().add(panel);
		} 
		catch (SQLException e) {
		System.out.println("Unhandled SQL Exception");
		e.printStackTrace();
		}
			

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
		}
	}
}

	
	
	
	


