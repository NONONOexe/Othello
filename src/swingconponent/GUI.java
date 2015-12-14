package swingconponent;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.Timer;

import reversiai.AIRandom;
import reversiai.Evaluater;
import reversiai.Formula;
import reversiai.Unknown;
import sound.BGM;
import sound.MoveSound;
import sound.VsUnknownBGM;
import boardsystem.BoardState;
import boardsystem.CUIPrint;
import boardsystem.SetPoint;

public class GUI implements ActionListener {
	// メインシステム
	private SetPoint setPoint;
	private BoardState board;

	// コンポーネント操作
	private Start start;
	private GameEnd gameEnd;
	private ReturnTitle returnTitle;

	// 音楽
	BGM bgm = new BGM();
	VsUnknownBGM unknownBGM = new VsUnknownBGM();

	// 入力座標
	private String in;

	// AI設定用変数
	private int aiColor;
	private int aiSpeed = 1000;
	private int aiLevel = 0;
	private int numAI = 3;// AIの数 AI数増やすときはAIConfig,AIName,Rebuildクラスも書き換える
	private Timer timer;

	// フレーム
	private BoardFrame frame;
	private JMenuBar menubar = new JMenuBar();
	private JMenu menu[] = { new JMenu("ファイル"), new JMenu("表示") };// メニュー項目
	private JMenuItem exit = new JMenuItem("終了");
	private JMenuItem retitle = new JMenuItem("タイトルに戻る");
	private JMenuItem hideAIW = new JMenuItem("AIWindowの非表示");

	// 操作用ボタン
	private JPanel cardButton;
	private CardLayout cardButtonLayout;
	private JPanel buttonPanel;
	private JButton restartButton;
	private JButton backButton;
	private JPanel endNextButtonPanel;
	private JPanel endRetryButtonPanel;
	private JPanel gameOverButtonPanel;

	// ゲーム画面用パネル
	private JLayeredPane gameLayer = new JLayeredPane();
	private ScoreGraphics scorePanel;
	private int score = 0;
	private int highScore = 0;
	private int stage;
	private String mode;// ゲームモード
	private JPanel cardPanel;
	private CardLayout cardPanelLayout;

	// タイトル
	private Title titlePanel;

	// ゲームメイン
	private JLayeredPane boardLayer = new JLayeredPane();
	private BoardGraphics boardPanel;
	private MarkerGraphics markerPanel;
	private Click click;// リスナー
	private CardLayout cardAILayout = new CardLayout();// AIWindow用カードレイアウト
	private JPanel aiWindow = new JPanel();// AIWindow
	private JPanel aiSet = new JPanel();// AIWindow、AI非起動時
	private JPanel aiLevelPanel = new JPanel();// AI非起動時のAI名設定用パネル
	private JLabel aiNameLabel;// AI名
	private JPanel aiPanel = new JPanel();// AIWindow、AI起動時
	private JLabel aiWindowName = new JLabel("AIWindow:Random");// AI名前
	private JPanel aiStopButtonPanel = new JPanel();// AI停止パネル
	private JPanel aiMessagePanel = new JPanel();
	private JLabel aiMessage = new JLabel();// AIコメント
	private JProgressBar pb = new JProgressBar(0, 100);// プログレスバー
	private int retry = 2;

	// エンド
	private FinishGraphics endPanel;

	// カラー
	Color aiWindowStringColor = new Color(99, 130, 191);

	// 隠し要素
	private boolean unknown = false;

	public GUI(BoardState board) {
		this.board = board;
	}

	public void game() {
		// コンポーネントのセット
		frameSetting();
		// タイトル画面の表示
		showTitlePanel();
		bgmStart();
		frame.repaint();
		timer = new Timer(aiSpeed, this);
		timer.start();
	}

	private void frameSetting() {
		// 画面設定
		frame = new BoardFrame("HumanVersusRiversiAI");
		frame.setSize(800, 600);
		frame.setResizable(false);
		frame.addWindowListener(new Window());
		frame.setVisible(true);// フレームの表示

		// メニューバーの設定
		menu[0].add(retitle);
		menu[0].add(exit);
		menu[1].add(hideAIW);
		returnTitle = new ReturnTitle(this, board);
		retitle.addActionListener(returnTitle);
		exit.addActionListener(new Exit());
		hideAIW.addActionListener(new AIWindowSwitch(this));
		menubar.add(menu[0]);
		menubar.add(menu[1]);
		frame.setJMenuBar(menubar);
		centerPanelSetting();// パネルをセット
		southButtonSetting();// ボタンのパネルをセット
	}

