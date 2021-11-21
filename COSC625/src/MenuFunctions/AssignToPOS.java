package MenuFunctions;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import project.RAPMenu;
import org.sqlite.SQLiteDataSource;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * This Class allows a teacher to assign a student to a specific Program of Study 
 * 
 * 
 * Author Jacob Facemire, Chad Whiteley 11/19/2021 
 * 
 */
public class AssignToPOS implements ActionListener {
	Connection conn;
	Statement smt;
	SQLiteDataSource ds;
	ResultSet rs;
	JLabel label;
	String in;
	RAPMenu rp1;
	
	
	/**
	 * This method calls the database to call the class history for 
	 * the student.
	 * 
	 * @param rp1 - The RAPMenu passed as an object
	 */
	public AssignToPOS(RAPMenu rp1) {		
		ds = new SQLiteDataSource();
		ds.setUrl("jdbc:sqlite:test.db");
		this.rp1 = rp1;
		in = null;
		
		/*
		 * This First Section pulls a list of all Programs of study
		 */
		try 
		{   
			conn = ds.getConnection();
			smt = conn.createStatement();
			String sql = "SELECT Name FROM Program";
		
			rs = smt.executeQuery(sql);
				
			String[] prog = new String[10];
			int count = 0;
						
			while(rs.next()) 
			{
				prog[count] = rs.getString(1);
				count++;
			}
					
			label = rp1.getMainLabel();
			label.setText("");
			JComboBox<String> cmbMessageList = new JComboBox<String>(prog);
			JFrame menu = rp1.getF();
			JPanel panel = rp1.getMainPanel();
			panel.removeAll();
			panel.setLayout(new GridLayout(8,1));
			panel.setBorder(BorderFactory.createTitledBorder("Assign " + rp1.getStudentID() + " to Program of Study"));
			cmbMessageList.setSelectedIndex(5);
			cmbMessageList.addActionListener(this);
			panel.add(cmbMessageList);
			panel.add(label);
			menu.repaint(); menu.revalidate();
			conn.close();
						
		}

		catch (SQLException e) {
			System.out.println("Unhandled SQL Exception");
			e.printStackTrace();
		}
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JComboBox<?> cb = (JComboBox<?>)e.getSource();
		in = (String)cb.getSelectedItem();
		ds = new SQLiteDataSource();
		ds.setUrl("jdbc:sqlite:test.db");
		String sql2 = "UPDATE STUDENTS SET PROGRAMOFSTUDY = \'" + in + "\' WHERE STUDENTID = \'" + rp1.getStudentID() + "\'";
		System.out.println(sql2);	
		try {
			conn = ds.getConnection();
			smt = conn.createStatement();
			smt.executeUpdate(sql2);
			label.setText("Student " + rp1.getStudentID() + " was updated to " + in + ".");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
}
