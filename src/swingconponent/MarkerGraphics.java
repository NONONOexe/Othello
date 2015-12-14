package swingconponent;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import boardsystem.BoardState;
import boardsystem.SetPoint;

public class MarkerGraphics extends JPanel {
	private static final long serialVersionUID = 1L;

	private BoardState board;

	public static final int SQUARE = 50;// マス目の幅
	public static final int MARGIN = 40;// 余白の幅
	public static final int DELT = 2;// マス目1つの余白の幅
	public static final int RAD = 46;// 石の直径
	public static final int ACROSS = MARGIN + SQUARE * BoardState.BOARD_SIZE;// ボードの右端と下端の位置

	private Color movableColor = new Color(50, 170, 140);// putGreen+30

	public MarkerGraphics(BoardState board) {
		this.board = board;
	}

	@Override
	public void paintComponent(Graphics g) {
		if (board != null) {
			// オーバーライドする親クラスのメソッドの呼び出し
			super.paintComponent(g);
			// マーカー表示
			ArrayList<SetPoint> movablePos = board.getMovablePos();
			for (int i = 0; i < movablePos.size(); i++) {
				SetPoint p = movablePos.get(i);
				int p_x = MARGIN + (p.getXY()[0] - 1) * SQUARE + DELT;
				int p_y = MARGIN + (p.getXY()[1] - 1) * SQUARE + DELT;
				g.setColor(movableColor);
				g.fillOval(p_x, p_y, RAD, RAD);
			}
		}
	}
}
