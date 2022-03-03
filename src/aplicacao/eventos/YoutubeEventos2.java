package aplicacao.eventos;

import java.awt.event.ActionEvent;

import java.io.BufferedReader;

/**
 * Classe responsável pelos eventos de configurar ao clique os botões 
 * modo noturno, áudio e vídeo.
 * Herda evento 1.
 */
public class YoutubeEventos2 extends YoutubeEventos1 {
    public YoutubeEventos2(String format, String cmdLineSaida, String[] links, Process pro, BufferedReader read, BufferedReader read2) {
        super(format, cmdLineSaida, links, pro, read, read2);
    }

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
