package reversiai;
import java.util.ArrayList;

import boardsystem.BoardState;
import boardsystem.SetPoint;

public class AIRandom {
	private SetPoint aiRPoint;

	public AIRandom(BoardState board) {
		if (board.getMovablePos().size() != 0) {
			ArrayList<SetPoint> poslist = board.getMovablePos();

			// 打てる箇所が一カ所だけなら探索は行わず、即座に打って返る
			if (poslist.size() == 1) {
				aiRPoint = poslist.get(0);
				return;
			}

			// 置く場所をランダムに決める
			int ran = (int) (Math.random() * poslist.size());
			aiRPoint = poslist.get(ran);
		} else {
			// パスorゲームエンド
			return;
		}
	}

	// AIの置く座標を返す
	public String getPoint() {
		// 入力座標、英字+数字に変換,表示
		if (aiRPoint != null) {
			return aiRPoint.toString();
		} else {
			//nullpointerを返す
			return null;
		}
	}
	
	public SetPoint getAIRandomPoint(){
		return aiRPoint;
	}
}
