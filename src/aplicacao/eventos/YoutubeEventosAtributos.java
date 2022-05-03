package aplicacao.eventos;

import java.io.BufferedReader;

import aplicacao.tela.YoutubeTelaCoresEEventos;

/**
 * Classe responsável por criar todos os atributos que serão usados nos eventos,
 * e, seus getters e setters.
 */
public class YoutubeEventosAtributos extends YoutubeTelaCoresEEventos {

    private String format;
    private String cmdLineSaida;
    private String[] links;
    protected int index = 20;
    protected Process pro;
    protected Process pro2;
    protected Process pro3;
    protected BufferedReader read, read2, read3, read4, read5, read6;

    protected String line;
    protected static final Runtime RUN = Runtime.getRuntime();

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getCmdLineSaida() {
        return cmdLineSaida;
    }

    public void setCmdLineSaida(String cmdLineSaida) {
        this.cmdLineSaida = cmdLineSaida;
    }

    public String[] getLinks() {
        return links;
    }

    public void setLinks(String[] links) {
        this.links = links;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Process getPro() {
        return pro;
    }

    public void setPro(Process pro) {
        this.pro = pro;
    }

    public BufferedReader getRead() {
        return read;
    }

    public void setRead(BufferedReader read) {
        this.read = read;
    }

    public BufferedReader getRead2() {
        return read2;
    }

    public void setRead2(BufferedReader read2) {
        this.read2 = read2;
    }
}
