package books;

import boardsystem.BoardState;
import boardsystem.SetPoint;

public class HorseBook {
	private SetPoint setPoint;
	private int turns;
	private int currentBoard[][] = new int[10][10];
	private int horseBoard[][] = new int[10][10];
	private SetPoint horsePoint;
	private Spiner s = new Spiner();
	public static final int BLACK = 1;// 黒石
	public static final int EMPTY = 0;// 石なし
	public static final int WHITE = -1;// 白石
	public static final int WALL = 2;// 周りの壁
	public static final int BOARD_SIZE = BoardState.BOARD_SIZE;

	public HorseBook(BoardState board) {
		this.turns = board.getTurns();

		// 現在のボードをコピー
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				currentBoard[i][j] = board.getRawBoard()[i][j];
			}
		}

		// 定石ボード初期化
		// 全ますを空マスに設定
		for (int x = 1; x <= BOARD_SIZE; x++) {
			for (int y = 1; y <= BOARD_SIZE; y++) {
				horseBoard[x][y] = EMPTY;
			}
		}

		// 壁の設定
		for (int y = 0; y < BOARD_SIZE + 2; y++) {
			horseBoard[0][y] = WALL;
			horseBoard[BOARD_SIZE + 1][y] = WALL;
		}
		for (int x = 0; x < BOARD_SIZE + 2; x++) {
			horseBoard[x][0] = WALL;
			horseBoard[x][BOARD_SIZE + 1] = WALL;
		}
	}

	public SetPoint getPoint() {
		return setPoint;
	}

	public boolean match() {
		switch (turns) {
		case 5:
			horseBoard[3][4] = BLACK;
			horseBoard[4][4] = BLACK;
			horseBoard[4][5] = BLACK;
			horseBoard[4][6] = WHITE;
			horseBoard[5][3] = BLACK;
			horseBoard[5][4] = BLACK;
			horseBoard[5][5] = WHITE;
			horseBoard[5][6] = BLACK;
			horseBoard[6][4] = WHITE;
			horsePoint = new SetPoint(3, 5);
			for (int i = 0; i < 8; i++) {
				if (horseEqual(currentBoard, s.convert(horseBoard, i))) {
					setPoint = s.convertP(horsePoint, i);
					return true;
				}
			}
			return false;
		case 6:
			horseBoard[3][4] = BLACK;
			horseBoard[3][5] = WHITE;
			horseBoard[4][4] = BLACK;
			horseBoard[4][5] = WHITE;
			horseBoard[4][6] = WHITE;
			horseBoard[5][3] = BLACK;
			horseBoard[5][4] = BLACK;
			horseBoard[5][5] = WHITE;
			horseBoard[5][6] = BLACK;
			horseBoard[6][4] = WHITE;
			horsePoint = new SetPoint(4, 7);
			for (int i = 0; i < 8; i++) {
				if (horseEqual(currentBoard, s.convert(horseBoard, i))) {
					setPoint = s.convertP(horsePoint, i);
					return true;
				}
			}
			return false;
		case 7:
			horseBoard[3][4] = BLACK;
			horseBoard[3][5] = WHITE;
			horseBoard[4][4] = BLACK;
			horseBoard[4][5] = BLACK;
			horseBoard[4][6] = BLACK;
			horseBoard[4][7] = BLACK;
			horseBoard[5][3] = BLACK;
			horseBoard[5][4] = BLACK;
			horseBoard[5][5] = WHITE;
			horseBoard[5][6] = BLACK;
			horseBoard[6][4] = WHITE;
			horsePoint = new SetPoint(5, 7);
			for (int i = 0; i < 8; i++) {
				if (horseEqual(currentBoard, s.convert(horseBoard, i))) {
					setPoint = s.convertP(horsePoint, i);
					return true;
				}
			}
			return false;
		case 8:
			horseBoard[3][4] = BLACK;
			horseBoard[3][5] = WHITE;
			horseBoard[4][4] = BLACK;
			horseBoard[4][5] = BLACK;
			horseBoard[4][6] = WHITE;
			horseBoard[4][7] = BLACK;
			horseBoard[5][3] = BLACK;
			horseBoard[5][4] = BLACK;
			horseBoard[5][5] = WHITE;
			horseBoard[5][6] = WHITE;
			horseBoard[5][7] = WHITE;
			horseBoard[6][4] = WHITE;
			horsePoint = new SetPoint(6, 5);
			for (int i = 0; i < 8; i++) {
				if (horseEqual(currentBoard, s.convert(horseBoard, i))) {
					setPoint = s.convertP(horsePoint, i);
					return true;
				}
			}
			return false;
		case 9:
			horseBoard[3][4] = BLACK;
			horseBoard[3][5] = WHITE;
			horseBoard[4][4] = BLACK;
			horseBoard[4][5] = BLACK;
			horseBoard[4][6] = WHITE;
			horseBoard[4][7] = BLACK;
			horseBoard[5][3] = BLACK;
			horseBoard[5][4] = BLACK;
			horseBoard[5][5] = BLACK;
			horseBoard[5][6] = BLACK;
			horseBoard[5][7] = WHITE;
			horseBoard[6][4] = WHITE;
			horseBoard[6][5] = BLACK;
			horsePoint = new SetPoint(4, 3);
			for (int i = 0; i < 8; i++) {
				if (horseEqual(currentBoard, s.convert(horseBoard, i))) {
					setPoint = s.convertP(horsePoint, i);
					return true;
				}
			}
			return false;
		default:
			return false;
		}
	}

	private boolean horseEqual(int[][] currentBoard, int[][] convertBoard) {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (currentBoard[i][j] != convertBoard[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
}
