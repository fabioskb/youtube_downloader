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

    protected boolean downloadDone3;

    @Override
    protected void btnBaixar3Click(ActionEvent ev) {
        Thread download3 = new Thread(() -> {
            downloadDone3 = false;
            setCmdLineSaida3("");
            String link = getTxtLink().getText();

            if ((isVideo() || isAudio()) && !getLstPesquisa().isSelectionEmpty()) {
                getTxtLink().setText(TEXTOS.pegarTexto("fieldtext.link"));
                setIndex(getLstPesquisa().getSelectedIndex());
                link = getLinks()[getIndex()];
            } else if (!isVideo() && !isAudio()) {
                lblResultado3.setForeground(CORES.pegarCor(isNoturno(), 8));
                lblResultado3.setText(TEXTOS.pegarTexto("label.resultado.aviso.video"));
                CMD.sleep(2);
                lblResultado3.setVisible(false);
                return;
            } else if (!getLstPesquisa().isSelectedIndex(getIndex()) && !link.startsWith("https://www.youtube.com/watch?v=")) {
                lblResultado3.setForeground(CORES.pegarCor(isNoturno(), 8));
                lblResultado3.setText(TEXTOS.pegarTexto("label.resultado.aviso.item"));
                CMD.sleep(2);
                lblResultado3.setVisible(false);
                return;
            }

            if (link.startsWith("https://www.youtube.com/watch?v=")) {
                try {
                    isBaixando3 = true;
                    btnBaixa3.setVisible(false);
                    if (!isBaixando) {
                        btnBaixa.setVisible(true);
                    } else if (!isBaixando2) {
                        btnBaixa2.setVisible(true);
                    }
                    lblResultado3.setText(TEXTOS.pegarTexto("label.resultado.verificando.download"));
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
                    YoutubeArquivo scriptBaixar3 = new YoutubeArquivo("/tmp/baixar3");
                    scriptBaixar3.criar(String.format(
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
                        setPro3(RUN_3.exec("python3 /tmp/baixar3"));
                        setRead5(new BufferedReader(new InputStreamReader(pro3.getInputStream())));
                        setRead6(new BufferedReader(new InputStreamReader(pro3.getErrorStream())));

                        while ((line3 = read5.readLine()) != null) {
                            getBtnCancelar().setVisible(true);
                            if ((!line3.startsWith("[download] 100%") && !line3.contains("Deleting"))) {
                                String progressPercentdownload = "", progressPercentdownload2 = "";
                                if (line3.contains("%")) {
                                    downloadProgressBar3.setVisible(true);
                                    lblProgressBar3.setVisible(false);
                                    lblResultado3.setVisible(false);
                                    progressPercentdownload = line3.substring(line3.indexOf(" "), line3.indexOf(".")).strip();
                                    progressPercentdownload2 = line3.substring(line3.indexOf(" ")) + "  ";
                                    if (!downloadDone3) {
                                        downloadProgressBar3.setString(TEXTOS.pegarTexto("label.resultado.baixando") + progressPercentdownload2);
                                    }
                                    downloadProgressBar3.setValue(Integer.parseInt(progressPercentdownload.strip()));
                                    if (downloadProgressBar3.getString().contains("100.0%")) {
                                        CMD.sleep(0.2);
                                    }
                                } else if (downloadProgressBar3.getValue() == 100) {
                                    downloadProgressBar3.setValue(0);
                                    downloadProgressBar3.setString(TEXTOS.pegarTexto("progressbar.baixando.audio") + progressPercentdownload2);
                                    downloadDone3 = true;
                                } else {
                                    if (line3.contains("[download] " + getPastaPrincipal())) {
                                        lblResultado3.setVisible(true);
                                        lblResultado3.setText(TEXTOS.pegarTexto("label.resultado.aviso.ja.baixado"));
                                        lblResultado3.setForeground(CORES.pegarCor(isNoturno(), 8));
                                    } else if (line3.startsWith("[youtube]")) {
                                        configurarCores();
                                        lblResultado3.setText(TEXTOS.pegarTexto("label.resultado.verificando.download"));
                                    } else {
                                        if (line3.startsWith("[ffmpeg]") && line3.endsWith("skipping")) {
                                            continue;
                                        }
                                        configurarCores();
                                        lblResultado3.setText(line3);
                                    }
                                }
                            } else if (downloadDone3) {
                                lblProgressBar3.setVisible(false);
                                lblResultado3.setVisible(true);
                                lblResultado3.setText(TEXTOS.pegarTexto("label.resultado.download.concluido"));
                                lblResultado3.setForeground(CORES.pegarCor(isNoturno(), 9));

                                downloadProgressBar3.setVisible(false);
                                downloadProgressBar3.setString(TEXTOS.pegarTexto("label.resultado.baixando"));
                            }

                            setCmdLineSaida3(cmdLineSaida3 + line3 + "\n");

                        }
                        if (line3 == null) {
                            downloadProgressBar3.setString(TEXTOS.pegarTexto("label.resultado.baixando"));
                            lblProgressBar3.setVisible(false);
                            lblResultado3.setVisible(true);
                            while ((line3 = read6.readLine()) != null) {
                                if (line3.contains("DownloadError:")) {
                                    lblResultado3.setForeground(CORES.pegarCor(isNoturno(), 7));
                                    lblResultado3.setText(TEXTOS.pegarTexto("label.resultado.falha.download"));
                                } else {
                                    lblResultado3.setText(String.format("<html>%s</html>", line3));
                                    lblResultado3.setForeground(CORES.pegarCor(isNoturno(), 7));
                                }

                                setCmdLineSaida3(cmdLineSaida3 + line3 + "\n");

                            }

                            read5.close();
                            read6.close();
                        }

                    } catch (IOException e) {
                        downloadProgressBar3.setString(TEXTOS.pegarTexto("label.resultado.baixando"));

                        lblProgressBar3.setVisible(false);
                        lblResultado3.setVisible(true);
                        if (e.toString().contains("Stream closed")) {
                            lblResultado3.setText(TEXTOS.pegarTexto("label.resultado.download.cancelado"));
                            lblResultado3.setForeground(CORES.pegarCor(isNoturno(), 8));
                            downloadProgressBar3.setVisible(false);
                            isBaixando3 = false;
                            if (isBaixando && isBaixando2) {
                                btnBaixa3.setVisible(true);
                            }
                            if (!isBaixando && !isBaixando2 && !isBaixando3) {
                                btnCancelar.setVisible(false);
                            }
                            CMD.sleep(3);
                            lblResultado3.setVisible(false);
                            downloadProgressBar3.setVisible(false);
                            lblProgressBar3.setVisible(false);
                            return;
                        } else {
                            lblResultado3.setText(String.format("<html>%s</html>", e.toString()));
                            lblResultado3.setForeground(CORES.pegarCor(isNoturno(), 7));
                            downloadProgressBar3.setVisible(false);
                            isBaixando3 = false;
                            if (isBaixando && isBaixando2) {
                                btnBaixa3.setVisible(true);
                            }
                            if (!isBaixando && !isBaixando2 && !isBaixando3) {
                                btnCancelar.setVisible(false);
                            }
                            CMD.sleep(3);
                            lblResultado3.setVisible(false);
                            downloadProgressBar3.setVisible(false);
                            lblProgressBar3.setVisible(false);
                            return;
                        }

                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if (cmdLineSaida3.equals("")) {
                lblProgressBar3.setVisible(false);
                lblResultado3.setVisible(true);
                lblResultado3.setForeground(CORES.pegarCor(isNoturno(), 7));
                lblResultado3.setText(TEXTOS.pegarTexto("label.resultado.falha.download"));
            }
            downloadProgressBar3.setString(TEXTOS.pegarTexto("label.resultado.baixando"));

            lblProgressBar3.setVisible(false);
            downloadProgressBar3.setVisible(false);
            lblResultado3.setVisible(true);
            isBaixando3 = false;
            if (isBaixando && isBaixando2) {
                btnBaixa3.setVisible(true);
            }
            if (!isBaixando && !isBaixando2 && !isBaixando3) {
                btnCancelar.setVisible(false);
            }
            CMD.sleep(3);
            lblResultado3.setVisible(false);
            downloadProgressBar3.setVisible(false);
            lblProgressBar3.setVisible(false);
        });
        download3.start();
    }
}
