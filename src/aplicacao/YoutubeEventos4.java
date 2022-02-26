package aplicacao;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;

import metodos.YoutubeArquivo;

/**
 * Classe responsável pelo evento que configura, executa e retorna os resultados 
 * da lista de pesquisa da aplicação, além de tratar possíveis erros.
 */
public class YoutubeEventos4 extends YoutubeEventos3 {

    public YoutubeEventos4(String format, String cmdLineSaida, String[] links, Process pro, BufferedReader read, BufferedReader read2) {
        super(format, cmdLineSaida, links, pro, read, read2);
    }

	@Override
	protected void btnPesquisaClick(ActionEvent ev) {
		Thread pesquisa = new Thread(() -> {
			if (!getLstTitulos().isEmpty()) getLstTitulos().removeAllElements();
			if (!getLstDescricao().isEmpty()) getLstDescricao().clear();
			YoutubeArquivo arquivoDesc = new YoutubeArquivo("/tmp/descricoes.txt");

			setLinks(new String[20]);
			String[] lstTitulosLinksTmp = null;

			setModificaBgLabelResultado(true);
			configurarCores(isModificaBgLabelResultado());
			getLblResultado().setText(getTEXTOS().getTextos(16));
			
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
				
				setCmdLineSaida(getCmd().comando("python3 /tmp/youtubeSearch"));

				for (String line : arquivoDesc.listar()) {
					if (!line.equals(null)) getLstDescricao().add(line);
				}
				
				if (!getCmdLineSaida().startsWith("Traceback (most recent call last):") || !getCmdLineSaida().startsWith("command not found") && getCmdLineSaida().length() > 0) {
					lstTitulosLinksTmp = getCmdLineSaida().split("\n");
					setContador(0);
					if (!lstTitulosLinksTmp[0].equals("command not found")) {
						for (int i = 0; i < lstTitulosLinksTmp.length; i++) {
							if (i % 2 == 0) getLstTitulos().addElement(lstTitulosLinksTmp[i]);
							else if (i % 2 == 1) {
								getLinks()[getContador()] = lstTitulosLinksTmp[i];
								setContador(getContador() + 1);
							}
							getCmd().sleep(0.1);
							
						}	
					}
				}


			} catch (Exception e) { 
				getLblResultado().setBackground(getCORES().getCor(isNoturno(), 7));
				getLblResultado().setText(getTEXTOS().getTextos(17));
				setModificaBgLabelResultado(false);
			}
			if (!getLstTitulos().isEmpty() && !getLblResultado().getText().startsWith("[downloading audio from video]")) {
				getLblResultado().setText(getTEXTOS().getTextos(25));
				getLblResultado().setBackground(getCORES().getCor(isNoturno(), 9));
				setModificaBgLabelResultado(false);
			} else {
				getLblResultado().setText(getTEXTOS().getTextos(17));
				getLblResultado().setBackground(getCORES().getCor(isNoturno(), 7));
				setModificaBgLabelResultado(false);
			}
		});
		pesquisa.start();
	}
    
}
