package aplicacao.tela;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.event.AncestorEvent;

/**
 * Classe responsável pelo método configurarCores(), que configura 
 * a maioria das cores dos componentes na aplicação e sobrescreve
 */
public class YoutubeTelaCoresEEventos extends YoutubeTelaComplemento {
    /**
	 * Modifica as cores da aplicação de acordo com o modo noturno ativado
	 * ou não e cores do lblResultado.
	 * @param modLblResultadoBg - se true modifica o background do label lblResultado, se 
	 * false, seta o background atual da aplicação como background do lblResultado.
	 */
	public void configurarCores(boolean modLblResultadoBg) {
		
		getLblBanner().setBackground(CORES.pegarCor(isNoturno(), 0));
		getLblBanner().setForeground(CORES.pegarCor(isNoturno(), 1));

		getLblDescricao().setOpaque(true);
		getLblDescricao().setBackground(CORES.pegarCor(isNoturno(), 4));
		getLblDescricao().setForeground(CORES.pegarCor(isNoturno(), 6));
		getLblDescricao().setBorder(new LineBorder(CORES.pegarCor(isNoturno(), 0)));

		getTxtLink().setOpaque(true);
		getTxtLink().setBackground(CORES.pegarCor(isNoturno(), 10));
		getTxtPesquisa().setOpaque(true);
		getTxtPesquisa().setBackground(CORES.pegarCor(isNoturno(), 10));
		getLstPesquisa().setOpaque(true);
		getLstPesquisa().setBackground(CORES.pegarCor(isNoturno(), 10));

		getDownloadProgressBar().setBackground(CORES.pegarCor(isNoturno(), 3));
		getDownloadProgressBar().setForeground(CORES.pegarCor(isNoturno(), 4));
		getLblProgressBar().setBackground(CORES.pegarCor(isNoturno(), 3));
		getLblProgressBar().setForeground(CORES.pegarCor(isNoturno(), 4));

		if (modLblResultadoBg) getLblResultado().setBackground(CORES.pegarCor(isNoturno(), 3));
		else getLblResultado().setBackground(getLblResultado().getBackground());
		getLblResultado().setForeground(CORES.pegarCor(isNoturno(), 6));

		getBarraMenu().setBackground(CORES.pegarCor(isNoturno(), 3));
		getMenuFile().setBackground(CORES.pegarCor(isNoturno(), 3));
		getMenuFile().setForeground(CORES.pegarCor(isNoturno(), 4));

		Component[] buttons = { getBtnModoNoite(), getLblLink(), getBtnPesquisa(), getBtnBaixa(), getCheckAudio(), getCheckVideo(), getBtnCancelar() };
		setCont(0);
		for (Component button : buttons) {
			if (getCont() == 0 && getBtnModoNoite().isSelected())
				button.setBackground(CORES.pegarCor(isNoturno(), 6));
			else {
				button.setBackground(CORES.pegarCor(isNoturno(), 5));	
				button.setForeground(CORES.pegarCor(isNoturno(), 6));
				button.setFocusable(false);
			}
			setCont(getCont() + 1);
		}
		
		JPanel[] paineis = {
			getPnlTopo(), getPnlEsquerda(), getPnlCentro(), getPnlCentro1(), 
			getPnlCentro2(), getPnlCentro3(), getPnlCentro4(), getPnlDireita(), 
			getPnlDireita1(), getPnlDireita2(), getPnlRodape()
		};
		for (JPanel jPanel : paineis) {
			jPanel.setBackground(CORES.pegarCor(isNoturno(), 3));
		}
	}

    @Override
	protected void btnModoNoiteClick(ActionEvent ev) {}
    @Override
    protected void checkVideoClick(ActionEvent ev) {}
    @Override
    protected void checkAudioClick(ActionEvent ev) {}
    @Override
    protected void btnBaixarClick(ActionEvent ev) {}
    @Override
    protected void btnPesquisaClick(ActionEvent ev) {}
    @Override
    protected void txtLinkMouseClick(MouseEvent ev) {}
    @Override
    protected void txtPesquisaMouseClick(MouseEvent ev) {}
    @Override
    protected void lstPesquisaAncestor(AncestorEvent ev) {}
    @Override
    protected void lstPesquisaMouseClickItem(MouseEvent ev) {}
	@Override
	protected void btnCancelarClick(ActionEvent ev) {}
	@Override
	protected void itemMenuExitClick(ActionEvent ev) {}
}
