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

        getLblBanner().setBackground(CORES.pegarCor(isNoturno(), 0));
        getLblBanner().setForeground(CORES.pegarCor(isNoturno(), 1));

        getLblDescricao().setOpaque(true);
        getLblDescricao().setBackground(CORES.pegarCor(isNoturno(), 4));
        getLblDescricao().setForeground(CORES.pegarCor(isNoturno(), 6));

        getTxtLink().setOpaque(true);
        getTxtLink().setBackground(CORES.pegarCor(isNoturno(), 10));
        getTxtPesquisa().setOpaque(true);
        getTxtPesquisa().setBackground(CORES.pegarCor(isNoturno(), 10));
        getLstPesquisa().setOpaque(true);
        getLstPesquisa().setBackground(CORES.pegarCor(isNoturno(), 10));

        downloadProgressBar.setBackground(CORES.pegarCor(isNoturno(), 3));
        downloadProgressBar.setForeground(CORES.pegarCor(isNoturno(), 9));
        downloadProgressBar2.setBackground(CORES.pegarCor(isNoturno(), 3));
        downloadProgressBar2.setForeground(CORES.pegarCor(isNoturno(), 9));
        downloadProgressBar3.setBackground(CORES.pegarCor(isNoturno(), 3));
        downloadProgressBar3.setForeground(CORES.pegarCor(isNoturno(), 9));

        lblResultado.setBackground(CORES.pegarCor(isNoturno(), 4));
        lblResultado2.setBackground(CORES.pegarCor(isNoturno(), 4));
        lblResultado3.setBackground(CORES.pegarCor(isNoturno(), 4));
        if (getLblResultado().getText() == TEXTOS.pegarTexto("label.resultado.pesquisando") || getLblResultado().getText() == TEXTOS.pegarTexto("label.resultado.verificando.download")) {
            getLblResultado().setForeground(CORES.pegarCor(isNoturno(), 6));
        }

        getBarraMenu().setBackground(CORES.pegarCor(isNoturno(), 3));
        getMenuFile().setBackground(CORES.pegarCor(isNoturno(), 3));
        getMenuFile().setForeground(CORES.pegarCor(isNoturno(), 4));

        Component[] paineis = {
            getPnlTopo(), getPnlCentro(), getPnlCentro1(),
            getPnlCentro2(), getPnlCentro3(), getPnlCentro4(), getPnlDireita(),
            getPnlDireita1(), getPnlDireita2(), getPnlRodape(), pnlRodape1,
            pnlRodape2, pnlRodape3, getPnlPadrao()
        };
        setContador(0);
        for (Component jPanel : paineis) {
            if (getContador() >= 6 && getContador() <= 12) {
                jPanel.setBackground(CORES.pegarCor(isNoturno(), 4));
            } else {
                jPanel.setBackground(CORES.pegarCor(isNoturno(), 3));
            }
            setContador(getContador() + 1);
        }

        Component[] buttons = {getBtnModoNoite(), getLblLink(), getBtnPesquisa(),
            getBtnBaixa(), btnBaixa2, btnBaixa3, getCheckAudio(), getCheckVideo(),
            getBtnCancelar()};
        setCont(0);
        for (Component button : buttons) {
            if (getCont() == 0 && getBtnModoNoite().isSelected()) {
                button.setBackground(CORES.pegarCor(isNoturno(), 6));
            } else {
                button.setBackground(CORES.pegarCor(isNoturno(), 5));
                button.setForeground(CORES.pegarCor(isNoturno(), 6));
            }
            setCont(getCont() + 1);
        }

        UIManager.put("OptionPane.background", CORES.pegarCor(isNoturno(), 3));
        UIManager.put("OptionPane.messageForeground", CORES.pegarCor(isNoturno(), 6));
        UIManager.put("Panel.background", CORES.pegarCor(isNoturno(), 3));
        UIManager.put("Button.background", CORES.pegarCor(isNoturno(), 5));
        UIManager.put("Button.foreground", CORES.pegarCor(isNoturno(), 6));

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
