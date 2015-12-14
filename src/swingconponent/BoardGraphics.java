package swingconponent;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

import boardsystem.BoardState;
import boardsystem.SetPoint;

public class BoardGraphics extends JPanel {
	private static final long serialVersionUID = 1L;

	private BoardState board;
	private GUI gui;

	public static final int SQUARE = 50;// マス目の幅
	public static final int MARGIN = 40;// 余白の幅
	public static final int DELT = 2;// マス目1つの余白の幅
	public static final int RAD = 46;// 石の直径
	public static final int ACROSS = MARGIN + SQUARE * BoardState.BOARD_SIZE;// ボードの右端と下端の位置

	private Color darkGreen = new Color(0, 120, 90);// 深緑
	private Color putGreen = new Color(50, 170, 90);

	public BoardGraphics(BoardState board, Click bc, GUI gui) {
		this.board = board;
		this.addMouseListener(bc);
		this.gui = gui;
	}

	@Override
	public void paintComponent(Graphics g) {
		if (board != null) {
			// オーバーライドする親クラスのメソッドの呼び出し
			super.paintComponent(g);

			// バックを緑に
			g.setColor(darkGreen);
			super.setBackground(darkGreen);

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
				g.drawString(String.valueOf((char) ('a' + i)), (MARGIN + 20)
						+ i * SQUARE, MARGIN - 10);
				g.drawString("" + (i + 1), MARGIN - 20, (MARGIN + 30) + i
						* SQUARE);
			}

			// 置いた場所の表示
			if (board.getTurns() > 0) {
				if (board.getUpdate().size() != 0) {
					g.setColor(putGreen);
					SetPoint putPos = board.getUpdate().get(0);
					int put_x = MARGIN + (putPos.getXY()[0] - 1) * SQUARE
							+ DELT;
					int put_y = MARGIN + (putPos.getXY()[1] - 1) * SQUARE
							+ DELT;
					g.fillRect(put_x, put_y, SQUARE - DELT * 2, SQUARE - DELT
							* 2);
				}
			}

			// 石の描画
			for (int y = 1; y <= BoardState.BOARD_SIZE; y++) {
				for (int x = 1; x <= BoardState.BOARD_SIZE; x++) {
					switch (board.getColor(new SetPoint(x, y))) {
					case BoardState.BLACK:
						g.setColor(Color.black);
						g.fillOval(DELT + SQUARE * (x - 1) + MARGIN, DELT
								+ SQUARE * (y - 1) + MARGIN, RAD, RAD);
						break;
					case BoardState.WHITE:
						g.setColor(Color.white);
						g.fillOval(DELT + SQUARE * (x - 1) + MARGIN, DELT
								+ SQUARE * (y - 1) + MARGIN, RAD, RAD);
						break;
					default:
						break;
					}
				}
			}

			// 文字表示(ついでに矢印も)
			String str_title = "HVRAI";
			if (board.getCurrentColor() == 1) {
				g.setColor(Color.orange);
				int px[] = { ACROSS + 5, ACROSS + 25, ACROSS + 5 };
				int py[] = { ACROSS - 190, ACROSS - 180, ACROSS - 170 };
				g.fillPolygon(px, py, 3);

				g.setColor(Color.black);

			} else {
				g.setColor(Color.orange);
				int px[] = { ACROSS + 5, ACROSS + 25, ACROSS + 5 };
				int py[] = { ACROSS - 150, ACROSS - 140, ACROSS - 130 };
				g.fillPolygon(px, py, 3);

				g.setColor(Color.white);
			}
			g.setFont(new Font("Arial Bold", Font.ITALIC, 40));
			g.drawString(str_title, ACROSS + 20, ACROSS);

			// 石の数表示
			g.setColor(Color.black);
			g.fillOval(ACROSS + 30, ACROSS - 200, RAD - 10, RAD - 10);
			g.setFont(new Font("Arial", Font.PLAIN, 40));
			g.drawString(String.valueOf(board.countDisc(BoardState.BLACK)),
					ACROSS + 70, ACROSS - 170);

			g.setColor(Color.white);
			g.fillOval(ACROSS + 30, ACROSS - 160, RAD - 10, RAD - 10);
			g.setFont(new Font("Arial", Font.PLAIN, 40));
			g.drawString(String.valueOf(board.countDisc(BoardState.WHITE)),
					ACROSS + 70, ACROSS - 128);

			// リトライ数表示
			if (gui.getMode() == "1 PLAYER GAME") {
				if (gui.getAIColor() != 0) {
					int retry = gui.getRetry();
					for (int i = 0; i < retry; i++) {
						if (gui.getAIColor() == BoardState.BLACK) {
							g.setColor(Color.white);
						} else if (gui.getAIColor() == BoardState.WHITE) {
							g.setColor(Color.black);
						}
						g.fillOval(ACROSS + SQUARE * 4 + 20 * i, ACROSS - 30,
								RAD - 10, RAD - 10);
						g.setColor(darkGreen);
						g.drawOval(ACROSS + SQUARE * 4 + 20 * i, ACROSS - 30,
								RAD - 10, RAD - 10);
					}
				}
			}
		}
	}
}
