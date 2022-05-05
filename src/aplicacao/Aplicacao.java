package aplicacao;

import aplicacao.tela.YoutubeTelaConstrutor;
import javax.swing.SwingUtilities;

public class Aplicacao {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			YoutubeTelaConstrutor ydl = new YoutubeTelaConstrutor();
			ydl.setVisible(true);
		});
	}

}
