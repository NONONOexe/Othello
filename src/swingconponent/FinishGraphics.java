package swingconponent;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import boardsystem.BoardState;

public class FinishGraphics extends JPanel {
	private static final long serialVersionUID = 1L;

	public static final int SQUARE = BoardGraphics.SQUARE;// マス目の幅
	public static final int MARGIN = BoardGraphics.MARGIN;// 余白の幅
	public static final int DELT = BoardGraphics.DELT;// マス目1つの余白の幅
	public static final int ACROSS = BoardGraphics.ACROSS;// ボードの右端と下端の位置
	public static final int RAD = 46;// 石の直径

	private Dimension d = new Dimension(1280, 800);// 表示領域(解像度に合わせた)
	private Color darkGreen = new Color(0, 120, 90);// 深緑

	private BoardState board;
	private GUI gui;

	public FinishGraphics(BoardState board, GUI gui) {
		this.board = board;
		this.gui = gui;
	}

	public int getDif() {
		int bd = board.countDisc(BoardState.BLACK);
		int wd = board.countDisc(BoardState.WHITE);
		int dif = bd - wd;
		return dif;
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

		// 勝者表示
		int dif = getDif();// 正:黒勝ち
		int aicolor = gui.getAIColor();
		g.setFont(new Font("Arial Bold", Font.PLAIN, 40));
		if (dif != 0) {
			if (aicolor != 0) {// AIあり
				String message;
				if ((aicolor == BoardState.BLACK && dif > 0)
						|| (aicolor == BoardState.WHITE && dif < 0)) {// プレイヤーの負け
					if (gui.getRetry() != -1) {
						message = "YOU LOSE";
						System.out.println(gui.getRetry());
					} else {
						message = "YOU LOSEGAMEOVER";
					}
				} else {// プレイヤーの勝ち
					if (gui.getStage() != 11) {
						message = "YOU WIN!";
					} else {
						message = "YOU WIN!GAMEOVER";
					}
				}

				if (aicolor != BoardState.BLACK) {
					g.setColor(Color.black);
				} else {
					g.setColor(Color.white);
				}
				drawStringOnBoard(g, message);

			} else {// AIなし
				String message;
				if (dif > 0) {// 黒勝ち
					message = "BLACK\n  WIN!";
					g.setColor(Color.black);
					drawStringOnBoard(g, message);
				} else {// 白勝ち
					message = "WHITE\n  WIN!";
					g.setColor(Color.white);
					drawStringOnBoard(g, message);
				}
			}
		} else {// 引き分け
			g.setColor(Color.black);
			drawStringOnBoard(g, "DR");
			g.setColor(Color.white);
			drawStringOnBoard(g, "  AW");
		}

		// スコア表示
		g.setColor(Color.orange);
		drawStringOnBoard(g, "\n\n\n-SCORE-");
		g.setColor(Color.black);
		drawStringOnBoard(g,
				"\n\n\n\nBLACK:" + board.countDisc(BoardState.BLACK));
		g.setColor(Color.white);
		drawStringOnBoard(g,
				"\n\n\n\n\nWHITE:" + board.countDisc(BoardState.WHITE));
		int score = gui.getPieceScore();
		int totalscore = gui.getScore();
		if (gui.getMode() == "1 PLAYER GAME") {
			if (gui.getAIColor() != 0) {
				g.setColor(Color.orange);
				drawStringOnBoard(g, "\n\n\n\n\n\n" + score + "\n" + totalscore);
			}
		}

		// 石積み
		for (int i = 0; i < board.countDisc(BoardState.BLACK); i++) {
			g.setColor(Color.black);
			g.fillRect(ACROSS + SQUARE * 4 - 30, ACROSS - i * 6, 40, 5);
		}
		for (int i = 0; i < board.countDisc(BoardState.WHITE); i++) {
			g.setColor(Color.white);
			g.fillRect(ACROSS + SQUARE * 5 - 30, ACROSS - i * 6, 40, 5);
		}

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

	private void drawStringOnBoard(Graphics g, String message) {
		int i = 1;
		int j = 0;
		int count = 0;
		while (true) {
			g.drawString(String.valueOf(message.charAt(count)), MARGIN + DELT
					+ SQUARE * j + 5, MARGIN - DELT + SQUARE * i);
			j++;
			if (j > 7 || message.charAt(count) == '\n') {
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