	private void centerPanelSetting() {
		titlePanelSetting();
		boardPanelSetting();
		endPanelSetting();
		cardPanelSetting();
		gameLayerSetting();
	}

	private void southButtonSetting() {
		boardButtonSetting();
		endNextButtonSetting();
		endRetryButtonSetting();
		gameOverButtonSetting();
		cardButtonSetting();
	}

	private void titlePanelSetting() {
		// タイトル設定
		titlePanel = new Title();
		titlePanel.setLayout(null);// レイアウトマネージャ無効化

		// ゲームモード選択ボタン用のパネル作成
		JPanel jp = new JPanel();
		int jp_x = BoardGraphics.MARGIN + BoardGraphics.SQUARE * 2;
		int jp_y = BoardGraphics.MARGIN + BoardGraphics.SQUARE * 5 + 25;
		jp.setBounds(jp_x, jp_y, 300, 150);
		jp.setLayout(new GridLayout(3, 1));

		// モード選択ボタン設定
		JButton opg = new JButton("1 PLAYER GAME");
		JButton tpg = new JButton("2 PLAYER GAME");
		JButton prc = new JButton("PRACTICE");
		opg.setFont(new Font("Arial Bold", Font.BOLD, 15));
		tpg.setFont(new Font("Arial Bold", Font.BOLD, 15));
		prc.setFont(new Font("Arial Bold", Font.BOLD, 15));
		start = new Start(this);
		opg.addActionListener(start);
		tpg.addActionListener(start);
		prc.addActionListener(start);

		// モード選択ボタン取り付け
		jp.add(opg);
		jp.add(tpg);
		jp.add(prc);
		titlePanel.add(jp);// タイトルに貼り付け
	}

	private void boardPanelSetting() {
		click = new Click(this);
		boardPanel = new BoardGraphics(board, click, this);
		boardPanel.setLayout(null);// レイアウトマネージャ無効化
		boardPanel.setBounds(0, 0, 800, 600);

		markerPanel = new MarkerGraphics(board);
		markerPanel.setBounds(0, 0, 800, 600);
		markerPanel.setOpaque(false);
		markerPanel.setVisible(false);

		// レイヤー化
		boardLayer.setLayout(null);
		boardLayer.add(boardPanel);
		boardLayer.add(markerPanel);
		boardLayer.setLayer(boardPanel, 100);
		boardLayer.setLayer(markerPanel, 200);
		// 二重貼り付け
		scorePanel = new ScoreGraphics(this);
		scorePanel.setBounds(0, 0, 800, 600);
		scorePanel.setOpaque(false);
		boardLayer.add(scorePanel);
		boardLayer.setLayer(scorePanel, 300);

		aiSetSetting();
		aiPanelSetting();
		aiWindowSetting();
	}

	private void aiSetSetting() {
		// AI非起動時パネル
		aiSet.setLayout(new GridLayout(5, 1));
		// 設定項目
		JLabel aiLevel = new JLabel("AIのレベル");
		JLabel aiColor = new JLabel("AIの手番");

		// AIレベル
		aiLevelPanel.setLayout(new BorderLayout());
		// 左右ボタン
		JButton nextAI = new JButton();
		JButton backAI = new JButton();
		ImageIcon nextIcon = new ImageIcon("./img/arrow_c_right.png");
		ImageIcon backIcon = new ImageIcon("./img/arrow_c_left.png");
		nextAI.setIcon(nextIcon);
		backAI.setIcon(backIcon);
		nextAI.setActionCommand("next");
		backAI.setActionCommand("back");
		AIName aiName = new AIName(this);
		nextAI.addActionListener(aiName);
		backAI.addActionListener(aiName);
		// AI名
		aiNameLabel = new JLabel("Level1:Random");
		// 取り付け
		aiLevelPanel.add(nextAI, BorderLayout.EAST);
		aiLevelPanel.add(backAI, BorderLayout.WEST);
		aiLevelPanel.add(aiNameLabel);
		// AI手番
		JPanel aiColorPanel = new JPanel();
		JRadioButton aiColorButton[] = { new JRadioButton("黒(先手)"),
				new JRadioButton("白(後手)") };// AI手番
		// ボタンのグループ化
		ButtonGroup aiColorButtonGroup = new ButtonGroup();
		for (int i = 0; i < aiColorButton.length; i++) {
			aiColorButtonGroup.add(aiColorButton[i]);
		}
		// 文字色の設定、ボタンの取り付け
		for (int i = 0; i < aiColorButton.length; i++) {
			aiColorButton[i].setForeground(aiWindowStringColor);
			aiColorPanel.add(aiColorButton[i]);
		}
		// AIWindowラベル色設定
		aiLevel.setForeground(aiWindowStringColor);
		aiColor.setForeground(aiWindowStringColor);
		// AI起動ボタン
		JPanel moveAIButtonPanel = new JPanel();
		JButton moveAIButton = new JButton("AI起動");
		AIConfig aiconfig = new AIConfig(aiNameLabel, aiColorButton, this);
		moveAIButton.addActionListener(aiconfig);
		moveAIButtonPanel.add(moveAIButton);
		// 取り付け
		aiSet.add(aiLevel);
		aiSet.add(aiLevelPanel);
		aiSet.add(aiColor);
		aiSet.add(aiColorPanel);
		aiSet.add(moveAIButtonPanel);
	}

