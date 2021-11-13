package main;

import MenuFunctions.*;
import DataFunctions.*;
import project.RAPMenu;

public class MainMenu {

	public static void main(String[] args) {
		DataSource d = new DataSource();
		d.addCourseTable();
		d.courseImport();
		CourseGenerate.CourseGenerate("25690");
//		new RAPMenu();
		}

}
