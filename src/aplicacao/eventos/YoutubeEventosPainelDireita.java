package aplicacao.eventos;

import java.awt.event.ActionEvent;

/**
 * Classe responsável pelos eventos dos botões do painel esquerdo (modo noturno,
 * áudio e vídeo).
 */
public class YoutubeEventosPainelDireita extends YoutubeEventosAtributos {

    @Override
    protected void btnModoNoiteClick(ActionEvent ev) {
        if (!btnModoNoite.isSelected()) {
            noturno = false;
            configurarCores();
            btnModoNoite.setText(TEXTOS.pegarTexto("botao.modo.noite"));
            btnModoNoite.setIcon(IMAGEM.pegarIcon("/imagens/night.png"));
            btnModoNoite.setToolTipText(TEXTOS.pegarTexto("tooltip.botao.noturno"));
        } else {
            noturno = true;
            configurarCores();
            btnModoNoite.setText(TEXTOS.pegarTexto("botao.modo.dia"));
            btnModoNoite.setIcon(IMAGEM.pegarIcon("/imagens/day.png"));
            btnModoNoite.setToolTipText(TEXTOS.pegarTexto("tooltip.botao.diurno"));
        }
    }

    @Override
    protected void checkVideoClick(ActionEvent ev) {
        if (checkVideo.isSelected()) {
            checkAudio.setSelected(false);
            video = true;
            audio = false;
        } else {
            video = false;
        }
    }

    @Override
    protected void checkAudioClick(ActionEvent ev) {
        if (checkAudio.isSelected()) {
            checkVideo.setSelected(false);
            audio = true;
            video = false;
        } else {
            audio = false;
        }
    }
}
