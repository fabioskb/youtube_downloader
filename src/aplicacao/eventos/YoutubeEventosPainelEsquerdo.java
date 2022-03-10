package aplicacao.eventos;

import java.awt.event.ActionEvent;

/**
 * Classe responsável pelos eventos dos botões do painel esquerdo (modo noturno, áudio e vídeo).
 */
public class YoutubeEventosPainelEsquerdo extends YoutubeEventosAtributos {
    

    @Override
	protected void btnModoNoiteClick(ActionEvent ev) {
		if (!getBtnModoNoite().isSelected()) {
			setNoturno(false);
			configurarCores(this.isModificaBgLabelResultado());
		} else {
			setNoturno(true);
			configurarCores(this.isModificaBgLabelResultado());
		}
	}

	@Override
	protected void checkVideoClick(ActionEvent ev) {
		if (getCheckVideo().isSelected()) {
			getCheckAudio().setSelected(false);
			setVideo(true);
			setAudio(false);
		} else
			setVideo(false);
	}

	@Override
	protected void checkAudioClick(ActionEvent ev) {
		if (getCheckAudio().isSelected()) {
			getCheckVideo().setSelected(false);
			setAudio(true);
			setVideo(false);
		} else
			setAudio(false);
	}
}
