package aplicacao.tela;

import aplicacao.eventos.YoutubeEventosMenores;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.plaf.DimensionUIResource;

/**
 * Classe responsável pelo método inicializar() da aplicação. Herda Eventos.
 */
public class YoutubeTelaInicializa extends YoutubeEventosMenores {

    /**
     * Checa se esta Ok, corrige se necessário e possível, e, inicializa todos
     * os componentes da aplicação (se possível).
     *
     */
    protected void inicializar() {
        String youtubeDlSaida = CMD.comando("pip show youtube_dl");
        String youtubeSearchSaida = CMD.comando("pip show youtube_search");

        if (youtubeDlSaida.contains("command not found") || youtubeSearchSaida.contains("command not found")) {
            JOptionPane.showMessageDialog(null, TEXTOS.pegarTexto("joptionpane.pip.nao.instalado"), "YouTube Downloader", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        } else if ((!youtubeDlSaida.startsWith("Name: youtube-dl") || !youtubeSearchSaida.startsWith("Name: youtube-search")) && arquivoChecaPrograma.getArq().isFile()) {
            arquivoChecaPrograma.deletar();
        } else {
            arquivoChecaPrograma.criar("Checado!");
        }

        if (!arquivoChecaPrograma.getArq().isFile()) {
            JOptionPane.showMessageDialog(null, TEXTOS.pegarTexto("joptionpane.boas.vndas"), "YouTube Downloader", JOptionPane.INFORMATION_MESSAGE);
            installYoutubeDl = CMD.comando("pip install youtube-dl");
            installYoutubeSearch = CMD.comando("pip install youtube-search");
            if (installYoutubeDl.contains("ERROR: Could not find a version that satisfies the requirement youtube-dl (from versions: none)") 
                    || installYoutubeSearch.equals("ERROR: Could not find a version that satisfies the requirement youtube-search (from versions: none)")) {
                JOptionPane.showMessageDialog(null, TEXTOS.pegarTexto("joptionpane.erro.dependencias"), "YouTube Downloader", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            } else {
                arquivoChecaPrograma.criar("Checado!");
                JOptionPane.showMessageDialog(null, TEXTOS.pegarTexto("joptionpane.instalacao.concluida"), "YouTube Downloader", JOptionPane.INFORMATION_MESSAGE);
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

            checkVideo.setSelected(true);
            video = true;

            if (HORA >= 17 || HORA < 5) {
                btnModoNoite.setSelected(true);
                noturno = true;
                btnModoNoite.setText(TEXTOS.pegarTexto("botao.modo.dia"));
                btnModoNoite.setIcon(IMAGEM.pegarIcon("/imagens/day.png"));
                btnModoNoite.setToolTipText(TEXTOS.pegarTexto("tooltip.botao.diurno"));
            }

            btnCancelar.setVisible(false);
            btnBaixa2.setVisible(false);
            btnBaixa3.setVisible(false);
            downloadProgressBar.setVisible(false);
            downloadProgressBar2.setVisible(false);
            downloadProgressBar3.setVisible(false);
            lblProgressBar.setVisible(false);
            lblProgressBar2.setVisible(false);
            lblProgressBar3.setVisible(false);
            
            try {
                pro = RUN.exec("ls");
                pro2 = RUN_2.exec("ls");
                pro3 = RUN_3.exec("ls");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            index = 20;

        }
    }
}
