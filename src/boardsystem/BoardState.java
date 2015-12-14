package boardsystem;

import java.util.ArrayList;

public class BoardState {
	// 定数
	public static final int BLACK = 1;// 黒石
	public static final int EMPTY = 0;// 石なし
	public static final int WHITE = -1;// 白石
	public static final int WALL = 2;// 周りの壁
	public static final int BOARD_SIZE = 8;// マス目の数(8*8=64)
	public static final int MAX_TURNS = 60;// 最大ターン数

	// 変数
	protected int RawBoard[][] = new int[BOARD_SIZE + 2][BOARD_SIZE + 2];// ボードの配列(+2はWALL)
	protected int Turns;// 手数(0からはじまる)
	protected int CurrentColor;// 現在のプレイヤーの色

	// データ
	protected ArrayList<SetPoint> MovablePos = new ArrayList<SetPoint>(60);// 置ける場所をlistにしてコレクション,置ける場所60手まで記録可能
	protected ArrayList<SetPoint> MovablePosAI = new ArrayList<SetPoint>(60);// 置ける場所をlistにしてコレクション,置ける場所60手まで記録可能
	private ArrayList<ArrayList<SetPoint>> UpdateLog = new ArrayList<ArrayList<SetPoint>>(
			100);// パスを含むため100ターン記録可能

	public BoardState() {
		restart();
	}

	// 石を置いて、置けるならtrue
	public boolean move(SetPoint p) {
		int x = p.getXY()[0];
		int y = p.getXY()[1];
		// 座標がボード外なら、false
		if (x <= 0 || x > BOARD_SIZE)
			return false;
		if (y <= 0 || y > BOARD_SIZE)
			return false;

		// ひっくり返せないのでfalse
		if (!checkFlip(p, CurrentColor))
			return false;

		// ひっくり返せるので石を置く
		RawBoard[x][y] = CurrentColor;

		// 置いてひっくり返す
		flipDiscs(p);

		Turns++;
		CurrentColor = -1 * CurrentColor;

		// 置ける場所をコレクション
		makeMovablePos();

		return true;
	}

	// 指定した座標に石を置き、ひっくり返す
	private void flipDiscs(SetPoint p) {
		int x = p.getXY()[0];
		int y = p.getXY()[1];
		int[] vec_x = { 0, 1, 1, 1, 0, -1, -1, -1 };
		int[] vec_y = { -1, -1, 0, 1, 1, 1, 0, -1 };
		// 置いた石を記録
		ArrayList<SetPoint> update = new ArrayList<SetPoint>();
		update.add(p);

		// 8方向調べる
		for (int vec = 0; vec < BOARD_SIZE; vec++) {
			int flipcount = 0;// 指定方向にあるひっくり返せる相手の石の数

			// ベクトル方向が相手の色でなければ、continueして方向を変える
			if (RawBoard[x + vec_x[vec]][y + vec_y[vec]] != -1 * CurrentColor) {
				continue;
			}
			// ベクトル方向のマスを検索する。searchのマスを調べる
			int distance = 0;
			while (true) {
				distance++;
				int search = RawBoard[x + distance * vec_x[vec]][y + distance
						* vec_y[vec]];

				if (search == WALL || search == EMPTY)
					break;// 壁もしくは空マスなら検索終了。方向変え

				if (search == -1 * CurrentColor) {
					flipcount++;
					continue;// 相手の色なら、次のマスを調べる
				}

				if (search == CurrentColor) {
					// ベクトル方向flips個目の石をひっくり返す
					for (int flips = 1; flips <= flipcount; flips++) {
						RawBoard[x + flips * vec_x[vec]][y + flips * vec_y[vec]] = CurrentColor;
						// ひっくり返した石を記録
						SetPoint up = new SetPoint(x + flips * vec_x[vec], y
								+ flips * vec_y[vec]);
						update.add(up);
					}
					break;
				}
				// 石、壁、空き以外が配列に入っている
				System.err.println("配列要素に異常があります:flipDiscs");
			}
		}
		// ログに追記
		UpdateLog.add(update);
	}

