package aplicacao.tela;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
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
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import metodos.YoutubeArquivo;
import metodos.YoutubeComando;
import metodos.YoutubeCores;
import metodos.YoutubeImage;
import metodos.YoutubeText;

/**
 * Classe abstrata responsável por criar os componentes, e, os getters e setters
 * dos atributos.
 */
public abstract class YoutubeTela extends JFrame {

    protected boolean isExecutando;
    protected JLabel lblBanner;
    protected JLabel lblDescricao;
    protected static final JLabel LBL_VAZIO = new JLabel("                ");
    protected static final JLabel LBL_VAZIO1 = new JLabel("                ");

    protected JCheckBox checkVideo, checkAudio;

    protected JTextField txtLink;
    protected JTextField txtPesquisa;
    protected JButton btnPesquisa;
    protected JLabel lblLink;

    protected JList<String> lstPesquisa;
    protected DefaultListModel<String> lstTitulos = new DefaultListModel<String>();
    protected List<String> lstDescricao = new LinkedList<>();
    protected JButton btnBaixa;
    protected JButton btnBaixa2;
    protected JButton btnBaixa3;
    protected JButton btnCancelar;

    protected JLabel lblResultado;
    protected JLabel lblResultado2;
    protected JLabel lblResultado3;

    protected JPanel pnlTopo;

    protected JPanel pnlEsquerda;
    //protected JPanel pnlEsquerda1;
    //protected JPanel pnlEsquerda2;
    protected JMenuBar barraMenu;
    protected JMenu menuFile;
    protected JMenuItem itemMenuExit;
    protected JMenuItem itemMenuTutorial;
    protected JMenuItem itemMenuSobre;

    protected JPanel pnlCentro;
    protected JPanel pnlCentro1;
    protected JPanel pnlCentro2;
    protected JPanel pnlCentro3;
    protected JPanel pnlCentro4;
    protected JScrollPane pnlExtra;

    protected JPanel pnlDireita;
    protected JPanel pnlDireita1;
    protected JPanel pnlDireita2;
    protected JPanel pnlDireita3;
    protected JToggleButton btnTema;
    protected JButton btnCancelProcess;

    protected JPanel pnlRodape;
    protected JPanel pnlRodape1;
    protected JPanel pnlRodape2;
    protected JPanel pnlRodape3;
    protected JProgressBar downloadProgressBar;
    protected JProgressBar downloadProgressBar2;
    protected JProgressBar downloadProgressBar3;
    protected JLabel lblProgressBar;
    protected JLabel lblProgressBar2;
    protected JLabel lblProgressBar3;

    protected JRootPane pnlPadrao;

    protected boolean noturno, video, audio;
    protected boolean isBaixando, isBaixando2, isBaixando3;
    protected String pastaPrincipal, pastaVideo, pastaAudio;
    protected String downloadPath = "";
    protected String installYoutubeSearch, installYoutubeDl;
    protected int contador, cont;
    protected YoutubeArquivo arquivoChecaPrograma, diretorioPadrao;

    protected final BorderLayout borderLayout = new BorderLayout();
    protected static final String USUARIO = System.getProperty("user.name");
    protected static final String SISTEMA = System.getProperty("os.name");
    protected static final Font FONT_BANNER = new Font(Font.SANS_SERIF, Font.BOLD, 48);
    protected static final Font FONT_DESC = new Font(Font.SANS_SERIF, Font.BOLD, 12);
    protected static final Font FONT_PROGRESS_BAR = new Font(Font.SANS_SERIF, Font.BOLD, 11);
    protected static YoutubeImage IMAGEM = new YoutubeImage();
    protected static final YoutubeCores CORES = new YoutubeCores();
    protected static final String IDIOMA = Locale.getDefault().getDisplayLanguage();
    protected final int HORA = LocalTime.now().getHour();
    protected static final YoutubeText TEXTOS = new YoutubeText(IDIOMA);
    public static final ResourceBundle TEXTOS_BR = ResourceBundle.getBundle("textos_pt_BR", new Locale("pt", "BR"));
    public static final ResourceBundle TEXTOS_US = ResourceBundle.getBundle("textos_en_US", new Locale("en", "US"));
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

