package boardsystem;

import java.util.ArrayList;

public class CUIPrint {
	public static void printDebug(BoardState board) {
		printBoard(board);
		printEval(board);
		printStatus(board);
	}

	// ボード
	public static void printBoard(BoardState board) {
		System.out.println("   a b c d e f g h ");
		for (int y = 1; y <= 8; y++) {
			System.out.print("  " + y);
			for (int x = 1; x <= 8; x++) {
				switch (board.getColor(new SetPoint(x, y))) {
				case BoardState.BLACK:
					System.out.print("● ");
					break;
				case BoardState.WHITE:
					System.out.print("○ ");
					break;
				default:
					System.out.print("  ");
					break;
				}
			}
			System.out.println();
		}
	}

	// 評価値
	public static void printEval(BoardState board) {
		ArrayList<SetPoint> pos = board.getMovablePosAI();
		int eval = 0;
		System.out.println("Eval default\t:  eval = " + eval);
		eval += 60 * pos.size();
		System.out.println("MovablePos\t:" + pos.size() + " eval = " + eval);
		eval += -530 * board.countStar();
		System.out.println("Star\t\t:" + board.countStar() + " eval = " + eval);
		eval += -400 * board.countCornerAround();
		System.out.println("CornerAround\t:" + board.countCornerAround()
				+ " eval = " + eval);
		eval += 0 * board.countCorner();
		System.out.println("Corner\t\t:" + board.countCorner() + " eval = "
				+ eval);
		eval += 250 * board.countMt(-1 * board.getCurrentColor());
		System.out.println("Mt(my)\t\t:"
				+ board.countMt(-1 * board.getCurrentColor()) + " eval = "
				+ eval);
		eval += -100 * board.countMt(board.getCurrentColor());
		System.out.println("Mt(you)\t\t:"
				+ board.countMt(board.getCurrentColor()) + " eval = " + eval);
		eval += 250 * board.countBlock(-1 * board.getCurrentColor());
		System.out.println("Block\t\t:"
				+ board.countBlock(-1 * board.getCurrentColor()) + " eval = "
				+ eval);
		eval += -200 * board.countWing(-1 * board.getCurrentColor());
		System.out.println("Wing\t\t:"
				+ board.countWing(-1 * board.getCurrentColor()) + " eval = "
				+ eval);

	}

	// ボードステータス一覧
	public static void printStatus(BoardState board) {
		System.out.println("\nCurrentColor\t:" + board.getCurrentColor());
		System.out.println("Turns\t\t:" + board.getTurns());
		System.out.println("MovablePosSize\t:" + board.getMovablePos().size());

		if (board.getMovablePos().size() != 0) {
			System.out.print("MovablePos\t:(");
			for (int i = 0; i < board.getMovablePos().size() - 1; i++) {
				System.out.print(board.getMovablePos().get(i).toString() + ",");
			}
			System.out.println(board.getMovablePos()
					.get(board.getMovablePos().size() - 1).toString()
					+ ")");
		} else {
			System.out.println("MovablePos\t:NULL");
		}
		if (board.getUpdate() != null) {
			if (board.getUpdate().size() != 0) {
				System.out.print("Update\t\t:(");
				ArrayList<SetPoint> update = board.getUpdate();
				for (int i = 0; i < update.size() - 1; i++) {
					System.out.print(update.get(i).toString() + ",");
				}
				System.out.println(update.get(update.size() - 1).toString()
						+ ")");
			} else {
				System.out.println("Update\t\t:NULL");
			}
		} else {
			System.out.println("Update\t\t:NULL");
		}
	}
}