	public void setNameAILevelPanel(String aiName) {
		aiNameLabel.setText(aiName);
	}

	private void aiPanelSetting() {
		// AI起動時パネル
		aiPanel.setLayout(new BorderLayout());
		// AIWindowラベル色設定
		aiWindowName.setForeground(aiWindowStringColor);
		aiMessage.setForeground(aiWindowStringColor);
		// プログレスバー設定
		pb.setIndeterminate(false);
		pb.setString("AI思考中");
		pb.setStringPainted(false);
		// aiMessagePanel
		JButton aiStopButton = new JButton("AI停止");
		AIStop aiStop = new AIStop(this);
		aiStopButton.addActionListener(aiStop);
		aiStopButtonPanel.add(aiStopButton);
		aiMessagePanel.setLayout(new BorderLayout());
		aiMessagePanel.add(aiMessage, BorderLayout.CENTER);
		aiMessagePanel.add(aiStopButtonPanel, BorderLayout.SOUTH);
		// 取り付け
		aiPanel.add(aiWindowName, BorderLayout.NORTH);
		aiPanel.add(aiMessagePanel, BorderLayout.CENTER);
		aiPanel.add(pb, BorderLayout.SOUTH);
	}

	private void aiWindowSetting() {
		// AIWindow設定
		aiWindow.setLayout(cardAILayout);
		int aiWindow_x = BoardGraphics.ACROSS + BoardGraphics.SQUARE * 1;
		int aiWindow_y = BoardGraphics.ACROSS - BoardGraphics.SQUARE * 8;
		aiWindow.setBounds(aiWindow_x, aiWindow_y, 250, 150);

		// 取り付け
		aiWindow.add(aiSet);
		aiWindow.add(aiPanel);
		boardPanel.add(aiWindow);
	}

	public void showAISet() {
		String cmd = start.getCommand();
		if (cmd != "2 PLAYER GAME") {// 2P時は表示させない
			cardAILayout.first(aiWindow);
			aiWindow.setVisible(true);
			frame.repaint();
		}
	}

	public void showAIPanel() {
		cardAILayout.last(aiWindow);
		aiWindow.setVisible(true);
		frame.repaint();
	}

	public void aiWindowSwitch() {
		if (aiWindow.isVisible()) {
			aiWindow.setVisible(false);
			hideAIW.setText("AIWindowの表示");
		} else {
			aiWindow.setVisible(true);
			hideAIW.setText("AIWindowの非表示");
		}
	}

	private void endPanelSetting() {
		endPanel = new FinishGraphics(board, this);
	}

	private void cardPanelSetting() {
		cardPanel = new JPanel();
		cardPanelLayout = new CardLayout();
		cardPanel.setLayout(cardPanelLayout);
		cardPanel.setBounds(0, 0, 800, 600);

		cardPanel.add(titlePanel);
		cardPanel.add(boardLayer);
		cardPanel.add(endPanel);
	}

