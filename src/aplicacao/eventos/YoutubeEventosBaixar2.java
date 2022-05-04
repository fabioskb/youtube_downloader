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
public class YoutubeEventosBaixar2 extends YoutubeEventosBaixar {


    @Override
    protected void btnBaixar2Click(ActionEvent ev) {
        Thread download2 = new Thread(() -> {
            System.out.println("exec!");
            downloadDone2 = false;
            setCmdLineSaida2("");
            String link = getTxtLink().getText();

            if ((isVideo() || isAudio()) && !getLstPesquisa().isSelectionEmpty()) {
                getTxtLink().setText(TEXTOS.pegarTexto("fieldtext.link"));
                setIndex(getLstPesquisa().getSelectedIndex());
                link = getLinks()[getIndex()];
            } else if (!isVideo() && !isAudio()) {
                lblResultado2.setForeground(CORES.pegarCor(isNoturno(), 8));
                lblResultado2.setText(TEXTOS.pegarTexto("label.resultado.aviso.video"));
                return;
            } else if (!getLstPesquisa().isSelectedIndex(getIndex()) && !link.startsWith("https://www.youtube.com/watch?v=")) {
                lblResultado2.setForeground(CORES.pegarCor(isNoturno(), 8));
                lblResultado2.setText(TEXTOS.pegarTexto("label.resultado.aviso.item"));
                return;
            }

            if (link.startsWith("https://www.youtube.com/watch?v=")) {
                try {
                    isBaixando2 = true;
                    btnBaixa2.setVisible(false);
                    if (!isBaixando3) { btnBaixa3.setVisible(true); } 
                    else if (!isBaixando) { btnBaixa.setVisible(true); }
                    lblResultado2.setText(TEXTOS.pegarTexto("label.resultado.verificando.download"));
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
                    YoutubeArquivo scriptBaixar2 = new YoutubeArquivo("/tmp/baixar2");
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
                            link, getFormat()));

                    try {
                        setPro2(RUN_2.exec("python3 /tmp/baixar2"));
                        setRead3(new BufferedReader(new InputStreamReader(pro2.getInputStream())));
                        setRead4(new BufferedReader(new InputStreamReader(pro2.getErrorStream())));

                        while ((line2 = read3.readLine()) != null) {
                            System.out.println("Executando 2");
                            getBtnCancelar().setVisible(true);
                            if ((!line2.startsWith("[download] 100%") && !line2.contains("Deleting"))) {
                                String progressPercentdownload = "", progressPercentdownload2 = "";
                                if (line2.contains("%")) {
                                    downloadProgressBar2.setVisible(true);
                                    lblProgressBar2.setVisible(false);
                                    lblResultado2.setVisible(false);
                                    progressPercentdownload = line2.substring(line2.indexOf(" "), line2.indexOf(".")).strip();
                                    progressPercentdownload2 = line2.substring(line2.indexOf(" ")) + "  ";
                                    if (!downloadDone2) {
                                        downloadProgressBar2.setString(TEXTOS.pegarTexto("label.resultado.baixando") + progressPercentdownload2);
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
                                    if (line2.contains("[download] " + getPastaPrincipal())) {
                                        lblResultado2.setVisible(true);
                                        lblResultado2.setText(TEXTOS.pegarTexto("label.resultado.aviso.ja.baixado"));
                                        lblResultado2.setForeground(CORES.pegarCor(isNoturno(), 8));
                                    } else if (line2.startsWith("[youtube]")) {
                                        configurarCores();
                                        lblResultado2.setText(TEXTOS.pegarTexto("label.resultado.verificando.download"));
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
                                lblResultado2.setText(TEXTOS.pegarTexto("label.resultado.download.concluido"));
                                lblResultado2.setForeground(CORES.pegarCor(isNoturno(), 9));
                                
                                downloadProgressBar2.setVisible(false);
                                downloadProgressBar2.setString(TEXTOS.pegarTexto("label.resultado.baixando"));
                            }

                            setCmdLineSaida2(cmdLineSaida2 + line2 + "\n");

                        }
                        if (line2 == null) {
                            downloadProgressBar2.setString(TEXTOS.pegarTexto("label.resultado.baixando"));
                            lblProgressBar2.setVisible(false);
                            lblResultado2.setVisible(true);
                            while ((line2 = read4.readLine()) != null) {
                                if (line2.contains("DownloadError:")) {
                                    lblResultado2.setForeground(CORES.pegarCor(isNoturno(), 7));
                                    lblResultado2.setText(TEXTOS.pegarTexto("label.resultado.falha.download"));
                                    System.out.println(line2);
                                } else {
                                    lblResultado2.setText(String.format("<html>%s</html>", line2));
                                    lblResultado2.setForeground(CORES.pegarCor(isNoturno(), 7));
                                    System.out.println(line2);
                                }

                                setCmdLineSaida2(cmdLineSaida2 + line2 + "\n");

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
                            lblResultado2.setForeground(CORES.pegarCor(isNoturno(), 8));
                            downloadProgressBar2.setVisible(false);
                            isBaixando2 = false;
                            if (isBaixando && isBaixando3) { btnBaixa2.setVisible(true); }
                            if (!isBaixando && !isBaixando2 && !isBaixando3) { btnCancelar.setVisible(false); }
                            return;
                        } else {
                            lblResultado2.setText(String.format("<html>%s</html>", e.toString()));
                            lblResultado2.setForeground(CORES.pegarCor(isNoturno(), 7));
                            downloadProgressBar2.setVisible(false);
                            isBaixando2 = false;
                            if (isBaixando && isBaixando3) { btnBaixa2.setVisible(true); }
                            if (!isBaixando && !isBaixando2 && !isBaixando3) { btnCancelar.setVisible(false); }
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
                lblResultado2.setForeground(CORES.pegarCor(isNoturno(), 7));
                lblResultado2.setText(TEXTOS.pegarTexto("label.resultado.falha.download"));
            }
            downloadProgressBar2.setString(TEXTOS.pegarTexto("label.resultado.baixando"));
            
            lblProgressBar2.setVisible(false);
            downloadProgressBar2.setVisible(false);
            lblResultado2.setVisible(true);
            isBaixando2 = false;
            if (isBaixando && isBaixando3) { btnBaixa2.setVisible(true); }
            if (!isBaixando && !isBaixando2 && !isBaixando3) { btnCancelar.setVisible(false); }
        });
        download2.start();
    }
}
