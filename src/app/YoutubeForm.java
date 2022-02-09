package app;

import io.netty.util.internal.SystemPropertyUtil;
import metodos.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Locale;

public abstract class YoutubeForm extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Topo componentes
	protected JLabel lblBanner;
	protected JLabel lblDescricao;
	protected final JLabel LBL_VAZIO = new JLabel("                ");
	// Esquerda componentes
	protected JToggleButton btnModoNoite;
	protected JCheckBox checkVideo, checkAudio;
	// Centro componentes
	protected JTextField txtLink;
	protected JTextField txtPesquisa;
	protected JButton btnPesquisa;
	protected JLabel lblLink;
	// Direita componentes
	protected JList<String> lstPesquisa;
	protected DefaultListModel<String> lstTitulos = new DefaultListModel<String>();
	protected JButton btnBaixa;
	// Rodapé componentes
	protected JLabel lblResultado;

	// Paineis atributos
	protected JPanel pnlTopo;

	protected JPanel pnlEsquerda;
	protected JPanel pnlEsquerda1;
	protected JPanel pnlEsquerda2;

	protected JPanel pnlCentro;
	protected JPanel pnlCentro1;
	protected JPanel pnlCentro2;
	protected JPanel pnlCentro3;
	protected JPanel pnlCentro4;
	protected JScrollPane pnlExtra;

	protected JPanel pnlDireito;
	
	protected JPanel pnlRodape;
	//

	// Atributos
	private boolean noturno, video, audio;
	protected String pasta, titulo;
	private int cont;

	// Atributos Finais
	private final String usuario = SystemPropertyUtil.get("user.name");
	private final String sistema = SystemPropertyUtil.get("os.name");
	private final Font FONT_BANNER = new Font(Font.SANS_SERIF, Font.BOLD, 48);
	private final Font FONT_DESC = new Font(Font.SANS_SERIF, Font.BOLD, 12);
	private final YoutubeImage IMAGEM = new YoutubeImage();
	protected final YoutubeCores CORES = new YoutubeCores();
	protected final String IDIOMA = Locale.getDefault().getDisplayLanguage();
	protected final YoutubeText TEXTOS = new YoutubeText(IDIOMA);
	protected final YoutubeComando cmd = new YoutubeComando();

	/**
	 * Construtor
	 */
	public YoutubeForm() {
		this.inicializar();
		this.setCores(true);
		this.eventos();
	}

	/**
	 * Checa se esta Ok, corrige se necessário e possível, e, 
	 * inicializa todos os componentes da aplicação (se possível).
	 */
	private void inicializar() {
		// Seta a pasta principal da app
		if (this.sistema.contains("Windows"))
			this.pasta = "C:\\users\\" + usuario + "\\YDownloads\\";
		else
			this.pasta = "/home/" + usuario + "/YDownloads/";

		File d = new File(this.pasta);
		if (!d.isDirectory()) d.mkdir();
		
		// Checa se pip e dependências estão instalados
		String ydlSaida = cmd.comando("pip", "show", "youtube_dl");
		String ysSaida = cmd.comando("pip", "show", "youtube_search");
		YoutubeArquivo check = new YoutubeArquivo(pasta + ".check");

		if (ydlSaida.contains("command not found") || ysSaida.contains("command not found")) {
			JOptionPane.showMessageDialog(null, TEXTOS.getTextos(29), "YouTube Downloader", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		} else if ((!ydlSaida.startsWith("Name: youtube-dl") || !ysSaida.startsWith("Name: youtube-search")) && check.getArq().isFile())
			check.deletar();
		else check.criar("Checado!");
		
		// Checa se o sistema está configurado corretamente, e, corrige se necessário; 
		if (!check.getArq().isFile()) {
			JOptionPane.showMessageDialog(null, TEXTOS.getTextos(26), "YouTube Downloader", JOptionPane.INFORMATION_MESSAGE);
			String installYdl = cmd.comando("pip", "install", "youtube-dl");
			String installYs = cmd.comando("pip", "install", "youtube-search");
			if (installYdl.contains("ERROR: Could not find a version that satisfies the requirement youtube-dl") 
					|| installYs.contains("ERROR: Could not find a version that satisfies the requirement youtube-search")) {
				JOptionPane.showMessageDialog(null, TEXTOS.getTextos(27), "YouTube Downloader", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			} else {
				check.criar("Checado!");
				JOptionPane.showMessageDialog(null, TEXTOS.getTextos(28), "YouTube Downloader", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
		// Inicia os componentes se estiver tudo OK.
		this.setTitle("YouTube Downloader");
		this.setResizable(true);
		this.setSize(920, 680);
		this.setLocationRelativeTo(null);
		this.setIconImage(IMAGEM.pegarImage("/imagens/ytdBanner.png"));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		this.getContentPane().setLayout(new BorderLayout());

		this.getContentPane().add(getPnlTopo(), BorderLayout.NORTH);
		this.getContentPane().add(getPnlEsquerda(), BorderLayout.WEST);
		this.getContentPane().add(getPnlCentro(), BorderLayout.CENTER);
		this.getContentPane().add(getPnlRodape(), BorderLayout.SOUTH);

		this.checkVideo.setSelected(true);
		this.video = true;

		
		//this.pack();
	}
		
	// METODOS CUSTOMIZADOS
	
	/**
	 * Ativa o modo noturno
	 * @param ev - evento
	 */
	protected abstract void btnModoNoiteClick(ActionEvent ev);

	/**
	 * Marca vídeo e desmarca audio
	 * @param ev - evento
	 */
	protected abstract void checkVideoClick(ActionEvent ev);

	/**
	 * Marca áudio e desmarca vídeo
	 * @param ev - evento
	 */
	protected abstract void checkAudioClick(ActionEvent ev);

	/**
	 * Seta todas as formas possíveis no download do arquivo,
	 * tratamentos de erros, avisos, pasta do download e etc.
	 * @param ev - evento
	 */
	protected abstract void baixarClick(ActionEvent ev);

	/**
	 * Pesquisa o vídeo ou áudio pelo nome e retorna até 20 opções 
	 * para baixar na lista lstResultado.
	 * @param ev - evento
	 */
	protected abstract void btnPesquisaClick(ActionEvent ev);

	/**
	 * Ao clicar na caixa de texto do link seleciona o texto
	 * @param ev - evento
	 */
	protected abstract void txtLinkMouseClick(MouseEvent ev);

	/**
	 * Ao clicar na caixa de texto da pesquisa com o mouse seleciona o texto.
	 * @param ev - evento
	 */
	protected abstract void txtPesquisaMouseClick(MouseEvent ev);
	/**
	 * Chama todos os eventos de clique da aplicação
	 * em botões, caixas de texto, labels e etc.
	 */
	private void eventos() {
		btnModoNoite.addActionListener(this::btnModoNoiteClick);
		checkVideo.addActionListener(this::checkVideoClick);
		checkAudio.addActionListener(this::checkAudioClick);
		btnBaixa.addActionListener(this::baixarClick);
		btnPesquisa.addActionListener(this::btnPesquisaClick);

		txtLink.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ev) {
				txtLinkMouseClick(ev);
			}
		});

		txtPesquisa.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ev) {
				txtPesquisaMouseClick(ev);
			}
		});
	}

	/**
	 * Modifica as cores da aplicação de acordo com o modo noturno ativado
	 * ou não e cores do lblResultado.
	 * @param modLblResultadoBg - se true modifica o background do label lblResultado, se 
	 * false, seta o background atual da aplicação como background do lblResultado.
	 */
	protected void setCores(boolean modLblResultadoBg) {
		lblBanner.setBackground(CORES.getCor(isNoturno(), 0));
		lblBanner.setForeground(CORES.getCor(isNoturno(), 1));

		lblDescricao.setOpaque(true);
		lblDescricao.setBackground(CORES.getCor(isNoturno(), 4));
		lblDescricao.setForeground(CORES.getCor(isNoturno(), 6));
		lblDescricao.setBorder(new LineBorder(CORES.getCor(isNoturno(), 0)));

		if (modLblResultadoBg) lblResultado.setBackground(CORES.getCor(isNoturno(), 3));
		else lblResultado.setBackground(lblResultado.getBackground());
		lblResultado.setForeground(CORES.getCor(isNoturno(), 6));

		Component[] btns = { btnModoNoite, lblLink, btnPesquisa, btnBaixa, checkAudio, checkVideo };
		setCont(0);
		for (Component b : btns) {
			if (getCont() >= 4 && getCont() <= 5) {
				// Seta Background checkBoxes
				b.setBackground(CORES.getCor(isNoturno(), 3));
			  // Seta Background e Foreground de todos os Botões
			} else if (getCont() == 0 && btnModoNoite.isSelected())
				b.setBackground(CORES.getCor(isNoturno(), 6));
			else
				b.setBackground(CORES.getCor(isNoturno(), 5));
			b.setForeground(CORES.getCor(isNoturno(), 6));
			b.setFocusable(false);
			setCont(getCont() + 1);
		}

		getPnlTopo().setBackground(CORES.getCor(isNoturno(), 3));
		getPnlEsquerda().setBackground(CORES.getCor(isNoturno(), 3));
		pnlEsquerda1.setBackground(CORES.getCor(isNoturno(), 3));
		pnlEsquerda2.setBackground(CORES.getCor(isNoturno(), 3));
		getPnlCentro().setBackground(CORES.getCor(isNoturno(), 3));
		pnlCentro1.setBackground(CORES.getCor(isNoturno(), 3));
		pnlCentro2.setBackground(CORES.getCor(isNoturno(), 3));
		pnlCentro3.setBackground(CORES.getCor(isNoturno(), 3));
		pnlCentro4.setBackground(CORES.getCor(isNoturno(), 3));
		getPnlRodape().setBackground(CORES.getCor(isNoturno(), 3));
	}

	// METODOS ESPECIAIS
	
	public void setCont(int cont) {
		this.cont = cont;
	}

	public int getCont() {
		return cont;
	}

	/**
	 * Retorna o nome do sistema atual
	 * 
	 * @return String
	 */
	public String getSistema() {
		return sistema;
	}

	/**
	 * Retorna true se o checkbox video estiver selecionado.
	 * É uma condição para baixar em vídeo (mp4).
	 * 
	 * @return boolean video
	 */
	public boolean isVideo() {
		return video;
	}

	public void setVideo(boolean video) {
		this.video = video;
	}

	public boolean isAudio() {
		return audio;
	}

	/**
	 * Retorna true se o checkbox Audio estiver selecionado.
	 * É uma condição para baixar em áudio (mp3).
	 * 
	 * @param audio
	 */
	public void setAudio(boolean audio) {
		this.audio = audio;
	}

	/**
	 * Retorna true se o modo noturno estiver ativo, se não,
	 * retorna false.
	 * 
	 * @return boolean
	 */
	public boolean isNoturno() {
		return noturno;
	}

	public void setNoturno(boolean noturno) {
		this.noturno = noturno;
	}

	
	// Paineis (Getters)
	/**
	 * Configura e retorna o Painel superior da aplicação
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
	 * @return JPanel pnlCentro
	 */
	public JPanel getPnlCentro() {
		
		LineBorder lstBorda = new LineBorder(getForeground());
		
		if (pnlCentro == null) {
			pnlCentro = new JPanel(new GridLayout(4, 1));
			Component[] pnls = { pnlCentro1 = new JPanel(new FlowLayout()), pnlCentro2 = new JPanel(new FlowLayout()),
					pnlCentro3 = new JPanel(new GridLayout()), pnlCentro4 = new JPanel(new FlowLayout()), };

			Component[] comps = {lblLink = new JLabel(""), 
					txtLink = new JTextField(TEXTOS.getTextos(7), 48),
					txtPesquisa = new JTextField(TEXTOS.getTextos(8), 25),
					btnPesquisa = new JButton(TEXTOS.getTextos(5)),
					lstPesquisa = new JList<>(lstTitulos),
					btnBaixa = new JButton(TEXTOS.getTextos(6)) };

			// Configs dos Componentes acima
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
			// -------------------------------------
			
			// Setando o scroll da lista de resultados da pesquisa
			pnlExtra = new JScrollPane();
			pnlExtra.setViewportView(lstPesquisa);
			pnlExtra.setSize(80, 200);

			// Adicionando componentes ao painel direito
			for (int i = 0; i < comps.length; i++) {
				if (i <= 1)
					pnlCentro1.add(comps[i]);
				else if (i <= 3)
					pnlCentro2.add(comps[i]);
				else if (i == 4)
					pnlCentro3.add(pnlExtra);
				else pnlCentro4.add(comps[i]);
			}
			
			// Adicionando os paineis centrais no painel central principal
			for (Component c : pnls)
				pnlCentro.add(c);

		}
		return pnlCentro;
	}

	/**
	 * Configura e retorna o Painel inferior da aplicação
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
}
