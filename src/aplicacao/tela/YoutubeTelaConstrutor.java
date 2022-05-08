package aplicacao.tela;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.JOptionPane;
import metodos.YoutubeArquivo;

/**
 * Classe responsável pelo construtor da aplicação. Herda Telas 1, 2, 3 e 4
 */
public class YoutubeTelaConstrutor extends YoutubeTelaInicializa {

    /**
     * Construtor
     */
    public YoutubeTelaConstrutor() {
        try {
            this.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    ActionEvent ev = null;
                    itemMenuExitClick(ev);
                }
            });
            if (SISTEMA.contains("Windows")) {
                pastaPrincipal = "C:\\users\\" + USUARIO + "\\YDownloads\\";
                pastaVideo = pastaPrincipal+"videos\\";
                pastaAudio = pastaPrincipal+"audios\\";
            } else {
                pastaPrincipal = "/home/" + USUARIO + "/YDownloads/";
                pastaVideo = pastaPrincipal+"videos/";
                pastaAudio = pastaPrincipal+"audios/";
            }
            YoutubeArquivo videoDir = new YoutubeArquivo(pastaVideo, true);
            YoutubeArquivo audioDir = new YoutubeArquivo(pastaAudio, true);
            diretorioPadrao = new YoutubeArquivo(pastaPrincipal, true);
            if (!diretorioPadrao.getArq().isDirectory()) {
                diretorioPadrao.getArq().mkdir();
            }
            arquivoChecaPrograma = new YoutubeArquivo(pastaPrincipal + ".check", false);
            try {
                this.inicializar();
                this.configurarCores();
                this.eventos();
            } catch (NullPointerException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, (IDIOMA.contains("português")) ? "<html><center>Falhou!<br>Clique em OK e inicie a aplicação novamente</center></html>" : "<html><center>Failed!<br>Click OK and start the application again</center></html>", "YouTube Downloader", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
