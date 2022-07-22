package aplicacao.tela;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.concurrent.TimeUnit;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.UIManager;
import javax.swing.event.AncestorEvent;
import javax.swing.plaf.metal.MetalToggleButtonUI;

/**
 * Classe responsável pelo método configurarCores(), que configura a maioria das
 * cores dos componentes na aplicação e sobrescreve
 */
@SuppressWarnings("serial")
public class YoutubeTelaCoresEEventos extends YoutubeTelaComplemento {

    /**
     * Modifica as cores da aplicação de acordo com o modo noturno ativado ou
     * não e cores do lblResultado.
     *
     */
    public void configurarCores() {

        executeColorBtnCancelPro();

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
        if (lblResultado.getText()
                .equals(TEXTOS.pegarTexto("label.resultado.pesquisando"))
                || lblResultado.getText().equals(TEXTOS.pegarTexto("label.resultado.resolucao"))) {
            lblResultado.setForeground(
                    CORES.pegarCor(noturno, "Botoes e descricao FG"));
        } else {
            if (noturno) {
                lblResultado
                        .setForeground(lblResultado.getForeground().brighter());
            } else {
                lblResultado
                        .setForeground(lblResultado.getForeground().darker());
            }
        }
        lblResultado1.setBackground(CORES.pegarCor(noturno, 4));
        lblResultado2.setBackground(CORES.pegarCor(noturno, 4));
        lblResultado3.setBackground(CORES.pegarCor(noturno, 4));

        if (lblResultado1.getText()
                .equals(verifyingDownload + " " + tituloFormatado)
                || lblResultado1.getText().equals(
                        TEXTOS.pegarTexto("label.resultado.pegandotitulo"))) {
            lblResultado1.setForeground(
                    CORES.pegarCor(noturno, "Botoes e descricao FG"));
        }
        if (lblResultado2.getText()
                .equals(verifyingDownload + " " + tituloFormatado2)
                || lblResultado2.getText().equals(
                        TEXTOS.pegarTexto("label.resultado.pegandotitulo"))) {
            lblResultado2.setForeground(
                    CORES.pegarCor(noturno, "Botoes e descricao FG"));
        }
        if (lblResultado3.getText()
                .equals(verifyingDownload + " " + tituloFormatado3)
                || lblResultado3.getText().equals(
                        TEXTOS.pegarTexto("label.resultado.pegandotitulo"))) {
            lblResultado3.setForeground(
                    CORES.pegarCor(noturno, "Botoes e descricao FG"));
        }

        barraMenu.setForeground(CORES.pegarCor(noturno, 3));
        menuFile.setBackground(CORES.pegarCor(noturno, 3));
        menuFile.setForeground(CORES.pegarCor(noturno, 3));
        itemMenuTutorial.setBackground(CORES.pegarCor(noturno, "Descricao BG"));
        itemMenuSobre.setBackground(CORES.pegarCor(noturno, "Descricao BG"));
        itemMenuExit.setBackground(CORES.pegarCor(noturno, "Descricao BG"));
        itemMenuTutorial.setForeground(
                CORES.pegarCor(noturno, "Botoes e descricao FG"));
        itemMenuSobre.setForeground(
                CORES.pegarCor(noturno, "Botoes e descricao FG"));
        itemMenuExit.setForeground(
                CORES.pegarCor(noturno, "Botoes e descricao FG"));

        Component[] paineis = {pnlTopo, pnlCentro, pnlCentro1, pnlCentro2,
            pnlCentro3, pnlCentro4, pnlDireita, pnlDireita1, pnlDireita2, pnlDireita3,
            pnlDireita4, pnlRodape, pnlRodape1, pnlRodape2, pnlRodape3,
            pnlRodape4, pnlPadrao, jTopFlowPanel, pnlEsquerda};
        contadorLinksTitulos = 0;
        for (Component jPanel : paineis) {
            if (contadorLinksTitulos >= 6 && contadorLinksTitulos <= 18) {
                jPanel.setBackground(CORES.pegarCor(noturno, 4));
            } else {
                jPanel.setBackground(CORES.pegarCor(noturno, 3));
            }
            contadorLinksTitulos++;
        }

        Component[] clicaveis = {btnTema, lblLink, btnPesquisa, btnBaixa,
            btnBaixa2, btnBaixa3, checkAudio, checkVideo, btnCancelar,
            btnCancelProcess, menuBarRadioBtn, menuRadioBtn};
        cont = 0;
        for (Component button : clicaveis) {
            if (cont == 0 && btnTema.isSelected()) {
                button.setBackground(CORES.pegarCor(noturno, 6));
                button.setForeground(Color.black);
            } else if (cont == 9 && scheduleColorBtnCancelPro.isCancelled()) {
                button.setBackground(CORES.pegarCor(noturno, 3));
                button.setForeground(CORES.pegarCor(noturno, 6));
            } else {
                if (cont != 9) {
                    button.setBackground(CORES.pegarCor(noturno, 5));
                    button.setForeground(CORES.pegarCor(noturno, 6));
                } else {
                    for (JRadioButtonMenuItem bt : radBtnList) {
                        bt.setBackground(CORES.pegarCor(noturno, 5));
                        bt.setForeground(CORES.pegarCor(noturno, 6));
                    }
                    btnTema.setBackground(Color.darkGray);
                    if (!btnTema.isSelected()) {
                        btnTema.setForeground(Color.white);
                    }
                }
            }
            cont++;
        }

        UIManager.put("OptionPane.background", CORES.pegarCor(noturno, 3));
        UIManager.put("OptionPane.messageForeground",
                CORES.pegarCor(noturno, 6));
        UIManager.put("Panel.background", CORES.pegarCor(noturno, 3));
        UIManager.put("Button.background", CORES.pegarCor(noturno, 5));
        UIManager.put("Button.foreground", CORES.pegarCor(noturno, 6));

    }

