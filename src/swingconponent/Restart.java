package swingconponent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import boardsystem.BoardState;

public class Restart implements ActionListener {
	private BoardState board;
	private GUI gui;

	public Restart(BoardState board, GUI gui) {
		this.board = board;
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		board.restart();
		gui.repaint();
	}

}
