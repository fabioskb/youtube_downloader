/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aplicacao.eventos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import metodos.YoutubeArquivo;

/**
 *
 * @author fabio
 */
public class Download extends YoutubeEventosPainelDireita {

    
    public String check(boolean downloadDone, String saida) {
        downloadDone = false;
        cmdLineSaida = "";
        link = txtLink.getText();

        if ((video || audio) && !lstPesquisa.isSelectionEmpty()) {
            txtLink.setText(TEXTOS.pegarTexto("fieldtext.link"));
            index = lstPesquisa.getSelectedIndex();
            link = links[index];
        } else if (!video && !audio) {
            lblResultado.setText(TEXTOS.pegarTexto("label.resultado.aviso.video"));
            lblResultado.setForeground(CORES.pegarCor(noturno, 8));
        } else if (!lstPesquisa.isSelectedIndex(index) && !link.startsWith("https://www.youtube.com/watch?v=")) {
            lblResultado.setForeground(CORES.pegarCor(noturno, 8));
            lblResultado.setText(TEXTOS.pegarTexto("label.resultado.aviso.item"));
        }
        return link;
    }

    public void format(String link, String scriptDownPath, String scriptTitlePath) {
        downloadPath = (video) ? TEXTOS.pegarTexto("label.resultado.downloadvideo.concluido")
                : TEXTOS.pegarTexto("label.resultado.downloadaudio.concluido");
        
        configurarCores();
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
            YoutubeArquivo scriptBaixar = new YoutubeArquivo(scriptDownPath, false);
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

            YoutubeArquivo pegarTitulos = new YoutubeArquivo(scriptTitlePath, false);
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
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void buttonsSettings(JButton btnPadrao, JButton btnSecundario, JButton btnTerciario,
            boolean isBaixandoPadrao, boolean isBaixandoSecundario, boolean isBaixandoTerciario,
            boolean isFinalSettings, boolean isCancelBtnSet) {
        if (isFinalSettings) {
            isCancelBtnSet = false;
        }
        if (isFinalSettings || isCancelBtnSet) {
            if (isFinalSettings && btnCancelar.isVisible()) {
                isBaixandoPadrao = false;
                if (isBaixandoSecundario && isBaixandoTerciario) {
                    btnPadrao.setVisible(true);
                }
            } else if (isCancelBtnSet) {
                isBaixandoPadrao = false;
                if (!isBaixando && !isBaixando2 && !isBaixando3) {
                    btnCancelar.setVisible(false);
                    downloadProgressBar.setVisible(false);
                    downloadProgressBar2.setVisible(false);
                    downloadProgressBar3.setVisible(false);
                    pro1.destroy();
                    pro2.destroy();
                    pro3.destroy();
                }
            }
        } else {
            isBaixandoPadrao = true;
            btnPadrao.setVisible(false);
            if (!isBaixandoSecundario) {
                btnSecundario.setVisible(true);
            } else if (!isBaixandoTerciario) {
                btnTerciario.setVisible(true);
            }
        }
    }

    public String title(JLabel resultLbl, String videoTitle, String script,
            String formatedTitle) {

        resultLbl.setVisible(true);
        resultLbl.setText(TEXTOS.pegarTexto("label.resultado.pegandotitulo"));
        configurarCores();
        videoTitle = CMD.comandoDownload("python3 " + script);
        try {
            int tituloTam = (videoTitle.length() > 50)
                    ? videoTitle.length() - (videoTitle.length() - 50)
                    : videoTitle.length() - 1;
            formatedTitle = (videoTitle.length() > 50)
                    ? videoTitle.substring(0, tituloTam) + "..."
                    : videoTitle.substring(0, tituloTam);
        } catch (StringIndexOutOfBoundsException s) {
            formatedTitle = "...";
            s.printStackTrace();
        }
        if (formatedTitle.startsWith("Traceback (most recent call last):")) {
            formatedTitle = "...";
        }
        resultLbl.setText(verifyingDownload + " " + formatedTitle);
        return formatedTitle;
    }

    public List<Object> processes(Process pro, Runtime run, String scriptDown, BufferedReader reader,
            BufferedReader reader2) {
        try {
            pro = run.exec("python3 " + scriptDown);
        } catch (IOException ex) {
            Logger.getLogger(YoutubeEventosBaixar.class.getName()).log(Level.SEVERE, null, ex);
        }
        reader = new BufferedReader(new InputStreamReader(pro.getInputStream()));
        reader2 = new BufferedReader(new InputStreamReader(pro.getErrorStream()));
        List<Object> pros = new ArrayList<>(List.of(reader, reader2, pro));
        btnCancelar.setVisible(true);

        return pros;
    }

    public String downloader(String line, String commandOut, BufferedReader reader,
            BufferedReader reader2, JProgressBar progressBarPadrao, JProgressBar progressBarSecundario,
            JProgressBar progressBarTerciario, JLabel resultLbl, boolean downloadDone,
            boolean isBaixandoPadrao, boolean isBaixandoSecundario, boolean isBaixandoTerciario,
            String formatedTitle, String script, JButton btnPadrao,
            JButton btnSecundario, JButton btnTerciario) {

        try {
            progressBarPadrao.setValue(0);
            while ((line = reader.readLine()) != null) {
                if ((!line.startsWith("[download] 100%") && !line.contains("Deleting"))) {
                    String progressPercentdownload = "", progressPercentdownload2 = "";
                    if (line.contains("%")) {
                        progressBarPadrao.setVisible(true);
                        progressPercentdownload = line.substring(line.indexOf(" "), line.indexOf(".")).strip();
                        progressPercentdownload2 = line.substring(line.indexOf(" ")) + "  ";
                        if (!downloadDone) {
                            progressBarPadrao.setString(formatedTitle + progressPercentdownload2);
                        }
                        progressBarPadrao.setValue(Integer.parseInt(progressPercentdownload.strip()));
                        if (progressBarPadrao.getString().contains("100.0%")) {
                            CMD.sleep(0.2);
                        }
                    } else if (progressBarPadrao.getValue() == 100) {
                        progressBarPadrao.setValue(0);
                        progressBarPadrao.setString(TEXTOS.pegarTexto("progressbar.baixando.audio") + progressPercentdownload2);
                        downloadDone = true;
                    } else {
                        if (line.contains("[download] " + pastaVideo)
                                || line.contains("[download] " + pastaAudio)) {
                            resultLbl.setText(TEXTOS.pegarTexto("label.resultado.aviso.ja.baixado"));
                            resultLbl.setForeground(CORES.pegarCor(noturno, 8));
                        } else if (line.startsWith("[youtube]")) {
                            resultLbl.setText(verifyingDownload + " " + formatedTitle);
                            configurarCores();
                        } else {
                            resultLbl.setText(line);
                            if (line.startsWith("[ffmpeg]") && line.endsWith("skipping")) {
                                continue;
                            }
                        }
                    }
                } else if (downloadDone) {
                    resultLbl.setText(downloadPath + "/" + formatedTitle);
                    resultLbl.setForeground(CORES.pegarCor(noturno, 9));
                    progressBarPadrao.setVisible(false);
                    progressBarPadrao.setString(TEXTOS.pegarTexto("label.resultado.baixando"));
                    progressBarPadrao.setValue(0);
                    isBaixandoPadrao = false;
                    return resultLbl.getText();
                }
                commandOut = commandOut + line + "\n";
            }
            if (line == null) {
                progressBarPadrao.setString(TEXTOS.pegarTexto("label.resultado.baixando"));
                while ((line = reader2.readLine()) != null) {
                    if (line.contains("DownloadError:")) {
                        resultLbl.setForeground(CORES.pegarCor(noturno, 7));
                        resultLbl.setText(TEXTOS.pegarTexto("label.resultado.falha.download"));
                    } else {
                        resultLbl.setText(String.format("<html>%s</html>", line));
                        resultLbl.setForeground(CORES.pegarCor(noturno, 7));
                    }
                    commandOut = commandOut + line + "\n";
                }
                CMD.sleep(0.5);
                isBaixandoPadrao = false;
                buttonsSettings(btnPadrao, btnSecundario, btnTerciario, isBaixandoPadrao,
                        isBaixandoSecundario, isBaixandoTerciario, false, true);
                progressBarPadrao.setVisible(false);
                return resultLbl.getText();
            }
            reader.close();
            reader2.close();
        } catch (IOException ex) {
            progressBarPadrao.setString(TEXTOS.pegarTexto("label.resultado.baixando"));
            if (ex.toString().contains("Stream closed")) {
                resultLbl.setText(TEXTOS.pegarTexto("label.resultado.download.cancelado"));
                resultLbl.setForeground(CORES.pegarCor(noturno, 8));
                progressBarPadrao.setVisible(false);
//                isBaixandoPadrao = false;
                CMD.sleep(0.5);
                buttonsSettings(btnPadrao, btnSecundario, btnTerciario, isBaixandoPadrao,
                        isBaixandoSecundario, isBaixandoTerciario, false, true);
                return resultLbl.getText();
            } else {
                resultLbl.setText(String.format("<html>%s</html>", ex.toString()));
                resultLbl.setForeground(CORES.pegarCor(noturno, 7));
                progressBarPadrao.setVisible(false);
                buttonsSettings(btnPadrao, btnSecundario, btnTerciario, isBaixandoPadrao,
                        isBaixandoSecundario, isBaixandoTerciario, true, false);
                CMD.sleep(0.5);
                buttonsSettings(btnPadrao, btnSecundario, btnTerciario, isBaixandoPadrao,
                        isBaixandoSecundario, isBaixandoTerciario, false, true);
                isBaixandoPadrao = false;
                return resultLbl.getText();
            }
        }

        if (commandOut.equals("")) {
            resultLbl.setForeground(CORES.pegarCor(noturno, 7));
            resultLbl.setText(TEXTOS.pegarTexto("label.resultado.falha.download"));
        } else {
            configurarCores();
            resultLbl.setText(cmdLineSaida);
        }
        progressBarPadrao.setString(TEXTOS.pegarTexto("label.resultado.baixando"));
        progressBarPadrao.setVisible(false);
        isBaixandoPadrao = false;

        return commandOut;
    }

    protected void downsThreadStop() {
        Object[] downs = new Object[3];
        if (download != null) {
            downs[0] = download;
        }
        if (download2 != null) {
            downs[1] = download2;
        }
        if (download3 != null) {
            downs[2] = download3;
        }
        for (Object down : downs) {
            if (down instanceof Thread) {
                ((Thread) down).stop();
            }
        }
    }
}
