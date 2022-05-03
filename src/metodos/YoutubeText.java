package metodos;

import aplicacao.tela.YoutubeTela;

public class YoutubeText {
    private String idioma;
    
    public YoutubeText(String idioma) {
        this.idioma = idioma;
    }

    /**
     * Retorna o texto, traduzido, de acordo com o idioma do sistema.
     *
     * @param key (String) - KEYS: 
     * DESCRIÇÃO-----------> descricao. 
     * BUTTONS-------------> botao.modo.noite, botao.video, botao.audio,
     * botao.pesquisa, botao.baixar, botao.cancelar. 
     * FIELDTEXT----------> fieldtext.link, fieldtext.pesquisa. 
     * TOOLTIP------------> tooltip.botao.noturno, 
     * tooltip.link, tooltip.pesquisa, tooltip.botao.pesquisa, 
     * tooltip.botao.baixar, tooltip.botao.video, tooltip.botao.audio, 
     * tooltip.lista, tooltip.botao.cancelar, tooltip.label.resultado, 
     * tooltip.menuitem.sair, tooltip.menuitem.sobre, tooltip.menuitem.tutorial.
     * LABEL RESULTADO-----------> label.resultado.pesquisando, 
     * label.resultado.baixando, label.resultado.baixando.pendentes, label.resultado.falha.pesquisa, 
     * label.resultado.falha.download, label.resultado.aviso.item, 
     * label.resultado.aviso.video, label.resultado.download.concluido, 
     * label.resultado.pesquisa.concluida, label.resultado.aviso.ja.baixado, 
     * label.resultado.download.cancelado, label.resultado.verificando.download.
     * JPTIONPANE----------> joptionpane.boas.vndas, 
     * joptionpane.erro.dependencias, joptionpane.instalacao.concluida, 
     * joptionpane.pip.nao.instalado, joptionpane.tutorial, joptionpane.sobre, 
     * joptionpane.sobre.titulo, joptionpane.sair.pergunta, 
     * joptionpane.botao.oksair, joptionpane.botao.cancelar. 
     * PROGRESSBAR----------> progressbar.baixando.audio. 
     * MENU ITEM-----------> menuitem.sobre, menuitem.sair, menuitem.tutorial.
     * @return texto
     */
    public String pegarTexto(String key) {
        if (idioma.contains("português")) {
            return YoutubeTela.TEXTOS_BR.getString(key);
        } else {
            return YoutubeTela.TEXTOS_US.getString(key);
        }
    }
}
