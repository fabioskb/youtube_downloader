package aplicacao.tela;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import javax.swing.JToggleButton;
import javax.swing.UIManager;
import javax.swing.event.AncestorEvent;
import javax.swing.plaf.metal.MetalToggleButtonUI;

/**
 * Classe responsável pelo método configurarCores(), que configura a maioria das
 * cores dos componentes na aplicação e sobrescreve
 */
public class YoutubeTelaCoresEEventos extends YoutubeTelaComplemento {

    /**
     * Modifica as cores da aplicação de acordo com o modo noturno ativado ou
     * não e cores do lblResultado.
     *
     */
    public void configurarCores() {

        lblBanner.setBackground(CORES.pegarCor(noturno, 0));
        lblBanner.setForeground(CORES.pegarCor(noturno, 1));

        lblDescricao.setBackground(CORES.pegarCor(noturno, "Descricao BG"));
        lblDescricao.setForeground(CORES.pegarCor(noturno, "Padrao BG"));

        txtLink.setOpaque(true);
        txtLink.setBackground(CORES.pegarCor(noturno, 10));
        txtPesquisa.setOpaque(true);
        txtPesquisa.setBackground(CORES.pegarCor(noturno, 10));
        lstPesquisa.setOpaque(true);
        lstPesquisa.setBackground(CORES.pegarCor(noturno, 10));
        lstPesquisa.setSelectionBackground(Color.gray);
        lstPesquisa.setSelectionForeground(Color.white);

        downloadProgressBar.setBackground(CORES.pegarCor(noturno, 3));
        downloadProgressBar.setForeground(CORES.pegarCor(noturno, 11));
        downloadProgressBar2.setBackground(CORES.pegarCor(noturno, 3));
        downloadProgressBar2.setForeground(CORES.pegarCor(noturno, 11));
        downloadProgressBar3.setBackground(CORES.pegarCor(noturno, 3));
        downloadProgressBar3.setForeground(CORES.pegarCor(noturno, 11));

        lblResultado.setBackground(CORES.pegarCor(noturno, 4));
        if (lblResultado.getText().equals(TEXTOS.pegarTexto("label.resultado.pesquisando"))) {
            lblResultado.setForeground(CORES.pegarCor(noturno, "Botoes e descricao FG"));
        }
        lblResultado1.setBackground(CORES.pegarCor(noturno, 4));
        lblResultado2.setBackground(CORES.pegarCor(noturno, 4));
        lblResultado3.setBackground(CORES.pegarCor(noturno, 4));
        if (lblResultado1.getText().equals(TEXTOS.pegarTexto("label.resultado.verificando.download"))
                || lblResultado2.getText().equals(TEXTOS.pegarTexto("label.resultado.verificando.download"))
                || lblResultado3.getText().equals(TEXTOS.pegarTexto("label.resultado.verificando.download"))
                || lblResultado1.getText().equals(TEXTOS.pegarTexto("label.resultado.pegandotitulo"))
                || lblResultado2.getText().equals(TEXTOS.pegarTexto("label.resultado.pegandotitulo"))
                || lblResultado3.getText().equals(TEXTOS.pegarTexto("label.resultado.pegandotitulo"))) {
            lblResultado1.setForeground(CORES.pegarCor(noturno, 6));
            lblResultado2.setForeground(CORES.pegarCor(noturno, 6));
            lblResultado3.setForeground(CORES.pegarCor(noturno, 6));
        }

        barraMenu.setForeground(CORES.pegarCor(noturno, 3));
        menuFile.setBackground(CORES.pegarCor(noturno, 3));
        menuFile.setForeground(CORES.pegarCor(noturno, 3));
        itemMenuTutorial.setBackground(CORES.pegarCor(noturno, "Descricao BG"));
        itemMenuSobre.setBackground(CORES.pegarCor(noturno, "Descricao BG"));
        itemMenuExit.setBackground(CORES.pegarCor(noturno, "Descricao BG"));
        itemMenuTutorial.setForeground(CORES.pegarCor(noturno, "Botoes e descricao FG"));
        itemMenuSobre.setForeground(CORES.pegarCor(noturno, "Botoes e descricao FG"));
        itemMenuExit.setForeground(CORES.pegarCor(noturno, "Botoes e descricao FG"));

        Component[] paineis = {
            pnlTopo, pnlCentro, pnlCentro1, pnlCentro2, pnlCentro3, pnlCentro4,
            pnlDireita, pnlDireita1, pnlDireita2, pnlDireita3, pnlRodape, pnlRodape1,
            pnlRodape2, pnlRodape3, pnlRodape4, pnlPadrao, jTopFlowPanel, pnlEsquerda
        };
        contador = 0;
        for (Component jPanel : paineis) {
            if (contador >= 6  && contador <= 17) {
                jPanel.setBackground(CORES.pegarCor(noturno, 4));
            } else {
                jPanel.setBackground(CORES.pegarCor(noturno, 3));
            }
            contador++;
        }

        Component[] buttons = {btnTema, lblLink, btnPesquisa,
            btnBaixa, btnBaixa2, btnBaixa3, checkAudio, checkVideo,
            btnCancelar, btnCancelProcess};
        cont = 0;
        for (Component button : buttons) {
            if (cont == 0 && btnTema.isSelected()) {
                button.setBackground(CORES.pegarCor(noturno, 6));
                btnTema.setUI(new MetalToggleButtonUI() {
                    @Override
                    protected Color getSelectColor() {
                        return Color.lightGray;
                    }
                });
                button.setForeground(Color.black);
            } else if (cont == 9) {
                button.setBackground(CORES.pegarCor(noturno, 3));
                button.setForeground(CORES.pegarCor(noturno, 6));
            } else {
                button.setBackground(CORES.pegarCor(noturno, 5));
                button.setForeground(CORES.pegarCor(noturno, 6));
                btnTema.setBackground(Color.darkGray);
                if (!btnTema.isSelected()) {
                    btnTema.setForeground(Color.white);
                }
            }
            cont++;
        }

        UIManager.put("OptionPane.background", CORES.pegarCor(noturno, 3));
        UIManager.put("OptionPane.messageForeground", CORES.pegarCor(noturno, 6));
        UIManager.put("Panel.background", CORES.pegarCor(noturno, 3));
        UIManager.put("Button.background", CORES.pegarCor(noturno, 5));
        UIManager.put("Button.foreground", CORES.pegarCor(noturno, 6));

        colorBtnCancelPro = new Thread(() -> {
            while (true) {
                if (CMD.getPro().isAlive() || CMD.getProDownloadTitle().isAlive()) {
                    btnCancelProcess.setBackground(Color.RED);
                    btnCancelProcess.setForeground(Color.white);
                    CMD.sleep(1.5);
                    btnCancelProcess.setBackground(CORES.pegarCor(noturno, 3));
                    btnCancelProcess.setForeground(CORES.pegarCor(noturno, 6));
                }
            }
        });
        //212 maratona java virado no jiraya
        colorBtnCancelPro.start();
    }