            lblDescricao = new JLabel(TEXTOS.pegarTexto("descricao"), (int) CENTER_ALIGNMENT);
            lblDescricao.setFont(FONT_DESC);
            lblDescricao.setOpaque(true);

            pnlTopo.add(lblBanner);
            pnlTopo.add(lblDescricao);
        }
        return pnlTopo;
    }

    public JPanel getPnlEsquerda() {
        pnlEsquerda = new JPanel();

        barraMenu = new JMenuBar();
        barraMenu.setOpaque(false);
        menuFile = new JMenu("Menu");

        itemMenuTutorial = new JMenuItem(TEXTOS.pegarTexto("menuitem.tutorial"));
        itemMenuTutorial.setToolTipText(TEXTOS.pegarTexto("tooltip.menuitem.tutorial"));
        itemMenuSobre = new JMenuItem(TEXTOS.pegarTexto("menuitem.sobre"));
        itemMenuSobre.setToolTipText(TEXTOS.pegarTexto("tooltip.menuitem.sobre"));
        itemMenuExit = new JMenuItem(TEXTOS.pegarTexto("menuitem.sair"));
        itemMenuExit.setToolTipText(TEXTOS.pegarTexto("tooltip.menuitem.sair"));

        menuFile.add(itemMenuTutorial);
        menuFile.add(itemMenuSobre);
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
            Component[] pnls = {pnlCentro1 = new JPanel(new FlowLayout()), pnlCentro2 = new JPanel(new FlowLayout()), pnlCentro3 = new JPanel(new GridLayout()), pnlCentro4 = new JPanel(new FlowLayout())};

            Component[] comps = {lblLink = new JLabel(""),
                txtLink = new JTextField(TEXTOS.pegarTexto("fieldtext.link"), 38),
                txtPesquisa = new JTextField(TEXTOS.pegarTexto("fieldtext.pesquisa"), 25),
                btnPesquisa = new JButton(TEXTOS.pegarTexto("botao.pesquisa")),
                lstPesquisa = new JList<>(lstTitulos),
                btnBaixa = new JButton(TEXTOS.pegarTexto("botao.baixar")),
                btnBaixa2 = new JButton(TEXTOS.pegarTexto("botao.baixar2")),
                btnBaixa3 = new JButton(TEXTOS.pegarTexto("botao.baixar3")),
                btnCancelar = new JButton(TEXTOS.pegarTexto("botao.cancelar"))};

            lstPesquisa.setToolTipText(TEXTOS.pegarTexto("tooltip.lista"));
            lstPesquisa.setOpaque(false);
            lblLink.setOpaque(false);
            lblLink.setIcon(IMAGEM.pegarIcon("/imagens/link.png"));
            txtLink.setToolTipText(TEXTOS.pegarTexto("tooltip.link"));
            txtPesquisa.setToolTipText(TEXTOS.pegarTexto("tooltip.pesquisa"));
            btnBaixa.setIcon(IMAGEM.pegarIcon("/imagens/download.png"));
            btnBaixa.setToolTipText(TEXTOS.pegarTexto("tooltip.botao.baixar"));
            btnBaixa2.setIcon(IMAGEM.pegarIcon("/imagens/download.png"));
            btnBaixa2.setToolTipText(TEXTOS.pegarTexto("tooltip.botao.baixar"));
            btnBaixa3.setIcon(IMAGEM.pegarIcon("/imagens/download.png"));
            btnBaixa3.setToolTipText(TEXTOS.pegarTexto("tooltip.botao.baixar"));
            btnPesquisa.setToolTipText(TEXTOS.pegarTexto("tooltip.botao.pesquisa"));
            btnPesquisa.setIcon(IMAGEM.pegarIcon("/imagens/system_search.png"));
            btnCancelar.setIcon(IMAGEM.pegarIcon("/imagens/cancel.png"));
            btnCancelar.setToolTipText(TEXTOS.pegarTexto("tooltip.botao.cancelar"));

            pnlExtra = new JScrollPane();
            pnlExtra.setViewportView(lstPesquisa);

            for (int i = 0; i < comps.length; i++) {
                if (i <= 1) {
                    pnlCentro1.add(comps[i]);
                } else if (i <= 3) {
                    pnlCentro2.add(comps[i]);
                } else if (i == 4) {
                    pnlCentro3.add(pnlExtra);
                } //pnlCentro3.add(comps[i]);
                else {
                    pnlCentro4.add(comps[i]);
                }
            }
            for (Component c : pnls) {
                pnlCentro.add(c);
            }
        }
        return pnlCentro;
    }

    public JPanel getPnlDireita() {
        if (pnlDireita == null) {
            pnlDireita = new JPanel(new GridLayout(3, 1));
            pnlDireita1 = new JPanel(new FlowLayout());
            pnlDireita2 = new JPanel(new FlowLayout());
            pnlDireita3 = new JPanel(new FlowLayout());

            checkVideo = new JCheckBox(TEXTOS.pegarTexto("botao.video"));
            checkAudio = new JCheckBox(TEXTOS.pegarTexto("botao.audio"));
            checkVideo.setToolTipText(TEXTOS.pegarTexto("tooltip.botao.video"));
            checkAudio.setToolTipText(TEXTOS.pegarTexto("tooltip.botao.audio"));
            btnTema = new JToggleButton(TEXTOS.pegarTexto("botao.modo.noite"));
            btnTema.setToolTipText(TEXTOS.pegarTexto("tooltip.botao.noturno"));
            btnTema.setIcon(IMAGEM.pegarIcon("/imagens/night.png"));
            btnTema.setFocusable(false);
            btnCancelProcess = new JButton(TEXTOS.pegarTexto("botao.cancel.process"));
            btnCancelProcess.setToolTipText(TEXTOS.pegarTexto("tootip.botao.cancel.process"));
            btnCancelProcess.setFocusable(false);
            

            pnlDireita1.add(btnTema);
            pnlDireita2.add(checkVideo);
            pnlDireita2.add(LBL_VAZIO);
            pnlDireita2.add(checkAudio);
            pnlDireita3.add(btnCancelProcess);
            pnlDireita.add(pnlDireita1);
            pnlDireita.add(pnlDireita2);
            pnlDireita.add(pnlDireita3);
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
            pnlRodape = new JPanel(new GridLayout(3, 1));
            pnlRodape1 = new JPanel(new FlowLayout());
            pnlRodape2 = new JPanel(new FlowLayout());
            pnlRodape3 = new JPanel(new FlowLayout());

            lblResultado = new JLabel("", (int) CENTER_ALIGNMENT);
            lblResultado.setAlignmentX(CENTER_ALIGNMENT);
            lblResultado.setAlignmentY(CENTER_ALIGNMENT);
            lblResultado.setOpaque(true);
            lblResultado.setAutoscrolls(true);
            //lblResultado.setIcon(IMAGEM.pegarIcon("/imagens/invisible.png"));
            lblResultado.setToolTipText(TEXTOS.pegarTexto("tooltip.label.resultado"));
            lblResultado2 = new JLabel("", (int) CENTER_ALIGNMENT);
            lblResultado2.setAlignmentX(CENTER_ALIGNMENT);
            lblResultado2.setAlignmentY(CENTER_ALIGNMENT);
            lblResultado2.setOpaque(true);
            lblResultado2.setAutoscrolls(true);
            //lblResultado2.setIcon(IMAGEM.pegarIcon("/imagens/invisible.png"));
            lblResultado2.setToolTipText(TEXTOS.pegarTexto("tooltip.label.resultado"));
            lblResultado3 = new JLabel("", (int) CENTER_ALIGNMENT);
            lblResultado3.setAlignmentX(CENTER_ALIGNMENT);
            lblResultado3.setAlignmentY(CENTER_ALIGNMENT);
            lblResultado3.setOpaque(true);
            lblResultado3.setAutoscrolls(true);
            //lblResultado3.setIcon(IMAGEM.pegarIcon("/imagens/invisible.png"));
            lblResultado3.setToolTipText(TEXTOS.pegarTexto("tooltip.label.resultado"));

            lblProgressBar = new JLabel("");
            lblProgressBar.setAlignmentX(CENTER_ALIGNMENT);
            lblProgressBar.setAlignmentY(CENTER_ALIGNMENT);
            lblProgressBar.setOpaque(true);
            lblProgressBar.setText(TEXTOS.pegarTexto("label.resultado.baixando"));
            lblProgressBar2 = new JLabel("");
            lblProgressBar2.setAlignmentX(CENTER_ALIGNMENT);
            lblProgressBar2.setAlignmentY(CENTER_ALIGNMENT);
            lblProgressBar2.setOpaque(true);
            lblProgressBar2.setText(TEXTOS.pegarTexto("label.resultado.baixando"));
            lblProgressBar3 = new JLabel("");
            lblProgressBar3.setAlignmentX(CENTER_ALIGNMENT);
            lblProgressBar3.setAlignmentY(CENTER_ALIGNMENT);
            lblProgressBar3.setOpaque(true);
            lblProgressBar3.setText(TEXTOS.pegarTexto("label.resultado.baixando"));

            downloadProgressBar = new JProgressBar(0, 100);
            downloadProgressBar.setAlignmentX(CENTER_ALIGNMENT);
            downloadProgressBar.setAlignmentY(CENTER_ALIGNMENT);
            downloadProgressBar.setStringPainted(true);
            downloadProgressBar.setFont(FONT_PROGRESS_BAR);
            downloadProgressBar.setSize(new Dimension(200, 10));
            downloadProgressBar2 = new JProgressBar(0, 100);
            downloadProgressBar2.setAlignmentX(CENTER_ALIGNMENT);
            downloadProgressBar2.setAlignmentY(CENTER_ALIGNMENT);
            downloadProgressBar2.setStringPainted(true);
            downloadProgressBar2.setFont(FONT_PROGRESS_BAR);
            downloadProgressBar2.setSize(new Dimension(200, 10));
            downloadProgressBar3 = new JProgressBar(0, 100);
            downloadProgressBar3.setAlignmentX(CENTER_ALIGNMENT);
            downloadProgressBar3.setAlignmentY(CENTER_ALIGNMENT);
            downloadProgressBar3.setStringPainted(true);
            downloadProgressBar3.setFont(FONT_PROGRESS_BAR);
            downloadProgressBar3.setSize(new Dimension(200, 10));

            pnlRodape1.add(lblResultado);
            pnlRodape1.add(lblProgressBar);
            pnlRodape1.add(downloadProgressBar);

            pnlRodape2.add(lblResultado2);
            pnlRodape2.add(lblProgressBar2);
            pnlRodape2.add(downloadProgressBar2);

            pnlRodape3.add(lblResultado3);
            pnlRodape3.add(lblProgressBar3);
            pnlRodape3.add(downloadProgressBar3);

            pnlRodape.add(pnlRodape1);
            pnlRodape.add(pnlRodape2);
            pnlRodape.add(pnlRodape3);
        }
        return pnlRodape;
    }

    public JRootPane getPnlPadrao() {
        if (pnlPadrao == null) {
            pnlPadrao = new JRootPane();
            pnlPadrao.setLayout(borderLayout);

            pnlPadrao.add(getPnlTopo(), BorderLayout.NORTH);
            pnlPadrao.add(getPnlEsquerda(), BorderLayout.WEST);
            pnlPadrao.add(getPnlCentro(), BorderLayout.CENTER);
            pnlPadrao.add(getPnlDireita(), BorderLayout.EAST);
            pnlPadrao.add(getPnlRodape(), BorderLayout.SOUTH);
        }

        return pnlPadrao;
    }
}
