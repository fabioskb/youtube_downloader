package aplicacao.tela;

import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import metodos.YoutubeArquivo;
import metodos.YoutubeComando;
import metodos.YoutubeCores;
import metodos.YoutubeImage;
import metodos.YoutubeText;

/**
 * Classe abstrata responsável por criar os componentes, e, os getters e setters dos atributos.
 */
public abstract class YoutubeTela extends JFrame {
	
	private JLabel lblBanner;
	private JLabel lblDescricao;
	protected static final JLabel LBL_VAZIO = new JLabel("                ");
	
	private JToggleButton btnModoNoite;
	private JCheckBox checkVideo, checkAudio;
	
	private JTextField txtLink;
	private JTextField txtPesquisa;
	private JButton btnPesquisa;
	private JLabel lblLink;
	
	private JList<String> lstPesquisa;
	private DefaultListModel<String> lstTitulos = new DefaultListModel<String>();
	private List<String> lstDescricao = new LinkedList<>();
	private JButton btnBaixa;
	private JButton btnCancelar;
	
	private JMenuBar barraMenu;
	private JMenu menuFile;
	private JMenuItem itemMenuExit;
	
	private JLabel lblResultado;
	
	private JPanel pnlTopo;
	
	private JPanel pnlEsquerda;
	//private JPanel pnlEsquerda1;
	//private JPanel pnlEsquerda2;
	
	private JPanel pnlCentro;
	private JPanel pnlCentro1;
	private JPanel pnlCentro2;
	private JPanel pnlCentro3;
	private JPanel pnlCentro4;
	private JScrollPane pnlExtra;
	
	private JPanel pnlDireita;
	private JPanel pnlDireita1;
	private JPanel pnlDireita2;
	
	private JPanel pnlRodape;
	private JProgressBar downloadProgressBar;
	private JLabel lblProgressBar;
	
	private boolean noturno, video, audio;
	private String pastaPrincipal;
	private String installYoutubeSearch, installYoutubeDl;
	private int contador;
	private YoutubeArquivo arquivoChecaPrograma, diretorioPadrao;

	private final BorderLayout borderLayout = new BorderLayout();
	protected static final String USUARIO = System.getProperty("user.name");
	protected static final String SISTEMA = System.getProperty("os.name");
	protected static final Font FONT_BANNER = new Font(Font.SANS_SERIF, Font.BOLD, 48);
	protected static final Font FONT_DESC = new Font(Font.SANS_SERIF, Font.BOLD, 12);
	protected static YoutubeImage IMAGEM = new YoutubeImage();
	protected static final YoutubeCores CORES = new YoutubeCores();
	protected static final String IDIOMA = Locale.getDefault().getDisplayLanguage();
	protected final Calendar CALENDARIO = Calendar.getInstance(Locale.getDefault());
	protected final int HORA = CALENDARIO.get(Calendar.HOUR_OF_DAY);
	protected static final YoutubeText TEXTOS = new YoutubeText(IDIOMA);
	protected static final YoutubeComando CMD = new YoutubeComando();

	//////// PAINEIS PRINCIPAIS (Getters)
	/**
	 * Configura e retorna o Painel superior da aplicação
	 * 
	 * @return JPanel - pnlTopo
	 */
	public JPanel getPnlTopo() {
		if (pnlTopo == null) {
			pnlTopo = new JPanel(new GridLayout(2, 1));

			lblBanner = new JLabel("<html><center>YouTube<br/>Downloader</center></html>", (int) CENTER_ALIGNMENT);
			lblBanner.setFont(FONT_BANNER);
			lblBanner.setOpaque(true);
			lblBanner.setIcon(IMAGEM.pegarIcon("/imagens/ytdBanner.png"));

			lblDescricao = new JLabel(TEXTOS.pegarTexto(0), (int) CENTER_ALIGNMENT);
			lblDescricao.setFont(FONT_DESC);

			pnlTopo.add(lblBanner);
			pnlTopo.add(lblDescricao);
		}
		return pnlTopo;
	}

	
	public JPanel getPnlEsquerda() {
		pnlEsquerda = new JPanel(new FlowLayout());

		this.barraMenu = new JMenuBar();

		this.menuFile = new JMenu("Menu");
		this.itemMenuExit = new JMenuItem(TEXTOS.pegarTexto(39));
		itemMenuExit.setToolTipText(TEXTOS.pegarTexto(40));

		menuFile.add(itemMenuExit);
		barraMenu.add(menuFile);

		pnlEsquerda.add(barraMenu);

		return pnlEsquerda;
	}

