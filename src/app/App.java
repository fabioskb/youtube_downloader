package app;

import javax.swing.SwingUtilities;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(() -> {
			Youtube ydl = new Youtube();
			ydl.setVisible(true);
		});
	}

}
