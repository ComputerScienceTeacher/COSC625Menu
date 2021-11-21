package MenuFunctions;

import project.RAPMenu;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.sqlite.SQLiteDataSource;


public class CurrentCourses {
	Connection conn;
	Statement smt;
	SQLiteDataSource ds;
	ResultSet rs;
	String [] prog;
	
	public CurrentCourses(RAPMenu rp1) { 

		ds = new SQLiteDataSource();
		ds.setUrl("jdbc:sqlite:test.db");

		
		try 
		{ 
			conn = ds.getConnection();
			smt = conn.createStatement();
			String sql = "SELECT Schedule FROM STUDENTS WHERE StudentID = \'" + rp1.getStudentID() + "\';";
		
			rs = smt.executeQuery(sql);
			prog = rs.getString("Schedule").split(";");
			rp1.getMainLabel().setText("");
			JPanel panel = rp1.getMainPanel();
			panel.removeAll();
			panel.setLayout(new GridLayout(8,1));
			panel.setBorder(BorderFactory.createTitledBorder("Student " + rp1.getStudentID() + "'s Current Schedule"));
			for(int i = 0; i < prog.length; i++) {
				JTextArea label = new JTextArea(prog[i]);
				label.setLineWrap( true );
				label.setWrapStyleWord(true);
				panel.add(label, BorderLayout.SOUTH);
				//System.out.println(prog[i]);
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


}
