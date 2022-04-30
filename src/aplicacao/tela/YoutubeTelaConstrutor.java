package aplicacao.tela;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import metodos.YoutubeArquivo;

/**
 * Classe responsável pelo construtor da aplicação.
 * Herda Telas 1, 2, 3 e 4
 */
public class YoutubeTelaConstrutor extends YoutubeTelaInicializa {
    /**
	 * Construtor
	 */
	public YoutubeTelaConstrutor() {
		try {
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
                        this.configurarCores();
                        this.eventos();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null, (IDIOMA.contains("português")) ? "<html><center>Falhou!<br>Clique em OK e inicie a aplicação novamente</center></html>":"<html><center>Failed!<br>Click OK and start the application again</center></html>", "YouTube Downloader", JOptionPane.ERROR_MESSAGE);
                        System.exit(0);
                    }
		} catch (IOException ex) {
			Logger.getLogger(YoutubeTelaConstrutor.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
