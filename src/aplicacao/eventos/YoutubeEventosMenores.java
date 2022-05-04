package aplicacao.eventos;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.ToolTipManager;
import javax.swing.event.AncestorEvent;

/**
 * Classe responsável pelos eventos menores.
 */
public class YoutubeEventosMenores extends YoutubeEventosPesquisa {

    @Override
    protected void txtLinkMouseClick(MouseEvent ev) {
        getTxtLink().selectAll();
    }

    @Override
    protected void txtPesquisaMouseClick(MouseEvent ev) {
        getTxtPesquisa().selectAll();
    }

    @Override
    protected void lstPesquisaAncestor(AncestorEvent ev) {
        if (!getLstPesquisa().isSelectionEmpty()) {
            setIndex(getLstPesquisa().getSelectedIndex());
            getLstPesquisa().setToolTipText("<html>" + getLstDescricao().get(getIndex()) + "</html>");
        }
    }

    @Override
    protected void lstPesquisaMouseClickItem(MouseEvent ev) {
        if (!getLstPesquisa().isSelectionEmpty() && !getLstPesquisa().isSelectedIndex(getIndex())) {
            setIndex(getLstPesquisa().getSelectedIndex());
            getLstPesquisa().setToolTipText("<html>" + getLstDescricao().get(getIndex()) + "</html>");
            ToolTipManager.sharedInstance().mouseMoved(ev);
            ToolTipManager.sharedInstance().setDismissDelay(6000);
        } else {
            getLstPesquisa().clearSelection();
            setIndex(20);
        }
    }

    @Override
    protected void btnCancelarClick(ActionEvent ev) {
        if (getDownloadProgressBar().isVisible() || getLblResultado().getText().startsWith(TEXTOS.pegarTexto("label.resultado.verificando.download"))
                || downloadProgressBar2.isVisible() || lblResultado2.getText().startsWith(TEXTOS.pegarTexto("label.resultado.verificando.download"))
                || downloadProgressBar3.isVisible() || lblResultado3.getText().startsWith(TEXTOS.pegarTexto("label.resultado.verificando.download"))) {
            if (pro.isAlive()) {
                btnBaixa.setVisible(true);
                getPro().destroy();
            }
            if (pro2.isAlive()) {
                btnBaixa2.setVisible(false);
                pro2.destroy();
            }
            if (pro3.isAlive()) {
                btnBaixa3.setVisible(false);
                pro3.destroy();
            }
            getBtnCancelar().setVisible(false);
            System.out.println(pro.isAlive());
            System.out.println(pro2.isAlive());
            System.out.println(pro3.isAlive());
        }
    }

    @Override
    protected void itemMenuExitClick(ActionEvent ev) {
        if (getBtnCancelar().isVisible()) {
            Object[] choices = {TEXTOS.pegarTexto("joptionpane.botao.oksair"), TEXTOS.pegarTexto("joptionpane.botao.cancelar")};
            int option = JOptionPane.showOptionDialog(null, TEXTOS.pegarTexto("joptionpane.sair.pergunta"), "", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, IMAGEM.pegarIcon("/imagens/ytdBanner.png"), choices, choices[0]);
            if (option == 0) {
                btnCancelarClick(ev);
                this.dispose();
                System.exit(0);
            }
        } else {
            this.dispose();
            System.exit(0);
        }
    }

    @Override
    protected void itemMenuTutorialClick(ActionEvent ev) {
        JOptionPane.showMessageDialog(null, TEXTOS.pegarTexto("joptionpane.tutorial"), "Tutorial", JOptionPane.INFORMATION_MESSAGE, IMAGEM.pegarIcon("/imagens/ytdBanner.png"));
    }

    @Override
    protected void itemMenuSobreClick(ActionEvent ev) {
        JOptionPane.showMessageDialog(null, TEXTOS.pegarTexto("joptionpane.sobre"), TEXTOS.pegarTexto("joptionpane.sobre.titulo"), JOptionPane.INFORMATION_MESSAGE, IMAGEM.pegarIcon("/imagens/ytdBanner.png"));
    }

}
