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
public class YoutubeEventosPesquisa extends YoutubeEventosBaixar3 {

    @Override
    protected void btnPesquisaClick(ActionEvent ev) {
        Thread pesquisa = new Thread(() -> {
            try {
                index = 20;
                if (!lstTitulos.isEmpty()) {
                    lstTitulos.removeAllElements();
                }
                if (!lstDescricao.isEmpty()) {
                    lstDescricao.clear();
                }
                YoutubeArquivo arquivoDesc = new YoutubeArquivo("/tmp/descricoes.txt");

                links = new String[20];
                String[] lstTitulosLinksTmp = null;

                lblResultado.setText(TEXTOS.pegarTexto("label.resultado.pesquisando"));
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
                            txtPesquisa.getText(), links.length));

                    cmdLineSaida = CMD.comando("python3 /tmp/youtubeSearch");

                    for (String line : arquivoDesc.listar()) {
                        if (!line.equals(null)) {
                            lstDescricao.add(line);
                        }
                    }

                    if (!cmdLineSaida.startsWith("Traceback (most recent call last):") || !cmdLineSaida.startsWith("command not found") && cmdLineSaida.length() > 0) {
                        lstTitulosLinksTmp = cmdLineSaida.split("\n");
                        contador = 0;
                        if (!lstTitulosLinksTmp[0].equals("command not found")) {
                            for (int i = 0; i < lstTitulosLinksTmp.length; i++) {
                                if (i % 2 == 0) {
                                    lstTitulos.addElement(lstTitulosLinksTmp[i]);
                                } else if (i % 2 == 1) {
                                    links[contador] = lstTitulosLinksTmp[i];
                                    contador = contador + 1;
                                }
                                CMD.sleep(0.2);

                            }
                        }
                    }

                } catch (Exception e) {
                    lblResultado.setForeground(CORES.pegarCor(noturno, 7));
                    lblResultado.setText(TEXTOS.pegarTexto("label.resultado.falha.pesquisa"));
                    return;
                }

                if (!lstTitulos.isEmpty() && (!lblResultado.getText().startsWith("[download")
                        && !lblResultado.getText().equals(TEXTOS.pegarTexto("label.resultado.verificando.download")))) {
                    lblResultado.setText(TEXTOS.pegarTexto("label.resultado.pesquisa.concluida"));
                    lblResultado.setForeground(CORES.pegarCor(noturno, 9));
                } else if (!lblResultado.getText().startsWith("[download")
                        && !lblResultado.getText().equals(TEXTOS.pegarTexto("label.resultado.verificando.download"))) {
                    lblResultado.setText(TEXTOS.pegarTexto("label.resultado.falha.pesquisa"));
                    lblResultado.setForeground(CORES.pegarCor(noturno, 7));
                }
            } catch (IOException ex) {
                Logger.getLogger(YoutubeEventosPesquisa.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        pesquisa.start();
    }

}
