package reversiai;

import java.util.ArrayList;

import boardsystem.BoardState;
import boardsystem.SetPoint;
import books.CowBook;
import books.HorseBook;
import books.MouseBook;
import books.RabitBook;
import books.TigerBook;

public class Unknown {
	private SetPoint aiUPoint;
	private BoardState board;

	public Unknown(BoardState board, int limit) {
		this.board = board;
		int eval;// 評価値
		int eval_max = -5000;// 最大評価値(最小を-100としている)
		int alpha = -5000;
		int beta = 5000;
		RabitBook rabit = new RabitBook(board);
		TigerBook tiger = new TigerBook(board);
		MouseBook mouse = new MouseBook(board);
		CowBook cow = new CowBook(board);
		HorseBook horse = new HorseBook(board);

		board.makeMovablePosAI();// 打てる場所を数え上げ
		ArrayList<SetPoint> pos = board.getMovablePosAI();

		if (rabit.match()) {
			aiUPoint = rabit.getPoint();
		} else if (tiger.match()) {
			aiUPoint = tiger.getPoint();
		} else if (mouse.match()) {
			aiUPoint = mouse.getPoint();
		} else if (cow.match()) {
			aiUPoint = cow.getPoint();
		} else if (horse.match()) {
			aiUPoint = horse.getPoint();
		} else {
			for (int i = 0; i < pos.size(); i++) {
				if (!board.move(pos.get(i))) {
					System.err.println("\nAIMiniMax:異常検出");
					System.err.println("強制終了します");
					System.exit(0);
				}
				eval = yourturn(board, limit - 1, alpha, beta);// 評価値を取得
				board.undo();
				board.makeMovablePosAI();// 打てる場所を数え上げ

				// 最大評価値の更新
				if (eval > eval_max) {
					aiUPoint = pos.get(i);
					eval_max = eval;
				}
			}
		}
	}

	// AIのターン
	private int myturn(BoardState board, int limit, int alpha, int beta) {
		// ゲーム終了か深さが指定した値に達したら、評価値を測る
		if (board.isGameOver() || limit == 0) {
			if (BoardState.MAX_TURNS - board.getTurns() <= 12) {
				// 終了5ターン以内なら、完全読み
				if (BoardState.MAX_TURNS - board.getTurns() <= 1) {
					Value myvalue = new Perfect();
					int m = myvalue.valueMain(board);
					return m;
				} else {
					// 10ターン以内なら必勝読み
					Value myvalue = new Victory();
					int m = myvalue.valueMain(board);
					return m;
				}
			} else {
				// それ以外はMovablePosを基準に測る
				Value myvalue = new EvalStrong();
				int m = myvalue.valueMain(board);
				return m;
			}
		}

		// 探索
		board.makeMovablePosAI();// 打てる場所を数え上げ
		ArrayList<SetPoint> pos = board.getMovablePosAI();
		int score;
		int score_max = -5000;

		if (pos.size() != 0) {
			for (int i = 0; i < pos.size(); i++) {
				board.move(pos.get(i));
				score = yourturn(board, limit - 1, alpha, beta);
				board.undo();
				board.makeMovablePosAI();// 打てる場所を数え上げ

				// betaより大きい評価だったら
				if (score >= beta) {
					return score;
				}
				
				// 最大スコア更新
				if (score > score_max) {
					score_max = score;
					alpha = Math.max(alpha, score_max);// 大きいほうをalpha値とする
				}
			}
		} else {
			board.pass();
			score = yourturn(board, limit, alpha, beta);
			board.undo();
			board.makeMovablePosAI();// 打てる場所を数え上げ
		}

		return score_max;
	}

	// 人間プレイヤーのターン
	private int yourturn(BoardState board, int limit, int alpha, int beta) {
		// ゲーム終了か深さが指定した値に達したら、評価値を測る
		if (board.isGameOver() || limit == 0) {
			if (BoardState.MAX_TURNS - board.getTurns() <= 12) {
				// 終了5ターン以内なら、完全読み
				if (BoardState.MAX_TURNS - board.getTurns() <= 1) {
					Value yourvalue = new Perfect();
					int y = yourvalue.valueMain(board);
					return y;
				} else {
					// 10ターン以内なら必勝読み
					Value yourvalue = new Victory();
					int y = yourvalue.valueMain(board);
					return y;
				}
			} else {
				// それ以外はMovablePosを基準に測る
				Value yourvalue = new EvalStrong();
				int y = yourvalue.valueMain(board);
				return y;
			}
		}

		// 探索
		board.makeMovablePosAI();// 打てる場所を数え上げ
		ArrayList<SetPoint> pos = board.getMovablePosAI();
		int score;
		int score_min = 5000;

		if (pos.size() != 0) {
			for (int i = 0; i < pos.size(); i++) {
				board.move(pos.get(i));
				score = myturn(board, limit - 1, alpha, beta);
				board.undo();
				board.makeMovablePosAI();// 打てる場所を数え上げ

				// alphaより大きい評価だったら
				if (score <= alpha) {
					return score;
				}
				
				// 最小スコア更新
				if (score < score_min) {
					score_min = score;
					beta = Math.min(beta, score_min);// 小さいほうをbeta値とする
				}
			}
		} else {
			board.pass();
			score = myturn(board, limit, alpha, beta);
			board.undo();
			board.makeMovablePosAI();// 打てる場所を数え上げ
		}

		return score_min;
	}

	// AIの置く座標を返す
	public String getPoint() {
		String p;
		// 入力座標、英字+数字に変換,表示
		if (aiUPoint != null) {
			p = aiUPoint.toString();
		} else {
			// 全ての手が最悪手の場合、ランダムを打つ
			AIRandom aiR = new AIRandom(board);
			p = aiR.getPoint();
		}
		return p;
	}
}
