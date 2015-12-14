package swingconponent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Click extends MouseAdapter {

	private String clickcoord;
	private GUI gui;

	public Click(GUI guiBoard) {
		this.gui = guiBoard;
	}

	public void convertCoord(MouseEvent e) {
		int clickX;
		int clickY;
		// ボードの(x,y)に座標変換
		if (BoardGraphics.MARGIN < e.getX() && e.getX() < BoardGraphics.ACROSS) {
			clickX = (e.getX() - BoardGraphics.MARGIN) / BoardGraphics.SQUARE + 1;
		} else
			return;
		if (BoardGraphics.MARGIN < e.getY() && e.getY() < BoardGraphics.ACROSS) {
			clickY = (e.getY() - BoardGraphics.MARGIN) / BoardGraphics.SQUARE + 1;
		} else
			return;

		// 入力座標、英字+数字に変換
		clickcoord = new String();
		String x = String.valueOf((char) ('a' + clickX - 1));
		String y = String.valueOf(clickY);
		clickcoord = x + y;
		System.out.println(clickcoord);
	}

	// クリック処理
	public void mouseClicked(MouseEvent e) {
		convertCoord(e);
		gui.setIn(clickcoord);
		gui.GUImain();
	}
	
	public void mousePressed(MouseEvent e){
		try {
			Thread.sleep(100);//長押し
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
		gui.showMarker();
		gui.repaint();
	}
	
	public void mouseReleased(MouseEvent e){
		gui.unshowMarker();
	}
}
