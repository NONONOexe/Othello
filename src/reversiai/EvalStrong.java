package reversiai;

import java.util.ArrayList;

import boardsystem.BoardState;
import boardsystem.SetPoint;

public class EvalStrong implements Value {

	@Override
	public int valueMain(BoardState board) {
		ArrayList<SetPoint> pos = board.getMovablePosAI();
		int eval = 0;

		eval += 60 * pos.size();

		eval += -530 * board.countStar();
		eval += -400 * board.countCornerAround();
		eval += 0 * board.countCorner();
		eval += 250 * board.countMt(-1 * board.getCurrentColor());
		eval += -100 * board.countMt(board.getCurrentColor());
		eval += 250 * board.countBlock(-1 * board.getCurrentColor());
		eval += -200 * board.countWing(-1 * board.getCurrentColor());
		
		return eval;
	}
}
