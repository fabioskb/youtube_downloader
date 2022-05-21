package aplicacao.eventos;

import aplicacao.tela.YoutubeTelaCoresEEventos;
import java.io.BufferedReader;

/**
 * Classe responsável por criar todos os atributos que serão usados nos eventos,
 * e, seus getters e setters.
 */
public class YoutubeEventosAtributos extends YoutubeTelaCoresEEventos {

    protected String tituloVideo = "";
    protected String tituloVideo2 = "";
    protected String tituloVideo3 = "";
    protected String tituloFormatado = "";
    protected String tituloFormatado2 = "";
    protected String tituloFormatado3 = "";
    protected String format;
    protected String cmdLineSaida;
    protected String cmdLineSaida2;
    protected String cmdLineSaida3;
    protected String[] links;
    protected int index = 20;
    protected Process pro;
    protected Process pro2;
    protected Process pro3;
    protected BufferedReader read, read2, read3, read4, read5, read6;
    protected String line;
    protected String line2;
    protected String line3;
    protected boolean downloadDone, downloadDone2, downloadDone3;
    protected Thread download, download2, download3;
    protected static final Runtime RUN = Runtime.getRuntime();
    protected static final Runtime RUN_2 = Runtime.getRuntime();
    protected static final Runtime RUN_3 = Runtime.getRuntime();
    
}
