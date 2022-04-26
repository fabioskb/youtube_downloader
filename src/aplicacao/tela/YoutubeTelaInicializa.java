package aplicacao.tela;

import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.plaf.DimensionUIResource;

import aplicacao.eventos.YoutubeEventosMenores;

/**
 * Classe responsável pelo método inicializar() da aplicação.
 * Herda Eventos.
 */
public class YoutubeTelaInicializa extends YoutubeEventosMenores {
	/**
	 * Checa se esta Ok, corrige se necessário e possível, e, 
	 * inicializa todos os componentes da aplicação (se possível).
	 * @throws IOException
	 */
	protected void inicializar() {
		String youtubeDlSaida = CMD.comando("pip show youtube_dl");
		String youtubeSearchSaida = CMD.comando("pip show youtube_search");

		if (youtubeDlSaida.contains("command not found") || youtubeSearchSaida.contains("command not found")) {
			JOptionPane.showMessageDialog(null, TEXTOS.pegarTexto(29), "YouTube Downloader", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		} else if ((!youtubeDlSaida.startsWith("Name: youtube-dl") || !youtubeSearchSaida.startsWith("Name: youtube-search")) && getArquivoChecaPrograma().getArq().isFile()) {
			getArquivoChecaPrograma().deletar();
		} else {
			getArquivoChecaPrograma().criar("Checado!");
		}

		if (!getArquivoChecaPrograma().getArq().isFile()) {
			JOptionPane.showMessageDialog(null, TEXTOS.pegarTexto(26), "YouTube Downloader", JOptionPane.INFORMATION_MESSAGE);
			setInstallYoutubeDl(CMD.comando("pip install youtube-dl"));
			setInstallYoutubeSearch(CMD.comando("pip install youtube-search"));
			if (getInstallYoutubeDl().contains("ERROR: Could not find a version that satisfies the requirement youtube-dl (from versions: none)") || getInstallYoutubeSearch().equals("ERROR: Could not find a version that satisfies the requirement youtube-search (from versions: none)")) {
				JOptionPane.showMessageDialog(null, TEXTOS.pegarTexto(27), "YouTube Downloader", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			} else {
				getArquivoChecaPrograma().criar("Checado!");
				JOptionPane.showMessageDialog(null, TEXTOS.pegarTexto(28), "YouTube Downloader", JOptionPane.INFORMATION_MESSAGE);
				youtubeDlSaida = "Name: youtube-dl";
				youtubeSearchSaida = "Name: youtube-search";
			}
		}

		if (youtubeDlSaida.startsWith("Name: youtube-dl") || youtubeSearchSaida.startsWith("Name: youtube-search")) {
			this.setTitle("YouTube Downloader");
			this.setResizable(true);
			this.setSize(920, 680);
			this.setMinimumSize(new DimensionUIResource(760, 560));
			this.setLocationRelativeTo(null);
			this.setIconImage(IMAGEM.pegarImage("/imagens/ytdBanner.png"));

			this.getContentPane().add(getPnlPadrao());

			this.getCheckVideo().setSelected(true);
			this.setVideo(true);

			if (this.HORA >= 18 || this.HORA < 5) {
				this.getBtnModoNoite().setSelected(true);
				this.setNoturno(true);
			}

			this.getBtnCancelar().setVisible(false);
			this.getDownloadProgressBar().setVisible(false);
			this.getLblProgressBar().setVisible(false);
			this.setIndex(20);

		}
	}
}
