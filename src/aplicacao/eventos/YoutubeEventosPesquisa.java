package aplicacao.eventos;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import metodos.YoutubeArquivo;

/**
 * Classe responsável pelo evento que configura, executa e retorna os resultados
 * da lista de pesquisa da aplicação, além de tratar possíveis erros.
 */
public class YoutubeEventosPesquisa extends YoutubeEventosBaixar {

    @Override
    protected void btnPesquisaClick(ActionEvent ev) {
        Thread pesquisa = new Thread(() -> {
            try {
                setIndex(20);
                if (!getLstTitulos().isEmpty()) {
                    getLstTitulos().removeAllElements();
                }
                if (!getLstDescricao().isEmpty()) {
                    getLstDescricao().clear();
                }
                YoutubeArquivo arquivoDesc = new YoutubeArquivo("/tmp/descricoes.txt");

                setLinks(new String[20]);
                String[] lstTitulosLinksTmp = null;

                getLblResultado().setText(TEXTOS.pegarTexto("label.resultado.pesquisando"));
                configurarCores();
                CMD.sleep(1);

                try {
                    arquivoDesc.deletar();
                    YoutubeArquivo scriptTitulosLinks = new YoutubeArquivo("/tmp/youtubeSearch");
                    scriptTitulosLinks.criar(String.format("#!/usr/bin/python3\n"
                            + "import os, time\n"
                            + "from youtube_search import YoutubeSearch\n\n"
                            + ""
                            + "texto = '%s'\n"
                            + "arquivo = '/tmp/descricoes.txt'\n"
                            + "results = YoutubeSearch(texto, %d)\n\n"
                            + ""
                            + "for i in results.videos:\n"
                            + "  for k, v in i.items():\n"
                            + "    if k == 'title':\n"
                            + "      print(v)\n"
                            + "      print('https://www.youtube.com'+i['url_suffix'])\n"
                            + "      with open(arquivo, 'a' if os.path.isfile(arquivo) else 'w') as arqDesc:\n"
                            + "        arqDesc.write(f'Channel: {i[\"channel\"]}, Duration: {i[\"duration\"]}, Views: {i[\"views\"]}, Publish_time: {i[\"publish_time\"]}\\n')\n",
                            getTxtPesquisa().getText(), getLinks().length));

                    setCmdLineSaida(CMD.comando("python3 /tmp/youtubeSearch"));

                    for (String line : arquivoDesc.listar()) {
                        if (!line.equals(null)) {
                            getLstDescricao().add(line);
                        }
                    }

                    if (!getCmdLineSaida().startsWith("Traceback (most recent call last):") || !getCmdLineSaida().startsWith("command not found") && getCmdLineSaida().length() > 0) {
                        lstTitulosLinksTmp = getCmdLineSaida().split("\n");
                        setContador(0);
                        if (!lstTitulosLinksTmp[0].equals("command not found")) {
                            for (int i = 0; i < lstTitulosLinksTmp.length; i++) {
                                if (i % 2 == 0) {
                                    getLstTitulos().addElement(lstTitulosLinksTmp[i]);
                                } else if (i % 2 == 1) {
                                    getLinks()[getContador()] = lstTitulosLinksTmp[i];
                                    setContador(getContador() + 1);
                                }
                                CMD.sleep(0.1);

                            }
                        }
                    }

                } catch (Exception e) {
                    getLblResultado().setForeground(CORES.pegarCor(isNoturno(), 7));
                    getLblResultado().setText(TEXTOS.pegarTexto("label.resultado.falha.pesquisa"));
                    return;
                }

                if (!getLstTitulos().isEmpty() && (!getLblResultado().getText().startsWith("[download")
                        && !getLblResultado().getText().equals(TEXTOS.pegarTexto("label.resultado.verificando.download")))) {
                    getLblResultado().setText(TEXTOS.pegarTexto("label.resultado.pesquisa.concluida"));
                    getLblResultado().setForeground(CORES.pegarCor(isNoturno(), 9));
                } else if (!getLblResultado().getText().startsWith("[download")
                        && !getLblResultado().getText().equals(TEXTOS.pegarTexto("label.resultado.verificando.download"))) {
                    getLblResultado().setText(TEXTOS.pegarTexto("label.resultado.falha.pesquisa"));
                    getLblResultado().setForeground(CORES.pegarCor(isNoturno(), 7));
                }
            } catch (IOException ex) {
                Logger.getLogger(YoutubeEventosPesquisa.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        pesquisa.start();
    }

}