	private void gameLayerSetting() {
		scorePanel = new ScoreGraphics(this);
		scorePanel.setBounds(0, 0, 800, 600);
		gameLayer.setLayout(null);
		gameLayer.add(cardPanel);
		gameLayer.add(scorePanel);
		gameLayer.setLayer(cardPanel, 300);
		gameLayer.setLayer(scorePanel, 400);

		Container contentPane = frame.getContentPane();
		contentPane.add(gameLayer, BorderLayout.CENTER);
	}

	private void boardButtonSetting() {
		// ゲームメインのボタン設定
		ImageIcon ricon = new ImageIcon("./img/reload_r_default.png");
		ImageIcon bicon = new ImageIcon("./img/operation_undo.png");
		restartButton = new JButton("はじめから");
		backButton = new JButton("1手戻す");
		restartButton.setIcon(ricon);
		backButton.setIcon(bicon);
		restartButton.addActionListener(new Restart(board, this));
		backButton.addActionListener(new Back(board, this));

		buttonPanel = new JPanel();
		buttonPanel.add(restartButton);
		buttonPanel.add(backButton);
	}

	private void endNextButtonSetting() {
		// ゲーム終了時のボタン設定
		ImageIcon nextIcon = new ImageIcon("./img/arrow_d_right.png");
		ImageIcon exitIcon = new ImageIcon("./img/x_c.png");
		JButton nextButton = new JButton("次のレベルへ");
		JButton exitbutton = new JButton("終了");
		nextButton.setIcon(nextIcon);
		exitbutton.setIcon(exitIcon);
		nextButton.addActionListener(new Rebuild(board, this));
		exitbutton.addActionListener(new Exit());

		endNextButtonPanel = new JPanel();
		endNextButtonPanel.add(nextButton);
		endNextButtonPanel.add(exitbutton);
	}

	private void endRetryButtonSetting() {
		// ゲーム終了時のボタン設定
		ImageIcon retryIcon = new ImageIcon("./img/reload_c_default.png");
		ImageIcon exitIcon = new ImageIcon("./img/x_c.png");
		JButton retryButton = new JButton("リトライ");
		JButton exitbutton = new JButton("終了");
		retryButton.setIcon(retryIcon);
		exitbutton.setIcon(exitIcon);
		retryButton.addActionListener(new Rebuild(board, this));
		exitbutton.addActionListener(new Exit());

		endRetryButtonPanel = new JPanel();
		endRetryButtonPanel.add(retryButton);
		endRetryButtonPanel.add(exitbutton);
	}

	private void gameOverButtonSetting() {
		// ゲーム終了時のボタン設定
		ImageIcon retitleIcon = new ImageIcon("./img/reload_d_default.png");
		ImageIcon exitIcon = new ImageIcon("./img/x_c.png");
		JButton retitleButton = new JButton("タイトルに戻る");
		JButton exitbutton = new JButton("終了");
		retitleButton.setIcon(retitleIcon);
		exitbutton.setIcon(exitIcon);
		retitleButton.addActionListener(new ReturnTitle(this, board));
		exitbutton.addActionListener(new Exit());

		gameOverButtonPanel = new JPanel();
		gameOverButtonPanel.add(retitleButton);
		gameOverButtonPanel.add(exitbutton);
	}

	private void cardButtonSetting() {
		cardButton = new JPanel();
		cardButtonLayout = new CardLayout();
		cardButton.setLayout(cardButtonLayout);

		cardButton.add(buttonPanel);
		cardButton.add(endNextButtonPanel);
		cardButton.add(endRetryButtonPanel);
		cardButton.add(gameOverButtonPanel);

		// カードパネル取り付け
		Container contentPane = frame.getContentPane();
		contentPane.add(cardButton, BorderLayout.SOUTH);
	}

	private void showButtonPanel() {
		cardButtonLayout.first(cardButton);
	}

	private void showEndNextButtonPanel() {
		cardButtonLayout.first(cardButton);
		cardButtonLayout.next(cardButton);
	}

	private void showEndRetryButtonPanel() {
		cardButtonLayout.first(cardButton);
		cardButtonLayout.next(cardButton);
		cardButtonLayout.next(cardButton);
	}

	private void showGameOverButtonPanel() {
		cardButtonLayout.last(cardButton);
	}

	public void showTitlePanel() {
		retitle.setEnabled(false);// タイトルからタイトルへは戻れない
		cardPanelLayout.first(cardPanel);
		cardButton.setVisible(false);
		frame.repaint();
	}

