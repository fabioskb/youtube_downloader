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
        txtLink.selectAll();
    }

    @Override
    protected void txtPesquisaMouseClick(MouseEvent ev) {
        txtPesquisa.selectAll();
    }

    @Override
    protected void lstPesquisaAncestor(AncestorEvent ev) {
        if (!lstPesquisa.isSelectionEmpty()) {
            index = lstPesquisa.getSelectedIndex();
            lstPesquisa.setToolTipText("<html>" + lstDescricao.get(index) + "</html>");
        }
    }

    @Override
    protected void lstPesquisaMouseClickItem(MouseEvent ev) {
        if (!lstPesquisa.isSelectionEmpty() && !lstPesquisa.isSelectedIndex(index)) {
            index = lstPesquisa.getSelectedIndex();
            lstPesquisa.setToolTipText("<html>" + lstDescricao.get(index) + "</html>");
            ToolTipManager.sharedInstance().mouseMoved(ev);
            ToolTipManager.sharedInstance().setDismissDelay(6000);
        } else {
            lstPesquisa.clearSelection();
            index = 20;
        }
    }

    @Override
    protected void btnCancelarClick(ActionEvent ev) {
        if (downloadProgressBar.isVisible() || lblResultado.getText().startsWith(TEXTOS.pegarTexto("label.resultado.verificando.download"))
                || downloadProgressBar2.isVisible() || lblResultado2.getText().startsWith(TEXTOS.pegarTexto("label.resultado.verificando.download"))
                || downloadProgressBar3.isVisible() || lblResultado3.getText().startsWith(TEXTOS.pegarTexto("label.resultado.verificando.download"))) {
            btnBaixa.setVisible(true);
            btnBaixa2.setVisible(false);
            btnBaixa3.setVisible(false);
            btnCancelar.setVisible(false);
            pro.destroy();
            pro2.destroy();
            pro3.destroy();
        }
    }

    @Override
    protected void itemMenuExitClick(ActionEvent ev) {
        if (btnCancelar.isVisible()) {
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
