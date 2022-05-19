package aplicacao.eventos;

import java.awt.Color;
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
        if (txtLink.getSelectionColor() == Color.lightGray 
                || txtLink.getSelectionColor() == Color.gray) {
            txtLink.setSelectionEnd(0);
            txtLink.setSelectionColor(Color.white);
            txtLink.setSelectedTextColor(Color.black);
            return;
        }
        txtLink.selectAll();
        if (!noturno) txtLink.setSelectionColor(Color.lightGray);
        else txtLink.setSelectionColor(Color.gray);
        txtLink.setSelectedTextColor(Color.white);
    }

    @Override
    protected void txtPesquisaMouseClick(MouseEvent ev) {
        if (txtPesquisa.getSelectionColor() == Color.lightGray 
                || txtPesquisa.getSelectionColor() == Color.gray) {
            txtPesquisa.setSelectionEnd(0);
            txtPesquisa.setSelectionColor(Color.white);
            txtPesquisa.setSelectedTextColor(Color.black);
            return;
        }
        txtPesquisa.selectAll();
        if (!noturno) txtPesquisa.setSelectionColor(Color.lightGray);
        else txtPesquisa.setSelectionColor(Color.gray);
        txtPesquisa.setSelectedTextColor(Color.white);
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
            if (noturno) lstPesquisa.setSelectionBackground(Color.gray);
            else lstPesquisa.setSelectionBackground(Color.lightGray);
            lstPesquisa.setSelectionForeground(Color.white);
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
        inTheExit();
    }

    @Override
    protected void itemMenuTutorialClick(ActionEvent ev) {
        JOptionPane.showMessageDialog(null, TEXTOS.pegarTexto("joptionpane.tutorial"), "Tutorial", JOptionPane.INFORMATION_MESSAGE, IMAGEM.pegarIcon("/imagens/ytdBanner.png"));
    }

    @Override
    protected void itemMenuSobreClick(ActionEvent ev) {
        JOptionPane.showMessageDialog(null, TEXTOS.pegarTexto("joptionpane.sobre"), TEXTOS.pegarTexto("joptionpane.sobre.titulo"), JOptionPane.INFORMATION_MESSAGE, IMAGEM.pegarIcon("/imagens/ytdBanner.png"));
    }

    protected void inTheExit() {
        if (downloadProgressBar.isVisible() || lblResultado.getText().startsWith(TEXTOS.pegarTexto("label.resultado.verificando.download"))
                || downloadProgressBar2.isVisible() || lblResultado2.getText().startsWith(TEXTOS.pegarTexto("label.resultado.verificando.download"))
                || downloadProgressBar3.isVisible() || lblResultado3.getText().startsWith(TEXTOS.pegarTexto("label.resultado.verificando.download"))) {
            Object[] choices = {TEXTOS.pegarTexto("joptionpane.botao.sim"), TEXTOS.pegarTexto("joptionpane.botao.nao")};
            int showConfirmDownloadOnExit = JOptionPane.showOptionDialog(null, TEXTOS.pegarTexto("joptionpane.intheexit"), "", 
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, IMAGEM.pegarIcon("/imagens/ytdBanner.png"), choices, choices[0]);
            if (showConfirmDownloadOnExit == 1) {
                ActionEvent ev = null;
                btnCancelarClick(ev);
                this.dispose();
                System.exit(0);
            } else this.dispose();
        } else {
            this.dispose();
            System.exit(0);
        }
    }
}