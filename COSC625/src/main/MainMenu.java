package main;

import DataFunctions.*;
import project.RAPMenu;

public class MainMenu {

	public static void main(String[] args) {
		DataSource d = new DataSource();
		AddTables ats = new AddTables();
		CSVImport ci = new CSVImport();
		new RAPMenu();
	}

}
