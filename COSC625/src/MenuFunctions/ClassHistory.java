package MenuFunctions;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import project.RAPMenu;
import org.sqlite.SQLiteDataSource;

public class ClassHistory {
	
	Connection conn;
	Statement smt;
	SQLiteDataSource ds;
	ResultSet rs;

	/**
	 * This method calls the database to call the class history for 
	 * the student.
	 * 
	 * @param rp1 - The RAPMenu passed as an object
	 */
	public ClassHistory(RAPMenu rp1) {
		ds = new SQLiteDataSource();
		ds.setUrl("jdbc:sqlite:test.db");
		
		
		try 
		{   conn = ds.getConnection();
			smt = conn.createStatement();
			String sql = "SELECT HISTORY FROM STUDENTS WHERE StudentID = \'" + rp1.getStudentID() + "\';";
		
			rs = smt.executeQuery(sql);
			
			String[] classes = rs.getString("HISTORY").split(";");
			//System.out.println("Student " + rp1.getStudentID() + "'s Schedule:\n--------------------------------------");
			
			
			rp1.getMainLabel().setText("");
			JPanel panel = rp1.getMainPanel();
			panel.removeAll();
			panel.setLayout(new GridLayout(8,1));
			panel.setBorder(BorderFactory.createTitledBorder("Student " + rp1.getStudentID() + "'s History:"));
                        
                        //This block of code iterates through the "classes" array, 
                        //and displays the contents of its indices in the window

			for(int i = 0; i<classes.length; i++) {				
				JTextArea label = new JTextArea(classes[i]);
				label.setLineWrap( true );
				label.setWrapStyleWord(true);
				panel.add(label, BorderLayout.SOUTH);
				//System.out.println(classes[i]);
			}
			rp1.getF().repaint();
			rp1.getF().revalidate();
			conn.close();
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

	
	
	
	


