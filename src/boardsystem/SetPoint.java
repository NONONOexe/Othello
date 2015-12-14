package boardsystem;
public class SetPoint {
	private int x;
	private int y;

	// 英字+数字の座標を(x,y)に変換
	public SetPoint(String in) {
		x = in.charAt(0) - 'a' + 1;
		y = in.charAt(1) - '1' + 1;
	}

	// 引数そのままで評価(CUIBoardPrintなどでloopするときに使用)
	public SetPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}

	// (x,y)座標を配列で取得、[0]=x座標、[1]=y座標
	public int[] getXY() {
		int[] xy = { x, y };
		return xy;
	}

	// コードに変換
	public String toString() {
		String coord = new String();
		coord += (char) ('a' + x - 1);
		coord += (char) ('1' + y - 1);

		return coord;
	}
}
