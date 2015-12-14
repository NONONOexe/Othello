package swingconponent;

import boardsystem.BoardState;

public class GameEnd {
	private GUI gui;
	private BoardState board;
	private boolean unknownFlag = false;

	public GameEnd(GUI gui, BoardState board) {
		this.gui = gui;
		this.board = board;
	}

	public void end() {
		gui.score();// スコア計算
		gui.highScore();// ハイスコア計算
		gui.setLabelText("");// ラベル初期化

		// Unknown切
		if (gui.isUnknown()) {
			gui.unknownBGMStop();
			gui.bgmStart();
			gui.setUnknown(false);
		} else {
			// Unknown付
			if (gui.getScore() > 3500 && unknownFlag != true) {
				gui.bgmStop();
				unknownFlag = true;
				gui.setUnknown(true);
			}
		}

		gui.showEndPanel();// 画面切り替え
	}

	public int checkWinner() {
		int winner;
		final int HUMAN = 1;
		final int RAI = -1;
		final int DRAW = 0;
		int dif = board.countDisc(gui.getAIColor())
				- board.countDisc(-1 * gui.getAIColor());
		if (dif < 0) {
			winner = HUMAN;
		} else if (dif > 0) {
			winner = RAI;
		} else {
			winner = DRAW;
		}
		return winner;
	}
}
