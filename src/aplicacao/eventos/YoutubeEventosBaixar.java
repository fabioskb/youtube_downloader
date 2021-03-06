package aplicacao.eventos;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Classe responsável pelo evento que configura o Download e o executa. Além de
 * tratar os erros.
 */
@SuppressWarnings("serial")
public class YoutubeEventosBaixar extends Download {

    @Override
    protected void btnBaixarClick(ActionEvent ev) {
        scheduleDownload = EXECUTOR.schedule(() -> {
            String link = check(downloadDone, cmdLineSaida);

            if (link.startsWith("https://www.youtube.com/watch?v=")) {
                isBaixando = true;
                format(link, scriptDown, scriptTitle, btnBaixa, extensao);

                List<String> titulos = title(lblResultado1, tituloVideo,
                        scriptTitle, tituloFormatado, btnBaixa, btnBaixa2, btnBaixa3);
                CMD.sleep(1);
                tituloFormatado = titulos.get(1);
                tituloVideo = titulos.get(0);

                buttonsSettings(btnBaixa, btnBaixa2, btnBaixa3, isBaixando,
                        isBaixando2, isBaixando3, false, false);

                List<Object> objects = processes(lblResultado1, pro1, RUN_1, scriptDown, read,
                        read2);
                read = (BufferedReader) objects.get(0);
                read2 = (BufferedReader) objects.get(1);
                pro1 = (Process) objects.get(2);

                cmdLineSaida = downloader(line, cmdLineSaida, read, read2,
                        downloadProgressBar, downloadProgressBar2,
                        downloadProgressBar3, lblResultado1, downloadDone,
                        isBaixando, isBaixando2, isBaixando3, tituloVideo,
                        tituloFormatado, scriptDown, btnBaixa, btnBaixa2,
                        btnBaixa3, extensao, downloadPath);
            } else {
                lblResultado1.setText(cmdLineSaida);
                return;
            }

            isBaixando = false;
            buttonsSettings(btnBaixa, btnBaixa2, btnBaixa3, isBaixando,
                    isBaixando2, isBaixando3, true, false);
            buttonsSettings(btnBaixa, btnBaixa2, btnBaixa3, isBaixando,
                    isBaixando2, isBaixando3, false, true);
        }, 0, TimeUnit.SECONDS);
        // download.start();
    }

    @Override
    protected void btnBaixar2Click(ActionEvent ev) {
        scheduleDownload2 = EXECUTOR.schedule(() -> {
            String link = check(downloadDone2, cmdLineSaida2);

            if (link.startsWith("https://www.youtube.com/watch?v=")) {
                isBaixando2 = true;
                format(link, scriptDown2, scriptTitle2, btnBaixa2, extensao2);

                List<String> titulos = title(lblResultado2, tituloVideo2,
                        scriptTitle2, tituloFormatado2, btnBaixa2, btnBaixa, btnBaixa3);
                CMD.sleep(1);
                tituloFormatado2 = titulos.get(1);
                tituloVideo2 = titulos.get(0);

                buttonsSettings(btnBaixa2, btnBaixa, btnBaixa3, isBaixando2,
                        isBaixando, isBaixando3, false, false);

                List<Object> readers = processes(lblResultado2, pro2, RUN_2, scriptDown2,
                        read3, read4);
                read3 = (BufferedReader) readers.get(0);
                read4 = (BufferedReader) readers.get(1);
                pro2 = (Process) readers.get(2);

                cmdLineSaida2 = downloader(line2, cmdLineSaida2, read3, read4,
                        downloadProgressBar2, downloadProgressBar,
                        downloadProgressBar3, lblResultado2, downloadDone2,
                        isBaixando2, isBaixando, isBaixando3, tituloVideo2,
                        tituloFormatado2, scriptDown2, btnBaixa2, btnBaixa,
                        btnBaixa3, extensao2, downloadPath2);
            } else {
                lblResultado2.setText(cmdLineSaida2);
                return;
            }

            isBaixando2 = false;
            buttonsSettings(btnBaixa2, btnBaixa, btnBaixa3, isBaixando2,
                    isBaixando, isBaixando3, true, false);
            buttonsSettings(btnBaixa2, btnBaixa, btnBaixa3, isBaixando2,
                    isBaixando, isBaixando3, false, true);
        }, 0, TimeUnit.SECONDS);
        // download2.start();
    }

    @Override
    protected void btnBaixar3Click(ActionEvent ev) {
        scheduleDownload3 = EXECUTOR.schedule(() -> {
            String link = check(downloadDone3, cmdLineSaida3);

            if (link.startsWith("https://www.youtube.com/watch?v=")) {
                isBaixando3 = true;

                format(link, scriptDown3, scriptTitle3, btnBaixa3, extensao3);

                List<String> titulos = title(lblResultado3, tituloVideo3,
                        scriptTitle3, tituloFormatado3, btnBaixa3, btnBaixa, btnBaixa2);
                CMD.sleep(1);
                tituloFormatado3 = titulos.get(1);
                tituloVideo3 = titulos.get(0);

                buttonsSettings(btnBaixa3, btnBaixa, btnBaixa2, isBaixando3,
                        isBaixando, isBaixando2, false, false);

                List<Object> readers = processes(lblResultado3, pro3, RUN_3, scriptDown3,
                        read5, read6);
                read5 = (BufferedReader) readers.get(0);
                read6 = (BufferedReader) readers.get(1);
                pro3 = (Process) readers.get(2);

                cmdLineSaida3 = downloader(line3, cmdLineSaida3, read5, read6,
                        downloadProgressBar3, downloadProgressBar,
                        downloadProgressBar2, lblResultado3, downloadDone3,
                        isBaixando3, isBaixando, isBaixando2, tituloVideo3,
                        tituloFormatado3, scriptDown3, btnBaixa3, btnBaixa,
                        btnBaixa2, extensao3, downloadPath3);
            } else {
                lblResultado3.setText(cmdLineSaida3);
                return;
            }

            isBaixando3 = false;
            buttonsSettings(btnBaixa3, btnBaixa, btnBaixa2, isBaixando3,
                    isBaixando, isBaixando2, true, false);
            buttonsSettings(btnBaixa3, btnBaixa, btnBaixa2, isBaixando3,
                    isBaixando, isBaixando2, false, true);
        }, 0, TimeUnit.SECONDS);
        // download3.start();
    }
}
