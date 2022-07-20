package aplicacao.eventos;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import metodos.YoutubeArquivo;

/**
 * Classe responsável pelo evento que configura, executa e retorna os resultados
 * da lista de pesquisa da aplicação.
 */
@SuppressWarnings("serial")
public class YoutubeEventosPesquisa extends YoutubeEventosBaixar {

    @Override
    protected void btnPesquisaClick(ActionEvent ev) {

        if (!CMD.getPro().isAlive()) {
            disableRadioButtons();
            menuBarRadioBtn.setVisible(false);
            lblResultado
                    .setText(TEXTOS.pegarTexto("label.resultado.pesquisando"));
            configurarCores();

            schedulePesquisa = EXECUTOR.schedule(() -> {
                try {
                    index = 20;
                    if (!lstTitulos.isEmpty()) {
                        lstTitulos.removeAllElements();
                    }
                    if (!lstDescricao.isEmpty()) {
                        lstDescricao.clear();
                    }
                    YoutubeArquivo arquivoDesc = new YoutubeArquivo(
                            "/tmp/descricoes.txt", false);

                    links = new String[20];
                    List<String> lstTitulosLinksTmp = null;

                    // CMD.sleep(1);
                    try {
                        arquivoDesc.deletar();
                        YoutubeArquivo scriptTitulosLinks = new YoutubeArquivo(
                                "/tmp/youtubeSearch", false);
                        scriptTitulosLinks.criar(String.format(
                                "#!/usr/bin/python3\n" + "import os, time\n"
                                + "from youtube_search import YoutubeSearch\n\n"
                                + "" + "texto = '%s'\n"
                                + "texto = '_'.join(texto)\n"
                                + "arquivo = '/tmp/descricoes.txt'\n"
                                + "results = YoutubeSearch(texto, %d)\n\n"
                                + "" + "cont = 1\n"
                                + "for i in results.videos:\n"
                                + "  for k, v in i.items():\n"
                                + "    if k == 'title':\n"
                                + "      print(f'{cont}) {v}')\n"
                                + "      print('https://www.youtube.com'+i['url_suffix'])\n"
                                + "      with open(arquivo, 'a' if os.path.isfile(arquivo) else 'w') as arqDesc:\n"
                                + "        arqDesc.write(f'Channel: {i[\"channel\"]}, Duration: {i[\"duration\"]}, Views: {i[\"views\"]}, Publish_time: {i[\"publish_time\"]}\\n')\n"
                                + "      cont += 1\n",
                                txtPesquisa.getText(), links.length));

                        cmdLineSaida = CMD
                                .comando("python3 /tmp/youtubeSearch");
                        cancelScheduleBtnCancelPro();

                        for (String line : arquivoDesc.listar()) {
                            if (!line.equals(null)) {
                                lstDescricao.add(line);
                            }
                        }

                        if (!cmdLineSaida.startsWith(
                                "Traceback (most recent call last):")
                                || !cmdLineSaida.startsWith("command not found")
                                && cmdLineSaida.length() > 0) {
                            lstTitulosLinksTmp = new ArrayList<>(
                                    Arrays.asList(cmdLineSaida.split("\n")));
                            contador = 0;

                            if (!lstTitulosLinksTmp.get(0)
                                    .equals("command not found")) {
                                for (int i = 0; i < lstTitulosLinksTmp
                                        .size(); i++) {
                                    if (i % 2 == 0) {
                                        lstTitulos.addElement(
                                                lstTitulosLinksTmp.get(i));
                                    } else if (i % 2 == 1) {
                                        links[contador] = lstTitulosLinksTmp
                                                .get(i);
                                        contador++;
                                    }
                                    CMD.sleep(0.1);
                                }
                            }
                        }

                    } catch (Exception e) {
                        System.out.println(
                                "YoutubeEventosPesquisa.btnPesquisaClick() - Processo pesquisa cancelado!");
                    }

                    if (!lstTitulos.isEmpty()) {
                        lblResultado.setText(TEXTOS.pegarTexto(
                                "label.resultado.pesquisa.concluida"));
                        lblResultado.setForeground(
                                CORES.pegarCorComBrilho(noturno, "Concluido"));
                    } else {
                        lblResultado.setText(TEXTOS
                                .pegarTexto("label.resultado.falha.pesquisa"));
                        lblResultado.setForeground(
                                CORES.pegarCorComBrilho(noturno, "Erros"));
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }, 1, TimeUnit.MILLISECONDS);
            // pesquisa.start();
        } else {
            System.err.println("Processo já em andamento.");
        }
    }
}
