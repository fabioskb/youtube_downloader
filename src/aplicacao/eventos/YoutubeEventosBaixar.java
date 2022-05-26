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
public class YoutubeEventosBaixar extends YoutubeEventosPainelDireita {

    @Override
    protected void btnBaixarClick(ActionEvent ev) {
        download = new Thread(() -> {
            downloadDone = false;
            cmdLineSaida = "";
            String link = txtLink.getText();

            if ((video || audio) && !lstPesquisa.isSelectionEmpty()) {
                txtLink.setText(TEXTOS.pegarTexto("fieldtext.link"));
                index = lstPesquisa.getSelectedIndex();
                link = links[index];
                downloadPath = (video) ? TEXTOS.pegarTexto("label.resultado.downloadvideo.concluido") 
                        : TEXTOS.pegarTexto("label.resultado.downloadaudio.concluido");
            } else if (!video && !audio) {
                lblResultado.setText(TEXTOS.pegarTexto("label.resultado.aviso.video"));
                lblResultado.setForeground(CORES.pegarCor(noturno, 8));
                return;
            } else if (!lstPesquisa.isSelectedIndex(index) && !link.startsWith("https://www.youtube.com/watch?v=")) {
                lblResultado.setForeground(CORES.pegarCor(noturno, 8));
                lblResultado.setText(TEXTOS.pegarTexto("label.resultado.aviso.item"));
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
                    YoutubeArquivo scriptBaixar = new YoutubeArquivo("/tmp/baixar", false);
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
                            link, format));
                
                    YoutubeArquivo pegarTitulos = new YoutubeArquivo("/tmp/titulo", false);
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
                    isBaixando = true;
                    btnBaixa.setVisible(false);
                    if (!isBaixando2) {
                        btnBaixa2.setVisible(true);
                    } else if (!isBaixando3) {
                        btnBaixa3.setVisible(true);
                    }
                    lblResultado.setText(TEXTOS.pegarTexto("label.resultado.pegandotitulo"));
                    configurarCores();
                    tituloVideo = CMD.comando("python3 /tmp/titulo");
                    
                    try {
                        int tituloTam = (tituloVideo.length() > 50) 
                                ? tituloVideo.length() - (tituloVideo.length() - 50) 
                                : tituloVideo.length()-1;
                        tituloFormatado = (tituloVideo.length() > 50) 
                                ? tituloVideo.substring(0, tituloTam)+"..."
                                : tituloVideo.substring(0, tituloTam);
                    } catch (StringIndexOutOfBoundsException s) {
                        s.printStackTrace();
                    }
//                    pegarTitulos.deletar();

                    try {
                        lblResultado.setText(String.format("%s %s", 
                        TEXTOS.pegarTexto("label.resultado.verificando.download"), tituloFormatado));
                        pro1 = RUN_1.exec("python3 /tmp/baixar");
                        read = new BufferedReader(new InputStreamReader(pro1.getInputStream()));
                        read2 = new BufferedReader(new InputStreamReader(pro1.getErrorStream()));
                        
                        btnCancelar.setVisible(true);


                        while ((line = read.readLine()) != null) {
                            if ((!line.startsWith("[download] 100%") && !line.contains("Deleting"))) {
                                String progressPercentdownload = "", progressPercentdownload2 = "";
                                if (line.contains("%")) {
                                    lblProgressBar.setVisible(false);
                                    lblResultado.setVisible(false);
                                    downloadProgressBar.setVisible(true);
                                    progressPercentdownload = line.substring(line.indexOf(" "), line.indexOf(".")).strip();
                                    progressPercentdownload2 = line.substring(line.indexOf(" ")) + "  ";
                                    if (!downloadDone) {
                                        downloadProgressBar.setString(tituloFormatado + progressPercentdownload2);
                                    }
                                    downloadProgressBar.setValue(Integer.parseInt(progressPercentdownload.strip()));
                                    if (downloadProgressBar.getString().contains("100.0%")) {
                                        CMD.sleep(0.2);
                                    }
                                } else if (downloadProgressBar.getValue() == 100) {
                                    downloadProgressBar.setValue(0);
                                    downloadProgressBar.setString(TEXTOS.pegarTexto("progressbar.baixando.audio") + progressPercentdownload2);
                                    downloadDone = true;
                                } else {
                                    if (line.contains("[download] " + pastaPrincipal)) {
                                        lblResultado.setVisible(true);
                                        lblResultado.setText(TEXTOS.pegarTexto("label.resultado.aviso.ja.baixado"));
                                        lblResultado.setForeground(CORES.pegarCor(noturno, 8));
                                    } else if (line.startsWith("[youtube]")) {
                                        configurarCores();
                                        lblResultado.setText(String.format("%s (%s)", TEXTOS.pegarTexto("label.resultado.verificando.download"), 
                                                tituloFormatado));
                                    } else {
                                        if (line.startsWith("[ffmpeg]") && line.endsWith("skipping")) {
                                            continue;
                                        }
                                        configurarCores();
                                        lblResultado.setText(line);
                                    }
                                }
                            } else if (downloadDone) {
                                lblProgressBar.setVisible(false);
                                lblResultado.setVisible(true);
                                lblResultado.setText(downloadPath);
                                lblResultado.setForeground(CORES.pegarCor(noturno, 9));

                                downloadProgressBar.setVisible(false);
                                downloadProgressBar.setString(TEXTOS.pegarTexto("label.resultado.baixando"));
                            }

                            cmdLineSaida = cmdLineSaida + line + "\n";

                        }
                        if (line == null) {
                            downloadProgressBar.setString(TEXTOS.pegarTexto("label.resultado.baixando"));
                            lblProgressBar.setVisible(false);
                            lblResultado.setVisible(true);
                            while ((line = read2.readLine()) != null) {
                                if (line.contains("DownloadError:")) {
                                    lblResultado.setForeground(CORES.pegarCor(noturno, 7));
                                    lblResultado.setText(TEXTOS.pegarTexto("label.resultado.falha.download"));
                                } else {
                                    lblResultado.setText(String.format("<html>%s</html>", line));
                                    lblResultado.setForeground(CORES.pegarCor(noturno, 7));
                                }

                                cmdLineSaida = cmdLineSaida + line + "\n";

                            }

                        }
                        read.close();
                        read2.close();

                    } catch (IOException e) {
                        downloadProgressBar.setString(TEXTOS.pegarTexto("label.resultado.baixando"));

                        lblProgressBar.setVisible(false);
                        lblResultado.setVisible(true);
                        if (e.toString().contains("Stream closed")) {
                            lblResultado.setText(TEXTOS.pegarTexto("label.resultado.download.cancelado"));
                            lblResultado.setForeground(CORES.pegarCor(noturno, 8));
                            downloadProgressBar.setVisible(false);
                            isBaixando = false;
                            CMD.sleep(0.5);
                            if (!isBaixando && !isBaixando2 && !isBaixando3) {
                                btnCancelar.setVisible(false);
                                lblResultado3.setVisible(false);
                                downloadProgressBar3.setVisible(false);
                                lblProgressBar3.setVisible(false);
                                lblResultado2.setVisible(false);
                                downloadProgressBar2.setVisible(false);
                                lblProgressBar2.setVisible(false);
                            }
                            return;
                        } else {
                            lblResultado.setText(String.format("<html>%s</html>", e.toString()));
                            lblResultado.setForeground(CORES.pegarCor(noturno, 7));
                            downloadProgressBar.setVisible(false);
                            isBaixando = false;
                            if (isBaixando2 && isBaixando3) {
                                btnBaixa.setVisible(true);
                            }
                            CMD.sleep(0.5);
                            if (!isBaixando && !isBaixando2 && !isBaixando3) {
                                btnCancelar.setVisible(false);
                                lblResultado3.setVisible(false);
                                downloadProgressBar3.setVisible(false);
                                lblProgressBar3.setVisible(false);
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
            if (cmdLineSaida.equals("")) {
                lblProgressBar.setVisible(false);
                lblResultado.setVisible(true);
                lblResultado.setForeground(CORES.pegarCor(noturno, 7));
                lblResultado.setText(TEXTOS.pegarTexto("label.resultado.falha.download"));
            }
            downloadProgressBar.setString(TEXTOS.pegarTexto("label.resultado.baixando"));

            lblProgressBar.setVisible(false);
            downloadProgressBar.setVisible(false);
            lblResultado.setVisible(true);
            isBaixando = false;
            if (isBaixando2 && isBaixando3) {
                btnBaixa.setVisible(true);
            }
            CMD.sleep(0.5);
            if (!isBaixando && !isBaixando2 && !isBaixando3) {
                btnCancelar.setVisible(false);
                lblResultado3.setVisible(false);
                downloadProgressBar3.setVisible(false);
                lblProgressBar3.setVisible(false);
                lblResultado2.setVisible(false);
                downloadProgressBar2.setVisible(false);
                lblProgressBar2.setVisible(false);
            }
        });
        download.start();
    }
}
