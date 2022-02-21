package app;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import javax.swing.ToolTipManager;
import javax.swing.event.AncestorEvent;
import metodos.YoutubeArquivo;

public class Youtube extends YoutubeForm {
	private String format, cmdLineSaida;
	private String[] links;
	private boolean modificaBgLabelResultado = true;
	private int index = 20;
			
	@Override
	protected void btnModoNoiteClick(ActionEvent ev) {
		if (!btnModoNoite.isSelected()) {
			setNoturno(false);
			setCores(modificaBgLabelResultado);
		} else {
			setNoturno(true);
			setCores(modificaBgLabelResultado);
		}
	}

	@Override
	protected void checkVideoClick(ActionEvent ev) {
		if (checkVideo.isSelected()) {
			checkAudio.setSelected(false);
			setVideo(true);
			setAudio(false);
		} else
			setVideo(false);
	}

	@Override
	protected void checkAudioClick(ActionEvent ev) {
		if (checkAudio.isSelected()) {
			checkVideo.setSelected(false);
			setAudio(true);
			setVideo(false);
		} else
			setAudio(false);
	}

	@Override
	protected void btnBaixarClick(ActionEvent ev) {
		Thread download = new Thread(() -> {
			cmdLineSaida = ""; 
			String link = txtLink.getText();

			lblResultado.setText(TEXTOS.getTextos(31));
			if ((isVideo() || isAudio()) && !lstPesquisa.isSelectionEmpty()) {
				txtLink.setText(TEXTOS.getTextos(7));
				index = lstPesquisa.getSelectedIndex();
				link = links[index];
			} else if (!isVideo() && !isAudio()) {
				lblResultado.setBackground(CORES.getCor(isNoturno(), 8));
				lblResultado.setText(TEXTOS.getTextos(20));
				modificaBgLabelResultado = false;
				Thread.currentThread().interrupt();
			} else if (!lstPesquisa.isSelectedIndex(index) && !link.startsWith("https://www.youtube.com/watch?v=")) {
				lblResultado.setBackground(CORES.getCor(isNoturno(), 8));
				lblResultado.setText(TEXTOS.getTextos(19));
				modificaBgLabelResultado = false;
				Thread.currentThread().interrupt();
			}
			
			if (link.startsWith("https://www.youtube.com/watch?v=")) {
				modificaBgLabelResultado = true;
				setCores(modificaBgLabelResultado);
				System.out.println("link: "+link);

				setCores(modificaBgLabelResultado);
				lblResultado.setText(TEXTOS.getTextos(18));

				format = (isVideo()) ? String.format("{'format': 'bestvideo[ext=mp4]+bestaudio[ext=m4a]/bestvideo+bestaudio',\n" 
				+ "'outtmpl': '%s' + title + '.mp4'}", pastaPrincipal)                        // format video para o YoutubeDL
				:
				String.format("{'format': 'bestaudio[ext=m4a]',\n" 
				+ "'outtmpl': '%s' + title + '.mp3',\n"
				+ "'postprocessors': [{\n" 
				+ "    'key': 'FFmpegExtractAudio',\n"                    // format audio para o YoutubeDL
				+ "    'preferredcodec': 'mp3',\n" 
				+ "    'preferredquality': '0'\n" 
				+ "    }]\n" 
				+ "}", pastaPrincipal);                                                     
				YoutubeArquivo scriptBaixar = new YoutubeArquivo("/tmp/baixar");
				scriptBaixar.criar(String.format(
						"#!/usr/bin/python3\n" 
				+ "import youtube_dl\n\n"
				+ "" 
				+ "link = '%s'\n" 
				+ "v = youtube_dl.YoutubeDL()\n" 
				+ "i = v.extract_info(link, download=False)\n"
				+ "title = i.get('title')\n"
				+ "format = %s\n" 
				+ "options = format\n\n" 
				+ ""				
				+ "with youtube_dl.YoutubeDL(options) as ydl:\n" 
				+ "    ydl.download([link])\n",
				link, format));

				cmdLineSaida = cmd.comando("python3 /tmp/baixar");
			}

			if (cmdLineSaida.contains("[download] 100%")) {
				lblResultado.setBackground(CORES.getCor(isNoturno(), 9));
				lblResultado.setText(TEXTOS.getTextos(22));
				modificaBgLabelResultado = false;
			} else if (!cmdLineSaida.equals("command not found")) {
				lblResultado.setBackground(CORES.getCor(isNoturno(), 7));
				lblResultado.setText(TEXTOS.getTextos(21));
				modificaBgLabelResultado = false;
			}
		});
		download.start();
	}

