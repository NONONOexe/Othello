package swingconponent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public class Window implements WindowListener {

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		System.out.println("\nオセロを終了します");
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
