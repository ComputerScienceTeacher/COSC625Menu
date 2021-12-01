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

import org.sqlite.SQLiteDataSource;

import project.RAPMenu;

public class ViewRegisteredStudents {
	Connection conn;
	Statement smt;
	SQLiteDataSource ds;
	ResultSet rs;

	/**
	 * This method is the view teachers constructor.
	 * 
	 * @param rp1 - the menu that is used for the program
	 */
	public ViewRegisteredStudents(RAPMenu rp1) {
		ds = new SQLiteDataSource();
		ds.setUrl("jdbc:sqlite:test.db");
		try 
		{   conn = ds.getConnection();
			smt = conn.createStatement();
			String sql = "SELECT (LASTNAME || ' ' || FIRSTNAME) AS NAME FROM STUDENTS ORDER BY LASTNAME";
		
			rs = smt.executeQuery(sql);
			
			JPanel panel = rp1.getMainPanel();
			panel.removeAll();
			panel.setLayout(new GridLayout(50,1));
			panel.setBorder(BorderFactory.createTitledBorder("List of all Students:"));

			while(rs.next()) {		
				JTextArea label = new JTextArea(rs.getString(1));
				label.setLineWrap( true );
				label.setWrapStyleWord(true);
				panel.add(label, BorderLayout.SOUTH);
				rs.next();
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