	/**
	 * Configura e retorna o Painel central da aplicação
	 * 
	 * @return JPanel pnlCentro
	 */
	public JPanel getPnlCentro() {

		// LineBorder lstBorda = new LineBorder(getForeground());

		if (pnlCentro == null) {
			pnlCentro = new JPanel(new GridLayout(4, 1));
			Component[] pnls = { pnlCentro1 = new JPanel(new FlowLayout()), pnlCentro2 = new JPanel(new FlowLayout()), pnlCentro3 = new JPanel(new GridLayout()), pnlCentro4 = new JPanel(new FlowLayout()) };

			Component[] comps = { lblLink = new JLabel(""),
					txtLink = new JTextField(TEXTOS.pegarTexto(7), 38),
					txtPesquisa = new JTextField(TEXTOS.pegarTexto(8), 25),
					btnPesquisa = new JButton(TEXTOS.pegarTexto(5)),
					lstPesquisa = new JList<>(lstTitulos),
					btnBaixa = new JButton(TEXTOS.pegarTexto(6)),
					btnCancelar = new JButton(TEXTOS.pegarTexto(35)) };

			lstPesquisa.setToolTipText(TEXTOS.pegarTexto(24));
			lstPesquisa.setOpaque(false);
			lblLink.setOpaque(false);
			lblLink.setIcon(IMAGEM.pegarIcon("/imagens/link.png"));
			txtLink.setToolTipText(TEXTOS.pegarTexto(10));
			txtPesquisa.setToolTipText(TEXTOS.pegarTexto(11));
			btnBaixa.setIcon(IMAGEM.pegarIcon("/imagens/download.png"));
			btnBaixa.setToolTipText(TEXTOS.pegarTexto(23));
			btnPesquisa.setToolTipText(TEXTOS.pegarTexto(13));
			btnPesquisa.setIcon(IMAGEM.pegarIcon("/imagens/system_search.png"));
			btnCancelar.setIcon(IMAGEM.pegarIcon("/imagens/cancel.png"));
			btnCancelar.setToolTipText(TEXTOS.pegarTexto(36));

			pnlExtra = new JScrollPane();
			pnlExtra.setViewportView(lstPesquisa);

			for (int i = 0; i < comps.length; i++) {
				if (i <= 1)
					pnlCentro1.add(comps[i]);
				else if (i <= 3)
					pnlCentro2.add(comps[i]);
				else if (i == 4)
					pnlCentro3.add(pnlExtra);
				else
					pnlCentro4.add(comps[i]);
			}
			for (Component c : pnls)
				pnlCentro.add(c);
		}
		return pnlCentro;
	}

	public JPanel getPnlDireita() {
		if (pnlDireita == null) {
			pnlDireita = new JPanel(new GridLayout(2, 1));
			pnlDireita1 = new JPanel(new FlowLayout());
			pnlDireita2 = new JPanel(new FlowLayout());

			checkVideo = new JCheckBox(TEXTOS.pegarTexto(2));
			checkAudio = new JCheckBox(TEXTOS.pegarTexto(3));
			checkVideo.setToolTipText(TEXTOS.pegarTexto(14));
			checkAudio.setToolTipText(TEXTOS.pegarTexto(15));
			btnModoNoite = new JToggleButton(TEXTOS.pegarTexto(1));
			btnModoNoite.setToolTipText(TEXTOS.pegarTexto(9));
			btnModoNoite.setIcon(IMAGEM.pegarIcon("/imagens/night.png"));

			pnlDireita1.add(btnModoNoite);
			pnlDireita2.add(checkVideo);
			pnlDireita2.add(LBL_VAZIO);
			pnlDireita2.add(checkAudio);
			pnlDireita.add(pnlDireita1);
			pnlDireita.add(pnlDireita2);
		}
		return pnlDireita;
	}

	/**
	 * Configura e retorna o Painel inferior da aplicação
	 * 
	 * @return JPanel pnlRodape
	 */
	public JPanel getPnlRodape() {
		if (pnlRodape == null) {
			pnlRodape = new JPanel(new FlowLayout());

			lblResultado = new JLabel("", (int) CENTER_ALIGNMENT);
			lblResultado.setAlignmentX(CENTER_ALIGNMENT);
			lblResultado.setAlignmentY(CENTER_ALIGNMENT);
			lblResultado.setOpaque(true);
			lblResultado.setAutoscrolls(true);
			lblResultado.setIcon(IMAGEM.pegarIcon("/imagens/info.png"));
			lblResultado.setToolTipText(TEXTOS.pegarTexto(32));

			downloadProgressBar = new JProgressBar(0, 100);
			downloadProgressBar.setAlignmentX(CENTER_ALIGNMENT);
			downloadProgressBar.setAlignmentY(CENTER_ALIGNMENT);
			downloadProgressBar.setStringPainted(true);

			lblProgressBar = new JLabel("");
			lblProgressBar.setAlignmentX(CENTER_ALIGNMENT);
			lblProgressBar.setAlignmentY(CENTER_ALIGNMENT);
			lblProgressBar.setOpaque(true);
			lblProgressBar.setText(TEXTOS.pegarTexto(18));

			pnlRodape.add(lblResultado);
			pnlRodape.add(downloadProgressBar);
			pnlRodape.add(lblProgressBar);
		}
		return pnlRodape;
	}
	////////////

