package books;

import reversiai.AIRandom;
import boardsystem.BoardState;
import boardsystem.SetPoint;

public class RabitBook {
	private BoardState board;
	private SetPoint setPoint;
	private int turns;
	private int currentBoard[][] = new int[10][10];
	private int rabitBoard[][] = new int[10][10];
	private SetPoint rabitPoint;// 兎定石:通常
	private SetPoint horsePoint;// 兎派生:馬定石
	private SetPoint tigerPoint;// 虎定石
	private Spiner s = new Spiner();// 回転クラス
	public static final int BLACK = 1;// 黒石
	public static final int EMPTY = 0;// 石なし
	public static final int WHITE = -1;// 白石
	public static final int WALL = 2;// 周りの壁
	public static final int BOARD_SIZE = BoardState.BOARD_SIZE;

	public RabitBook(BoardState board) {
		this.board = board;
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
				rabitBoard[x][y] = EMPTY;
			}
		}

		// 壁の設定
		for (int y = 0; y < BOARD_SIZE + 2; y++) {
			rabitBoard[0][y] = WALL;
			rabitBoard[BOARD_SIZE + 1][y] = WALL;
		}
		for (int x = 0; x < BOARD_SIZE + 2; x++) {
			rabitBoard[x][0] = WALL;
			rabitBoard[x][BOARD_SIZE + 1] = WALL;
		}
	}

	public SetPoint getPoint() {
		return setPoint;
	}

	public boolean match() {
		AIRandom aiR = new AIRandom(board);
		switch (turns) {
		case 0:
			setPoint = aiR.getAIRandomPoint();
			return true;
		case 1:
			setPoint = aiR.getAIRandomPoint();
			return true;
		case 2:
			rabitBoard[4][4] = WHITE;
			rabitBoard[4][5] = BLACK;
			rabitBoard[5][4] = WHITE;
			rabitBoard[5][5] = BLACK;
			rabitBoard[5][6] = BLACK;
			rabitBoard[6][4] = WHITE;
			rabitPoint = new SetPoint(5, 3);
			tigerPoint = new SetPoint(3, 3);
			for (int i = 0; i < 8; i++) {
				if (rabitEqual(currentBoard, s.convert(rabitBoard, i))) {
					int r = (int) (Math.random() * 2);
					if (r == 0) {
						setPoint = s.convertP(rabitPoint, i);
					} else if (r == 1) {
						setPoint = s.convertP(tigerPoint, i);
					}
					return true;
				}
			}
			return false;
		case 3:
			rabitBoard[4][4] = WHITE;
			rabitBoard[4][5] = BLACK;
			rabitBoard[5][3] = BLACK;
			rabitBoard[5][4] = BLACK;
			rabitBoard[5][5] = BLACK;
			rabitBoard[5][6] = BLACK;
			rabitBoard[6][4] = WHITE;
			rabitPoint = new SetPoint(4, 6);
			for (int i = 0; i < 8; i++) {
				if (rabitEqual(currentBoard, s.convert(rabitBoard, i))) {
					setPoint = s.convertP(rabitPoint, i);
					return true;
				}
			}
			return false;
		case 4:
			rabitBoard[4][4] = WHITE;
			rabitBoard[4][5] = WHITE;
			rabitBoard[4][6] = WHITE;
			rabitBoard[5][3] = BLACK;
			rabitBoard[5][4] = BLACK;
			rabitBoard[5][5] = WHITE;
			rabitBoard[5][6] = BLACK;
			rabitBoard[6][4] = WHITE;
			rabitPoint = new SetPoint(3, 5);
			horsePoint = new SetPoint(3, 4);
			for (int i = 0; i < 8; i++) {
				if (rabitEqual(currentBoard, s.convert(rabitBoard, i))) {
					int r = (int) (Math.random() * 2);
					if (r == 0) {
						setPoint = s.convertP(rabitPoint, i);
					} else if (r == 1) {
						setPoint = s.convertP(horsePoint, i);
					}
					return true;
				}
			}
			return false;
		case 5:
			rabitBoard[3][5] = BLACK;
			rabitBoard[4][4] = BLACK;
			rabitBoard[4][5] = WHITE;
			rabitBoard[4][6] = WHITE;
			rabitBoard[5][3] = BLACK;
			rabitBoard[5][4] = BLACK;
			rabitBoard[5][5] = WHITE;
			rabitBoard[5][6] = BLACK;
			rabitBoard[6][4] = WHITE;
			rabitPoint = new SetPoint(6, 3);
			for (int i = 0; i < 8; i++) {
				if (rabitEqual(currentBoard, s.convert(rabitBoard, i))) {
					setPoint = s.convertP(rabitPoint, i);
					return true;
				}
			}
			return false;
		case 6:
			rabitBoard[3][5] = BLACK;
			rabitBoard[4][4] = BLACK;
			rabitBoard[4][5] = WHITE;
			rabitBoard[4][6] = WHITE;
			rabitBoard[5][3] = BLACK;
			rabitBoard[5][4] = WHITE;
			rabitBoard[5][5] = WHITE;
			rabitBoard[5][6] = BLACK;
			rabitBoard[6][3] = WHITE;
			rabitBoard[6][4] = WHITE;
			rabitPoint = new SetPoint(3, 4);
			for (int i = 0; i < 8; i++) {
				if (rabitEqual(currentBoard, s.convert(rabitBoard, i))) {
					setPoint = s.convertP(rabitPoint, i);
					return true;
				}
			}
			return false;
		case 7:
			rabitBoard[3][4] = BLACK;
			rabitBoard[3][5] = BLACK;
			rabitBoard[4][4] = BLACK;
			rabitBoard[4][5] = BLACK;
			rabitBoard[4][6] = WHITE;
			rabitBoard[5][3] = BLACK;
			rabitBoard[5][4] = WHITE;
			rabitBoard[5][5] = WHITE;
			rabitBoard[5][6] = BLACK;
			rabitBoard[6][3] = WHITE;
			rabitBoard[6][4] = WHITE;
			rabitPoint = new SetPoint(6, 6);
			for (int i = 0; i < 8; i++) {
				if (rabitEqual(currentBoard, s.convert(rabitBoard, i))) {
					setPoint = s.convertP(rabitPoint, i);
					return true;
				}
			}
			return false;
		case 8:
			rabitBoard[3][4] = BLACK;
			rabitBoard[3][5] = BLACK;
			rabitBoard[4][4] = BLACK;
			rabitBoard[4][5] = BLACK;
			rabitBoard[4][6] = WHITE;
			rabitBoard[5][3] = BLACK;
			rabitBoard[5][4] = WHITE;
			rabitBoard[5][5] = WHITE;
			rabitBoard[5][6] = WHITE;
			rabitBoard[6][3] = WHITE;
			rabitBoard[6][4] = WHITE;
			rabitBoard[6][6] = WHITE;
			rabitPoint = new SetPoint(6, 5);
			for (int i = 0; i < 8; i++) {
				if (rabitEqual(currentBoard, s.convert(rabitBoard, i))) {
					setPoint = s.convertP(rabitPoint, i);
					return true;
				}
			}
			return false;
		case 9:
			rabitBoard[3][4] = BLACK;
			rabitBoard[3][5] = BLACK;
			rabitBoard[4][4] = BLACK;
			rabitBoard[4][5] = BLACK;
			rabitBoard[4][6] = WHITE;
			rabitBoard[5][3] = BLACK;
			rabitBoard[5][4] = WHITE;
			rabitBoard[5][5] = BLACK;
			rabitBoard[5][6] = WHITE;
			rabitBoard[6][3] = WHITE;
			rabitBoard[6][4] = WHITE;
			rabitBoard[6][5] = BLACK;
			rabitBoard[6][6] = WHITE;
			rabitPoint = new SetPoint(7, 4);// fローズ
			for (int i = 0; i < 8; i++) {
				if (rabitEqual(currentBoard, s.convert(rabitBoard, i))) {
					setPoint = s.convertP(rabitPoint, i);
					return true;
				}
			}
			return false;
		default:
			return false;
		}
	}

	private boolean rabitEqual(int[][] currentBoard, int[][] convertBoard) {
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
