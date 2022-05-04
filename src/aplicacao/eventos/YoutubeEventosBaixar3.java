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
public class YoutubeEventosBaixar3 extends YoutubeEventosBaixar2 {

    protected boolean downloadDone, isRunningDownload;
    protected boolean isBaixarExecutando, isBaixar2Executando, isBaixar3Executando;

    @Override
    protected void btnBaixar3Click(ActionEvent ev) {
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

                        while ((line = getRead().readLine()) != null) {
                            getBtnCancelar().setVisible(true);
                            if ((isBaixarExecutando && isBaixar2Executando) || (isBaixarExecutando && isBaixar3Executando)
                                    || (isBaixar2Executando && isBaixarExecutando) || (isBaixar2Executando && isBaixar3Executando)
                                    || (isBaixar3Executando && isBaixarExecutando) || (isBaixar3Executando && isBaixar2Executando)) {
                                isRunningDownload = true;
                            } else {
                                isRunningDownload = false;
                            }
                            if (isBaixar2Executando && isBaixar3Executando) {
                                getBtnBaixa().setVisible(false);
                            } else {
                                getBtnBaixa().setVisible(true);
                            }
                            System.out.println(line);
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
                                getBtnBaixa().setVisible(true);
                                getLblProgressBar().setVisible(false);
                                getLblResultado().setVisible(true);
                                getLblResultado().setText(TEXTOS.pegarTexto("label.resultado.download.concluido"));
                                getLblResultado().setForeground(CORES.pegarCor(isNoturno(), 9));
                                getBtnCancelar().setVisible(false);
                                getDownloadProgressBar().setVisible(false);
                                getDownloadProgressBar().setString(TEXTOS.pegarTexto("label.resultado.baixando"));
                            }

                            setCmdLineSaida(getCmdLineSaida() + line + "\n");

                        }
                        if (line == null) {
                            getDownloadProgressBar().setString(TEXTOS.pegarTexto("label.resultado.baixando"));
                            getBtnBaixa().setVisible(true);
                            getLblProgressBar().setVisible(false);
                            getLblResultado().setVisible(true);
                            while ((line = getRead2().readLine()) != null) {
                                if (line.contains("DownloadError:")) {
                                    getLblResultado().setForeground(CORES.pegarCor(isNoturno(), 7));
                                    getLblResultado().setText(TEXTOS.pegarTexto("label.resultado.falha.download"));
                                    System.out.println(line);
                                } else {
                                    getLblResultado().setText(String.format("<html>%s</html>", line));
                                    getLblResultado().setForeground(CORES.pegarCor(isNoturno(), 7));
                                    System.out.println(line);
                                }

                                setCmdLineSaida(getCmdLineSaida() + line + "\n");

                            }

                            getRead().close();
                            getRead2().close();
                        }

                    } catch (IOException e) {
                        getDownloadProgressBar().setString(TEXTOS.pegarTexto("label.resultado.baixando"));
                        getBtnBaixa().setVisible(true);
                        getBtnCancelar().setVisible(false);
                        getLblProgressBar().setVisible(false);
                        getLblResultado().setVisible(true);
                        if (e.toString().contains("Stream closed")) {
                            getLblResultado().setText(TEXTOS.pegarTexto("label.resultado.download.cancelado"));
                            getLblResultado().setForeground(CORES.pegarCor(isNoturno(), 8));
                            getDownloadProgressBar().setVisible(false);
                            return;
                        } else {
                            getLblResultado().setText(String.format("<html>%s</html>", e.toString()));
                            getLblResultado().setForeground(CORES.pegarCor(isNoturno(), 7));
                            getDownloadProgressBar().setVisible(false);
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
            getBtnCancelar().setVisible(false);
            getLblProgressBar().setVisible(false);
            getDownloadProgressBar().setVisible(false);
            getLblResultado().setVisible(true);
        });
        download.start();
        // Multilaser MS5v2
    }
}