	public void showBoardPanel() {
		cardPanelLayout.first(cardPanel);
		cardPanelLayout.next(cardPanel);
		showButtonPanel();
		cardButton.setVisible(true);
		frame.repaint();
	}

	public void showEndPanel() {
		cardPanelLayout.last(cardPanel);
		if (mode == "1 PLAYER GAME") {
			if (stage != 11) {
				if (gameEnd.checkWinner() != 1) {// 人間負け・引き分け
					if (retry != 0) {
						retry--;
						showEndRetryButtonPanel();
					} else {
						retry--;// gameover時、retry = -1
						showGameOverButtonPanel();
					}
				} else {// 人間勝ち
					showEndNextButtonPanel();
				}
			} else {
				showGameOverButtonPanel();
			}
		} else {
			showEndRetryButtonPanel();
		}
		cardButton.setVisible(true);
		frame.repaint();
	}

	public int getRetry() {
		return retry;
	}

	public int getPieceScore() {
		int piecescore;
		int dif = board.countDisc(-1 * aiColor) - board.countDisc(aiColor);
		if (dif > 0) {
			if (isUnknown()) {
				piecescore = dif * 30;
			} else {
				piecescore = dif * 10;
			}
		} else {
			piecescore = 0;
		}
		return piecescore;
	}

	public void score() {
		score += getPieceScore();
	}

	public int getScore() {
		return score;
	}

	public void scoreReset() {
		score = 0;
	}

	public void highScore() {
		// ボーナス1up
		if (highScore > 1000) {
			if (retry == 2) {
				retry++;
			}
		}

		if (highScore < score) {
			highScore = score;
		}
	}

	public int getHighScore() {
		return highScore;
	}

	public void showMarker() {
		markerPanel.setVisible(true);
	}

	public void unshowMarker() {
		markerPanel.setVisible(false);
	}

	public void gameStart() {
		gameEnd = new GameEnd(this, board);// GameEndクラスの初期化
		String cmd = start.getCommand();
		this.mode = cmd;

		retitle.setEnabled(true);

		// 1P時は、設定変更、undo、reset不可
		if (cmd == "1 PLAYER GAME") {
			stage = 0;
			retry = 2;
			aiWindow.setVisible(true);
			cardAILayout.last(aiWindow);
			aiStopButtonPanel.setVisible(false);

			restartButton.setEnabled(false);
			backButton.setEnabled(false);
			hideAIW.setEnabled(true);
		} else if (cmd == "2 PLAYER GAME") {// 2P時は、設定変更のみ不可
			retry = 1000;
			aiWindow.setVisible(false);

			restartButton.setEnabled(true);
			backButton.setEnabled(true);
			hideAIW.setEnabled(false);
		} else if (cmd == "PRACTICE") {
			retry = 1000;
			aiWindow.setVisible(true);
			cardAILayout.first(aiWindow);
			aiStopButtonPanel.setVisible(true);

			restartButton.setEnabled(true);
			backButton.setEnabled(true);
			hideAIW.setEnabled(true);
		}
	}

	public String getMode() {
		return mode;
	}

	public void setIn(String in) {
		if (board.getCurrentColor() == aiColor) {
			return;
		} else {
			this.in = in;
		}
	}

	public int getAIColor() {
		return aiColor;
	}

	public void setAIColor(int aiColor) {
		this.aiColor = aiColor;
	}

	public void GUImain() {
		// パターンマッチング
		String regix = "^[a-h][1-8]$";
		Pattern pat = Pattern.compile(regix);

		// inがnullだとMacherClassでエラーが生じるため例外処理
		if (in == null) {
			System.out.println();
			return;
		}

		Matcher m = pat.matcher(in);
		if (!m.find()) {
			System.err.println("英字(a～h)+数字(1～8)で入力してください(例.a3)");
			return;
		}

		try {
			setPoint = new SetPoint(in);
		} catch (StringIndexOutOfBoundsException e) {
			System.err.println("英字(a～h)+数字(1～8)で入力してください(例.a3)");
			return;
		}

		if (!board.move(setPoint)) {
			return;// 打てない
		} else if (!board.isGameOver()) {// ここでゲームオーバーの条件をつけないとタイマーがループする
			// プログレスバー起動
			if (aiColor != 0) {
				pb.setStringPainted(true);
				pb.setIndeterminate(true);
			}
			new MoveSound();
			repaint();
			// Timerで時間差書き換え
			timer = new Timer(aiSpeed, this);
			timer.start();
		}

		if (board.isGameOver()) {
			// プログレスバー停止
			pb.setStringPainted(false);
			pb.setIndeterminate(false);
			frame.repaint();
			System.out.println("\n----------------ゲーム終了----------------");
			JOptionPane.showMessageDialog(frame, "ゲーム終了");
			gameEnd.end();
			return;
		}

		// AIの打つ手がない
		if (board.getMovablePos().size() == 0) {
			// プログレスバー停止
			pb.setStringPainted(false);
			pb.setIndeterminate(false);
			System.out.println();
			repaint();
			board.pass();
			if (aiColor != 0) {
				JOptionPane.showMessageDialog(frame, "AIの置く場所がありませんパスします");
			} else {
				JOptionPane.showMessageDialog(frame, "置く場所がありませんパスします");
			}
			return;
		}
	}

