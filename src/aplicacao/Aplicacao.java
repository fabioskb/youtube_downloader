package aplicacao;

import javax.swing.SwingUtilities;

public class Aplicacao {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			YoutubeDl ydl = new YoutubeDl();
			ydl.setVisible(true);
		});
	}

}
