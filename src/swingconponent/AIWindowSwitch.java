package swingconponent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AIWindowSwitch implements ActionListener {	
	private GUI gui;

	public AIWindowSwitch(GUI gui) {
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		gui.aiWindowSwitch();
	}
}
