package aplicacao.eventos;

import aplicacao.tela.YoutubeTelaCoresEEventos;
import java.io.BufferedReader;
import java.util.concurrent.ScheduledFuture;

/**
 * Classe responsável por criar todos os atributos que serão usados nos eventos,
 * e, seus getters e setters.
 */
@SuppressWarnings("serial")
public class YoutubeEventosAtributos extends YoutubeTelaCoresEEventos {

    protected String tituloVideo = "";
    protected String tituloVideo2 = "";
    protected String tituloVideo3 = "";
    protected String scriptDown = "/tmp/baixar";
    protected String scriptDown2 = "/tmp/baixar2";
    protected String scriptDown3 = "/tmp/baixar3";
    protected String scriptRes = "/tmp/resolution";
    protected String resId;
    protected String[] splitRes;
    protected String scriptTitle = "/tmp/titulo";
    protected String scriptTitle2 = "/tmp/titulo2";
    protected String scriptTitle3 = "/tmp/titulo3";
    protected String downloadPath = "";
    protected String downloadPath2 = "";
    protected String downloadPath3 = "";
    protected String extensao = "";
     protected String extensao2 = "";
      protected String extensao3 = "";
    protected boolean isBaixando, isBaixando2, isBaixando3;
    protected String format;
    protected String cmdLineSaida;
    protected String cmdLineSaida2;
    protected String cmdLineSaida3;
    protected String link = "";
    protected String[] links;
    protected int index = 20;
    protected Process pro1 = null;
    protected Process pro2 = null;
    protected Process pro3 = null;
    protected BufferedReader read = null, read2 = null, read3 = null, read4 = null,
            read5 = null, read6 = null;
    protected String line = "";
    protected String line2 = "";
    protected String line3 = "";
    protected boolean downloadDone, downloadDone2, downloadDone3;
    protected ScheduledFuture<?> scheduleDownload, scheduleDownload2, scheduleDownload3, 
            schedulePesquisa, scheduleGetId;
    protected YoutubeTelaCoresEEventos coresEvento = new YoutubeTelaCoresEEventos();
    protected static final Runtime RUN_1 = Runtime.getRuntime();
    protected static final Runtime RUN_2 = Runtime.getRuntime();
    protected static final Runtime RUN_3 = Runtime.getRuntime();

}
