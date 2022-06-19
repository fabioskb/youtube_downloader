package aplicacao.tela;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

/**
 * Classe abstrata responsável por criar o método eventos() da aplicação.
 * Herda Tela.
 */
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

    /**
     * Seta todas as formas possíveis no download do arquivo,
     * tratamentos de erros, avisos, pasta do download e etc.
     * 
     * @param ev - evento
     */
    protected abstract void btnBaixarClick(ActionEvent ev);
    protected abstract void btnBaixar2Click(ActionEvent ev);
    protected abstract void btnBaixar3Click(ActionEvent ev);

    /**
     * Pesquisa o vídeo ou áudio pelo nome e retorna até 20 opções
     * para baixar na lista lstResultado.
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
     * Chama todos os eventos de clique da aplicação
     * em botões, caixas de texto, labels e etc.
     */
    protected void eventos() {
        btnTema.addActionListener(this::btnModoTemaClick);
        checkVideo.addActionListener(this::checkVideoClick);
        checkAudio.addActionListener(this::checkAudioClick);
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
            public void mouseClicked(MouseEvent ev) {
                txtLinkMouseClick(ev);
            }
        });

        txtPesquisa.addMouseListener(new MouseAdapter() {
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
            public void mouseClicked(MouseEvent ev) {
                lstPesquisaMouseClickItem(ev);
            }
        });
    }

}
