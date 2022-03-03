package aplicacao;

import javax.swing.SwingUtilities;

import aplicacao.tela.YoutubeTelaConstrutor;

public class Aplicacao {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			YoutubeTelaConstrutor ydl = new YoutubeTelaConstrutor();
			ydl.setVisible(true);
		});
	}

}
