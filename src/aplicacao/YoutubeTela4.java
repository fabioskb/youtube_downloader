package aplicacao;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JOptionPane;

/**
 * Classe responsável pelo método inicializar() da aplicação.
 */
public class YoutubeTela4 extends YoutubeTela3 {
	/**
	 * Checa se esta Ok, corrige se necessário e possível, e, 
	 * inicializa todos os componentes da aplicação (se possível).
	 * @throws IOException
	 */
	protected void inicializar() {
		String youtubeDlSaida = getCmd().comando("pip show youtube_dl");
		String youtubeSearchSaida = getCmd().comando("pip show youtube_search");

		if (youtubeDlSaida.contains("command not found") || youtubeSearchSaida.contains("command not found")) {
			JOptionPane.showMessageDialog(null, getTEXTOS().getTextos(29), "YouTube Downloader", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		} else if ((!youtubeDlSaida.startsWith("Name: youtube-dl") || !youtubeSearchSaida.startsWith("Name: youtube-search")) && getArquivoChecaPrograma().getArq().isFile()) {
			getArquivoChecaPrograma().deletar();
		} else {
			getArquivoChecaPrograma().criar("Checado!");
		}

		if (!getArquivoChecaPrograma().getArq().isFile()) {
			JOptionPane.showMessageDialog(null, getTEXTOS().getTextos(26), "YouTube Downloader", JOptionPane.INFORMATION_MESSAGE);
			setInstallYoutubeDl(getCmd().comando("pip install youtube-dl"));
			setInstallYoutubeSearch(getCmd().comando("pip install youtube-search"));
			if (getInstallYoutubeDl().contains("ERROR: Could not find a version that satisfies the requirement youtube-dl (from versions: none)") || getInstallYoutubeSearch().equals("ERROR: Could not find a version that satisfies the requirement youtube-search (from versions: none)")) {
				JOptionPane.showMessageDialog(null, getTEXTOS().getTextos(27), "YouTube Downloader", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			} else {
				getArquivoChecaPrograma().criar("Checado!");
				JOptionPane.showMessageDialog(null, getTEXTOS().getTextos(28), "YouTube Downloader", JOptionPane.INFORMATION_MESSAGE);
				youtubeDlSaida = "Name: youtube-dl";
				youtubeSearchSaida = "Name: youtube-search";
			}
		}

		if (youtubeDlSaida.startsWith("Name: youtube-dl") || youtubeSearchSaida.startsWith("Name: youtube-search")) {
			this.setTitle("YouTube Downloader");
			this.setResizable(true);
			this.setSize(920, 680);
			this.setLocationRelativeTo(null);
			this.setIconImage(getIMAGEM().pegarImage("/imagens/ytdBanner.png"));

			this.getContentPane().setLayout(new BorderLayout());

			this.getContentPane().add(getPnlTopo(), BorderLayout.NORTH);
			this.getContentPane().add(getPnlEsquerda(), BorderLayout.WEST);
			this.getContentPane().add(getPnlCentro(), BorderLayout.CENTER);
			this.getContentPane().add(getPnlRodape(), BorderLayout.SOUTH);

			this.getCheckVideo().setSelected(true);
			this.setVideo(true);

			if (this.getHORA() >= 18 || this.getHORA() <= 5) {
				this.setNoturno(true);
				this.getBtnModoNoite().setSelected(true);
			}
		}
	}
}
