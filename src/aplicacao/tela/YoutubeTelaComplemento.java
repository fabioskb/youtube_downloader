package aplicacao.tela;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

/**
 * Classe abstrata responsável por criar o método eventos() da aplicação. Herda
 * Tela.
 */
@SuppressWarnings("serial")
public abstract class YoutubeTelaComplemento extends YoutubeTela {

    /**
     * Ativa o modo noturno
     *
     * @param ev - evento
     */
    protected abstract void btnModoTemaClick(ActionEvent ev);

    /**
     * Marca vídeo e desmarca audio
     *
     * @param ev - evento
     */
    protected abstract void checkVideoClick(ActionEvent ev);

    /**
     * Marca áudio e desmarca vídeo
     *
     * @param ev - evento
     */
    protected abstract void checkAudioClick(ActionEvent ev);
    
    protected abstract void selectRadioMenuItem1(ActionEvent ev);
    
    protected abstract void selectRadioMenuItem2(ActionEvent ev);
    protected abstract void selectRadioMenuItem3(ActionEvent ev);
    protected abstract void selectRadioMenuItem4(ActionEvent ev);
    protected abstract void selectRadioMenuItem5(ActionEvent ev);
    protected abstract void selectRadioMenuItem6(ActionEvent ev);
    protected abstract void selectRadioMenuItem7(ActionEvent ev);
    protected abstract void selectRadioMenuItem8(ActionEvent ev);
    protected abstract void selectRadioMenuItem9(ActionEvent ev);
    protected abstract void selectRadioMenuItem10(ActionEvent ev);
    protected abstract void selectRadioMenuItem11(ActionEvent ev);
    protected abstract void selectRadioMenuItem12(ActionEvent ev);
    protected abstract void selectRadioMenuItem13(ActionEvent ev);
    protected abstract void selectRadioMenuItem14(ActionEvent ev);
    protected abstract void selectRadioMenuItem15(ActionEvent ev);
    protected abstract void selectRadioMenuItem16(ActionEvent ev);
    protected abstract void selectRadioMenuItem17(ActionEvent ev);
    protected abstract void selectRadioMenuItem18(ActionEvent ev);
    protected abstract void selectRadioMenuItem19(ActionEvent ev);
    protected abstract void selectRadioMenuItem20(ActionEvent ev);

    /**
     * Seta todas as formas possíveis no download do arquivo, tratamentos de
     * erros, avisos, pasta do download e etc.
     *
     * @param ev - evento
     */
    protected abstract void btnBaixarClick(ActionEvent ev);

    protected abstract void btnBaixar2Click(ActionEvent ev);

    protected abstract void btnBaixar3Click(ActionEvent ev);

    /**
     * Pesquisa o vídeo ou áudio pelo nome e retorna até 20 opções para baixar
     * na lista lstResultado.
     *
     * @param ev - evento
     */
    protected abstract void btnPesquisaClick(ActionEvent ev);

    protected abstract void btnCancelarClick(ActionEvent ev);

    protected abstract void btnCancelProcessClick(ActionEvent ev);

    protected abstract void itemMenuExitClick(ActionEvent ev);

    protected abstract void itemMenuTutorialClick(ActionEvent ev);

    protected abstract void itemMenuSobreClick(ActionEvent ev);

    /**
     * Ao clicar na caixa de texto do link seleciona o texto
     *
     * @param ev - evento
     */
    protected abstract void txtLinkMouseClick(MouseEvent ev);

    /**
     * Ao clicar na caixa de texto da pesquisa com o mouse seleciona o texto.
     *
     * @param ev - evento
     *
     */
    protected abstract void txtPesquisaMouseClick(MouseEvent ev);

    /**
     * Muda o Tooltip da lstPesquisa quando ocorre um AncestorEvent
     *
     * @param ev
     */
    protected abstract void lstPesquisaAncestor(AncestorEvent ev);

    /**
     * Muda o Tooltip da lstPesquisa de acordo com a seleção do item na lista
     * atraves do mouseClick.
     *
     * @param ev
     */
    protected abstract void lstPesquisaMouseClickItem(MouseEvent ev);

    /**
     * Chama todos os eventos de clique da aplicação em botões, caixas de texto,
     * labels e etc.
     */
    protected void eventos() {
        btnTema.addActionListener(this::btnModoTemaClick);
        checkVideo.addActionListener(this::checkVideoClick);
        checkAudio.addActionListener(this::checkAudioClick);
        radioBtnMenuItem1.addActionListener(this::selectRadioMenuItem1);
        radioBtnMenuItem2.addActionListener(this::selectRadioMenuItem2);
        radioBtnMenuItem3.addActionListener(this::selectRadioMenuItem3);
        radioBtnMenuItem4.addActionListener(this::selectRadioMenuItem4);
        radioBtnMenuItem5.addActionListener(this::selectRadioMenuItem5);
        radioBtnMenuItem5.addActionListener(this::selectRadioMenuItem6);
        radioBtnMenuItem5.addActionListener(this::selectRadioMenuItem7);
        radioBtnMenuItem5.addActionListener(this::selectRadioMenuItem8);
        radioBtnMenuItem5.addActionListener(this::selectRadioMenuItem9);
        radioBtnMenuItem5.addActionListener(this::selectRadioMenuItem10);
        radioBtnMenuItem5.addActionListener(this::selectRadioMenuItem11);
        radioBtnMenuItem5.addActionListener(this::selectRadioMenuItem12);
        radioBtnMenuItem5.addActionListener(this::selectRadioMenuItem13);
        radioBtnMenuItem5.addActionListener(this::selectRadioMenuItem14);
        radioBtnMenuItem5.addActionListener(this::selectRadioMenuItem15);
        radioBtnMenuItem5.addActionListener(this::selectRadioMenuItem16);
        radioBtnMenuItem5.addActionListener(this::selectRadioMenuItem17);
        radioBtnMenuItem5.addActionListener(this::selectRadioMenuItem18);
        radioBtnMenuItem5.addActionListener(this::selectRadioMenuItem19);
        radioBtnMenuItem5.addActionListener(this::selectRadioMenuItem20);
        btnBaixa.addActionListener(this::btnBaixarClick);
        btnBaixa2.addActionListener(this::btnBaixar2Click);
        btnBaixa3.addActionListener(this::btnBaixar3Click);
        btnPesquisa.addActionListener(this::btnPesquisaClick);
        btnCancelar.addActionListener(this::btnCancelarClick);
        btnCancelProcess.addActionListener(this::btnCancelProcessClick);
        itemMenuExit.addActionListener(this::itemMenuExitClick);
        itemMenuTutorial.addActionListener(this::itemMenuTutorialClick);
        itemMenuSobre.addActionListener(this::itemMenuSobreClick);

        txtLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ev) {
                txtLinkMouseClick(ev);
            }
        });

        txtPesquisa.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ev) {
                txtPesquisaMouseClick(ev);
            }
        });

        lstPesquisa.addAncestorListener(new AncestorListener() {

            @Override
            public void ancestorAdded(AncestorEvent event) {
                lstPesquisaAncestor(event);
            }

            @Override
            public void ancestorMoved(AncestorEvent event) {
                lstPesquisaAncestor(event);
            }

            @Override
            public void ancestorRemoved(AncestorEvent event) {
                lstPesquisaAncestor(event);
            }

        });

        lstPesquisa.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ev) {
                lstPesquisaMouseClickItem(ev);
            }
        });
    }

}
