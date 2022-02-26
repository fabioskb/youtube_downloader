package aplicacao;

import java.io.BufferedReader;

/**
 * Classe responsável por criar todas as variáveis que serão usaas nos eventos, 
 * e, seus getters e setters.
 */
public class YoutubeEventos1 extends YoutubeTela {
	private String format, cmdLineSaida;
	private String[] links;
	private boolean modificaBgLabelResultado;
	private int index;
	private Runtime RUN;
	private Process pro;
	private BufferedReader read, read2;
	protected String line;
	
	public YoutubeEventos1(String format, String cmdLineSaida, String[] links, Process pro, BufferedReader read, BufferedReader read2) {
		this.format = format;
		this.cmdLineSaida = cmdLineSaida;
		this.links = links;
		this.modificaBgLabelResultado = true;
		this.index = 20;
		this.pro = pro;
		this.read = read;
		this.read2 = read2;
		this.line = null;
		this.RUN  = Runtime.getRuntime();
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

	public boolean isModificaBgLabelResultado() {
		return modificaBgLabelResultado;
	}

	public void setModificaBgLabelResultado(boolean modificaBgLabelResultado) {
		this.modificaBgLabelResultado = modificaBgLabelResultado;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Runtime getRUN() {
		return RUN;
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

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public void setRUN(Runtime rUN) {
		RUN = rUN;
	}	
	
}
