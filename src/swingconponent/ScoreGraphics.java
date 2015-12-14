package swingconponent;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class ScoreGraphics extends JPanel {
	private static final long serialVersionUID = 1L;

	public static final int SQUARE = BoardGraphics.SQUARE;// マス目の幅
	public static final int MARGIN = BoardGraphics.MARGIN;// 余白の幅
	public static final int DELT = BoardGraphics.DELT;// マス目1つの余白の幅
	public static final int RAD = BoardGraphics.RAD;// 石の直径
	public static final int ACROSS = BoardGraphics.ACROSS;// ボードの右端と下端の位置

	private int score;
	private int highScore;
	private int stage;
	private GUI gui;

	public ScoreGraphics(GUI gui) {
		this.gui = gui;
	}

	@Override
	public void paintComponent(Graphics g) {
		score = gui.getScore();
		g.setColor(Color.black);
		g.setFont(new Font("Arial Bold", Font.BOLD, 25));
		g.drawString("SCORE:" + score, MARGIN, ACROSS + SQUARE);

		highScore = gui.getHighScore();
		g.setColor(Color.black);
		g.setFont(new Font("Arial Bold", Font.BOLD, 25));
		g.drawString("HIGH SCORE:" + highScore, ACROSS, ACROSS + SQUARE);

		if (gui.getMode() == "1 PLAYER GAME") {
			stage = gui.getStage() + 1;
			g.setColor(Color.black);
			g.setFont(new Font("Arial Bold", Font.BOLD, 25));
			g.drawString("STAGE " + stage, ACROSS - SQUARE * 4, ACROSS + SQUARE);
		}
	}
}
