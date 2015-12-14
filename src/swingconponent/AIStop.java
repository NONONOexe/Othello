package swingconponent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AIStop implements ActionListener {

	private GUI gui;

	public AIStop(GUI gui) {
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		gui.setAIColor(0);//AI停止
		gui.showAISet();//AIWindow切り替え
	}

}
