package project;

import java.awt.GridLayout;
import java.awt.event.*;
import java.util.Hashtable;
import DataFunctions.DataSource;
import MenuFunctions.*;

import javax.swing.*;


/**
 * This is the constructor for the RAPMenu
 * It is an extension of JFrame.
 * It implements the ActionListener for clicking the menu.
 * 
 * @author Not-so-agile-developers
 * @version 25.0
 */
public class RAPMenu extends JFrame implements ActionListener{
    private JMenu menu1, menu2, submenu1, submenu2, submenu3;  
    private JMenuItem i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13;
    private JLabel mainLabel, label;
    private JTextField textField;
    private JButton button1;
    private JFrame f, vsFrame;
    private JMenuBar mb;
    private Hashtable <Object, String> options;
    private DataSource db1;
    private String studentID;
    
    /**
     * This is the general constructor for a RAPMenu window.
     */
    public RAPMenu(){
    	//the necessary variables
    	f= new JFrame("RAP (Requirements Assistance Planning)"); 
    	mainLabel =new JLabel(); 
    	options = new Hashtable<>();
    	vsFrame = new JFrame();
    	textField=new JTextField(10);
        button1 = new JButton("GO!");
        mb = new JMenuBar();
        
        
        button1.setActionCommand("GO");
        button1.addActionListener(this);
		validateStudent();
    	db1 = new DataSource();
    	db1.addCourseTable();
    	db1.addStudentTable();
    	db1.addHistoryTable();
    	db1.courseImport();
    	db1.studentImport();
    	db1.historyImport();
    	setUpMenu();
    }

     /**
     * This method establishes the student validation window
     */
    
    
    private void validateStudent() {
    	vsFrame.setLayout(new GridLayout(4,4));
        label = new JLabel("Please enter a student ID:", JLabel.CENTER);
        label.setVerticalAlignment(JLabel.TOP);
        vsFrame.add(label);
        vsFrame.add(textField);
        vsFrame.add(button1);
        vsFrame.setBounds(200,250,400,400);    
        vsFrame.setVisible(true);
    }
    
    
    /*
     * This is the method for setting up the RAP Menu.
     */
    private void setUpMenu() {
    	//setting up menu
    	f= new JFrame("RAP (Requirements Assistance Planning)"); 
    	
    	
    	f.addWindowListener(new WindowAdapter()
    	{
    	    @Override
    	    public void windowClosing(WindowEvent e)
    	    {
    	        super.windowClosing(e);
    	        db1.closeConnection();
    	    }
    	});
    
    	
        JMenuBar mb=new JMenuBar();
        label.setText(" ");
        mainLabel.setText("Please select a menu option");
        mainLabel.setHorizontalAlignment(JLabel.CENTER);
        mainLabel.setVerticalAlignment(JLabel.TOP);
        
       
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
    	options.put(button1, "Please select option from the menu");
        
    	//Frame made visible on the screen
    	f.add(mainLabel);
        f.setJMenuBar(mb);  
        f.setBounds(440,300,450,450);    
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

        if(e.getSource()== i1) {
        	ClassHistory history = new ClassHistory(this);
        }
        else if(e.getSource()== i2) { 
        	GPACalc GPA1 = new GPACalc(this);
        }
        else if(e.getSource()== i3) { 
            CurrentCourses courses1 = new CurrentCourses(this);
        }
        else if(e.getSource()== i4) { 
        	ProgramOfStudy program1 = new ProgramOfStudy(this);
        }
        else if(e.getSource()== i5) { 
        	SuggestClasses suggest1 = new SuggestClasses(this);
        }
        
        else if(e.getActionCommand()=="GO") { 
          studentID = textField.getText().toString();
        	System.out.println("GO pressed");
        	System.out.println("StudentID = " + studentID);
        	vsFrame.dispose();

        }

    }

     /**
     * This method returns "mainLabel"
     */
    
    public JLabel getMainLabel() {
    	return mainLabel;
    }

     /**
     * This method returns "student ID"
     */
    
    public String getStudentID() {
    	return studentID;
    }

     /**
     * This method returns "f"
     */
    
    public JFrame getF() {
    	return f;
    }
      
	
}
