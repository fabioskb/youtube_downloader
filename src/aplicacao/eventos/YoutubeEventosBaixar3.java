package aplicacao.eventos;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import metodos.YoutubeArquivo;

/**
 * Classe responsável pelo evento que configura o Download e o executa. Além de
 * tratar os erros.
 */
public class YoutubeEventosBaixar3 extends YoutubeEventosBaixar2 {

    protected boolean downloadDone3;

    @Override
    protected void btnBaixar3Click(ActionEvent ev) {
        download3 = new Thread(() -> {
            downloadDone3 = false;
            cmdLineSaida3 = "";
            String link = txtLink.getText();
            lblResultado3.setVisible(true);

            if ((video || audio) && !lstPesquisa.isSelectionEmpty()) {
                txtLink.setText(TEXTOS.pegarTexto("fieldtext.link"));
                index = lstPesquisa.getSelectedIndex();
                link = links[index];
                downloadPath = (video) ? TEXTOS.pegarTexto("label.resultado.downloadvideo.concluido") 
                        : TEXTOS.pegarTexto("label.resultado.downloadaudio.concluido");
            } else if (!video && !audio) {
                lblResultado3.setForeground(CORES.pegarCor(noturno, 8));
                lblResultado3.setText(TEXTOS.pegarTexto("label.resultado.aviso.video"));
                return;
            } else if (!lstPesquisa.isSelectedIndex(index) && !link.startsWith("https://www.youtube.com/watch?v=")) {
                lblResultado3.setForeground(CORES.pegarCor(noturno, 8));
                lblResultado3.setText(TEXTOS.pegarTexto("label.resultado.aviso.item"));
                return;
            }
            if (link.startsWith("https://www.youtube.com/watch?v=")) {
                try {
                    format = ((video) ? String.format("{'format': 'bestvideo[ext=mp4]+bestaudio[ext=m4a]',\n"
                            + "'outtmpl': '%s' + title + '.mp4'}", pastaVideo) // format video para o YoutubeDL
                            : String.format("{'format': 'bestaudio[ext=m4a]',\n"
                                    + "'outtmpl': '%s' + title + '.mp3',\n"
                                    + "'postprocessors': [{\n"
                                    + "    'key': 'FFmpegExtractAudio',\n" // format audio para o YoutubeDL
                                    + "    'preferredcodec': 'mp3',\n"
                                    + "    'preferredquality': '0'\n"
                                    + "    }]\n"
                                    + "}", pastaAudio));
                    YoutubeArquivo scriptBaixar3 = new YoutubeArquivo("/tmp/baixar3", false);
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
                            link, format));

                    YoutubeArquivo pegarTitulos = new YoutubeArquivo("/tmp/titulo.py", false);
                    pegarTitulos.criar(String.format("#!/usr/bin/python3\n"
                            + "import os, time\n"
                            + "from youtube_search import YoutubeSearch\n\n"
                            + ""
                            + "link = '%s'\n"
                            + "results = YoutubeSearch(link, 1)\n\n"
                            + ""
                            + "for i in results.videos:\n"
                            + "  for k, v in i.items():\n"
                            + "    if k == 'title':\n"
                            + "      print(v)\n", link));
                    btnBaixa3.setVisible(false);
                    if (!isBaixando) {
                        btnBaixa.setVisible(true);
                    } else if (!isBaixando2) {
                        btnBaixa2.setVisible(true);
                    }
                    lblResultado3.setText(TEXTOS.pegarTexto("label.resultado.pegandotitulo"));
                    configurarCores();
                    tituloVideo3 = CMD.comando("python3 /tmp/titulo.py");
                    int tituloTam = (tituloVideo3.length() > 50) ? tituloVideo3.length() - 20 
                            : tituloVideo3.length()-1;
                    String tituloFormatado = (tituloVideo3.length() > 50) ? tituloVideo3.substring(0, tituloTam)+"..."
                            : tituloVideo3.substring(0, tituloTam);

                    try {
                        pro3 = RUN_3.exec("python3 /tmp/baixar3");
                        read5 = new BufferedReader(new InputStreamReader(pro3.getInputStream()));
                        read6 = new BufferedReader(new InputStreamReader(pro3.getErrorStream()));
                        isBaixando3 = true;
                        btnCancelar.setVisible(true);

                        lblResultado3.setText(TEXTOS.pegarTexto("label.resultado.verificando.download") 
                                + " (" + tituloFormatado + ")");
                        configurarCores();
                        
                        while ((line3 = read5.readLine()) != null) {
                            if ((!line3.startsWith("[download] 100%") && !line3.contains("Deleting"))) {
                                String progressPercentdownload = "", progressPercentdownload2 = "";
                                if (line3.contains("%")) {
                                    lblProgressBar3.setVisible(false);
                                    lblResultado3.setVisible(false);
                                    downloadProgressBar3.setVisible(true);
                                    progressPercentdownload = line3.substring(line3.indexOf(" "), line3.indexOf(".")).strip();
                                    progressPercentdownload2 = line3.substring(line3.indexOf(" ")) + "  ";
                                    if (!downloadDone3) {
                                        downloadProgressBar3.setString(tituloFormatado + progressPercentdownload2);
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
                                    if (line3.contains("[download] " + pastaPrincipal)) {
                                        lblResultado3.setVisible(true);
                                        lblResultado3.setText(TEXTOS.pegarTexto("label.resultado.aviso.ja.baixado"));
                                        lblResultado3.setForeground(CORES.pegarCor(noturno, 8));
                                    } else if (line3.startsWith("[youtube]")) {
                                        configurarCores();
                                        lblResultado3.setText(TEXTOS.pegarTexto("label.resultado.verificando.download")
                                        + " (" + tituloFormatado + ")");
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
                                lblResultado3.setText(downloadPath);
                                lblResultado3.setForeground(CORES.pegarCor(noturno, 9));

                                downloadProgressBar3.setVisible(false);
                                downloadProgressBar3.setString(TEXTOS.pegarTexto("label.resultado.baixando"));
                            }

                            cmdLineSaida3 = cmdLineSaida3 + line3 + "\n";

                        }
                        if (line3 == null) {
                            downloadProgressBar3.setString(TEXTOS.pegarTexto("label.resultado.baixando"));
                            lblProgressBar3.setVisible(false);
                            lblResultado3.setVisible(true);
                            while ((line3 = read6.readLine()) != null) {
                                if (line3.contains("DownloadError:")) {
                                    lblResultado3.setForeground(CORES.pegarCor(noturno, 7));
                                    lblResultado3.setText(TEXTOS.pegarTexto("label.resultado.falha.download"));
                                } else {
                                    lblResultado3.setText(String.format("<html>%s</html>", line3));
                                    lblResultado3.setForeground(CORES.pegarCor(noturno, 7));
                                }

                                cmdLineSaida3 = cmdLineSaida3 + line3 + "\n";

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
                            lblResultado3.setForeground(CORES.pegarCor(noturno, 8));
                            downloadProgressBar3.setVisible(false);
                            isBaixando3 = false;
                            if (!isBaixando && !isBaixando2 && !isBaixando3) {
                                btnCancelar.setVisible(false);
                                CMD.sleep(3);
                                lblResultado3.setVisible(false);
                                downloadProgressBar3.setVisible(false);
                                lblProgressBar3.setVisible(false);
                            }
                            return;
                        } else {
                            lblResultado3.setText(String.format("<html>%s</html>", e.toString()));
                            lblResultado3.setForeground(CORES.pegarCor(noturno, 7));
                            downloadProgressBar3.setVisible(false);
                            isBaixando3 = false;
                            if (isBaixando && isBaixando2) {
                                btnBaixa3.setVisible(true);
                            }
                            if (!isBaixando && !isBaixando2 && !isBaixando3) {
                                btnCancelar.setVisible(false);
                                CMD.sleep(3);
                                lblResultado3.setVisible(false);
                                downloadProgressBar3.setVisible(false);
                                lblProgressBar3.setVisible(false);
                            }
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
                lblResultado3.setForeground(CORES.pegarCor(noturno, 7));
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
