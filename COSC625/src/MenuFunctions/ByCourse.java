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
//import project.RAPMenu;
import org.sqlite.SQLiteDataSource;

/**
 * 
 * This class allows a counselor to view the teachers
 * 
 * @author Nnabugwu Onyirimba
 *
 */



public class ByCourse {
	
	
	Connection conn; //, conn1;
	Statement smt; //, smt1;
	SQLiteDataSource ds;
	ResultSet rs; //,rs1;

	/**
	 * This method is the view teachers constructor.
	 * 
	 * @param rp1 - the menu that is used for the program
	 */
	public ByCourse(RAPMenu1 rp1) {
		
		ds = new SQLiteDataSource();
		ds.setUrl("jdbc:sqlite:test.db");
		try 
		{   conn = ds.getConnection();
			smt = conn.createStatement();
			int count = 0;
			String sql = "SELECT * FROM COURSES";
		
			rs = smt.executeQuery(sql);
			
			//conn1 = ds.getConnection();
			//smt1 = conn.createStatement();
			
			//String sqlx = sql//"SELECT * FROM COURSES";

			//rs1 = smt1.executeQuery(sqlx);
			
			JPanel panel = rp1.getMainPanel();
			panel.removeAll();
			panel.setLayout(new GridLayout(1000,1));
			panel.setBorder(BorderFactory.createTitledBorder("# sections per course"));
		 
                        //This block of code iterates through the result set and initializes
                        //the JTextArea instantiation "label" with a calculation of the # of
		        //sections per course, wraps label with the setLineWrap and setWrapStyleWord 
		        //functions, instantiates JPanel object panel with parameters that 
		        //includes "label" each iteration
                        while(rs.next()) {		
				JTextArea label = new JTextArea("# of sections" + "   " + Integer.toString(Math.round(Integer.parseInt(rs.getString(5))/10)) + "    " + rs.getString(2));
                                label.setLineWrap( true );
				label.setWrapStyleWord(true);
				panel.add(label, BorderLayout.SOUTH);
				//rs.next();
				//count = count + 1;
			}
			
			//Integer.parseInt("200");
			
			JTextArea label1 = new JTextArea(Integer.toString(count));
			label1.setLineWrap( true );
			label1.setWrapStyleWord(true);
			panel.add(label1, BorderLayout.SOUTH);


			rp1.getF().repaint();
			rp1.getF().revalidate();
			conn.close();
			//conn1.close();
		} 
		catch (SQLException e) {
		System.out.println("Unhandled SQL Exception");
		e.printStackTrace();
		}
	

	}
}
