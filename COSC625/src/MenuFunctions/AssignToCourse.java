package MenuFunctions;

import java.awt.BorderLayout;
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
import javax.swing.JTextArea;


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

	RAPMenu rp1;
	String in;


	/**

	 *
	 * @param rp1 - The RAPMenu passed as an object
	 */
	public AssignToCourse(RAPMenu rp1) {
		ds = new SQLiteDataSource();
		ds.setUrl("jdbc:sqlite:test.db");
		in = null;


		try
		{
			conn = ds.getConnection();
			smt = conn.createStatement();
			String sql= "Select Name from COURSE";



			int count = 0;


			rs = smt.executeQuery(sql);
			String [] courselist = new String[115];

			//This block of code iterates through the result set and initializes
                        //the associated "course_list" array index with the element in the result
			//set that us currently indicated by the result set cursor
			while(rs.next())
				{

				courselist[count] = rs.getString("Name");
				count++;
			}



			label = rp1.getMainLabel();
			label.setText("");
			JFrame menu = rp1.getF();
			JPanel panel = rp1.getMainPanel();

			for(int i = 0; i < courselist.length; i++) {
				JTextArea label = new JTextArea(courselist[i]);
				label.setLineWrap( true );
				label.setWrapStyleWord(true);
				panel.add(label, BorderLayout.SOUTH);
			}

			JComboBox<String> Courselist = new JComboBox<String>(courselist);

			panel.removeAll();
			panel.setLayout(new GridLayout(8,1));
			panel.setBorder(BorderFactory.createTitledBorder("Assign " + rp1.getStudentID() + " to Course"));
			Courselist.setSelectedIndex(5);
			Courselist.addActionListener(this);
			panel.add(Courselist);
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


		}

	}
