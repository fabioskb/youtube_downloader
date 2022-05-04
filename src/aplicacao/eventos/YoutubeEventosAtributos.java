package aplicacao.eventos;

import java.io.BufferedReader;

import aplicacao.tela.YoutubeTelaCoresEEventos;

/**
 * Classe responsável por criar todos os atributos que serão usados nos eventos,
 * e, seus getters e setters.
 */
public class YoutubeEventosAtributos extends YoutubeTelaCoresEEventos {

    private String format;
    protected String cmdLineSaida;
    protected String cmdLineSaida2;
    protected String cmdLineSaida3;
    private String[] links;
    protected int index = 20;
    protected Process pro;
    protected Process pro2;
    protected Process pro3;
    protected BufferedReader read, read2, read3, read4, read5, read6;
    protected String line;
    protected String line2;
    protected String line3;
    protected boolean downloadDone, downloadDone2, downloadDone3;
    protected static final Runtime RUN = Runtime.getRuntime();
    protected static final Runtime RUN_2 = Runtime.getRuntime();
    protected static final Runtime RUN_3 = Runtime.getRuntime();
    


    public void setCmdLineSaida2(String cmdLineSaida2) {
        this.cmdLineSaida2 = cmdLineSaida2;
    }

    public void setCmdLineSaida3(String cmdLineSaida3) {
        this.cmdLineSaida3 = cmdLineSaida3;
    }
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

    public void setPro2(Process pro2) {
        this.pro2 = pro2;
    }

    public void setPro3(Process pro3) {
        this.pro3 = pro3;
    }

    public void setRead3(BufferedReader read3) {
        this.read3 = read3;
    }

    public void setRead4(BufferedReader read4) {
        this.read4 = read4;
    }

    public void setRead5(BufferedReader read5) {
        this.read5 = read5;
    }

    public void setRead6(BufferedReader read6) {
        this.read6 = read6;
    }
    
}
