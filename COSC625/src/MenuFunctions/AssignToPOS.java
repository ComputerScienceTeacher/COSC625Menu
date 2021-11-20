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
import java.util.*;


/**
 * This Class allows a teacher to assign a student to a specific Program of Study 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * Author Jacob Facemire, 11/19/2021 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */
public class AssignToPOS {
		/**
		 * This method calls the database to call the class history for 
		 * the student.
		 * 
		 * @param rp1 - The RAPMenu passed as an object
		 */
	public static void AssignToPOS(String rp1) {
			
		Connection conn;
		Statement smt;
		SQLiteDataSource ds;
		ResultSet rs;
		ds = new SQLiteDataSource();
		ds.setUrl("jdbc:sqlite:test.db");
		Scanner sc = new Scanner(System.in);
		String in = null;
		
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
			System.out.println(prog[count]);
			count++;
			}
			
			System.out.println("\n\nPlease Enter New Program of study: ");
			
			//This calls for the counselor to input a new Programof Study
			boolean test = false;
			while(test == false)
				{
					in = sc.nextLine();
					for(int i = 0; i < prog.length;i++) {
						if(in.equals(prog[i])) {
							test = true;
						}
					}
					if(test == false)
						System.out.println("Not a valid program, please try again");			
				}
		
			String sql2 = "UPDATE STUDENTS SET PROGRAMOFSTUDY = \'" + in + "\' WHERE STUDENTID = \'" + rp1 + "\'";
			System.out.println(sql2);
			smt.executeUpdate(sql2);		
		
		}

		catch (SQLException e) {
		System.out.println("Unhandled SQL Exception");
		e.printStackTrace();
		}
	}
}
