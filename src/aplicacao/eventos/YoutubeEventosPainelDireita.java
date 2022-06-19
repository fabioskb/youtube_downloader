package aplicacao.eventos;

import java.awt.event.ActionEvent;

/**
 * Classe responsável pelos eventos dos botões do painel direito.
 */
public class YoutubeEventosPainelDireita extends YoutubeEventosAtributos {

    @Override
    protected void btnModoTemaClick(ActionEvent ev) {
        if (!btnTema.isSelected()) {
            noturno = false;
            btnTema.setIcon(IMAGEM.pegarIcon("/imagens/night.png"));
            btnTema.setText(TEXTOS.pegarTexto("botao.modo.noite"));
            btnTema.setToolTipText(TEXTOS.pegarTexto("tooltip.botao.noturno"));
        } else {
            noturno = true;
            btnTema.setIcon(IMAGEM.pegarIcon("/imagens/day.png"));
            btnTema.setText(TEXTOS.pegarTexto("botao.modo.dia"));
            btnTema.setToolTipText(TEXTOS.pegarTexto("tooltip.botao.diurno"));
            setBtnTemaCor();
        }
        configurarCores();
    }
    
    @Override
    protected void btnCancelProcessClick(ActionEvent ev) {
        CMD.destruir();
        colorBtnCancelPro.stop();
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
