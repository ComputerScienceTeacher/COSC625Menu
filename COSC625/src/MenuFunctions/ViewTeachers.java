package MenuFunctions;

import project.RAPMenu;

public class ViewTeachers {

	public ViewTeachers(RAPMenu rp1) {
		//rp1.getMainLabel().setText("Success! View Teachers works!");
		
		ds = new SQLiteDataSource();
		ds.setUrl("jdbc:sqlite:test.db");
		
		
		try 
		{   conn = ds.getConnection();
			smt = conn.createStatement();
			String sql = "SELECT NAME FROM TEACHERS";
		
			rs = smt.executeQuery(sql);
			
			String[] teachers; // = rs.getString("NAME").split();
			//System.out.println("Student " + rp1.getStudentID() + "'s Schedule:\n--------------------------------------");
			teachers = new String[151];
			
			for(int j = 0; j<teachers.length; j++) {		
				teachers[j] = rs.getString("NAME");
				rs.next();
			}

			
			rp1.getMainLabel().setText("");
			JPanel panel = rp1.getMainPanel();
			panel.removeAll();
			panel.setLayout(new GridLayout(15,1));
			panel.setBorder(BorderFactory.createTitledBorder("List of all Teachers"));
			for(int i = 0; i<teachers.length; i++) {				
				JTextArea label = new JTextArea(teachers[i]);
				label.setLineWrap( true );
				label.setWrapStyleWord(true);
				panel.add(label, BorderLayout.SOUTH);
				//System.out.println(classes[i]);
			}
			rp1.getF().repaint();
			rp1.getF().revalidate();
		} 
		catch (SQLException e) {
		System.out.println("Unhandled SQL Exception");
		e.printStackTrace();
		}
		
		try {
			conn.close();
			System.out.println("Database connection closed.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit( 0 );
		}
			


	}

}
