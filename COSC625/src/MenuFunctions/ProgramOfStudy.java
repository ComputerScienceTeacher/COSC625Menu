package MenuFunctions;

import java.awt.BorderLayout;
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

    /**
     * This method implements the program of study menu option
     */
	public ProgramOfStudy(RAPMenu rp1) {
                //This block of code declares the SQLite database, iniatizes it's
                //url, and displays specified text in the window when the associated
                //menu option is selected
		SQLiteDataSource ds = new SQLiteDataSource();
		ds.setUrl("jdbc:sqlite:test.db");
		rp1.getMainLabel().setText("");
		
		try 
		{ 
                        //This block of code establishes a connection to the database and queries
                        //it for the student table and the "program of study" field associated
                        //with the given student id. The result is read into the string "prog"
                        //and displayed in the RAP GUI window
			conn = ds.getConnection();
			smt = conn.createStatement();
			String sql = "SELECT ProgramOfStudy FROM STUDENTS WHERE StudentID = \'" + rp1.getStudentID() + "\';";
		
			ResultSet rs = smt.executeQuery(sql);
			
			String prog = rs.getString("PROGRAMOFSTUDY");
			System.out.print(prog);
			rp1.getF().repaint();
			JPanel panel=rp1.getMainPanel();
			panel.removeAll();
			panel.setBorder(BorderFactory.createTitledBorder("Student " + rp1.getStudentID() + "'s Program of Study:"));
			JLabel label=new JLabel(prog);
			panel.add(label, BorderLayout.SOUTH);
			//return prog;
			conn.close();
			  
		} 
		catch (SQLException e) {
			System.out.println("Unhandled SQL Exception");
			e.printStackTrace();
		}
			
	}

}
