package DataFunctions;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteDataSource;

public class DataSource {
	SQLiteDataSource ds;
	
	public DataSource(){
		ds = null;
	
	    try {
	        ds = new SQLiteDataSource();
	        ds.setUrl("jdbc:sqlite:test.db");
	    } catch ( Exception e ) {
	        e.printStackTrace();
	        System.exit(0);
	    }
	    System.out.println( "Opened database successfully" );
	
	    try ( Connection conn = ds.getConnection() ) {
	    } catch ( SQLException e ) {
	        e.printStackTrace();
	        System.exit( 0 );
	    }
	
	    System.out.println( "Created database successfully" );
	}
	
	public void newQuery(String query) {
		try ( Connection conn = ds.getConnection();
	            Statement stmt = conn.createStatement(); ) {
	            int rv = stmt.executeUpdate( query );
	            System.out.println( "executeUpdate() returned " + rv );
	        
		} catch ( SQLException e ) {
	            e.printStackTrace();
	            System.exit( 0 );
	    }
	}
}

