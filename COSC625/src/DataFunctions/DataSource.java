package DataFunctions;

/**
 * This class sets up the database file and imports data for Students and courses from csv files stored in the location as the project
 * 
 * 
 * @author Chad Whiteley, Jacob Facemire 
 * @Version 0.9
 * 
 * 
 * 
 * 
 * 
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteDataSource;

public class DataSource {
	public SQLiteDataSource ds = new SQLiteDataSource();
	Connection conn;
	Statement smt;

        /**
        * This is the constructor for Datasource
        */
	
	public DataSource(){
		ds = null;

				
	    try {
                //
                //
	        ds = new SQLiteDataSource();
	        ds.setUrl("jdbc:sqlite:test.db");
	        conn = ds.getConnection();
	        newQuery("DELETE FROM STUDENTS");
		newQuery("DELETE FROM PROGRAM");
		newQuery("DELETE FROM TEACHERS");
		newQuery("DELETE FROM HISTORY");
		newQuery("DELETE FROM COURSE");
		conn.close();
	    } catch ( Exception e ) {
	        e.printStackTrace();
	        System.exit(0);

	    }
	    System.out.println( "Opened database successfully" );
	    System.out.println( "Created database successfully" ); 
	}

    /**
     * This is method closes the connection with ds
     */
	public void closeConnection() {
		try {
			conn.close();
			System.out.println("Database connection closed.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit( 0 );
		}
	}

	/**
	 * This function executes a query into the RAP database
	 */
	public void newQuery(String query) {
		try ( Statement stmt = conn.createStatement(); ) 
		{
	            int rv = stmt.executeUpdate( query );
	            System.out.println( "executeUpdate() returned " + rv );
	        
		} catch ( SQLException e ) {
	            e.printStackTrace();
	            System.exit( 0 );
	    }
	}


        
    public void StudentHistoryGen() {
		{
			/*
			 * This block of code makes a new table if it has yet to be created
			 * and imports student IDs and Grades through Student Table
			 * 
			 * All of This is outdated and should be removed once Schedule Assignment is possible
			 * 
			 * 
			 * 
			 * 
			 */
			SQLiteDataSource ds;
			ds = new SQLiteDataSource();
			ds.setUrl("jdbc:sqlite:test.db");
			try { Statement smt = conn.createStatement();
			String sql2 = "DROP TABLE HISTORY";
			
			smt.execute(sql2);
			
			String sql = "CREATE TABLE  IF NOT EXISTS SCHEDULE (StudentID VARCHAR(255), Grade INTEGER, CourseIDs VARCHAR(255))";
			
			smt.addBatch(sql);
			System.out.println("Course Table Created");

			
			/**
			 * Because our test data does not have course history, we are going to randomly generate it
			 * 
			 */			
			
			//Set relative paths for DB and CSV
			ds.setUrl("jdbc:sqlite:test.db");
			String csvPath = "Students.csv";
			
			//These Vars are for all the columns in the Students Table
			String StudentID;
			int Grade;
		
			@SuppressWarnings("resource")
			BufferedReader lineReader = new BufferedReader(new FileReader(csvPath));
			//This skips the header line
			String line = lineReader.readLine();

			String temp = null;
				// This while loop goes through the whole CSV Iteratively
				while ((line=lineReader.readLine())!=null) {
					if(!line.contains(",,,")) {
						String currentLine[] = line.split(",");
					
						StudentID = currentLine[3];
						Grade = Integer.valueOf(currentLine[5]);
						
						if(Grade == 9) 
							temp = "3220100,3100500,3010200,3320100";
						if (Grade == 10) 
							temp = "3220200,3100700,3040000,3340400";
						if (Grade == 11) {
							int r = (int)(Math.random() * 2 + 1);
							if(r == 1)
								temp = "A3220100,3100600,3050000,3340100";
							else 
								temp = "3220300,3100600,3050000,3340100";
						}
						if (Grade == 12) {
							int r = (int)(Math.random() * 3 + 1);	
							System.out.println("r is : " + r);
							if(r == 1)
								temp = "A3220200,3101100,13002100,3340100";
							if(r == 2)
								temp = "A3220100,A3100101,13000700,3310300";
							if(r == 3)
								temp = "3220400,A3100200,13020600,A3330100";		
						}
						
						sql = "INSERT INTO SCHEDULE (StudentID, GRADE, CourseIDs) VALUES (\'" + StudentID + "\', \'" + Grade +  "\',\'" + temp + "\') ";
						System.out.println(sql);
						smt.addBatch(sql);
					}
				}
				smt.executeBatch();
				System.out.println("SQL Executed Properly");
						
			} catch (SQLException | NumberFormatException | IOException e) {
				System.out.println("Unhandled SQL Exception");
				e.printStackTrace();
			}
		}
	}

}

