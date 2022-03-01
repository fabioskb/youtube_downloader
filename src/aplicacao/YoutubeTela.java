package aplicacao;

import javax.swing.JOptionPane;

import metodos.YoutubeArquivo;

/**
 * Classe responsável pelo construtor da aplicação.
 */
public class YoutubeTela extends YoutubeTela4 {
    /**
	 * Construtor
	 */
	public YoutubeTela() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		if (SISTEMA.contains("Windows")) {
			setPastaPrincipal("C:\\users\\" + USUARIO + "\\YDownloads\\");
		} else {
			setPastaPrincipal("/home/" + USUARIO + "/YDownloads/");
		}
		setDiretorioPadrao(new YoutubeArquivo(this.getPastaPrincipal()));
		if (!getDiretorioPadrao().getArq().isDirectory()) {
			getDiretorioPadrao().getArq().mkdir();
		}

		setArquivoChecaPrograma(new YoutubeArquivo(getPastaPrincipal() + ".check"));		
		try {
			this.inicializar();
			this.configurarCores(true);
			this.eventos();
		} catch (NullPointerException e) {
			//e.printStackTrace();
			JOptionPane.showMessageDialog(null, (IDIOMA.contains("português")) ? "<html><center>Falhou!<br>Clique em OK e inicie a aplicação novamente</center></html>":"<html><center>Failed!<br>Click OK and start the application again</center></html>", "YouTube Downloader", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
}
