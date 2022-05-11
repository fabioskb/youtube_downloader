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
public class YoutubeEventosBaixar2 extends YoutubeEventosBaixar {

    @Override
    protected void btnBaixar2Click(ActionEvent ev) {
        download2 = new Thread(() -> {
            downloadDone2 = false;
            cmdLineSaida2 = "";
            String link = txtLink.getText();
            lblResultado2.setVisible(true);

            if ((video || audio) && !lstPesquisa.isSelectionEmpty()) {
                txtLink.setText(TEXTOS.pegarTexto("fieldtext.link"));
                index = lstPesquisa.getSelectedIndex();
                link = links[index];
                downloadPath = (video) ? TEXTOS.pegarTexto("label.resultado.downloadvideo.concluido") 
                        : TEXTOS.pegarTexto("label.resultado.downloadaudio.concluido");
            } else if (!video && !audio) {
                lblResultado2.setForeground(CORES.pegarCor(noturno, 8));
                lblResultado2.setText(TEXTOS.pegarTexto("label.resultado.aviso.video"));
                return;
            } else if (!lstPesquisa.isSelectedIndex(index) && !link.startsWith("https://www.youtube.com/watch?v=")) {
                lblResultado2.setForeground(CORES.pegarCor(noturno, 8));
                lblResultado2.setText(TEXTOS.pegarTexto("label.resultado.aviso.item"));
                return;
            }
            if (link.startsWith("https://www.youtube.com/watch?v=")) {
                try {
                    format = (video) ? String.format("{'format': 'bestvideo[ext=mp4]+bestaudio[ext=m4a]',\n"
                            + "'outtmpl': '%s' + title + '.mp4'}", pastaVideo) // format video para o YoutubeDL
                            : String.format("{'format': 'bestaudio[ext=m4a]',\n"
                                    + "'outtmpl': '%s' + title + '.mp3',\n"
                                    + "'postprocessors': [{\n"
                                    + "    'key': 'FFmpegExtractAudio',\n" // format audio para o YoutubeDL
                                    + "    'preferredcodec': 'mp3',\n"
                                    + "    'preferredquality': '0'\n"
                                    + "    }]\n"
                                    + "}", pastaAudio);
                    YoutubeArquivo scriptBaixar2 = new YoutubeArquivo("/tmp/baixar2", false);
                    scriptBaixar2.criar(String.format(
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
                    btnBaixa2.setVisible(false);
                    if (!isBaixando3) {
                        btnBaixa3.setVisible(true);
                    } else if (!isBaixando) {
                        btnBaixa.setVisible(true);
                    }
                    lblResultado2.setText(TEXTOS.pegarTexto("label.resultado.pegandotitulo"));
                    configurarCores();
                    tituloVideo2 = CMD.comando("python3 /tmp/titulo.py");
                    try {
                        int tituloTam = (tituloVideo2.length() > 50) ? tituloVideo2.length() - 20 
                                : tituloVideo2.length()-1;
                        tituloFormatado = (tituloVideo2.length() > 50) ? tituloVideo2.substring(0, tituloTam)+"..."
                                : tituloVideo2.substring(0, tituloTam);
                    } catch (StringIndexOutOfBoundsException s) {
                        s.printStackTrace();
                    }

                    try {
                        pro2 = RUN_2.exec("python3 /tmp/baixar2");
                        read3 = new BufferedReader(new InputStreamReader(pro2.getInputStream()));
                        read4 = new BufferedReader(new InputStreamReader(pro2.getErrorStream()));
                        isBaixando2 = true;
                        btnCancelar.setVisible(true);

                        lblResultado2.setText(TEXTOS.pegarTexto("label.resultado.verificando.download")
                        + " (" + tituloFormatado + ")");
                        configurarCores();
                        
                        while ((line2 = read3.readLine()) != null) {
                            if ((!line2.startsWith("[download] 100%") && !line2.contains("Deleting"))) {
                                String progressPercentdownload = "", progressPercentdownload2 = "";
                                if (line2.contains("%")) {
                                    lblProgressBar2.setVisible(false);
                                    lblResultado2.setVisible(false);
                                    downloadProgressBar2.setVisible(true);
                                    progressPercentdownload = line2.substring(line2.indexOf(" "), line2.indexOf(".")).strip();
                                    progressPercentdownload2 = line2.substring(line2.indexOf(" ")) + "  ";
                                    if (!downloadDone2) {
                                        downloadProgressBar2.setString(tituloFormatado + progressPercentdownload2);
                                    }
                                    downloadProgressBar2.setValue(Integer.parseInt(progressPercentdownload.strip()));
                                    if (downloadProgressBar2.getString().contains("100.0%")) {
                                        CMD.sleep(0.2);
                                    }
                                } else if (downloadProgressBar2.getValue() == 100) {
                                    downloadProgressBar2.setValue(0);
                                    downloadProgressBar2.setString(TEXTOS.pegarTexto("progressbar.baixando.audio") + progressPercentdownload2);
                                    downloadDone2 = true;
                                } else {
                                    if (line2.contains("[download] " + pastaPrincipal)) {
                                        lblResultado2.setVisible(true);
                                        lblResultado2.setText(TEXTOS.pegarTexto("label.resultado.aviso.ja.baixado"));
                                        lblResultado2.setForeground(CORES.pegarCor(noturno, 8));
                                    } else if (line2.startsWith("[youtube]")) {
                                        configurarCores();
                                        lblResultado2.setText(TEXTOS.pegarTexto("label.resultado.verificando.download")
                                        + " (" + tituloFormatado + ")");
                                    } else {
                                        if (line2.startsWith("[ffmpeg]") && line2.endsWith("skipping")) {
                                            continue;
                                        }
                                        configurarCores();
                                        lblResultado2.setText(line2);
                                    }
                                }
                            } else if (downloadDone2) {
                                lblProgressBar2.setVisible(false);
                                lblResultado2.setVisible(true);
                                lblResultado2.setText(downloadPath);
                                lblResultado2.setForeground(CORES.pegarCor(noturno, 9));

                                downloadProgressBar2.setVisible(false);
                                downloadProgressBar2.setString(TEXTOS.pegarTexto("label.resultado.baixando"));
                            }

                            cmdLineSaida2 = cmdLineSaida2 + line2 + "\n";

                        }
                        if (line2 == null) {
                            downloadProgressBar2.setString(TEXTOS.pegarTexto("label.resultado.baixando"));
                            lblProgressBar2.setVisible(false);
                            lblResultado2.setVisible(true);
                            while ((line2 = read4.readLine()) != null) {
                                if (line2.contains("DownloadError:")) {
                                    lblResultado2.setForeground(CORES.pegarCor(noturno, 7));
                                    lblResultado2.setText(TEXTOS.pegarTexto("label.resultado.falha.download"));
                                } else {
                                    lblResultado2.setText(String.format("<html>%s</html>", line2));
                                    lblResultado2.setForeground(CORES.pegarCor(noturno, 7));
                                }

                                cmdLineSaida2 = cmdLineSaida2 + line2 + "\n";

                            }

                            read3.close();
                            read4.close();
                        }

                    } catch (IOException e) {
                        downloadProgressBar2.setString(TEXTOS.pegarTexto("label.resultado.baixando"));

                        lblProgressBar2.setVisible(false);
                        lblResultado2.setVisible(true);
                        if (e.toString().contains("Stream closed")) {
                            lblResultado2.setText(TEXTOS.pegarTexto("label.resultado.download.cancelado"));
                            lblResultado2.setForeground(CORES.pegarCor(noturno, 8));
                            downloadProgressBar2.setVisible(false);
                            isBaixando2 = false;
                            if (!isBaixando && !isBaixando2 && !isBaixando3) {
                                btnCancelar.setVisible(false);
                                CMD.sleep(3);
                                lblResultado2.setVisible(false);
                                downloadProgressBar2.setVisible(false);
                                lblProgressBar2.setVisible(false);
                            }
                            return;
                        } else {
                            lblResultado2.setText(String.format("<html>%s</html>", e.toString()));
                            lblResultado2.setForeground(CORES.pegarCor(noturno, 7));
                            downloadProgressBar2.setVisible(false);
                            isBaixando2 = false;
                            if (isBaixando && isBaixando3) {
                                btnBaixa2.setVisible(true);
                            }
                            if (!isBaixando && !isBaixando2 && !isBaixando3) {
                                btnCancelar.setVisible(false);
                                CMD.sleep(3);
                                lblResultado2.setVisible(false);
                                downloadProgressBar2.setVisible(false);
                                lblProgressBar2.setVisible(false);
                            }
                            return;
                        }

                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if (cmdLineSaida2.equals("")) {
                lblProgressBar2.setVisible(false);
                lblResultado2.setVisible(true);
                lblResultado2.setForeground(CORES.pegarCor(noturno, 7));
                lblResultado2.setText(TEXTOS.pegarTexto("label.resultado.falha.download"));
            }
            downloadProgressBar2.setString(TEXTOS.pegarTexto("label.resultado.baixando"));

            lblProgressBar2.setVisible(false);
            downloadProgressBar2.setVisible(false);
            lblResultado2.setVisible(true);
            isBaixando2 = false;
            if (isBaixando && isBaixando3) {
                btnBaixa2.setVisible(true);
            }
            if (!isBaixando && !isBaixando2 && !isBaixando3) {
                btnCancelar.setVisible(false);
                CMD.sleep(3);
                lblResultado2.setVisible(false);
                downloadProgressBar2.setVisible(false);
                lblProgressBar2.setVisible(false);
            }
        });
        download2.start();
    }
}
