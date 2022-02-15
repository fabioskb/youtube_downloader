package app;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

import metodos.YoutubeArquivo;

public class Youtube extends YoutubeForm {
	private String format, cmdLineSaida;
	private String[] links;
	private boolean modificaBgLabelResultado = true;
	

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
			int index = 0;

			lblResultado.setText(TEXTOS.getTextos(31));
			if ((isVideo() || isAudio()) && !lstPesquisa.isSelectionEmpty()) {
				txtLink.setText(" Link");
				index = lstPesquisa.getSelectedIndex();
				link = links[index];
			} else if (!isVideo() && !isAudio()) {
				lblResultado.setBackground(CORES.getCor(isNoturno(), 8));
				lblResultado.setText(TEXTOS.getTextos(20));
				modificaBgLabelResultado = false;
				Thread.currentThread().stop();
			} else if (!lstPesquisa.isSelectedIndex(index) && !link.startsWith("https://www.youtube.com/watch?v=")) {
				lblResultado.setBackground(CORES.getCor(isNoturno(), 8));
				lblResultado.setText(TEXTOS.getTextos(19));
				modificaBgLabelResultado = false;
				Thread.currentThread().stop();
			}
			
			if (link.startsWith("https://www.youtube.com/watch?v=")) {
				modificaBgLabelResultado = true;
				setCores(modificaBgLabelResultado);
				System.out.println("link: "+link);

				lblResultado.setText(TEXTOS.getTextos(31));
				modificaBgLabelResultado = true;
				setCores(modificaBgLabelResultado);
				lblResultado.setText(TEXTOS.getTextos(18));

				format = (isVideo()) ? String.format("{'format': 'bestvideo[ext=mp4]',\n" 
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
				+ "    ydl.download([link])",
				link, format));

				try {
					cmdLineSaida = cmd.comando("python3 /tmp/baixar");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (cmdLineSaida.contains("[download] 100%")) {
				lblResultado.setBackground(CORES.getCor(isNoturno(), 9));
				lblResultado.setText(TEXTOS.getTextos(22));
				modificaBgLabelResultado = false;
				Thread.currentThread().stop();
			} else {
				lblResultado.setBackground(CORES.getCor(isNoturno(), 7));
				lblResultado.setText(TEXTOS.getTextos(21));
				modificaBgLabelResultado = false;
				Thread.currentThread().stop();
			}
		});
		download.start();
	}

	@Override
	protected void btnPesquisaClick(ActionEvent ev) {
		Thread pesquisa = new Thread(() -> {
			if (!lstTitulos.isEmpty()) lstTitulos.removeAllElements();

			links = new String[20];
			String[] lstTitulosTmp = null;

			modificaBgLabelResultado = true;
			setCores(modificaBgLabelResultado);
			lblResultado.setText(TEXTOS.getTextos(16));
			
			try {
				YoutubeArquivo scriptTitulosLinks = new YoutubeArquivo("/tmp/youtubeSearch");
				scriptTitulosLinks.criar(String.format("#!/usr/bin/python3\n"
						+ "from youtube_search import YoutubeSearch\n\n"
						+ ""
						+ "texto = '%s'\n"
						+ "results = YoutubeSearch(texto, %d)\n\n"
						+ ""
						+ "for i in results.videos:\n"
						+ "  for k, v in i.items():\n"
						+ "    if k == 'title':\n"
						+ "      print(v)\n"
						+ "      print('https://www.youtube.com'+i['url_suffix'])", 
						txtPesquisa.getText(), links.length));
				
				cmdLineSaida = cmd.comando("python3 /tmp/youtubeSearch");

				if (!cmdLineSaida.startsWith("Traceback (most recent call last):") && cmdLineSaida.length() > 0) {
					lstTitulosTmp = cmdLineSaida.split("\n");
					contador = 0;
					for (int i = 0; i < lstTitulosTmp.length; i++) {
						if (i % 2 == 0)lstTitulos.addElement(lstTitulosTmp[i]);
						else if (i % 2 == 1) {
							links[contador] = lstTitulosTmp[i];
							contador++;
						}
						cmd.sleep(0.2);
					}	
				}
				
			} catch (Exception e) {
				lblResultado.setBackground(CORES.getCor(isNoturno(), 7));
				lblResultado.setText(TEXTOS.getTextos(17));
				modificaBgLabelResultado = false;
				Thread.currentThread().stop();
			}
			if (!lstTitulos.isEmpty()) {
				lblResultado.setText(TEXTOS.getTextos(25));
				lblResultado.setBackground(CORES.getCor(isNoturno(), 9));
				modificaBgLabelResultado = false;
				Thread.currentThread().stop();
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
	
}
