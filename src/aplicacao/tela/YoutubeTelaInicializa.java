package aplicacao.tela;

import aplicacao.eventos.YoutubeEventosMenores;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import javax.swing.plaf.DimensionUIResource;
import metodos.YoutubeArquivo;

/**
 * Classe responsável pelo método inicializar() da aplicação. Herda Eventos.
 */
@SuppressWarnings("serial")
public class YoutubeTelaInicializa extends YoutubeEventosMenores {

    /**
     * Checa se esta Ok, corrige se necessário e possível, e, inicializa todos
     * os componentes da aplicação (se possível).
     *
     * @throws java.io.IOException
     */
    protected void inicializar() throws IOException {

        if (SISTEMA.contains("Windows")) {
            pastaPrincipal = "C:\\users\\" + USUARIO + "\\YDownloads\\";
            pastaVideo = pastaPrincipal + "videos\\";
            pastaAudio = pastaPrincipal + "audios\\";
        } else {
            pastaPrincipal = "/home/" + USUARIO + "/YDownloads/";
            pastaVideo = pastaPrincipal + "videos/";
            pastaAudio = pastaPrincipal + "audios/";
        }
        diretorioPadrao = new YoutubeArquivo(pastaPrincipal, true);
        if (!diretorioPadrao.getArq().isDirectory()) {
            diretorioPadrao.getArq().mkdir();
        }
        arquivoChecaPrograma = new YoutubeArquivo(pastaPrincipal + ".check", false);
        new YoutubeArquivo(pastaVideo, true);
        new YoutubeArquivo(pastaAudio, true);

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
            JOptionPane.showMessageDialog(null, TEXTOS.pegarTexto("joptionpane.boas.vindas"), "YouTube Downloader", JOptionPane.INFORMATION_MESSAGE);
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
            this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    inTheExit();
                }
            });
            this.setTitle("YouTube Downloader");
            this.setResizable(true);
            this.setSize(900, 640);
            this.setMinimumSize(new DimensionUIResource(760, 560));
            this.setLocationRelativeTo(null);
            this.setIconImage(IMAGEM.pegarImage("/imagens/ytdBanner.png"));

            this.getContentPane().add(getPnlPadrao());

            checkVideo.setSelected(true);
            video = true;

            if (HORA >= 17 || HORA < 5) {
                btnTema.setSelected(true);
                noturno = true;
                btnTema.setText(TEXTOS.pegarTexto("botao.modo.dia"));
                btnTema.setIcon(IMAGEM.pegarIcon("/imagens/day.png"));
                btnTema.setToolTipText(TEXTOS.pegarTexto("tooltip.botao.diurno"));
                setBtnTemaCor();
            }

            pro1 = RUN_1.exec("ls");
            pro2 = RUN_2.exec("ls");
            pro3 = RUN_3.exec("ls");
            scheduleDownload = EXECUTOR.schedule((() -> { System.out.print("");}), 0, TimeUnit.SECONDS);
            scheduleDownload2 = EXECUTOR.schedule((() -> { System.out.print("");}), 0, TimeUnit.SECONDS);
            scheduleDownload3 = EXECUTOR.schedule((() -> { System.out.print("");}), 0, TimeUnit.SECONDS);
            scheduleGetId = EXECUTOR.schedule((() -> { System.out.print("");}), 0, TimeUnit.SECONDS);
            CMD.comandoDownload("ls");

            btnCancelar.setVisible(false);
            btnBaixa2.setVisible(false);
            btnBaixa3.setVisible(false);
            btnCancelProcess.setVisible(false);
            downloadProgressBar.setVisible(false);
            downloadProgressBar2.setVisible(false);
            downloadProgressBar3.setVisible(false);
            radioBtnMenuItem1.setSelected(true);
            radioBtnMenuItem2.setVisible(false);
            radioBtnMenuItem3.setVisible(false);
            radioBtnMenuItem4.setVisible(false);
            radioBtnMenuItem5.setVisible(false);
            radioBtnMenuItem6.setVisible(false);
            radioBtnMenuItem7.setVisible(false);
            radioBtnMenuItem8.setVisible(false);
            radioBtnMenuItem9.setVisible(false);
           menuBarRadioBtn.setVisible(false);

            index = 20;

        }
    }
}
