package books;

import boardsystem.BoardState;
import boardsystem.SetPoint;

public class TigerBook {
	private SetPoint setPoint;
	private int turns;
	private int currentBoard[][] = new int[10][10];
	private int tigerBoard[][] = new int[10][10];
	private SetPoint tigerPoint;
	private Spiner s = new Spiner();
	public static final int BLACK = 1;// 黒石
	public static final int EMPTY = 0;// 石なし
	public static final int WHITE = -1;// 白石
	public static final int WALL = 2;// 周りの壁
	public static final int BOARD_SIZE = BoardState.BOARD_SIZE;

	public TigerBook(BoardState board) {
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
				tigerBoard[x][y] = EMPTY;
			}
		}

		// 壁の設定
		for (int y = 0; y < BOARD_SIZE + 2; y++) {
			tigerBoard[0][y] = WALL;
			tigerBoard[BOARD_SIZE + 1][y] = WALL;
		}
		for (int x = 0; x < BOARD_SIZE + 2; x++) {
			tigerBoard[x][0] = WALL;
			tigerBoard[x][BOARD_SIZE + 1] = WALL;
		}
	}

	public SetPoint getPoint() {
		return setPoint;
	}

	public boolean match() {
		switch (turns) {
		case 3:
			tigerBoard[3][3] = BLACK;
			tigerBoard[4][4] = BLACK;
			tigerBoard[4][5] = BLACK;
			tigerBoard[5][4] = WHITE;
			tigerBoard[5][5] = BLACK;
			tigerBoard[5][6] = BLACK;
			tigerBoard[6][4] = WHITE;
			tigerPoint = new SetPoint(3, 4);
			for (int i = 0; i < 8; i++) {
				if (tigerEqual(currentBoard, s.convert(tigerBoard, i))) {
					setPoint = s.convertP(tigerPoint, i);
					return true;
				}
			}
			return false;
		case 4:
			tigerBoard[3][3] = BLACK;
			tigerBoard[3][4] = WHITE;
			tigerBoard[4][4] = WHITE;
			tigerBoard[4][5] = BLACK;
			tigerBoard[5][4] = WHITE;
			tigerBoard[5][5] = BLACK;
			tigerBoard[5][6] = BLACK;
			tigerBoard[6][4] = WHITE;
			tigerPoint = new SetPoint(4, 3);
			for (int i = 0; i < 8; i++) {
				if (tigerEqual(currentBoard, s.convert(tigerBoard, i))) {
					setPoint = s.convertP(tigerPoint, i);
					return true;
				}
			}
			return false;
		case 5:
			tigerBoard[3][3] = BLACK;
			tigerBoard[3][4] = WHITE;
			tigerBoard[4][3] = BLACK;
			tigerBoard[4][4] = BLACK;
			tigerBoard[4][5] = BLACK;
			tigerBoard[5][4] = WHITE;
			tigerBoard[5][5] = BLACK;
			tigerBoard[5][6] = BLACK;
			tigerBoard[6][4] = WHITE;
			tigerPoint = new SetPoint(4, 6);
			for (int i = 0; i < 8; i++) {
				if (tigerEqual(currentBoard, s.convert(tigerBoard, i))) {
					setPoint = s.convertP(tigerPoint, i);
					return true;
				}
			}
			return false;
		default:
			return false;
		}
	}

	private boolean tigerEqual(int[][] currentBoard, int[][] convertBoard) {
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
