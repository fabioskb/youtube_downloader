package aplicacao.eventos;

import java.io.BufferedReader;

import aplicacao.tela.YoutubeTela;

/**
 * Classe responsável por criar todas os atributos que serão usados nos eventos, 
 * e, seus getters e setters.
 * Herda Tela da aplicação.
 */
public class YoutubeEventos1 extends YoutubeTela {
	protected static String format;
	protected static String cmdLineSaida;
	protected static String[] links;
	private static boolean modificaBgLabelResultado;
	private static int index;
	private static Runtime RUN;
	protected static Process pro;
	protected static BufferedReader read;
    protected static BufferedReader read2;
	protected static String line;
	
	public YoutubeEventos1(String format, String cmdLineSaida, String[] links, Process pro, BufferedReader read, BufferedReader read2) {
		YoutubeEventos1.format = format;
		YoutubeEventos1.cmdLineSaida = cmdLineSaida;
		YoutubeEventos1.links = links;
		YoutubeEventos1.modificaBgLabelResultado = true;
		YoutubeEventos1.index = 20;
		YoutubeEventos1.pro = pro;
		YoutubeEventos1.read = read;
		YoutubeEventos1.read2 = read2;
		YoutubeEventos1.line = null;
		YoutubeEventos1.RUN  = Runtime.getRuntime();
	}

	public String getFormat() { return format; }

	public void setFormat(String format) { YoutubeEventos1.format = format; }

	public String getCmdLineSaida() { return cmdLineSaida; }

	public void setCmdLineSaida(String cmdLineSaida) {
		YoutubeEventos1.cmdLineSaida = cmdLineSaida;
	}

	public String[] getLinks() { return links; }

	public void setLinks(String[] links) { YoutubeEventos1.links = links; }

	public boolean isModificaBgLabelResultado() { return modificaBgLabelResultado; }

	public void setModificaBgLabelResultado(boolean modificaBgLabelResultado) {
		YoutubeEventos1.modificaBgLabelResultado = modificaBgLabelResultado;
	}

	public int getIndex() { return index; }

	public void setIndex(int index) { YoutubeEventos1.index = index; }

	public Runtime getRUN() { return RUN; }

	public Process getPro() { return pro; }

	public void setPro(Process pro) { YoutubeEventos1.pro = pro; }

	public BufferedReader getRead() { return read; }

	public void setRead(BufferedReader read) { YoutubeEventos1.read = read; }

	public BufferedReader getRead2() { return read2; }

	public void setRead2(BufferedReader read2) { YoutubeEventos1.read2 = read2; }
}