	// ひっくり返せるならtrue
	private boolean checkFlip(SetPoint p, int color) {
		int x = p.getXY()[0];
		int y = p.getXY()[1];
		int[] vec_x = { 0, 1, 1, 1, 0, -1, -1, -1 };
		int[] vec_y = { -1, -1, 0, 1, 1, 1, 0, -1 };

		// 引数colorの値が正しくない
		if (color < -1 || 2 < color) {
			System.err.println("colorの値に異常があります:checkFlip");
			return false;
		}

		// 指定場所にすでに石がある
		if (RawBoard[x][y] != EMPTY)
			return false;

		for (int vec = 0; vec < BOARD_SIZE; vec++) {
			// vec方向が相手の色でなければ、continueして方向を変える
			if (RawBoard[x + vec_x[vec]][y + vec_y[vec]] != -1 * color)
				continue;

			// vec方向のマスを8マス検索する。searchのマスを調べる
			for (int d = 1; d <= BOARD_SIZE; d++) {
				int search = RawBoard[x + d * vec_x[vec]][y + d * vec_y[vec]];
				if (search == WALL || search == EMPTY)
					break;// 壁もしくは空マスなら検索終了。方向変え
				if (search == -1 * color)
					continue;// 相手の色なら、次のマスを調べる
				if (search == color)
					return true;// 自分の色で挟んでいるのでtrue

				// それ以外が配列に入っている
				System.err.println("配列要素に異常があります:flipDiscs");
			}
		}
		return false;// 8方向に相手の色がないので、ひっくり返せない
	}

	// MovablePosの書き換え
	private void makeMovablePos() {
		MovablePos.clear();// リセット
		SetPoint p;
		// 全マスを検索
		for (int x = 1; x <= BOARD_SIZE; x++) {
			for (int y = 1; y <= BOARD_SIZE; y++) {
				p = new SetPoint(x, y);
				if (checkFlip(p, CurrentColor))
					MovablePos.add(p);// 置ける場所があればMovablePosに追加
			}
		}
	}

	// MovablePosの書き換え
	public void makeMovablePosAI() {
		MovablePosAI.clear();// リセット
		SetPoint p;
		// 全マスを検索
		for (int x = 1; x <= BOARD_SIZE; x++) {
			for (int y = 1; y <= BOARD_SIZE; y++) {
				p = new SetPoint(x, y);
				if (checkFlip(p, CurrentColor))
					MovablePosAI.add(p);// 置ける場所があればMovablePosに追加
			}
		}
	}

	// ゲーム終了ならばtrueを返す
	public boolean isGameOver() {
		// pass();
		if (Turns == MAX_TURNS)
			return true;// 60ターン目なら即終了
		if (MovablePos.size() != 0)
			return false;// 置ける場があるなら続行

		int playcolor = -1 * CurrentColor;// 相手が打てるか調べる
		// 全マスを検索
		for (int x = 1; x <= BOARD_SIZE; x++) {
			for (int y = 1; y <= BOARD_SIZE; y++) {
				if (checkFlip(new SetPoint(x, y), playcolor)) {
					return false;// 置ける場所が1つでもあれば続行
				}
			}
		}

		return true;// 置ける場所が1つもないのでゲーム終了
	}

	// 指定した要素が配列にいくつあるか数える(elements=WALL,BLACK,EMPTY,WHITEのいずれか)
	public int countDisc(int elements) {
		int count = 0;
		// 全配列を検索
		for (int x = 0; x < BOARD_SIZE + 2; x++) {
			for (int y = 0; y < BOARD_SIZE + 2; y++) {
				if (RawBoard[x][y] == elements) {
					count++;// 引数の要素と同じものがあればcountを1増やす
				}
			}
		}
		return count;
	}

	// パスするしかないならtrue
	public void pass() {
		int poscount = 0;

		// 自分の打つ場所がない
		if (MovablePos.size() != 0)
			return;
		// 相手の打てる場所があるかを全マス検索
		for (int x = 1; x <= BOARD_SIZE; x++) {
			for (int y = 1; y <= BOARD_SIZE; y++) {
				if (checkFlip(new SetPoint(x, y), -1 * CurrentColor))
					poscount++;
			}
		}
		// 相手も置く場所がない
		if (poscount == 0)
			return;
		// 空リストを追記
		UpdateLog.add(new ArrayList<SetPoint>());
		// ターン交代
		CurrentColor = -1 * CurrentColor;
		// MovablePosのリセット
		makeMovablePos();
		return;
	}