	public void setLabelText(String ainame) {
		aiWindowName.setText("AIWindow:" + ainame);
	}

	// 割り込み防止synchronized
	public synchronized void aiTurn() {
		int limit = 0;

		if (mode == "1 PLAYER GAME") {
			aiLevel = stage % numAI;
			limit = stage / numAI;
		}

		AIRandom r;
		Formula f;
		Evaluater e;
		if (unknown) {
			Unknown u = new Unknown(board, 6);
			in = u.getPoint();
		} else {
			switch (aiLevel) {
			case 0:
				r = new AIRandom(board);
				in = r.getPoint();
				break;
			case 1:
				f = new Formula(board, 2 + limit);
				in = f.getPoint();
				break;
			case 2:
				e = new Evaluater(board, 4 + limit);
				in = e.getPoint();
				break;
			default:
				break;
			}
		}

		if (board.getCurrentColor() == aiColor) {
			// aiRandomからnullが返ってきたらpass,gameoverのcheck
			if (in != null) {
				setPoint = new SetPoint(in);
				setAIMessage(in + "に置きました");

				if (!board.move(setPoint)) {
					System.err.println("AIが間違った場所に石を置いています");
					return;
				} else {
					// プログレスバー停止
					pb.setStringPainted(false);
					pb.setIndeterminate(false);
					new MoveSound();
					repaint();
					timer.stop();
				}
			}

			if (board.isGameOver()) {
				// プログレスバー停止
				pb.setStringPainted(false);
				pb.setIndeterminate(false);
				frame.repaint();
				System.out.println("\n----------------ゲーム終了----------------");
				JOptionPane.showMessageDialog(frame, "ゲーム終了");// 普通に終われる、パスなし
				gameEnd.end();// 終了画面へ移行
				return;
			}

			// 人間の打つ手がない
			if (board.getMovablePos().size() == 0) {
				// プログレスバー停止
				pb.setStringPainted(false);
				pb.setIndeterminate(false);
				System.out.println();
				repaint();
				board.pass();
				JOptionPane.showMessageDialog(frame, "置く場所がありませんパスします");
				aiTurn();
			}
		}
	}

	public void repaint() {
		frame.repaint();
		CUIPrint.printDebug(board);
	}

	@Override
	// Timer実行中にはTimer起動しないsynchronized
	public synchronized void actionPerformed(ActionEvent arg0) {
		if (board.getCurrentColor() != aiColor || board.isGameOver()) {// GameOver時にTimerが何度も呼び出されるので制御
			timer.stop();
		} else {
			aiTurn();
			timer.stop();
		}
	}

	public int getAILevel() {
		return aiLevel;
	}

	public void setAILevel(int aiLevel) {
		this.aiLevel = aiLevel;
	}

	public int getStage() {
		return stage;
	}

	public void setStage(int stage) {
		this.stage = stage;
	}

	public void setAIMessage(String string) {
		aiMessage.setText(string);
	}

	public int getNumAI() {
		return numAI;
	}

	public boolean isUnknown() {
		return unknown;
	}

	public void setUnknown(boolean unknown) {
		this.unknown = unknown;
	}

	public void bgmStop() {
		bgm.stop();
	}

	public void bgmStart() {
		bgm.start();
	}

	public void unknownBGMStop() {
		unknownBGM.stop();
	}

	public void unknownBGMStart() {
		unknownBGM.start();
	}

}
