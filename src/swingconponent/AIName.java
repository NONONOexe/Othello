package swingconponent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AIName implements ActionListener {

	private GUI gui;
	private int aiLevel;

	public AIName(GUI gui) {
		this.gui = gui;
		this.aiLevel = gui.getAILevel();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String aiName[] = new String[gui.getNumAI()];
		aiName[0] = "Level1:Random";
		aiName[1] = "Level2:Formula";
		aiName[2] = "Level3:Evaluater";
		String cmd = e.getActionCommand();
		int num = aiLevel;

		if (cmd == "next") {
			num++;
			if (num > gui.getNumAI() - 1) {
				num = 0;
			}
		} else if (cmd == "back") {
			num--;
			if (num < 0) {
				num = gui.getNumAI() - 1;
			}
		} else {
		}
		aiLevel = num;
		gui.setNameAILevelPanel(aiName[aiLevel]);
	}
}