	// 指定した座標になにがあるかを返す
	public int getColor(SetPoint p) {
		return RawBoard[p.getXY()[0]][p.getXY()[1]];
	}

	// 現在のターン数を返す
	public int getTurns() {
		return Turns;
	}

	// 現在のプレイヤーの色を返す
	public int getCurrentColor() {
		return CurrentColor;
	}

	// MovablePosを返す
	public ArrayList<SetPoint> getMovablePos() {
		return MovablePos;
	}

	// MovablePosを返す
	public ArrayList<SetPoint> getMovablePosAI() {
		return MovablePosAI;
	}

	public void undo() {
		if (Turns == 0) {
			System.out.println("\nこれ以上は戻れません!");
			return;
		}

		// 色交代
		CurrentColor = -1 * CurrentColor;

		// 前回のターンを更新情報から削除、削除したターンをローカルに代入
		ArrayList<SetPoint> update = UpdateLog.remove(UpdateLog.size() - 1);

		// パスの場合
		if (update.isEmpty()) {
			// MovablePosをリセット
			makeMovablePos();
		} else {
			// ターンを戻す
			Turns--;

			// 置いた石を戻す
			SetPoint pzero = update.get(0);
			RawBoard[pzero.getXY()[0]][pzero.getXY()[1]] = EMPTY;

			// ひっくり返した石を戻す
			for (int i = 1; i < update.size(); i++) {
				SetPoint p = update.get(i);
				RawBoard[p.getXY()[0]][p.getXY()[1]] = -1 * CurrentColor;
			}

			// 置ける場所をコレクション
			makeMovablePos();
		}
	}

	public void restart() {
		// 配列の初期化
		// 全ますを空マスに設定
		for (int x = 1; x <= BOARD_SIZE; x++) {
			for (int y = 1; y <= BOARD_SIZE; y++) {
				RawBoard[x][y] = EMPTY;
			}
		}

		// 壁の設定
		for (int y = 0; y < BOARD_SIZE + 2; y++) {
			RawBoard[0][y] = WALL;
			RawBoard[BOARD_SIZE + 1][y] = WALL;
		}
		for (int x = 0; x < BOARD_SIZE + 2; x++) {
			RawBoard[x][0] = WALL;
			RawBoard[x][BOARD_SIZE + 1] = WALL;
		}

		// 初期配置
		RawBoard[4][4] = WHITE;
		RawBoard[5][5] = WHITE;
		RawBoard[4][5] = BLACK;
		RawBoard[5][4] = BLACK;

		Turns = 0;// 手数は0から数える
		CurrentColor = BLACK;// 先手は黒

		// 置ける場所をコレクション(初期状態での)
		makeMovablePos();
	}

	public ArrayList<SetPoint> getUpdate() {
		ArrayList<SetPoint> update;
		if (UpdateLog.size() != 0) {
			update = UpdateLog.get(UpdateLog.size() - 1);
		} else {
			update = null;
		}
		return update;
	}

	public int[][] getRawBoard() {
		return RawBoard;
	}

	// 角の数を返す
	public int countCorner() {
		SetPoint corner[] = new SetPoint[4];
		int count = 0;
		corner[0] = new SetPoint(1, 1);
		corner[1] = new SetPoint(1, 8);
		corner[2] = new SetPoint(8, 1);
		corner[3] = new SetPoint(8, 8);
		for (int i = 0; i < corner.length; i++) {
			if (getColor(corner[i]) == -1 * getCurrentColor()) {
				count++;
			}
		}
		return count;
	}

	// 角のとなりの数を返す
	public int countCornerAround() {
		SetPoint corneraround[] = new SetPoint[8];
		int count = 0;
		corneraround[0] = new SetPoint(1, 2);
		corneraround[1] = new SetPoint(1, 7);
		corneraround[2] = new SetPoint(2, 1);
		corneraround[3] = new SetPoint(2, 8);
		corneraround[4] = new SetPoint(7, 1);
		corneraround[5] = new SetPoint(7, 8);
		corneraround[6] = new SetPoint(8, 2);
		corneraround[7] = new SetPoint(8, 7);
		for (int i = 0; i < corneraround.length; i++) {
			if (getColor(corneraround[i]) == -1 * getCurrentColor()) {
				count++;
			}
		}
		return count;
	}

