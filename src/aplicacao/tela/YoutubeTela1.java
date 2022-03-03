package aplicacao.tela;

import java.awt.Component;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.LineBorder;

import metodos.YoutubeArquivo;
import metodos.YoutubeComando;
import metodos.YoutubeCores;
import metodos.YoutubeImage;
import metodos.YoutubeText;

/**
 * Classe abstrata responsável por criar os componentes, e, os getters e setters dos atributos.
 */
public abstract class YoutubeTela1 extends JFrame {
	
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
	
	private JLabel lblResultado;
	
	private JPanel pnlTopo;
	
	private JPanel pnlEsquerda;
	private JPanel pnlEsquerda1;
	private JPanel pnlEsquerda2;
	
	private JPanel pnlCentro;
	private JPanel pnlCentro1;
	private JPanel pnlCentro2;
	private JPanel pnlCentro3;
	private JPanel pnlCentro4;
	private JScrollPane pnlExtra;
	
	//private JPanel pnlDireito;
	
	private JPanel pnlRodape;
	
	private boolean noturno, video, audio;
	private String pastaPrincipal;
	private String installYoutubeSearch, installYoutubeDl;
	private int contador;
	private YoutubeArquivo arquivoChecaPrograma, diretorioPadrao;

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

			lblDescricao = new JLabel(TEXTOS.getTextos(0), (int) CENTER_ALIGNMENT);
			lblDescricao.setFont(FONT_DESC);

			pnlTopo.add(lblBanner);
			pnlTopo.add(lblDescricao);
		}
		return pnlTopo;
	}

	/**
	 * Configura e retorna o Painel esquerdo da aplicação
	 * 
	 * @return JPanel pnlEsquerda
	 */
	public JPanel getPnlEsquerda() {
		if (pnlEsquerda == null) {
			pnlEsquerda = new JPanel(new GridLayout(2, 1));
			pnlEsquerda1 = new JPanel(new FlowLayout());
			pnlEsquerda2 = new JPanel(new FlowLayout());

			checkVideo = new JCheckBox(TEXTOS.getTextos(2));
			checkAudio = new JCheckBox(TEXTOS.getTextos(3));
			btnModoNoite = new JToggleButton(TEXTOS.getTextos(1));
			btnModoNoite.setToolTipText(TEXTOS.getTextos(9));
			btnModoNoite.setIcon(IMAGEM.pegarIcon("/imagens/night.png"));

			pnlEsquerda1.add(btnModoNoite);
			pnlEsquerda2.add(checkVideo);
			pnlEsquerda2.add(LBL_VAZIO);
			pnlEsquerda2.add(checkAudio);

			pnlEsquerda.add(pnlEsquerda1);
			pnlEsquerda.add(pnlEsquerda2);
		}
		return pnlEsquerda;
	}

	/**
	 * Configura e retorna o Painel central da aplicação
	 * 
	 * @return JPanel pnlCentro
	 */
	public JPanel getPnlCentro() {

		LineBorder lstBorda = new LineBorder(getForeground());

		if (pnlCentro == null) {
			pnlCentro = new JPanel(new GridLayout(4, 1));
			Component[] pnls = { pnlCentro1 = new JPanel(new FlowLayout()), pnlCentro2 = new JPanel(new FlowLayout()),
					pnlCentro3 = new JPanel(new GridLayout()), pnlCentro4 = new JPanel(new FlowLayout()), };

			Component[] comps = { lblLink = new JLabel(""),
					txtLink = new JTextField(TEXTOS.getTextos(7), 48),
					txtPesquisa = new JTextField(TEXTOS.getTextos(8), 25),
					btnPesquisa = new JButton(TEXTOS.getTextos(5)),
					lstPesquisa = new JList<>(lstTitulos),
					btnBaixa = new JButton(TEXTOS.getTextos(6)) };

			lstPesquisa.setBorder(lstBorda);
			lstPesquisa.setToolTipText(TEXTOS.getTextos(24));
			lblLink.setOpaque(false);
			lblLink.setIcon(IMAGEM.pegarIcon("/imagens/link.png"));
			txtLink.setToolTipText(TEXTOS.getTextos(10));
			txtPesquisa.setToolTipText(TEXTOS.getTextos(11));
			checkVideo.setToolTipText(TEXTOS.getTextos(14));
			checkAudio.setToolTipText(TEXTOS.getTextos(15));
			btnBaixa.setIcon(IMAGEM.pegarIcon("/imagens/download.png"));
			btnBaixa.setToolTipText(TEXTOS.getTextos(23));
			btnPesquisa.setToolTipText(TEXTOS.getTextos(13));
			btnPesquisa.setIcon(IMAGEM.pegarIcon("/imagens/system_search.png"));

			pnlExtra = new JScrollPane();
			pnlExtra.setViewportView(lstPesquisa);
			pnlExtra.setSize(80, 200);

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
			lblResultado.setToolTipText(TEXTOS.getTextos(32));

			pnlRodape.add(lblResultado);
		}
		return pnlRodape;
	}
	////////////

	public void setCont(int contador) { this.contador = contador; }

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

	public JPanel getPnlEsquerda1() { return pnlEsquerda1; }

	public JPanel getPnlEsquerda2() { return pnlEsquerda2; }

	public JPanel getPnlCentro1() { return pnlCentro1; }

	public void setPnlCentro1(JPanel pnlCentro1) { this.pnlCentro1 = pnlCentro1; }

	public JPanel getPnlCentro2() { return pnlCentro2; }

	public JPanel getPnlCentro3() { return pnlCentro3; }

	public JPanel getPnlCentro4() { return pnlCentro4; }

	public JScrollPane getPnlExtra() { return pnlExtra; }
}
