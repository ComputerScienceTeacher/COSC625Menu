package project;

import java.awt.GridLayout;
import java.awt.event.*;
import MenuFunctions.*;
import javax.swing.*;


/**
 * This is the constructor for the RAPMenu
 * It is an extension of JFrame.
 * It implements the ActionListener for clicking the menu.
 *
 * @author Chad Whiteley
 * @version 1.0
 */
public class RAPMenu extends JFrame implements ActionListener{
    /**
	 * The serial version ID for RAPMenu
	 */
	private static final long serialVersionUID = 1L;
	private JMenu menu1, menu2, submenu1, submenu2, submenu3;
    private JMenuItem i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13;
    private JMenuItem i14;  //Added for testing ByCourses.java
    private JLabel mainLabel, label;
    private JTextField textField;
    private JButton button1;
    private JPanel mainPanel;
    private JFrame f, vsFrame;
    private JMenuBar mb;
    private String studentID;

    /**
     * This is the general constructor for a RAPMenu window.
     */
    public RAPMenu(){
    	f= new JFrame("RAP (Requirements Assistance Planning)");
    	mainLabel =new JLabel();
    	label = new JLabel();
    	mainPanel = new JPanel();
    	vsFrame = new JFrame();
    	textField=new JTextField(10);
        button1 = new JButton("GO!");
        mb = new JMenuBar();
        button1.setActionCommand("GO");
        button1.addActionListener(this);
		validateStudent();
    	setUpMenu();
    }

     /**
     * This method establishes the student validation window
     */


    private void validateStudent() {
        //This block of code specifies the GUI elements of the student validation window
        //and displays a brief request to enter a student ID
    	vsFrame.setLayout(new GridLayout(4,4));
        JLabel vsLabel = new JLabel("Please enter a student ID:", JLabel.CENTER);
        vsLabel.setVerticalAlignment(JLabel.TOP);
        vsFrame.add(vsLabel);
        vsFrame.add(textField);
        vsFrame.add(button1);
        vsFrame.setBounds(200,250,400,400);
        vsFrame.setVisible(true);
    }


    /**
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
    	    }
    	});


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
        i5=new JMenuItem("Assign Core Classes");
        menu1.add(i1); menu1.add(i2); menu1.add(i3);
        submenu1.add(i4); submenu1.add(i5);
        menu1.add(submenu1);
        mb.add(menu1);

        //second menu bar
        menu2=new JMenu("Faculty");
        submenu2=new JMenu("View Sections");
        submenu3=new JMenu("Assign Students");
        i6=new JMenuItem("View Teachers");
        //i7=new JMenuItem("View Master Schedule");
        //i8=new JMenuItem("Without Allocated Teachers");
        //i9=new JMenuItem("With Allocated Teachers");
        i10 = new JMenuItem("By Total Enrollment");
        i11 = new JMenuItem("View Registered Students");
        i12 = new JMenuItem("To Program of Study");
        i13 = new JMenuItem("To Course");
        i14 = new JMenuItem("By Courses");
        //Added for testing ByCourse.java
        menu2.add(i6);
        //menu2.add(i7); submenu2.add(i8); submenu2.add(i9);
        submenu2.add(i10);  menu2.add(i11);
        submenu3.add(i12);
        submenu3.add(i13);
        submenu2.add(i14);   //Added for testing ByCourse.java
        menu2.add(submenu2);
        menu2.add(submenu3);
        mb.add(menu2);

        //actionListeners added to each button
        i1.addActionListener(this); i2.addActionListener(this);
        i3.addActionListener(this); i4.addActionListener(this);
        i5.addActionListener(this); i6.addActionListener(this);
        //i7.addActionListener(this); i8.addActionListener(this); i9.addActionListener(this);
        i10.addActionListener(this);
        i11.addActionListener(this); i12.addActionListener(this);
        i13.addActionListener(this);
        i14.addActionListener(this);  //Added for testing ByCourse.java

    	//Frame made visible on the screen
    	f.add(mainLabel);
        f.setJMenuBar(mb);
        f.setBounds(440,300,450,450);
        f.setVisible(true);
        f.add(new JScrollPane(mainPanel));
    }


    /**
     * This method allows the program to listen to
     * mouse clicks.
     *
     * @param e - the event that initiates the actionPerformed
     */
    public void actionPerformed(ActionEvent e) {
    	//this reset the JFrame
    	f.revalidate();

        //This block of code executes the ClassHistory, GPACalc,
        //CurrentCourses, ProgramOfStudy,and SuggestClasses methods
        //from MenuFunction Java files when the corresponding
        //menu option is selected
        if(e.getSource()== i1) {
        	new ClassHistory(this);
        }
        else if(e.getSource()== i2) {
        	new GPACalc(this);
        }
        else if(e.getSource()== i3) {
            new CurrentCourses(this);
        }
        else if(e.getSource()== i4) {
        	new ProgramOfStudy(this);
        }
        else if(e.getSource()== i5) {
        	CourseGenerate.CourseGenerate();
                //Suggestion that this line be redone without a value passed in given the
                //the latest change to CourseGenerate

        }

        else if(e.getSource()== i6) {
        	new ViewTeachers(this);
        }

        else if(e.getSource()== i7) {
        	new ViewMasterSchedule(this);
        }
        else if(e.getSource()== i8) {
        	new NoAllocatedTeachers(this);
        }
        else if(e.getSource()== i9) {
            new WithAllocatedTeachers(this);
        }
        else if(e.getSource()== i10) {
        	new TotalEnrollment(this);
        }
        else if(e.getSource()== i11) {
        	new ViewRegisteredStudents(this);
        }

        else if(e.getSource()== i12) {
        	new AssignToPOS(this);
        }

        else if(e.getSource()== i13) {
        	new AssignToCourse(this);
        }
        else if(e.getSource()== i14) { //Added for testing ByCourse.java
        	new ByCourse(this);
        }


        else if(e.getActionCommand()=="GO") {
        	studentID = textField.getText().toString();
          	System.out.println("GO pressed");
          	System.out.println("StudentID = " + studentID);
          	vsFrame.dispose();

        }

    }


    /**
     * This method returns "mainPanel"
     *
     * @return mainPanel - the main panel for RAPMenu
     */
    public JPanel getMainPanel() {
    	return mainPanel;
    }

     /**
     * This method returns "mainLabel"
     *
     * @return mainLabel - the main label for RAPMenu
     */
    public JLabel getMainLabel() {
    	return mainLabel;
    }

     /**
     * This method returns "student ID"
     *
     * @return studentID - the String variable for studentID
     */
    public String getStudentID() {
    	return studentID;
    }

     /**
     * This method returns the JFrame "f"
     *
     * @return f - the JFrame of the RAPMenu
     */
    public JFrame getF() {
    	return f;
    }


}
