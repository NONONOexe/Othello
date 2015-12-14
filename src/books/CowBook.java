package books;

import boardsystem.BoardState;
import boardsystem.SetPoint;

public class CowBook {
	private SetPoint setPoint;
	private int turns;
	private int currentBoard[][] = new int[10][10];
	private int cowBoard[][] = new int[10][10];
	private SetPoint cowPoint;
	private Spiner s = new Spiner();
	public static final int BLACK = 1;// 黒石
	public static final int EMPTY = 0;// 石なし
	public static final int WHITE = -1;// 白石
	public static final int WALL = 2;// 周りの壁
	public static final int BOARD_SIZE = BoardState.BOARD_SIZE;

	public CowBook(BoardState board) {
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
				cowBoard[x][y] = EMPTY;
			}
		}

		// 壁の設定
		for (int y = 0; y < BOARD_SIZE + 2; y++) {
			cowBoard[0][y] = WALL;
			cowBoard[BOARD_SIZE + 1][y] = WALL;
		}
		for (int x = 0; x < BOARD_SIZE + 2; x++) {
			cowBoard[x][0] = WALL;
			cowBoard[x][BOARD_SIZE + 1] = WALL;
		}
	}

	public SetPoint getPoint() {
		return setPoint;
	}

	public boolean match() {
		switch (turns) {
		case 2:
			cowBoard[4][4] = WHITE;
			cowBoard[4][5] = BLACK;
			cowBoard[5][4] = BLACK;
			cowBoard[5][5] = WHITE;
			cowBoard[5][6] = BLACK;
			cowBoard[6][6] = WHITE;
			cowPoint = new SetPoint(6, 5);
			for (int i = 0; i < 8; i++) {
				if (cowEqual(currentBoard, s.convert(cowBoard, i))) {
					setPoint = s.convertP(cowPoint, i);
					return true;
				}
			}
			return false;
		case 3:
			cowBoard[4][4] = WHITE;
			cowBoard[4][5] = BLACK;
			cowBoard[5][4] = BLACK;
			cowBoard[5][5] = BLACK;
			cowBoard[5][6] = BLACK;
			cowBoard[6][5] = BLACK;
			cowBoard[6][6] = WHITE;
			cowPoint = new SetPoint(6, 4);
			for (int i = 0; i < 8; i++) {
				if (cowEqual(currentBoard, s.convert(cowBoard, i))) {
					setPoint = s.convertP(cowPoint, i);
					return true;
				}
			}
			return false;
		default:
			return false;
		}
	}

	private boolean cowEqual(int[][] currentBoard, int[][] convertBoard) {
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
