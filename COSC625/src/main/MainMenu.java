package main;

import DataFunctions.*;
import project.RAPMenu;

public class MainMenu {

	public static void main(String[] args) {
		new DataSource();
		new AddTables();
		new CSVImport();
		new RAPMenu();
	}

}
