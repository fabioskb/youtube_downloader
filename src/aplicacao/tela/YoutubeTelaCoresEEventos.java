package aplicacao.tela;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import javax.swing.UIManager;
import javax.swing.event.AncestorEvent;

/**
 * Classe responsável pelo método configurarCores(), que configura a maioria das
 * cores dos componentes na aplicação e sobrescreve
 */
public class YoutubeTelaCoresEEventos extends YoutubeTelaComplemento {

    /**
     * Modifica as cores da aplicação de acordo com o modo noturno ativado ou
     * não e cores do lblResultado.
     *
     * @param modLblResultadoBg - se true modifica o background do label
     * lblResultado, se false, seta o background atual da aplicação como
     * background do lblResultado.
     */
    public void configurarCores() {

        lblBanner.setBackground(CORES.pegarCor(noturno, 0));
        lblBanner.setForeground(CORES.pegarCor(noturno, 1));

        lblDescricao.setOpaque(true);
        lblDescricao.setBackground(CORES.pegarCor(noturno, 4));
        lblDescricao.setForeground(CORES.pegarCor(noturno, 6));

        txtLink.setOpaque(true);
        txtLink.setBackground(CORES.pegarCor(noturno, 10));
        txtPesquisa.setOpaque(true);
        txtPesquisa.setBackground(CORES.pegarCor(noturno, 10));
        lstPesquisa.setOpaque(true);
        lstPesquisa.setBackground(CORES.pegarCor(noturno, 10));

        downloadProgressBar.setBackground(CORES.pegarCor(noturno, 3));
        downloadProgressBar.setForeground(CORES.pegarCor(noturno, 9));
        downloadProgressBar2.setBackground(CORES.pegarCor(noturno, 3));
        downloadProgressBar2.setForeground(CORES.pegarCor(noturno, 9));
        downloadProgressBar3.setBackground(CORES.pegarCor(noturno, 3));
        downloadProgressBar3.setForeground(CORES.pegarCor(noturno, 9));

        lblResultado.setBackground(CORES.pegarCor(noturno, 4));
        lblResultado2.setBackground(CORES.pegarCor(noturno, 4));
        lblResultado3.setBackground(CORES.pegarCor(noturno, 4));
        if (lblResultado.getText() == TEXTOS.pegarTexto("label.resultado.pesquisando") 
           || lblResultado.getText() == TEXTOS.pegarTexto("label.resultado.verificando.download")
           || lblResultado2.getText() == TEXTOS.pegarTexto("label.resultado.verificando.download")
           || lblResultado3.getText() == TEXTOS.pegarTexto("label.resultado.verificando.download")) {
            lblResultado.setForeground(CORES.pegarCor(noturno, 6));
            lblResultado2.setForeground(CORES.pegarCor(noturno, 6));
            lblResultado3.setForeground(CORES.pegarCor(noturno, 6));
        }

        barraMenu.setBackground(CORES.pegarCor(noturno, 3));
        menuFile.setBackground(CORES.pegarCor(noturno, 3));
        menuFile.setForeground(CORES.pegarCor(noturno, 4));

        Component[] paineis = {
            pnlTopo, pnlCentro, pnlCentro1, pnlCentro2, pnlCentro3, pnlCentro4, 
            pnlDireita, pnlDireita1, pnlDireita2, pnlRodape, pnlRodape1, 
            pnlRodape2, pnlRodape3, pnlPadrao
        };
        contador = 0;
        for (Component jPanel : paineis) {
            if (contador >= 6 && contador <= 12) {
                jPanel.setBackground(CORES.pegarCor(noturno, 4));
            } else {
                jPanel.setBackground(CORES.pegarCor(noturno, 3));
            }
            contador++;
        }

        Component[] buttons = {btnModoNoite, lblLink, btnPesquisa,
            btnBaixa, btnBaixa2, btnBaixa3, checkAudio, checkVideo,
            btnCancelar};
        cont = 0;
        for (Component button : buttons) {
            if (cont == 0 && btnModoNoite.isSelected()) {
                button.setBackground(CORES.pegarCor(noturno, 6));
            } else {
                button.setBackground(CORES.pegarCor(noturno, 5));
                button.setForeground(CORES.pegarCor(noturno, 6));
            }
            cont++;
        }

        UIManager.put("OptionPane.background", CORES.pegarCor(noturno, 3));
        UIManager.put("OptionPane.messageForeground", CORES.pegarCor(noturno, 6));
        UIManager.put("Panel.background", CORES.pegarCor(noturno, 3));
        UIManager.put("Button.background", CORES.pegarCor(noturno, 5));
        UIManager.put("Button.foreground", CORES.pegarCor(noturno, 6));

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
