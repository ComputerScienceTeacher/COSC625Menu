package MenuFunctions;

import java.awt.BorderLayout;

import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import project.RAPMenu;
import org.sqlite.SQLiteDataSource;

/**
 *
 * This class allows a counselor to view the teachers
 *
 * @author Nnabugwu Onyirimba
 *
 */



public class TotalEnrollment {
	/**
	 * This method is the view courses by total enrollment constructor.
	 *
	 * @param rp1 - the menu that is used for the program
	 *
	 *
	 * @author Nana, Chad, Debugging by Jacob
	 *
	 */
	public TotalEnrollment(RAPMenu rp1) {

		Connection conn;
		Statement smt;
		SQLiteDataSource ds;

		ds = new SQLiteDataSource();
		ds.setUrl("jdbc:sqlite:test.db");
		try
		{   conn = ds.getConnection();
		    smt = conn.createStatement();
			int count = 0;
			String sql = "SELECT * FROM COURSE ORDER BY NUMBEROFSTUDENTS DESC";

			ResultSet rs = smt.executeQuery(sql);
			
			JPanel panel = rp1.getMainPanel();
			panel.removeAll();
			panel.setLayout(new GridLayout(1000,1));
			panel.setBorder(BorderFactory.createTitledBorder("# sections per course"));

			while(rs.next()) {
				if(rs.getInt(5) == 0)
				{JTextArea label = new JTextArea("# of sections" + "    0    " + rs.getString(1));
				label.setLineWrap(true);
				label.setWrapStyleWord(true);
				panel.add(label, BorderLayout.SOUTH);
				count = count + 1;}
				else if (rs.getInt(5) < 5) {
				JTextArea label = new JTextArea("# of sections" + "    1    " + rs.getString(1));
				label.setLineWrap(true);
				label.setWrapStyleWord(true);
				panel.add(label, BorderLayout.SOUTH);
				count = count + 1;
				}
				else {
				JTextArea label = new JTextArea("# of sections" + "   " + (rs.getInt(5)/5) + "    " + rs.getString(1));
				label.setLineWrap(true);
				label.setWrapStyleWord(true);
				panel.add(label, BorderLayout.SOUTH);
				count = count + 1;
				}

			}

			Integer.parseInt("200");

			JTextArea label1 = new JTextArea(Integer.toString(count));
			label1.setLineWrap( true );
			label1.setWrapStyleWord(true);
			panel.add(label1, BorderLayout.SOUTH);


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
