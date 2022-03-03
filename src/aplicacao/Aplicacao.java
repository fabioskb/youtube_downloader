package aplicacao;

import javax.swing.SwingUtilities;

import aplicacao.eventos.YoutubeEventos5;

public class Aplicacao {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			YoutubeEventos5 ydl = new YoutubeEventos5();
			ydl.setVisible(true);
		});
	}

}