	@Override
	protected void btnPesquisaClick(ActionEvent ev) {
		Thread pesquisa = new Thread(() -> {
			if (!lstTitulos.isEmpty()) lstTitulos.removeAllElements();
			if (!lstDescricao.isEmpty()) lstDescricao.clear();
			YoutubeArquivo arquivoDesc = new YoutubeArquivo("/tmp/descricoes.txt");

			links = new String[20];
			String[] lstTitulosLinksTmp = null;

			modificaBgLabelResultado = true;
			setCores(modificaBgLabelResultado);
			lblResultado.setText(TEXTOS.getTextos(16));
			
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
				
				cmdLineSaida = cmd.comando("python3 /tmp/youtubeSearch");

				for (String line : arquivoDesc.listar()) {
					if (!line.equals(null)) lstDescricao.add(line);
				}
				
				if (!cmdLineSaida.startsWith("Traceback (most recent call last):") || !cmdLineSaida.startsWith("command not found") && cmdLineSaida.length() > 0) {
					lstTitulosLinksTmp = cmdLineSaida.split("\n");
					contador = 0;
					if (!lstTitulosLinksTmp[0].equals("command not found")) {
						for (int i = 0; i < lstTitulosLinksTmp.length; i++) {
							if (i % 2 == 0) lstTitulos.addElement(lstTitulosLinksTmp[i]);
							else if (i % 2 == 1) {
								links[contador] = lstTitulosLinksTmp[i];
								contador++;
							}
							cmd.sleep(0.1);
							
						}	
					}
				}


			} catch (Exception e) {
				lblResultado.setBackground(CORES.getCor(isNoturno(), 7));
				lblResultado.setText(TEXTOS.getTextos(17));
				modificaBgLabelResultado = false;
			}
			if (!lstTitulos.isEmpty()) {
				lblResultado.setText(TEXTOS.getTextos(25));
				lblResultado.setBackground(CORES.getCor(isNoturno(), 9));
				modificaBgLabelResultado = false;
			} else {
				lblResultado.setText(TEXTOS.getTextos(17));
				lblResultado.setBackground(CORES.getCor(isNoturno(), 7));
				modificaBgLabelResultado = false;
			}
		});
		pesquisa.start();
	}
	
	@Override
	protected void txtLinkMouseClick(MouseEvent ev) {
		txtLink.selectAll();
	}

	@Override
	protected void txtPesquisaMouseClick(MouseEvent ev) {
		txtPesquisa.selectAll();
	}

	@Override
	protected void lstPesquisaAncestor(AncestorEvent ev) {
		if (!lstPesquisa.isSelectionEmpty()) {
			index = lstPesquisa.getSelectedIndex();
			lstPesquisa.setToolTipText("<html>"+lstDescricao.get(index)+"</html>");
		}
	}
	
	@Override
	protected void lstPesquisaMouseClickItem(MouseEvent ev) {
		if (!lstPesquisa.isSelectionEmpty() && !lstPesquisa.isSelectedIndex(index)) {
			index = lstPesquisa.getSelectedIndex();
			lstPesquisa.setToolTipText("<html>"+lstDescricao.get(index)+"</html>");
			ToolTipManager.sharedInstance().mouseMoved(ev);
			ToolTipManager.sharedInstance().setDismissDelay(6000);
		} else {
			lstPesquisa.clearSelection();	
			index = 20;
		}
	}
}
