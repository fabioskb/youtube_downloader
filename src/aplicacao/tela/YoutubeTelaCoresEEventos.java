package aplicacao.tela;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.border.LineBorder;
import javax.swing.event.AncestorEvent;

/**
 * Classe responsável pelo método configurarCores(), que configura 
 * a maioria das cores dos componentes na aplicação e sobrescreve
 * os eventos da classe abstrata YoutubeTelaEventos.
 * Herda TelaEventos.
 */
public class YoutubeTelaCoresEEventos extends YoutubeTelaComplemento {
    /**
	 * Modifica as cores da aplicação de acordo com o modo noturno ativado
	 * ou não e cores do lblResultado.
	 * @param modLblResultadoBg - se true modifica o background do label lblResultado, se 
	 * false, seta o background atual da aplicação como background do lblResultado.
	 */
	public void configurarCores(boolean modLblResultadoBg) {
		getLblBanner().setBackground(CORES.getCor(isNoturno(), 0));
		getLblBanner().setForeground(CORES.getCor(isNoturno(), 1));

		getLblDescricao().setOpaque(true);
		getLblDescricao().setBackground(CORES.getCor(isNoturno(), 4));
		getLblDescricao().setForeground(CORES.getCor(isNoturno(), 6));
		getLblDescricao().setBorder(new LineBorder(CORES.getCor(isNoturno(), 0)));

		getTxtLink().setOpaque(true);
		getTxtLink().setBackground(CORES.getCor(isNoturno(), 10));
		getTxtPesquisa().setOpaque(true);
		getTxtPesquisa().setBackground(CORES.getCor(isNoturno(), 10));
		getLstPesquisa().setOpaque(true);
		getLstPesquisa().setBackground(CORES.getCor(isNoturno(), 10));

		if (modLblResultadoBg) getLblResultado().setBackground(CORES.getCor(isNoturno(), 3));
		else getLblResultado().setBackground(getLblResultado().getBackground());
		getLblResultado().setForeground(CORES.getCor(isNoturno(), 6));

		Component[] btns = { getBtnModoNoite(), getLblLink(), getBtnPesquisa(), getBtnBaixa(), getCheckAudio(), getCheckVideo(), getBtnCancelar() };
		setCont(0);
		for (Component b : btns) {
			if (getCont() >= 4 && getCont() <= 5) {
				// Seta Background checkBoxes
				b.setBackground(CORES.getCor(isNoturno(), 3));
			  // Seta Background e Foreground de todos os Botões
			} else if (getCont() == 0 && getBtnModoNoite().isSelected())
				b.setBackground(CORES.getCor(isNoturno(), 6));
			else
				b.setBackground(CORES.getCor(isNoturno(), 5));
			b.setForeground(CORES.getCor(isNoturno(), 6));
			b.setFocusable(false);
			setCont(getCont() + 1);
		}

		getPnlTopo().setBackground(CORES.getCor(isNoturno(), 3));
		getPnlEsquerda().setBackground(CORES.getCor(isNoturno(), 3));
		getPnlEsquerda1().setBackground(CORES.getCor(isNoturno(), 3));
		getPnlEsquerda2().setBackground(CORES.getCor(isNoturno(), 3));
		getPnlCentro().setBackground(CORES.getCor(isNoturno(), 3));
		getPnlCentro1().setBackground(CORES.getCor(isNoturno(), 3));
		getPnlCentro2().setBackground(CORES.getCor(isNoturno(), 3));
		getPnlCentro3().setBackground(CORES.getCor(isNoturno(), 3));
		getPnlCentro4().setBackground(CORES.getCor(isNoturno(), 3));
		getPnlRodape().setBackground(CORES.getCor(isNoturno(), 3));
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
}
