package aplicacao.eventos;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButtonMenuItem;
import metodos.YoutubeArquivo;

/**
 *
 * @author fabio
 */
@SuppressWarnings("serial")
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
            lblResultado.setForeground(CORES.pegarCorComBrilho(noturno, "Avisos"));
        } else if (!lstPesquisa.isSelectedIndex(index) && !link.startsWith("https://www.youtube.com/watch?v=")) {
            lblResultado.setForeground(CORES.pegarCorComBrilho(noturno, "Avisos"));
            lblResultado.setText(TEXTOS.pegarTexto("label.resultado.aviso.item"));
        }
        return link;
    }

    public void format(String link, String scriptDownPath, String scriptTitlePath, JButton btnPadrao,
            String ext) {

        btnPadrao.setVisible(false);
        configurarCores();

        try {
            if (schedulePesquisa.isDone()) {
                lblResultado.setText("");
            }

            if (ext.equals(extensao)) {
                extensao = video ? ".mp4" : ".mp3";
                downloadPath = video ? TEXTOS.pegarTexto("label.resultado.downloadvideo.concluido")
                        : TEXTOS.pegarTexto("label.resultado.downloadaudio.concluido");
            } else if (ext.equals(extensao2)) {
                extensao2 = video ? ".mp4" : ".mp3";
                downloadPath2 = video ? TEXTOS.pegarTexto("label.resultado.downloadvideo.concluido")
                        : TEXTOS.pegarTexto("label.resultado.downloadaudio.concluido");
            } else {
                extensao3 = video ? ".mp4" : ".mp3";
                downloadPath3 = video ? TEXTOS.pegarTexto("label.resultado.downloadvideo.concluido")
                        : TEXTOS.pegarTexto("label.resultado.downloadaudio.concluido");
            }

            boolean resContemAudio = false;
            for (JRadioButtonMenuItem buttonMenuItem : radBtnList) {
                if (buttonMenuItem.isSelected() && buttonMenuItem.getText().contains("(+audio)")) {
                    resContemAudio = true;
                    break;
                }
            }

            String resolution = (resId == null || resId.equals(TEXTOS.pegarTexto("radio.menu.melhor.resolucao")))
                    ? "'bestvideo[ext=mp4]+bestaudio[ext=m4a]',\n"
                    : (resContemAudio) ? "'" + resId + "',\n" : "'" + resId + "+bestaudio[ext=m4a]',\n";

            format = (video)
                    ? String.format("{'format': %s"
                            + "'outtmpl': '%s' + title + '.mp4'}", resolution, pastaVideo) // format video para o YoutubeDL
                    : String.format("{'format': 'bestaudio[ext=m4a]',\n" + "'outtmpl': '%s' + title + '.mp3',\n"
                            + "'postprocessors': [{\n" + "    'key': 'FFmpegExtractAudio',\n" // format
                            // audio
                            // para o
                            // YoutubeDL
                            + "    'preferredcodec': 'mp3',\n" + "    'preferredquality': '0'\n" + "    }]\n" + "}",
                            pastaAudio);

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
            pegarTitulos.criar(String.format("#!/usr/bin/python3\n" + "import os, time\n"
                    + "from youtube_search import YoutubeSearch\n\n" + "" + "link = '%s'\n"
                    + "results = YoutubeSearch(link, 1)\n\n" + "" + "for i in results.videos:\n"
                    + "  for k, v in i.items():\n" + "    if k == 'title':\n" + "      print(v)\n", link));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public List<String> title(JLabel resultLbl, String videoTitle, String script, String formatedTitle,
            JButton btnPadrao, JButton btnSecundario, JButton btnTerciario) {

        resultLbl.setVisible(true);
        resultLbl.setText(TEXTOS.pegarTexto("label.resultado.pegandotitulo"));
        configurarCores();
        videoTitle = CMD.comandoDownload("python3 " + script);
        cancelScheduleBtnCancelPro();
        try {
            int tituloTam = (videoTitle.length() > 50) ? videoTitle.length() - (videoTitle.length() - 50)
                    : videoTitle.length() - 1;
            formatedTitle = (videoTitle.length() > 50) ? videoTitle.substring(0, tituloTam) + "..."
                    : videoTitle.substring(0, tituloTam);
        } catch (StringIndexOutOfBoundsException s) {
            formatedTitle = "...";
            s.printStackTrace();
        }
        if (formatedTitle.startsWith("Traceback (most recent call last):")) {
            // formatedTitle = "...";
            resultLbl.setText(TEXTOS.pegarTexto("label.resultado.pegandotitulofalhou"));
            resultLbl.setForeground(CORES.pegarCor(noturno, "Erros"));
            if (!btnSecundario.isVisible() && !btnTerciario.isVisible()) {
                btnPadrao.setVisible(true);
            }
            return null;
        } else {
            resultLbl.setText(verifyingDownload + " " + formatedTitle);
            List<String> titles = List.of(videoTitle, formatedTitle);
            return titles;
        }
    }

    public List<Object> processes(JLabel resultLbl, Process pro, Runtime run, String scriptDown, BufferedReader reader,
            BufferedReader reader2) {
        if (!resultLbl.getText().equals(TEXTOS.pegarTexto("label.resultado.pegandotitulofalhou"))) {

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
        } else {
            return null;
        }
    }

    public String downloader(String line, String commandOut, BufferedReader reader, BufferedReader reader2,
            JProgressBar progressBarPadrao, JProgressBar progressBarSecundario, JProgressBar progressBarTerciario,
            JLabel resultLbl, boolean downloadConcluido, boolean isBaixandoPadrao, boolean isBaixandoSecundario,
            boolean isBaixandoTerciario, String videoTitle, String formatedTitle, String script, JButton btnPadrao,
            JButton btnSecundario, JButton btnTerciario, String ext, String downFolder) {

        if (!resultLbl.getText().equals(TEXTOS.pegarTexto("label.resultado.pegandotitulofalhou"))) {
            try {
                String prefix = formatedTitle;
                boolean containsVideo = false;
                String rgx = "^(\\d|\\D)+\\.f([0-9])+\\.mp4";

                if (".mp4".equals(ext)) {
                    String[] names = new File(pastaVideo).list();
                    for (String name : names) {
                        if (name.matches(rgx) && name.startsWith(prefix)) {
                            containsVideo = true;
                            break;
                        }
                    }
                }

                progressBarPadrao.setValue(0);

                while ((line = reader.readLine()) != null) {
                    if ((!line.startsWith("[download] 100%") && !line.contains("Deleting"))) {
                        String progressPercentDownload = "", progressPercentDownload2 = "";
                        if (line.contains("%")) {
                            progressBarPadrao.setVisible(true);
                            progressPercentDownload = line.substring(line.indexOf(" "), line.indexOf(".")).strip();
                            progressPercentDownload2 = line.substring(line.indexOf(" ")) + "  ";
                            progressBarPadrao.setValue(Integer.parseInt(progressPercentDownload.strip()));

                            if (progressBarPadrao.getString().contains("100.0%")) {
                                containsVideo = true;
                                CMD.sleep(0.2);
                            }

                            if (!containsVideo) {
                                progressBarPadrao.setString(formatedTitle + progressPercentDownload2);
                            } else {
                                if (progressBarPadrao.getString().contains("100.0%")) {
                                    progressBarPadrao.setValue(0);
                                }
                                progressBarPadrao
                                        .setString(TEXTOS.pegarTexto("progressbar.baixando.audio") + formatedTitle);
                            }

                        } else {
                            if (line.contains("[download] " + pastaVideo)
                                    || line.contains("[download] " + pastaAudio)) {
                                resultLbl.setText(TEXTOS.pegarTexto("label.resultado.aviso.ja.baixado"));
                                resultLbl.setForeground(CORES.pegarCorComBrilho(noturno, "Avisos"));
                            } else if (line.startsWith("[youtube]")) {
                                resultLbl.setText(verifyingDownload + " " + formatedTitle);
                                configurarCores();
                            } else {
                                if (line.startsWith("[ffmpeg]") || line.equals("[download] Destination: " + pastaVideo
                                        + videoTitle.strip() + ".mp4")) {
                                    downloadConcluido = true;
                                    continue;
                                }
                                resultLbl.setText(line);
                            }
                        }
                    } else if (downloadConcluido) {
                        resultLbl.setText("<html>" + downFolder + "/" + formatedTitle + ext + "</html>");
                        resultLbl.setForeground(CORES.pegarCorComBrilho(noturno, "Concluido"));
                        progressBarPadrao.setVisible(false);
                        progressBarPadrao.setString(TEXTOS.pegarTexto("label.resultado.baixando"));
                        progressBarPadrao.setValue(0);
                        isBaixandoPadrao = false;
                        resultLbl.setVisible(true);
                        commandOut = "<html>" + downFolder + "/" + formatedTitle + ext + "</html>";
                    } else {
                        commandOut = commandOut + line + "\n";
                    }
                }

                if (line == null) {
                    progressBarPadrao.setString(TEXTOS.pegarTexto("label.resultado.baixando"));
                    while ((line = reader2.readLine()) != null) {
                        if (line.contains("DownloadError:")) {
                            resultLbl.setForeground(CORES.pegarCorComBrilho(noturno, "Erros"));
                            resultLbl
                                    .setText(formatedTitle + " " + TEXTOS.pegarTexto("label.resultado.falha.download"));
                        } else {
                            resultLbl.setText(String.format("<html>%s</html>", line));
                            resultLbl.setForeground(CORES.pegarCorComBrilho(noturno, "Erros"));
                        }
                        commandOut = commandOut + line + "\n";
                    }
                    CMD.sleep(0.5);
                    isBaixandoPadrao = false;
                    buttonsSettings(btnPadrao, btnSecundario, btnTerciario, isBaixandoPadrao, isBaixandoSecundario,
                            isBaixandoTerciario, false, true);
                    progressBarPadrao.setVisible(false);
                    return resultLbl.getText();
                }
                reader.close();
                reader2.close();
            } catch (IOException ex) {
                resultLbl.setVisible(true);
                progressBarPadrao.setString(TEXTOS.pegarTexto("label.resultado.baixando"));
                if (ex.toString().contains("Stream closed")) {
                    resultLbl.setText(TEXTOS.pegarTexto("label.resultado.download.cancelado"));
                    resultLbl.setForeground(CORES.pegarCorComBrilho(noturno, "Avisos"));
                    progressBarPadrao.setVisible(false);
                    // isBaixandoPadrao = false;
                    CMD.sleep(0.5);
                    buttonsSettings(btnPadrao, btnSecundario, btnTerciario, isBaixandoPadrao, isBaixandoSecundario,
                            isBaixandoTerciario, false, true);
                    return resultLbl.getText();
                } else {
                    resultLbl.setText(String.format("<html>%s</html>", ex.toString()));
                    resultLbl.setForeground(CORES.pegarCorComBrilho(noturno, "Erros"));
                    progressBarPadrao.setVisible(false);
                    buttonsSettings(btnPadrao, btnSecundario, btnTerciario, isBaixandoPadrao, isBaixandoSecundario,
                            isBaixandoTerciario, true, false);
                    CMD.sleep(0.5);
                    buttonsSettings(btnPadrao, btnSecundario, btnTerciario, isBaixandoPadrao, isBaixandoSecundario,
                            isBaixandoTerciario, false, true);
                    isBaixandoPadrao = false;
                    return resultLbl.getText();
                }
            }

            switch (commandOut) {
                case "":
                    resultLbl.setForeground(CORES.pegarCorComBrilho(noturno, "Erros"));
                    resultLbl.setText(TEXTOS.pegarTexto(formatedTitle + " " + "label.resultado.falha.download"));
                    break;
                default:
                    configurarCores();
                    resultLbl.setText(cmdLineSaida);
                    break;
            }
            progressBarPadrao.setString(TEXTOS.pegarTexto("label.resultado.baixando"));
            progressBarPadrao.setVisible(false);
            isBaixandoPadrao = false;

            return commandOut;
        } else {
            return null;
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

}
