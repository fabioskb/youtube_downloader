package aplicacao.tela;

import java.io.IOException;
import javax.swing.JOptionPane;

/**
 * Classe responsável pelo construtor da aplicação.
 */
@SuppressWarnings("serial")
public class YoutubeTelaConstrutor extends YoutubeTelaInicializa {

    /**
     * Construtor
     */
    public YoutubeTelaConstrutor() {
        try {
            this.inicializar();
            this.configurarCores();
            this.eventos();
        } catch (NullPointerException | IOException e) {
            JOptionPane.showMessageDialog(null, (IDIOMA.contains("português"))
                    ? "<html><center>Falhou!<br>Clique em OK e inicie a "
                    + "aplicação novamente</center></html>"
                    : "<html><center>Failed!<br>Click OK and start the "
                    + "application again</center></html>", "YouTube "
                    + "Downloader", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }
}
