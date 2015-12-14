package books;

import boardsystem.BoardState;
import boardsystem.SetPoint;

public class MouseBook {
	private SetPoint setPoint;
	private int turns;
	private int currentBoard[][] = new int[10][10];
	private int mouseBoard[][] = new int[10][10];
	private SetPoint mousePoint;
	private Spiner s = new Spiner();
	public static final int BLACK = 1;// 黒石
	public static final int EMPTY = 0;// 石なし
	public static final int WHITE = -1;// 白石
	public static final int WALL = 2;// 周りの壁
	public static final int BOARD_SIZE = BoardState.BOARD_SIZE;

	public MouseBook(BoardState board) {
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
				mouseBoard[x][y] = EMPTY;
			}
		}

		// 壁の設定
		for (int y = 0; y < BOARD_SIZE + 2; y++) {
			mouseBoard[0][y] = WALL;
			mouseBoard[BOARD_SIZE + 1][y] = WALL;
		}
		for (int x = 0; x < BOARD_SIZE + 2; x++) {
			mouseBoard[x][0] = WALL;
			mouseBoard[x][BOARD_SIZE + 1] = WALL;
		}
	}

	public SetPoint getPoint() {
		return setPoint;
	}

	public boolean match() {
		switch (turns) {
		case 2:
			mouseBoard[4][4] = WHITE;
			mouseBoard[4][5] = WHITE;
			mouseBoard[4][6] = WHITE;
			mouseBoard[5][4] = BLACK;
			mouseBoard[5][5] = BLACK;
			mouseBoard[5][6] = BLACK;
			mousePoint = new SetPoint(3, 5);
			for (int i = 0; i < 8; i++) {
				if (mouseEqual(currentBoard, s.convert(mouseBoard, i))) {
					setPoint = s.convertP(mousePoint, i);
					return true;
				}
			}
			return false;
		case 3:
			mouseBoard[3][5] = BLACK;
			mouseBoard[4][4] = WHITE;
			mouseBoard[4][5] = BLACK;
			mouseBoard[4][6] = WHITE;
			mouseBoard[5][4] = BLACK;
			mouseBoard[5][5] = BLACK;
			mouseBoard[5][6] = BLACK;
			mousePoint = new SetPoint(6, 6);
			for (int i = 0; i < 8; i++) {
				if (mouseEqual(currentBoard, s.convert(mouseBoard, i))) {
					setPoint = s.convertP(mousePoint, i);
					return true;
				}
			}
			return false;
		case 4:
			mouseBoard[3][5] = BLACK;
			mouseBoard[4][4] = WHITE;
			mouseBoard[4][5] = BLACK;
			mouseBoard[4][6] = WHITE;
			mouseBoard[5][4] = BLACK;
			mouseBoard[5][5] = WHITE;
			mouseBoard[5][6] = WHITE;
			mouseBoard[6][6] = WHITE;
			mousePoint = new SetPoint(3, 4);
			for (int i = 0; i < 8; i++) {
				if (mouseEqual(currentBoard, s.convert(mouseBoard, i))) {
					setPoint = s.convertP(mousePoint, i);
					return true;
				}
			}
			return false;
		case 5:
			mouseBoard[3][4] = BLACK;
			mouseBoard[3][5] = BLACK;
			mouseBoard[4][4] = BLACK;
			mouseBoard[4][5] = BLACK;
			mouseBoard[4][6] = WHITE;
			mouseBoard[5][4] = BLACK;
			mouseBoard[5][5] = WHITE;
			mouseBoard[5][6] = WHITE;
			mouseBoard[6][6] = WHITE;
			mousePoint = new SetPoint(5, 3);
			for (int i = 0; i < 8; i++) {
				if (mouseEqual(currentBoard, s.convert(mouseBoard, i))) {
					setPoint = s.convertP(mousePoint, i);
					return true;
				}
			}
			return false;
		case 6:
			mouseBoard[3][4] = BLACK;
			mouseBoard[3][5] = BLACK;
			mouseBoard[4][4] = BLACK;
			mouseBoard[4][5] = BLACK;
			mouseBoard[4][6] = WHITE;
			mouseBoard[5][3] = WHITE;
			mouseBoard[5][4] = WHITE;
			mouseBoard[5][5] = WHITE;
			mouseBoard[5][6] = WHITE;
			mouseBoard[6][6] = WHITE;
			mousePoint = new SetPoint(6, 4);
			for (int i = 0; i < 8; i++) {
				if (mouseEqual(currentBoard, s.convert(mouseBoard, i))) {
					setPoint = s.convertP(mousePoint, i);
					return true;
				}
			}
			return false;
		case 7:
			mouseBoard[3][4] = BLACK;
			mouseBoard[3][5] = BLACK;
			mouseBoard[4][4] = BLACK;
			mouseBoard[4][5] = BLACK;
			mouseBoard[4][6] = WHITE;
			mouseBoard[5][3] = WHITE;
			mouseBoard[5][4] = BLACK;
			mouseBoard[5][5] = WHITE;
			mouseBoard[5][6] = WHITE;
			mouseBoard[6][4] = BLACK;
			mouseBoard[6][6] = WHITE;
			mousePoint = new SetPoint(4, 3);
			for (int i = 0; i < 8; i++) {
				if (mouseEqual(currentBoard, s.convert(mouseBoard, i))) {
					setPoint = s.convertP(mousePoint, i);
					return true;
				}
			}
			return false;
		case 8:
			mouseBoard[3][4] = BLACK;
			mouseBoard[3][5] = BLACK;
			mouseBoard[4][3] = WHITE;
			mouseBoard[4][4] = WHITE;
			mouseBoard[4][5] = WHITE;
			mouseBoard[4][6] = WHITE;
			mouseBoard[5][3] = WHITE;
			mouseBoard[5][4] = BLACK;
			mouseBoard[5][5] = WHITE;
			mouseBoard[5][6] = WHITE;
			mouseBoard[6][4] = BLACK;
			mouseBoard[6][6] = WHITE;
			mousePoint = new SetPoint(6, 5);
			for (int i = 0; i < 8; i++) {
				if (mouseEqual(currentBoard, s.convert(mouseBoard, i))) {
					setPoint = s.convertP(mousePoint, i);
					return true;
				}
			}
			return false;
		default:
			return false;
		}
	}

	private boolean mouseEqual(int[][] currentBoard, int[][] convertBoard) {
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
