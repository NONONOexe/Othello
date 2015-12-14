package reversiai;
import java.util.ArrayList;

import boardsystem.BoardState;
import boardsystem.SetPoint;


public class EvalCommon implements Value {

	@Override
	public int valueMain(BoardState board) {
		ArrayList<SetPoint> pos = board.getMovablePosAI();
		int eval = pos.size();
		return eval;
	}

}
