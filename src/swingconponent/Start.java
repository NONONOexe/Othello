package swingconponent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import boardsystem.BoardState;

public class Start implements ActionListener {

	private GUI gui;
	private String cmd;

	public Start(GUI gui) {
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		gui.showBoardPanel();
		cmd = e.getActionCommand();
		if (cmd == "1 PLAYER GAME") {
			gui.setAIColor(BoardState.WHITE);
			gui.setAILevel(0);//レベルリセット
			gui.setLabelText("Random");
		} else if (cmd == "2 PLAYER GAME") {
			gui.setAIColor(0);
		} else if (cmd == "PRACTICE") {
			gui.setAIColor(0);
		}
		gui.gameStart();
	}
	
	public String getCommand(){
		return cmd;
	}
}
