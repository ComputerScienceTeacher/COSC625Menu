package project;

import java.awt.event.*;
import java.util.Hashtable;
import java.util.ArrayList;
import DataFunctions.DataSource;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


/**
 * This is the constructor for the RAPMenu
 * It is an implementation of the JFrame.
 * It implements the ActionListener for clicking the menu.
 * 
 * @author Chad Whiteley
 * @version 1.0
 */
public class RAPMenu extends JFrame implements ActionListener{
	private JMenu menu1, menu2, submenu1, submenu2, submenu3;  
    private JMenuItem i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13;
    private JLabel mainLabel, label;
    private JTextField textField;
    private JButton button1,button2;
    private JFrame f;
    private Hashtable <Object, String> options;
	private DataSource db1;
    
    /**
     * This is the general constructor for a RAPMenu window.
     */
    public RAPMenu(){
    	addCourses();
    	setUpMenu();
    }
    
    /**
     * This is the method for setting up the RAP Menu.
     */
    private void setUpMenu() {
    	//setting up menu
    	f= new JFrame("RAP (Requirements Assistance Planning)");  
        JMenuBar mb=new JMenuBar();
        label = new JLabel(" ");
        mainLabel = new JLabel("Please select a menu option", JLabel.CENTER);
        mainLabel.setVerticalAlignment(JLabel.TOP);
        f.add(mainLabel);
        
        //first menu bar
        menu1=new JMenu("Student");  
        submenu1=new JMenu("Planning");  
        i1=new JMenuItem("Class History");  
        i2=new JMenuItem("GPA Calculation");  
        i3=new JMenuItem("Current Courses");  
        i4=new JMenuItem("Program of Study Checklist");  
        i5=new JMenuItem("Suggested Classes");  
        menu1.add(i1); menu1.add(i2); menu1.add(i3);  
        submenu1.add(i4); submenu1.add(i5);  
        menu1.add(submenu1);  
        mb.add(menu1);  
        
        //second menu bar
        menu2=new JMenu("Faculty");  
        submenu2=new JMenu("View Sections");
        submenu3=new JMenu("Assign Students");
        i6=new JMenuItem("View Teachers");  
        i7=new JMenuItem("View Master Schedule");
        i8=new JMenuItem("Without Allocated Teachers");
        i9=new JMenuItem("With Allocated Teachers");
        i10 = new JMenuItem("By Total Enrollment");
        i11 = new JMenuItem("View Registered Students");
        i12 = new JMenuItem("To Program of Study");
        i13 = new JMenuItem("To Course");
        menu2.add(i6); menu2.add(i7); menu2.add(i11); 
        submenu2.add(i8); submenu2.add(i9); submenu2.add(i10);
        submenu3.add(i12); submenu3.add(i13);
        menu2.add(submenu2);
        menu2.add(submenu3);
        mb.add(menu2);
        
        //actionListeners added to each button
        i1.addActionListener(this); i2.addActionListener(this);
        i3.addActionListener(this); i4.addActionListener(this);
        i5.addActionListener(this); i6.addActionListener(this);
        i7.addActionListener(this); i8.addActionListener(this);
        i9.addActionListener(this); i10.addActionListener(this);
        i11.addActionListener(this); i12.addActionListener(this);
        i3.addActionListener(this);
        
        //Hashtable created to report menu option clicked
    	options = new Hashtable<>();
    	options.put(i1, "Class History");
    	options.put(i2, "GPA Calculation");
    	options.put(i3, "Current Courses");
    	options.put(i4, "View Program of Study Checklist");
    	options.put(i5, "View Suggested Classes");
    	options.put(i6, "View Teachers");
    	options.put(i7, "View Master Schedule");
    	options.put(i8, "View Sections Without Allocated Teachers");
    	options.put(i9, "View Courses with an Allocated Teacher");
    	options.put(i10, "View Sections By Total Enrollment");
    	options.put(i11, "View Registered Students");
    	options.put(i12, "Assign Students to Program of Study");
    	options.put(i13, "Assign Student to Course Section");
        
    	//Frame made visible on the screen
        f.setJMenuBar(mb);  
        f.setBounds(440,300,400,400);    
        f.setVisible(true);
    }
    
    /**
     * This method allows the program to listen to
     * mouse clicks.
     * 
     * @param e - the event that initiates the actionPerformed
     */
    public void actionPerformed(ActionEvent e) {
    	mainLabel.setText(options.get(e.getSource())+" clicked");
    	f.revalidate();
    	if(e.getSource()== i7) {
    		
    	}
    }
    
    /**
     * This is the method for adding courses to the database.
     */
    private void addCourses() {
    	DataSource db1 = new DataSource();
    	db1.newQuery("DELETE FROM COURSES");
    	db1.newQuery("CREATE TABLE IF NOT EXISTS COURSES " +
    	        "(COURSEID VARCHAR(255) NOT NULL, " +
    	        "NAME VARCHAR(255) NOT NULL, " +
    	        "NUM_CREDITS INTEGER NOT NULL, " +
    	        "Course_Level INTEGER NOT NULL, "  +
    	        "NUM_STUDENTS INTEGER NOT NULL, "  +
    	        "PreReq VARCHAR(255) NOT NULL, " +
    	        "COREQ VARCHAR(255) NOT NULL," +
    	        "PRIMARY KEY (NAME))");
    	
    	String inputFile = "courses.csv";
    	ArrayList <String> coursesList = new ArrayList<String>();
		try {
			coursesList = createArrayList(inputFile);
		} catch (FileNotFoundException e) {
			System.err.println("Error reading file: " + e + "Input file " + inputFile + " not found.");
		} catch  (IOException e) { 
			System.err.println("Input file reading error:" + e);
		}
    
		for(int i=0; i<coursesList.size();i++) {
			String [] currentRecord = coursesList.get(i).split(",");
			String queryString = "INSERT INTO courses(courseID, name, num_credits, course_level, Num_Students, prereq, coreq )" +
				"VALUES ('" + currentRecord[0] + "'," + "'" + currentRecord[1] + "'," +
				currentRecord[2] + "," + currentRecord[3] + "," + currentRecord[4] + "," + 
				"'" + currentRecord[5] + "'," + "'" + currentRecord[6] +"')";
			System.out.println(queryString);
			db1.newQuery(queryString);
		}
    }
	
    private static ArrayList <String> createArrayList(String inputFile) throws FileNotFoundException, IOException{
		//variable declarations
		ArrayList <String> result = new ArrayList<String>();
		BufferedReader textIn = new BufferedReader(new FileReader(inputFile));
		String line;
		int lineCount = 0;
		
		//brings in all lines as potential infix equations
		while((line=textIn.readLine())!=null){
			if(!line.equals("")) {
				result.add(line);
				lineCount ++;
			}
		}
		System.out.println("There are " + lineCount + " records that have been added from the input file.");
		textIn.close();
		return result;
	}
	
}
