package aplicacao.eventos;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import metodos.YoutubeArquivo;

/**
 * Classe responsável pelo evento que configura o Download e o executa. Além de
 * tratar os erros.
 */
public class YoutubeEventosBaixar extends YoutubeEventosPainelDireita {

    protected boolean downloadDone;

    @Override
    protected void btnBaixarClick(ActionEvent ev) {
        Thread download = new Thread(() -> {
            downloadDone = false;
            setCmdLineSaida("");
            String link = getTxtLink().getText();

            if ((isVideo() || isAudio()) && !getLstPesquisa().isSelectionEmpty()) {
                getTxtLink().setText(TEXTOS.pegarTexto("fieldtext.link"));
                setIndex(getLstPesquisa().getSelectedIndex());
                link = getLinks()[getIndex()];
            } else if (!isVideo() && !isAudio()) {
                getLblResultado().setForeground(CORES.pegarCor(isNoturno(), 8));
                getLblResultado().setText(TEXTOS.pegarTexto("label.resultado.aviso.video"));
                return;
            } else if (!getLstPesquisa().isSelectedIndex(getIndex()) && !link.startsWith("https://www.youtube.com/watch?v=")) {
                getLblResultado().setForeground(CORES.pegarCor(isNoturno(), 8));
                getLblResultado().setText(TEXTOS.pegarTexto("label.resultado.aviso.item"));
                return;
            }

            if (link.startsWith("https://www.youtube.com/watch?v=")) {
                try {
                    isBaixando = true;
                    getLblResultado().setText(TEXTOS.pegarTexto("label.resultado.verificando.download"));
                    configurarCores();
                    setFormat((isVideo()) ? String.format("{'format': 'bestvideo[ext=mp4]+bestaudio[ext=m4a]',\n"
                            + "'outtmpl': '%s' + title + '.mp4'}", getPastaPrincipal()) // format video para o YoutubeDL
                            : String.format("{'format': 'bestaudio[ext=m4a]',\n"
                                    + "'outtmpl': '%s' + title + '.mp3',\n"
                                    + "'postprocessors': [{\n"
                                    + "    'key': 'FFmpegExtractAudio',\n" // format audio para o YoutubeDL
                                    + "    'preferredcodec': 'mp3',\n"
                                    + "    'preferredquality': '0'\n"
                                    + "    }]\n"
                                    + "}", getPastaPrincipal()));
                    YoutubeArquivo scriptBaixar = new YoutubeArquivo("/tmp/baixar");
                    scriptBaixar.criar(String.format(
                            "#!/usr/bin/python3\n"
                            + "import youtube_dl\n\n"
                            + ""
                            + "link = '%s'\n"
                            + "v = youtube_dl.YoutubeDL()\n"
                            + "i = v.extract_info(link, download=False)\n"
                            + "title = i.get('title')\n"
                            + "format = %s\n"
                            + "options = format\n\n"
                            + ""
                            + "with youtube_dl.YoutubeDL(options) as ydl:\n"
                            + "    ydl.download([link])\n",
                            link, getFormat()));

                    try {
                        setPro(RUN.exec("python3 /tmp/baixar"));
                        setRead(new BufferedReader(new InputStreamReader(getPro().getInputStream())));
                        setRead2(new BufferedReader(new InputStreamReader(getPro().getErrorStream())));
                        
                        btnBaixa.setVisible(false);
                        if (!isBaixando2) { btnBaixa2.setVisible(true); } 
                        else if (!isBaixando3) { btnBaixa3.setVisible(true); }
                        getBtnCancelar().setVisible(true);

                        while ((line = getRead().readLine()) != null) {
                            if ((!line.startsWith("[download] 100%") && !line.contains("Deleting"))) {
                                String progressPercentdownload = "", progressPercentdownload2 = "";
                                if (line.contains("%")) {
                                    getDownloadProgressBar().setVisible(true);
                                    getLblProgressBar().setVisible(false);
                                    getLblResultado().setVisible(false);
                                    progressPercentdownload = line.substring(line.indexOf(" "), line.indexOf(".")).strip();
                                    progressPercentdownload2 = line.substring(line.indexOf(" ")) + "  ";
                                    if (!downloadDone) {
                                        getDownloadProgressBar().setString(TEXTOS.pegarTexto("label.resultado.baixando") + progressPercentdownload2);
                                    }
                                    getDownloadProgressBar().setValue(Integer.parseInt(progressPercentdownload.strip()));
                                    if (getDownloadProgressBar().getString().contains("100.0%")) {
                                        CMD.sleep(0.2);
                                    }
                                } else if (getDownloadProgressBar().getValue() == 100) {
                                    getDownloadProgressBar().setValue(0);
                                    getDownloadProgressBar().setString(TEXTOS.pegarTexto("progressbar.baixando.audio") + progressPercentdownload2);
                                    downloadDone = true;
                                } else {
                                    if (line.contains("[download] " + getPastaPrincipal())) {
                                        getLblResultado().setVisible(true);
                                        getLblResultado().setText(TEXTOS.pegarTexto("label.resultado.aviso.ja.baixado"));
                                        getLblResultado().setForeground(CORES.pegarCor(isNoturno(), 8));
                                    } else if (line.startsWith("[youtube]")) {
                                        configurarCores();
                                        getLblResultado().setText(TEXTOS.pegarTexto("label.resultado.verificando.download"));
                                    } else {
                                        if (line.startsWith("[ffmpeg]") && line.endsWith("skipping")) {
                                            continue;
                                        }
                                        configurarCores();
                                        getLblResultado().setText(line);
                                    }
                                }
                            } else if (downloadDone) {
                                getLblProgressBar().setVisible(false);
                                getLblResultado().setVisible(true);
                                getLblResultado().setText(TEXTOS.pegarTexto("label.resultado.download.concluido"));
                                getLblResultado().setForeground(CORES.pegarCor(isNoturno(), 9));
                                
                                getDownloadProgressBar().setVisible(false);
                                getDownloadProgressBar().setString(TEXTOS.pegarTexto("label.resultado.baixando"));
                            }

                            setCmdLineSaida(getCmdLineSaida() + line + "\n");

                        }
                        if (line == null) {
                            getDownloadProgressBar().setString(TEXTOS.pegarTexto("label.resultado.baixando"));
                            getLblProgressBar().setVisible(false);
                            getLblResultado().setVisible(true);
                            while ((line = getRead2().readLine()) != null) {
                                if (line.contains("DownloadError:")) {
                                    getLblResultado().setForeground(CORES.pegarCor(isNoturno(), 7));
                                    getLblResultado().setText(TEXTOS.pegarTexto("label.resultado.falha.download"));
                                } else {
                                    getLblResultado().setText(String.format("<html>%s</html>", line));
                                    getLblResultado().setForeground(CORES.pegarCor(isNoturno(), 7));
                                }

                                setCmdLineSaida(getCmdLineSaida() + line + "\n");

                            }

                            getRead().close();
                            getRead2().close();
                        }

                    } catch (IOException e) {
                        getDownloadProgressBar().setString(TEXTOS.pegarTexto("label.resultado.baixando"));
                        
                        getLblProgressBar().setVisible(false);
                        getLblResultado().setVisible(true);
                        if (e.toString().contains("Stream closed")) {
                            getLblResultado().setText(TEXTOS.pegarTexto("label.resultado.download.cancelado"));
                            getLblResultado().setForeground(CORES.pegarCor(isNoturno(), 8));
                            getDownloadProgressBar().setVisible(false);
                            isBaixando = false;
                            if (isBaixando2 && isBaixando3) { btnBaixa.setVisible(true); }
                            if (!isBaixando && !isBaixando2 && !isBaixando3) { btnCancelar.setVisible(false); }
                            return;
                        } else {
                            getLblResultado().setText(String.format("<html>%s</html>", e.toString()));
                            getLblResultado().setForeground(CORES.pegarCor(isNoturno(), 7));
                            getDownloadProgressBar().setVisible(false);
                            isBaixando = false;
                            if (isBaixando2 && isBaixando3) { btnBaixa.setVisible(true); }
                            if (!isBaixando && !isBaixando2 && !isBaixando3) { btnCancelar.setVisible(false); }
                            return;
                        }

                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if (getCmdLineSaida().equals("")) {
                getLblProgressBar().setVisible(false);
                getLblResultado().setVisible(true);
                getLblResultado().setForeground(CORES.pegarCor(isNoturno(), 7));
                getLblResultado().setText(TEXTOS.pegarTexto("label.resultado.falha.download"));
            }
            getDownloadProgressBar().setString(TEXTOS.pegarTexto("label.resultado.baixando"));
            
            getLblProgressBar().setVisible(false);
            getDownloadProgressBar().setVisible(false);
            getLblResultado().setVisible(true);
            isBaixando = false;
            if (isBaixando2 && isBaixando3) { btnBaixa.setVisible(true); }
            if (!isBaixando && !isBaixando2 && !isBaixando3) { btnCancelar.setVisible(false); }
        });
        download.start();
    }
}
