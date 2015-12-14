package swingconponent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import boardsystem.BoardState;

public class ReturnTitle implements ActionListener {
	private GUI gui;
	private BoardState board;

	public ReturnTitle(GUI gui, BoardState board) {
		this.gui = gui;
		this.board = board;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		board.restart();
		gui.scoreReset();
		gui.setAIMessage(null);

		gui.showTitlePanel();
	}
}
