package aplicacao.eventos;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import javax.swing.JRadioButtonMenuItem;
import metodos.YoutubeArquivo;

/**
 * Classe responsável pelos eventos dos botões do painel direito.
 */
@SuppressWarnings("serial")
public class YoutubeEventosPainelDireita extends YoutubeEventosAtributos {

    private List<String> resIdList() throws IOException {
        YoutubeArquivo res = new YoutubeArquivo("/tmp/resolutions", false);
        List<String> list = res.listar();
        return list;
    }

    protected void setResId(JRadioButtonMenuItem buttonMenuItem) {
        List<String> lstResId;
        try {
            lstResId = resIdList();
            for (int i = 0; i < lstResId.size(); i++) {
                String[] split = lstResId.get(i).split(" ", -1);
                String splitText = (split[1].equals("DASH")) ? split[1] + " " + split[2] + " " + split[3] + " " + split[4]
                        : split[1] + " " + split[2] + " " + split[3];
                if (buttonMenuItem.getText().equals(splitText)) {
                    resId = lstResId.get(i).split(" ")[0];
                    break;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

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
        if (lblResultado.getText().equals(TEXTOS.pegarTexto("label.resultado.resolucao"))) {
            lblResultado.setText(TEXTOS.pegarTexto("label.resultado.resolucaocancelado"));
            lblResultado.setForeground(CORES.pegarCorComBrilho(noturno, "Avisos"));
        }
        CMD.destruir();
        cancelScheduleBtnCancelPro();
    }

    @Override
    protected void checkVideoClick(ActionEvent ev) {
        if (checkVideo.isSelected()) {
            try {
                if (link.equals(links[index])) {
                    menuBarRadioBtn.setVisible(true);
                }
            } catch (NullPointerException e) {
                System.err.println("Error: Null Point Exception");
            }
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
            menuBarRadioBtn.setVisible(false);
            checkVideo.setSelected(false);
            audio = true;
            video = false;
        } else {
            audio = false;
        }
    }

    @Override
    protected void selectRadioMenuItem20(ActionEvent ev) { setResId(radioBtnMenuItem20); }

    @Override
    protected void selectRadioMenuItem19(ActionEvent ev) { setResId(radioBtnMenuItem19); }

    @Override
    protected void selectRadioMenuItem18(ActionEvent ev) { setResId(radioBtnMenuItem18); }

    @Override
    protected void selectRadioMenuItem17(ActionEvent ev) { setResId(radioBtnMenuItem17); }

    @Override
    protected void selectRadioMenuItem16(ActionEvent ev) { setResId(radioBtnMenuItem16); }

    @Override
    protected void selectRadioMenuItem15(ActionEvent ev) { setResId(radioBtnMenuItem15); }

    @Override
    protected void selectRadioMenuItem14(ActionEvent ev) { setResId(radioBtnMenuItem14); }

    @Override
    protected void selectRadioMenuItem13(ActionEvent ev) { setResId(radioBtnMenuItem13); }

    @Override
    protected void selectRadioMenuItem12(ActionEvent ev) { setResId(radioBtnMenuItem12); }

    @Override
    protected void selectRadioMenuItem11(ActionEvent ev) { setResId(radioBtnMenuItem11); }

    @Override
    protected void selectRadioMenuItem10(ActionEvent ev) { setResId(radioBtnMenuItem10); }

    @Override
    protected void selectRadioMenuItem9(ActionEvent ev) { setResId(radioBtnMenuItem9); }

    @Override
    protected void selectRadioMenuItem8(ActionEvent ev) { setResId(radioBtnMenuItem8); }

    @Override
    protected void selectRadioMenuItem7(ActionEvent ev) { setResId(radioBtnMenuItem7); }

    @Override
    protected void selectRadioMenuItem6(ActionEvent ev) { setResId(radioBtnMenuItem6); }

    @Override
    protected void selectRadioMenuItem5(ActionEvent ev) { setResId(radioBtnMenuItem5); }

    @Override
    protected void selectRadioMenuItem4(ActionEvent ev) { setResId(radioBtnMenuItem4); }

    @Override
    protected void selectRadioMenuItem3(ActionEvent ev) { setResId(radioBtnMenuItem3); }

    @Override
    protected void selectRadioMenuItem2(ActionEvent ev) { setResId(radioBtnMenuItem2); }

    @Override
    protected void selectRadioMenuItem1(ActionEvent ev) { 
        resId = TEXTOS.pegarTexto("radio.menu.melhor.resolucao"); 
    }
}