	public BorderLayout getBorderLayout() { return borderLayout; }
	
	public JMenu getMenuFile() { return menuFile; }

	public void setMenuFile(JMenu menuFile) { this.menuFile = menuFile; }

	public JMenuItem getItemMenuExit() { return this.itemMenuExit; }

	public void setItemMenuExit(JMenuItem itemMenuExit) { this.itemMenuExit = itemMenuExit; }

	public JMenuBar getBarraMenu() { return barraMenu; }

	public void setCont(int contador) { this.contador = contador; }

	public JLabel getLblProgressBar() { return lblProgressBar; }

	public void setLblProgressBar(JLabel lblProgressBar) { this.lblProgressBar = lblProgressBar; }

	public JProgressBar getDownloadProgressBar() { return downloadProgressBar; }

	//public JPanel getPnlRodape1() { return pnlRodape1; }

	//public JPanel getPnlRodape2() { return pnlRodape2; }

	public JButton getBtnCancelar() { return btnCancelar; }

	public int getCont() { return contador; }

	/**
	 * Retorna true se o checkbox video estiver selecionado.
	 * É uma condição para baixar em vídeo (mp4).
	 * 
	 * @return boolean video
	 */
	public boolean isVideo() { return video; }

	public void setVideo(boolean video) { this.video = video; }

	public boolean isAudio() { return audio; }

	/**
	 * Retorna true se o checkbox Audio estiver selecionado.
	 * É uma condição para baixar em áudio (mp3).
	 * 
	 * @param audio
	 */
	public void setAudio(boolean audio) { this.audio = audio; }

	/**
	 * Retorna true se o modo noturno estiver ativo, se não,
	 * retorna false.
	 * 
	 * @return boolean
	 */
	public boolean isNoturno() { return noturno; }

	public void setNoturno(boolean noturno) { this.noturno = noturno; }

	public JLabel getLblBanner() { return lblBanner; }

	public JLabel getLblDescricao() { return lblDescricao; }

	public JToggleButton getBtnModoNoite() { return btnModoNoite; }

	public JCheckBox getCheckVideo() { return checkVideo; }

	public JCheckBox getCheckAudio() { return checkAudio; }

	public JTextField getTxtLink() { return txtLink; }

	public JTextField getTxtPesquisa() { return txtPesquisa; }

	public JButton getBtnPesquisa() { return btnPesquisa; }

	public JLabel getLblLink() { return lblLink; }

	public JList<String> getLstPesquisa() { return lstPesquisa; }

	public DefaultListModel<String> getLstTitulos() { return lstTitulos; }

	public List<String> getLstDescricao() { return lstDescricao; }

	public JButton getBtnBaixa() { return btnBaixa; }

	public JLabel getLblResultado() { return lblResultado; }

	public String getPastaPrincipal() { return pastaPrincipal; }

	public void setPastaPrincipal(String pastaPrincipal) { this.pastaPrincipal = pastaPrincipal; }

	public String getInstallYoutubeSearch() { return installYoutubeSearch; }

	public void setInstallYoutubeSearch(String installYoutubeSearch) { this.installYoutubeSearch = installYoutubeSearch; }

	public String getInstallYoutubeDl() { return installYoutubeDl; }

	public void setInstallYoutubeDl(String installYoutubeDl) { this.installYoutubeDl = installYoutubeDl; }

	public int getContador() { return contador; }

	public void setContador(int contador) { this.contador = contador; }

	public YoutubeArquivo getArquivoChecaPrograma() { return arquivoChecaPrograma; }

	public void setArquivoChecaPrograma(YoutubeArquivo arquivoChecaPrograma) { this.arquivoChecaPrograma = arquivoChecaPrograma; }

	public YoutubeArquivo getDiretorioPadrao() { return diretorioPadrao; }

	public void setDiretorioPadrao(YoutubeArquivo diretorioPadrao) { this.diretorioPadrao = diretorioPadrao; }

	public JPanel getPnlDireita1() { return pnlDireita1; }

	public JPanel getPnlDireita2() { return pnlDireita2; }

	public JPanel getPnlCentro1() { return pnlCentro1; }

	public void setPnlCentro1(JPanel pnlCentro1) { this.pnlCentro1 = pnlCentro1; }

	public JPanel getPnlCentro2() { return pnlCentro2; }

	public JPanel getPnlCentro3() { return pnlCentro3; }

	public JPanel getPnlCentro4() { return pnlCentro4; }

	public JScrollPane getPnlExtra() { return pnlExtra; }

}
