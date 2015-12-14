package boardsystem;

import swingconponent.GUI;

class ReversiGame {
	public static void main(String args[]) {
		BoardState board = new BoardState();
		GUI gui = new GUI(board);
		gui.game();
	}
}