    public void setBtnTemaCor() {
        btnTema.setUI(new MetalToggleButtonUI() {
            @Override
            protected Color getSelectColor() {
                return Color.lightGray;
            }

        });
    }

    private void executeColorBtnCancelPro() {
        if (scheduleColorBtnCancelPro.isCancelled() || scheduleColorBtnCancelPro.isDone()) {
            scheduleColorBtnCancelPro = EXECUTOR.scheduleWithFixedDelay(() -> {
                if (CMD.getPro().isAlive()
                        || CMD.getProDownloadTitle().isAlive()) {
                    btnCancelProcess.setVisible(true);
                    btnCancelProcess.setBackground(Color.RED);
//					btnCancelProcess.setForeground(Color.white);
                    CMD.sleep(0.5);
                    btnCancelProcess.setBackground(CORES.pegarCor(noturno, 3));
                    btnCancelProcess.setForeground(CORES.pegarCor(noturno, 6));
                }
            }, 0, 1, TimeUnit.SECONDS);
        }
    }

    public void cancelScheduleBtnCancelPro() {
        EXECUTOR.schedule(() -> {
            btnCancelProcess.setVisible(false);
            scheduleColorBtnCancelPro.cancel(false);
        }, 0, TimeUnit.MILLISECONDS);
    }

    public void disableRadioButtons() {
        if (radBtnList != null) {
            for (JRadioButtonMenuItem jRadioButtonMenuItem : radBtnList) {
                if (jRadioButtonMenuItem.isVisible()) {
                    jRadioButtonMenuItem.setVisible(false);
                    if (jRadioButtonMenuItem.isSelected()) {
                        jRadioButtonMenuItem.setSelected(false);
                    }
                }
            }
        }
    }

    @Override
    protected void btnModoTemaClick(ActionEvent ev) {
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

    @Override
    protected void selectRadioMenuItem1(ActionEvent ev) {
    }

    @Override
    protected void selectRadioMenuItem2(ActionEvent ev) {
    }

    @Override
    protected void selectRadioMenuItem3(ActionEvent ev) {
    }

    @Override
    protected void selectRadioMenuItem4(ActionEvent ev) {
    }

    @Override
    protected void selectRadioMenuItem5(ActionEvent ev) {
    }

    @Override
    protected void selectRadioMenuItem6(ActionEvent ev) {

    }

    @Override
    protected void selectRadioMenuItem7(ActionEvent ev) {

    }

    @Override
    protected void selectRadioMenuItem8(ActionEvent ev) {

    }

    @Override
    protected void selectRadioMenuItem9(ActionEvent ev) {

    }

    @Override
    protected void selectRadioMenuItem10(ActionEvent ev) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected void selectRadioMenuItem11(ActionEvent ev) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected void selectRadioMenuItem12(ActionEvent ev) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected void selectRadioMenuItem13(ActionEvent ev) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected void selectRadioMenuItem14(ActionEvent ev) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected void selectRadioMenuItem15(ActionEvent ev) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected void selectRadioMenuItem16(ActionEvent ev) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected void selectRadioMenuItem17(ActionEvent ev) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected void selectRadioMenuItem18(ActionEvent ev) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected void selectRadioMenuItem19(ActionEvent ev) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected void selectRadioMenuItem20(ActionEvent ev) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
