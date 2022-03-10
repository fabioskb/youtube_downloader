package aplicacao.eventos;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.ToolTipManager;
import javax.swing.event.AncestorEvent;

/**
 * Classe responsável pelos eventos de clique do mouse.
 * Herda Eventos.
 */
public class YoutubeEventos5 extends YoutubeEventos4 {

 
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
			getLstPesquisa().setToolTipText("<html>"+getLstDescricao().get(getIndex())+"</html>");
		}
	}
	
	@Override
	protected void lstPesquisaMouseClickItem(MouseEvent ev) {
		if (!getLstPesquisa().isSelectionEmpty() && !getLstPesquisa().isSelectedIndex(getIndex())) {
			setIndex(getLstPesquisa().getSelectedIndex());
			getLstPesquisa().setToolTipText("<html>"+getLstDescricao().get(getIndex())+"</html>");
			ToolTipManager.sharedInstance().mouseMoved(ev);
			ToolTipManager.sharedInstance().setDismissDelay(6000);
		} else {
			getLstPesquisa().clearSelection();
			setIndex(20);
		}
	}

	@Override
	protected void btnCancelarClick(ActionEvent ev) {
		if (getDownloadProgressBar().isVisible() || getLblResultado().getText().startsWith(TEXTOS.getTexto(31)) || getLblResultado().getText().equals(TEXTOS.getTexto(25))) {
			getPro().destroy();
			getBtnCancelar().setVisible(false);
			getBtnBaixa().setVisible(true);
		}
	}
    
}
