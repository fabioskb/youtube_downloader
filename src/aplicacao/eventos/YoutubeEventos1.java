package aplicacao.eventos;

import java.io.BufferedReader;

import aplicacao.tela.YoutubeTelaCoresEEventos;

/**
 * Classe responsável por criar todas os atributos que serão usados nos eventos, 
 * e, seus getters e setters.
 * Herda TelaCoresEventos da aplicação.
 */
public class YoutubeEventos1 extends YoutubeTelaCoresEEventos {
	private String format;
	private String cmdLineSaida;
	private String[] links;
	private boolean modificaBgLabelResultado;
	protected int index = 20;
	private Process pro;
	private BufferedReader read;
    private BufferedReader read2;
	
	protected String line;
	protected static final Runtime RUN = Runtime.getRuntime();
	
	public String getFormat() { return format; }

	public void setFormat(String format) { this.format = format; }

	public String getCmdLineSaida() { return cmdLineSaida; }

	public void setCmdLineSaida(String cmdLineSaida) {
		this.cmdLineSaida = cmdLineSaida;
	}

	public String[] getLinks() { return links; }

	public void setLinks(String[] links) { this.links = links; }

	public boolean isModificaBgLabelResultado() { return modificaBgLabelResultado; }

	public void setModificaBgLabelResultado(boolean modificaBgLabelResultado) {
		this.modificaBgLabelResultado = modificaBgLabelResultado;
	}

	public int getIndex() { return index; }

	public void setIndex(int index) { this.index = index; }

	public Process getPro() { return pro; }

	public void setPro(Process pro) { this.pro = pro; }

	public BufferedReader getRead() { return read; }

	public void setRead(BufferedReader read) { this.read = read; }

	public BufferedReader getRead2() { return read2; }

	public void setRead2(BufferedReader read2) { this.read2 = read2; }
}
