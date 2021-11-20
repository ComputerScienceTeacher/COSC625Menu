package MenuFunctions;

import java.sql.*;
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
 * This Class Checks a students progress in their program of study
 * 
 * Author Jacob Facemire, Chad Whiteley 11/20/2021 
 * 
 */
			/**
			 * This method calls the database to call the class history for 
			 * the student.
			 * 
			 * @param rp1 - The RAPMenu passed as an object
			 */
	@SuppressWarnings("unused")
	public class ProgramOfStudyChecklist {
		
		public static void ProgramOfStudyCheck(String rp1) {
				
			Connection conn;
			Statement smt;
			SQLiteDataSource ds;
			ResultSet rs;
			ResultSet rs2;
			ds = new SQLiteDataSource();
			ds.setUrl("jdbc:sqlite:test.db");
			String left = "";
			boolean test;
			/*
			 * This First Section pulls a list of all Programs of study
			 */
			try 
			{   
				conn = ds.getConnection();
				smt = conn.createStatement();
				String sql = "SELECT ProgramOfStudy,HISTORY FROM STUDENTS WHERE STUDENTID = \'" + rp1 + "\'";
				System.out.println(sql);
				rs = smt.executeQuery(sql);
				String[] hist = rs.getString("HISTORY").split(";");
				
				String sql2 = "SELECT Requirements FROM Program WHERE Name = \'" + rs.getString(1) + "\'";
				rs2 = smt.executeQuery(sql2);
				
				//Populate history and Program Strings
				String[] prog = rs2.getString(1).split(";");
	
				System.out.println("List Of Reqs for Program:\n--------------------------------------");
				for(int i = 0; i < prog.length; i++)
					System.out.println(prog[i]);
				
				System.out.println("\n\nList Of Classes in Hist:\n--------------------------------------");
				for(int i = 0; i < hist.length; i++)
					System.out.println(hist[i]);
				
				
				
				//Iteratively test every class in the history with class in program reqs
				for(int i = 0; i < prog.length; i++) 
				{
					test = false;
					for(int j = 0 ; j < hist.length; j++)
					{
						System.out.println("Comparing: " + prog[i] + "\n\t\t" + hist[j]);
						if(prog[i].equals(hist[j]))
							{test = true;}
					}
					//If the loop can not find the class in the history, append a string with the missing class
					if(test == false)
						left = left + prog[i] + ";"; 
				}
		
				
			System.out.println("Classes in Program yet to take: " + left);
			}

			catch (SQLException e) {
			System.out.println("Unhandled SQL Exception");
			e.printStackTrace();
			}
		}
	}