    @Override
    protected void btnModoNoiteClick(ActionEvent ev) {
    }

    @Override
    protected void checkVideoClick(ActionEvent ev) {
    }

    @Override
    protected void checkAudioClick(ActionEvent ev) {
    }

    @Override
    protected void btnBaixarClick(ActionEvent ev) {
    }

    @Override
    protected void btnPesquisaClick(ActionEvent ev) {
    }

    @Override
    protected void txtLinkMouseClick(MouseEvent ev) {
    }

    @Override
    protected void txtPesquisaMouseClick(MouseEvent ev) {
    }

    @Override
    protected void lstPesquisaAncestor(AncestorEvent ev) {
    }

    @Override
    protected void lstPesquisaMouseClickItem(MouseEvent ev) {
    }

    @Override
    protected void btnCancelarClick(ActionEvent ev) {
    }

    @Override
    protected void btnCancelProcessClick(ActionEvent ev) {
    }

    @Override
    protected void itemMenuExitClick(ActionEvent ev) {
    }

    @Override
    protected void itemMenuTutorialClick(ActionEvent ev) {
    }

    @Override
    protected void itemMenuSobreClick(ActionEvent ev) {
    }

    @Override
    protected void btnBaixar2Click(ActionEvent ev) {
    }

    @Override
    protected void btnBaixar3Click(ActionEvent ev) {
    }
}
