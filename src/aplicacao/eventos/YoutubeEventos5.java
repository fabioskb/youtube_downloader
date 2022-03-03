package aplicacao.eventos;

import java.io.BufferedReader;

import java.awt.event.MouseEvent;

import javax.swing.ToolTipManager;
import javax.swing.event.AncestorEvent;

/**
 * Classe responsável pelos eventos de clique do mouse.
 * Herda todos os eventos da aplicação.
 */
public class YoutubeEventos5 extends YoutubeEventos4 {

    public YoutubeEventos5(String format, String cmdLineSaida, String[] links, Process pro, BufferedReader read, BufferedReader read2) {
        super(format, cmdLineSaida, links, pro, read, read2);
    }
 
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

    
}
