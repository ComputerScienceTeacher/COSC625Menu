package main;

import project.RAPMenu;
import DataFunctions.*;

public class main {

	public static void main(String[] args) {
		DataSource.addCourseTable();
		DataSource.addStudentTable();
		DataSource.studentImport();
		DataSource.courseImport();
		
		new RAPMenu();
		}

}
