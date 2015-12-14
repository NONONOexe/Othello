package swingconponent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JRadioButton;

import boardsystem.BoardState;

public class AIConfig implements ActionListener {

	private JLabel aiNameLabel;
	private JRadioButton[] aiColorButton;
	private GUI gui;

	public AIConfig(JLabel aiNameLabel, JRadioButton[] aiColorButton, GUI gui) {
		this.aiNameLabel = aiNameLabel;
		this.aiColorButton = aiColorButton;
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// AILevel変更
		if (aiNameLabel.getText() == "Level1:Random") {
			gui.setAILevel(0);
			gui.setLabelText("Random");
		} else if (aiNameLabel.getText() == "Level2:Formula") {
			gui.setAILevel(1);
			gui.setLabelText("Formula");
		} else if (aiNameLabel.getText() == "Level3:Evaluater") {
			gui.setAILevel(2);
			gui.setLabelText("Evaluater");
		} else {
			return;// 選択されていなければなにもできない
		}

		// AIColor変更
		if (aiColorButton[0].isSelected()) {
			gui.setAIColor(BoardState.BLACK);
		} else if (aiColorButton[1].isSelected()) {
			gui.setAIColor(BoardState.WHITE);
		} else {
			return;// 選択されていなければなにもできない
		}

		gui.aiTurn();// ＡＩ起動
		gui.showAIPanel();// 起動パネルに切り替え
	}

}
