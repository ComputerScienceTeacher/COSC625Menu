package MenuFunctions;

import javax.swing.BorderFactory;

import project.RAPMenu;

public class NoAllocatedTeachers {

	public NoAllocatedTeachers(RAPMenu rp1) {
		rp1.getMainPanel().removeAll();
		rp1.getMainLabel().setText("Success! NoAllocatedTeachers works!");
		rp1.getMainPanel().setBorder(BorderFactory.createTitledBorder("Success! NoAllocatedTeachers works!"));
		rp1.getF().revalidate();
	}

}
