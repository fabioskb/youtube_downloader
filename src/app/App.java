package app;

import javax.swing.SwingUtilities;

public class App {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			Youtube ydl = new Youtube();
			ydl.setVisible(true);
		});
	}

}
