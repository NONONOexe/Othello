package swingconponent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import boardsystem.BoardState;

public class Back implements ActionListener {
	private BoardState board;
	private GUI gui;
	public Back(BoardState board, GUI gui) {
		this.board = board;
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int times = 1;
		// AIが起動しているときは前の自分の手(2手前にもどす)
		if (gui.getAIColor() != 0) {
			times = 2;
		}
		for (int i = 0; i < times; i++) {
			board.undo();
			gui.repaint();
		}
	}
}
