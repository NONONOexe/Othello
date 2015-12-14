package reversiai;

import boardsystem.BoardState;

public class Perfect implements Value {

	@Override
	public int valueMain(BoardState board) {
		int dif = board.countDisc(BoardState.BLACK)
				- board.countDisc(BoardState.WHITE);
		int result = board.getCurrentColor() * dif;
		return result;
	}
}
