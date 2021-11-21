package MenuFunctions;

import javax.swing.BorderFactory;

import project.RAPMenu;

public class WithAllocatedTeachers {

	public WithAllocatedTeachers(RAPMenu rp1) {
		rp1.getMainPanel().removeAll();
		rp1.getMainLabel().setText("");
		rp1.getMainPanel().setBorder(BorderFactory.createTitledBorder("Success! WithAllocatedTeachers works!"));
		rp1.getF().revalidate();
	}

}
