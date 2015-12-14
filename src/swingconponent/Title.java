package swingconponent;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import boardsystem.BoardState;

public class Title extends JPanel {
	private static final long serialVersionUID = 1L;

	public static final int SQUARE = BoardGraphics.SQUARE;// マス目の幅
	public static final int MARGIN = BoardGraphics.MARGIN;// 余白の幅
	public static final int DELT = BoardGraphics.DELT;// マス目1つの余白の幅
	public static final int ACROSS = BoardGraphics.ACROSS;// ボードの右端と下端の位置

	private Dimension d = new Dimension(1280, 800);// 表示領域(解像度に合わせた)
	private Color darkGreen = new Color(0, 120, 90);// 深緑


	public Title() {
	}

	@Override
	public void paintComponent(Graphics g) {

		// オーバーライドする親クラスのメソッドの呼び出し
		super.paintComponent(g);

		// バックを緑に
		g.setColor(darkGreen);
		g.fillRect(0, 0, d.width, d.height);

		// マス目の描画
		g.setColor(Color.black);
		for (int i = 0; i <= BoardState.BOARD_SIZE; i++) {
			g.drawLine(i * SQUARE + MARGIN, MARGIN, i * SQUARE + MARGIN,
					BoardState.BOARD_SIZE * SQUARE + MARGIN);
			g.drawLine(MARGIN, i * SQUARE + MARGIN, BoardState.BOARD_SIZE
					* SQUARE + MARGIN, i * SQUARE + MARGIN);
		}

		// 盤面座標の表示(a~h, 1~8)
		g.setColor(Color.black);
		g.setFont(new Font("Arial Bold", Font.BOLD, 20));
		for (int i = 0; i < BoardState.BOARD_SIZE; i++) {
			g.drawString(String.valueOf((char) ('a' + i)), (MARGIN + 20) + i
					* SQUARE, MARGIN - 10);
			g.drawString("" + (i + 1), MARGIN - 20, (MARGIN + 30) + i * SQUARE);
		}

		// 文字表示
		String str_title = "HVRAI";
		g.setColor(Color.black);
		g.setFont(new Font("Arial Bold", Font.ITALIC, 40));
		g.drawString(str_title, ACROSS + 20, ACROSS);
		
		g.setColor(Color.white);
		drawStringOnBoard(g, "H\nV\nR\nA\nI");
		g.setColor(Color.black);
		g.setFont(new Font("Arial", Font.PLAIN, 35));
		drawStringOnBoard(g, " uman\n ersus\n eversi\n rtificial\n ntelligence");
	}

	private void drawStringOnBoard(Graphics g, String message) {
		int i = 1;
		int j = 0;
		int count = 0;
		while (true) {
			g.drawString(String.valueOf(message.charAt(count)), MARGIN + DELT
					+ SQUARE * j + 5, MARGIN - DELT + SQUARE * i);
			j++;
			if (message.charAt(count) == '\n') {
				i++;
				j = 0;
			}
			count++;
			if (count >= message.length()) {
				break;
			}
		}
	}
}