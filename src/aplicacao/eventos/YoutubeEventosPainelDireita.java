package aplicacao.eventos;

import java.awt.event.ActionEvent;

/**
 * Classe responsável pelos eventos dos botões do painel esquerdo (modo noturno,
 * áudio e vídeo).
 */
public class YoutubeEventosPainelDireita extends YoutubeEventosAtributos {

    @Override
    protected void btnModoNoiteClick(ActionEvent ev) {
        if (!getBtnModoNoite().isSelected()) {
            setNoturno(false);
            configurarCores();
            getBtnModoNoite().setText(TEXTOS.pegarTexto("botao.modo.noite"));
            getBtnModoNoite().setIcon(IMAGEM.pegarIcon("/imagens/night.png"));
            getBtnModoNoite().setToolTipText(TEXTOS.pegarTexto("tooltip.botao.noturno"));
        } else {
            setNoturno(true);
            configurarCores();
            getBtnModoNoite().setText(TEXTOS.pegarTexto("botao.modo.dia"));
            getBtnModoNoite().setIcon(IMAGEM.pegarIcon("/imagens/day.png"));
            getBtnModoNoite().setToolTipText(TEXTOS.pegarTexto("tooltip.botao.diurno"));
        }
    }

    @Override
    protected void checkVideoClick(ActionEvent ev) {
        if (getCheckVideo().isSelected()) {
            getCheckAudio().setSelected(false);
            setVideo(true);
            setAudio(false);
        } else {
            setVideo(false);
        }
    }

    @Override
    protected void checkAudioClick(ActionEvent ev) {
        if (getCheckAudio().isSelected()) {
            getCheckVideo().setSelected(false);
            setAudio(true);
            setVideo(false);
        } else {
            setAudio(false);
        }
    }
}
