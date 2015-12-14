package books;

import boardsystem.SetPoint;

public class Spiner {
	public SetPoint convertP(SetPoint before, int cov) {
		SetPoint after;
		switch (cov) {
		case 0:
			after = before;
			break;
		case 1:
			after = transpositionP(before);
			break;
		case 2:
			after = untranspositionP(before);
			break;
		case 3:
			after = perpendicularP(before);
			break;
		case 4:
			after = horizontalP(before);
			break;
		case 5:
			after = revolution90P(before);
			break;
		case 6:
			after = revolution180P(before);
			break;
		case 7:
			after = revolution270P(before);
			break;
		default:
			return null;
		}
		return after;
	}

	// 転置
	private SetPoint transpositionP(SetPoint before) {
		SetPoint after;
		int i = before.getXY()[0];
		int j = before.getXY()[1];
		after = new SetPoint(j, i);
		return after;
	}

	// 逆転置
	private SetPoint untranspositionP(SetPoint before) {
		SetPoint after;
		before = revolution180P(before);
		after = transpositionP(before);
		return after;
	}

	// 上下反転
	private SetPoint perpendicularP(SetPoint before) {
		SetPoint after;
		int i = before.getXY()[0];
		int j = before.getXY()[1];
		after = new SetPoint(9 - i, j);
		return after;
	}

	// 左右反転
	private SetPoint horizontalP(SetPoint before) {
		SetPoint after;
		int i = before.getXY()[0];
		int j = before.getXY()[1];
		after = new SetPoint(i, 9 - j);
		return after;
	}

	// 90度回転
	private SetPoint revolution90P(SetPoint before) {
		SetPoint after;
		before = transpositionP(before);
		after = horizontalP(before);
		return after;
	}

	// 180度回転
	private SetPoint revolution180P(SetPoint before) {
		SetPoint after;
		before = revolution90P(before);
		after = revolution90P(before);
		return after;
	}

	// 270度回転
	private SetPoint revolution270P(SetPoint before) {
		SetPoint after;
		before = revolution180P(before);
		after = revolution90P(before);
		return after;
	}

	public int[][] convert(int[][] before, int cov) {
		int after[][] = new int[10][10];
		switch (cov) {
		case 0:
			after = before;
			break;
		case 1:
			after = transposition(before);
			break;
		case 2:
			after = untransposition(before);
			break;
		case 3:
			after = perpendicular(before);
			break;
		case 4:
			after = horizontal(before);
			break;
		case 5:
			after = revolution90(before);
			break;
		case 6:
			after = revolution180(before);
			break;
		case 7:
			after = revolution270(before);
			break;
		default:
			return null;
		}
		return after;
	}

	// 転置
	private int[][] transposition(int[][] before) {
		int after[][] = new int[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				after[i][j] = before[j][i];
			}
		}
		return after;
	}

	// 逆転置
	private int[][] untransposition(int[][] before) {
		int after[][] = new int[10][10];
		before = revolution180(before);
		after = transposition(before);
		return after;
	}

	// 上下反転
	private int[][] perpendicular(int[][] before) {
		int after[][] = new int[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				after[i][j] = before[9 - i][j];
			}
		}
		return after;
	}

	// 左右反転
	private int[][] horizontal(int[][] before) {
		int after[][] = new int[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				after[i][j] = before[i][9 - j];
			}
		}
		return after;
	}

	// 90度回転
	private int[][] revolution90(int[][] before) {
		int after[][] = new int[10][10];
		before = transposition(before);
		after = horizontal(before);
		return after;
	}

	// 180度回転
	private int[][] revolution180(int[][] before) {
		int after[][] = new int[10][10];
		before = revolution90(before);
		after = revolution90(before);
		return after;
	}

	// 270度回転
	private int[][] revolution270(int[][] before) {
		int after[][] = new int[10][10];
		before = revolution180(before);
		after = revolution90(before);
		return after;
	}
}
