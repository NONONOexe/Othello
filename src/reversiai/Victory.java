package reversiai;

import boardsystem.BoardState;

public class Victory implements Value {
	private static final int WIN = 1;
	private static final int DRAW = 0;
	private static final int LOSE = -1;

	@Override
	public int valueMain(BoardState board) {
		int dif = board.countDisc(BoardState.BLACK)
				- board.countDisc(BoardState.WHITE);
		int result = board.getCurrentColor() * dif;

		if (result > 0) {
			return WIN;
		} else if (result < 0) {
			return LOSE;
		} else {
			return DRAW;
		}

	}

}
