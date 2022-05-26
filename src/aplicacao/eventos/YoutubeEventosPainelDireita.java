package aplicacao.eventos;

import java.awt.event.ActionEvent;

/**
 * Classe responsável pelos eventos dos botões do painel esquerdo (modo noturno,
 * áudio e vídeo).
 */
public class YoutubeEventosPainelDireita extends YoutubeEventosAtributos {

    @Override
    protected void btnModoNoiteClick(ActionEvent ev) {
        if (!btnTema.isSelected()) {
            noturno = false;
            configurarCores();
            btnTema.setText(TEXTOS.pegarTexto("botao.modo.noite"));
            btnTema.setIcon(IMAGEM.pegarIcon("/imagens/night.png"));
            btnTema.setToolTipText(TEXTOS.pegarTexto("tooltip.botao.noturno"));
        } else {
            noturno = true;
            configurarCores();
            btnTema.setText(TEXTOS.pegarTexto("botao.modo.dia"));
            btnTema.setIcon(IMAGEM.pegarIcon("/imagens/day.png"));
            btnTema.setToolTipText(TEXTOS.pegarTexto("tooltip.botao.diurno"));
        }
    }
    
    @Override
    protected void btnCancelProcessClick(ActionEvent ev) {
        CMD.destruir();
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
