package MenuFunctions;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
			
			JPanel panel = new JPanel(new GridLayout(1,1));
			rp1.getF().repaint();
			rp1.getMainLabel().setText("");
			panel.setBorder(BorderFactory.createTitledBorder("Student " + rp1.getStudentID() + "'s Program of Study:"));
			JLabel label=new JLabel(prog);
			panel.add(label, BorderLayout.SOUTH);
			rp1.getF().add(panel);
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
