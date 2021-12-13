package MenuFunctions;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.sqlite.SQLiteDataSource;

import project.RAPMenu;

public class GPACalc {
	Connection conn;
	Statement smt;
	SQLiteDataSource ds;
	ResultSet rs;
	
	public GPACalc(RAPMenu rp1) {
		ds = new SQLiteDataSource();
		ds.setUrl("jdbc:sqlite:test.db");
		
		
		try 
		{   conn = ds.getConnection();
			smt = conn.createStatement();
			
			String sql = "SELECT HIST, GPA FROM HISTORY WHERE StudentID = \'" + rp1.getStudentID() + "\';";
		
			rs = smt.executeQuery(sql);
			System.out.println(rs.getString("HIST"));
			System.out.println(rs.getString("GPA"));
			String[] classes = rs.getString("HIST").split(";");
			String[] gradePoints = rs.getString("GPA").split(";");
			
			rp1.getMainLabel().setText("");
			double GPASum = 0;
			JPanel panel = rp1.getMainPanel();
			panel.removeAll();
			panel.setLayout(new GridLayout(13,2));
			panel.setBorder(BorderFactory.createTitledBorder("Student " + rp1.getStudentID() + "'s GPA:"));
			


                        //This block of code iterates through the "gradePoints" array
                        //and performs a running calculation on its indices to arrive
                        //at the correct GPA
                        for(int i = 0; i<gradePoints.length; i++) {	
				double GPA = Double.valueOf(gradePoints[i]);
				JTextArea label = new JTextArea(String.format("%-25.25s\t%5.2f", classes[i], GPA));
				label.setLineWrap( true );
				label.setWrapStyleWord(true);
				panel.add(label, BorderLayout.SOUTH);
				GPASum += GPA;
				//System.out.println(classes[i]);
			}
			double totalGPA = GPASum/gradePoints.length;
			JLabel GPALabel = new JLabel(String.format("Your cummulative GPA is: %3.2f", totalGPA));
			GPALabel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 5));
			panel.add(GPALabel);
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
