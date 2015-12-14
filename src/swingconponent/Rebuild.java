package swingconponent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import boardsystem.BoardState;

public class Rebuild implements ActionListener {

	private BoardState board;
	private GUI gui;

	public Rebuild(BoardState board, GUI gui) {
		this.board = board;
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		board.restart();
		gui.setLabelText("");
		gui.showBoardPanel();
		String cmd = e.getActionCommand();
		if (cmd.equals("次のレベルへ")) {
			int numAI = gui.getNumAI();// AIの数
			int stage = gui.getStage() + 1;
			gui.setStage(stage);
			int aiLevel = stage % numAI;
			String ainame[] = new String[numAI];
			ainame[0] = "Random";
			ainame[1] = "Formula";
			ainame[2] = "Evaluater";
			gui.setLabelText(ainame[aiLevel]);

			if(gui.isUnknown()){
				gui.setLabelText("Unknown");
				gui.unknownBGMStart();
			}

			if (stage != 0 && stage % numAI == 0) {
				gui.setAIColor(-1 * gui.getAIColor());
			}
			gui.aiTurn();
		} else if (cmd.equals("リトライ")) {
			if (gui.getMode() == "1 PLAYER GAME") {
				int numAI = gui.getNumAI();
				int aiLevel = gui.getAILevel();
				String ainame[] = new String[numAI];
				ainame[0] = "Random";
				ainame[1] = "Formula";
				ainame[2] = "Evaluater";
				gui.setLabelText(ainame[aiLevel]);
				gui.aiTurn();
			} else if (gui.getMode() == "PLACTICE") {
				gui.setAIColor(0);// AIの停止
				gui.showAISet();
			}
		}
		gui.repaint();
	}
}
