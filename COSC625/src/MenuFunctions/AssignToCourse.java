package MeenuFunctions;

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
 * This Class allows a teacher to assign a student to course(s)
 *
 *
 * Author Neil Sahoo 11/19/2021
 *
 */
public class AssignToCourse implements ActionListener {
	Connection conn;
	Statement smt;
	SQLiteDataSource ds;
	ResultSet rs;
	JLabel label;
	String in;
	RAPMenu rp1;


	/**

	 *
	 * @param rp1 - The RAPMenu passed as an object
	 */
	public AssignToCourse(RAPMenu rp1) {
		ds = new SQLiteDataSource();
		ds.setUrl("jdbc:sqlite:test.db");
		this.rp1 = rp1;
		in = null;

		/*

		 */
		try
		{
			conn = ds.getConnection();
			smt = conn.createStatement();
			String sql1= "Select Name, CoursePreRequisite from Courses";
			String sql2 = "Select History from Student";

			rs = smt.executeQuery(sql1);
			String course_history;
			String[] course_list = new String[115];
			int count = 0;

			while(rs.next())
			{
				course_list[count] = rs.getString(1);
				count++;
			}



			label = rp1.getMainLabel();
			label.setText("");
			JComboBox<String> cmbMessageList = new JComboBox<String>();
			JFrame menu = rp1.getF();
			JPanel panel = rp1.getMainPanel();
			panel.removeAll();
			panel.setLayout(new GridLayout(8,1));
			panel.setBorder(BorderFactory.createTitledBorder("Assign " + rp1.getStudentID() + " to Course"));
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
		String sql3 = "UPDATE STUDENTS CurrentCourses = \'" + in + "\' WHERE STUDENTID = \'" + rp1.getStudentID() + "\'";
		System.out.println(sql3);
		try {
			conn = ds.getConnection();
			smt = conn.createStatement();
			smt.executeUpdate(sql3);
			label.setText("Student " + rp1.getStudentID() + " was updated to " + in + ".");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}