	// 星の数を返す
	public int countStar() {
		SetPoint star[] = new SetPoint[4];
		int count = 0;
		star[0] = new SetPoint(2, 2);
		star[1] = new SetPoint(2, 7);
		star[2] = new SetPoint(7, 2);
		star[3] = new SetPoint(7, 7);
		for (int i = 0; i < star.length; i++) {
			if (getColor(star[i]) == -1 * getCurrentColor()) {
				count++;
			}
		}
		return count;
	}

	// 山の数を数える　○●●●●●●○
	public int countMt(int color) {
		int mount[] = new int[8];
		mount[0] = 0;
		mount[1] = 1;
		mount[2] = 1;
		mount[3] = 1;
		mount[4] = 1;
		mount[5] = 1;
		mount[6] = 1;
		mount[7] = 0;

		SetPoint edge[][] = new SetPoint[4][8];
		for (int i = 0; i < 8; i++) {
			edge[0][i] = new SetPoint(1, i + 1);
		}
		for (int i = 0; i < 8; i++) {
			edge[1][i] = new SetPoint(8, i + 1);
		}
		for (int i = 0; i < 8; i++) {
			edge[2][i] = new SetPoint(i + 1, 1);
		}
		for (int i = 0; i < 8; i++) {
			edge[3][i] = new SetPoint(i + 1, 8);
		}

		int count = 0;

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 8; j++) {
				if (getColor(edge[i][j]) != mount[j] * color) {
					break;
				} else if (j == 7) {
					count++;
				}
			}
		}

		return count;
	}

	// ブロックの数を数える　○○●●●●○○
	public int countBlock(int color) {
		int block[] = new int[8];
		block[0] = 0;
		block[1] = 0;
		block[2] = 1;
		block[3] = 1;
		block[4] = 1;
		block[5] = 1;
		block[6] = 0;
		block[7] = 0;

		SetPoint edge[][] = new SetPoint[4][8];
		for (int i = 0; i < 8; i++) {
			edge[0][i] = new SetPoint(1, i + 1);
		}
		for (int i = 0; i < 8; i++) {
			edge[1][i] = new SetPoint(8, i + 1);
		}
		for (int i = 0; i < 8; i++) {
			edge[2][i] = new SetPoint(i + 1, 1);
		}
		for (int i = 0; i < 8; i++) {
			edge[3][i] = new SetPoint(i + 1, 8);
		}

		int count = 0;

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 8; j++) {
				if (getColor(edge[i][j]) != block[j] * color) {
					break;
				} else if (j == 7) {
					count++;
				}
			}
		}

		return count;
	}

	// ウィングの数を数える　○●●●●●○○ ○○●●●●●○
	public int countWing(int color) {
		int wing[][] = new int[2][8];
		wing[0][0] = 0;
		wing[0][1] = 1;
		wing[0][2] = 1;
		wing[0][3] = 1;
		wing[0][4] = 1;
		wing[0][5] = 1;
		wing[0][6] = 0;
		wing[0][7] = 0;

		wing[1][0] = 0;
		wing[1][1] = 0;
		wing[1][2] = 1;
		wing[1][3] = 1;
		wing[1][4] = 1;
		wing[1][5] = 1;
		wing[1][6] = 1;
		wing[1][7] = 0;

		SetPoint edge[][] = new SetPoint[4][8];
		for (int i = 0; i < 8; i++) {
			edge[0][i] = new SetPoint(1, i + 1);
		}
		for (int i = 0; i < 8; i++) {
			edge[1][i] = new SetPoint(8, i + 1);
		}
		for (int i = 0; i < 8; i++) {
			edge[2][i] = new SetPoint(i + 1, 1);
		}
		for (int i = 0; i < 8; i++) {
			edge[3][i] = new SetPoint(i + 1, 8);
		}

		int count = 0;

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 8; j++) {
				if (getColor(edge[i][j]) != wing[0][j] * color) {
					break;
				} else if (j == 7) {
					count++;
				}
			}
		}

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 8; j++) {
				if (getColor(edge[i][j]) != wing[1][j] * color) {
					break;
				} else if (j == 7) {
					count++;
				}
			}
		}

		return count;
	}
}