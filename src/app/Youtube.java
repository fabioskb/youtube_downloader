package app;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import metodos.YoutubeArquivo;

@SuppressWarnings("serial")
public class Youtube extends YoutubeForm {
	private String format, cmdLine;
	private String[] links;
	private boolean modificaBg = true;
	

	@Override
	protected void btnModoNoiteClick(ActionEvent ev) {
		if (!btnModoNoite.isSelected()) {
			setNoturno(false);
			setCores(modificaBg);
		} else {
			setNoturno(true);
			setCores(modificaBg);
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
	protected void baixarClick(ActionEvent ev) {
		@SuppressWarnings("static-access")
		Thread b = new Thread(() -> {
			cmdLine = ""; 
			String link = txtLink.getText();
			int index = 0;

			// Preparando o Download
			lblResultado.setText(TEXTOS.getTextos(31));
			System.out.println((IDIOMA.contains("português")?"Verificando...":"Verifying..."));
			if ((isVideo() || isAudio()) && !lstPesquisa.isSelectionEmpty()) {
				txtLink.setText(" Link");
				index = lstPesquisa.getSelectedIndex();
				link = links[index];
			} else if (!isVideo() && !isAudio()) {
				lblResultado.setBackground(CORES.getCor(isNoturno(), 8));
				lblResultado.setText(TEXTOS.getTextos(20));
				modificaBg = false;
				Thread.currentThread().stop();
			} else if (!lstPesquisa.isSelectedIndex(index) && !link.startsWith("https://www.youtube.com/watch?v=")) {
				lblResultado.setBackground(CORES.getCor(isNoturno(), 8));
				lblResultado.setText(TEXTOS.getTextos(19));
				modificaBg = false;
				Thread.currentThread().stop();
			}
			
			if (link.startsWith("https://www.youtube.com/watch?v=")) {
				modificaBg = true;
				setCores(modificaBg);
				System.out.println("link: "+link);

				// Configura o Download
				lblResultado.setText(TEXTOS.getTextos(31));
				modificaBg = true;
				setCores(modificaBg);
				lblResultado.setText(TEXTOS.getTextos(18));
				System.out.println((IDIOMA.contains("português"))?"Baixando...":"Downloading...");
				format = (isVideo()) ? String.format("{'format': 'bestvideo[ext=mp4]',\n" 
				+ "'outtmpl': '%s' + title + '.mp4'}", pasta)                        // format video para o YoutubeDL
				:
				String.format("{'format': 'bestaudio[ext=m4a]',\n" 
				+ "'outtmpl': '%s' + title + '.mp3',\n"
				+ "'postprocessors': [{\n" 
				+ "    'key': 'FFmpegExtractAudio',\n"                    // format audio para o YoutubeDL
				+ "    'preferredcodec': 'mp3',\n" 
				+ "    'preferredquality': '0'\n" 
				+ "    }]\n" 
				+ "}", pasta);                                                     

				YoutubeArquivo arquivo = new YoutubeArquivo("/tmp/baixar");
				arquivo.criar(String.format(
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

				// Executa o download
				cmdLine = cmd.comando("python3", "/tmp/baixar");
			}

			// Mostra os resultados do download
			if (cmdLine.contains("[download] 100%")) {
				lblResultado.setBackground(CORES.getCor(isNoturno(), 9));
				lblResultado.setText(TEXTOS.getTextos(22) + String.format("<html><center><br/>%s</center></html>", pasta));
				modificaBg = false;
				System.out.println((IDIOMA.contains("português"))?"Concluído!":"Done!");
				Thread.currentThread().stop();
			} else {
				lblResultado.setBackground(CORES.getCor(isNoturno(), 7));
				lblResultado.setText(TEXTOS.getTextos(21));
				modificaBg = false;
				System.out.println((IDIOMA.contains("português"))?"Concluído!":"Done!");
				Thread.currentThread().stop();
			}
		});
		b.start();
	}

	@Override
	protected void btnPesquisaClick(ActionEvent ev) {
		Thread pesquisa = new Thread(() -> {
			if (!lstTitulos.isEmpty()) lstTitulos.removeAllElements();
			links = new String[20];
			modificaBg = true;
			setCores(modificaBg);
			lblResultado.setText(TEXTOS.getTextos(16));
			System.out.println((IDIOMA.contains("português")?"Pesquisando...":"Searching..."));
			String[] lstTits = null;
			
			// Pesquisando
			try {
				// Criando o arquivo temporario
				YoutubeArquivo ytTitulos = new YoutubeArquivo("/tmp/youtubeSearch");
				ytTitulos.criar(String.format("#!/usr/bin/python3\n"
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
				
				// Executando o arquivo temporario e pegando os títulos e links.
				cmdLine = cmd.comando("python3", "/tmp/youtubeSearch");
				if (!cmdLine.startsWith("Traceback (most recent call last):")) {
					lstTits = cmdLine.split("\n");
					// Adicionando os títulos na lstTitulos e os links no vetor links[].
					int c = 0;
					for (int i = 0; i < lstTits.length; i++) {
						if (i % 2 == 0)lstTitulos.addElement(lstTits[i]);
						else if (i % 2 == 1) {
							links[c] = lstTits[i];
							c++;
						}
						cmd.sleep(0.2);
					}	
				}
				
			// Mostrando os resultados no lblResultado.
			} catch (Exception e) {
				lblResultado.setBackground(CORES.getCor(isNoturno(), 7));
				lblResultado.setText(TEXTOS.getTextos(17));
				modificaBg = false;
				System.out.println((IDIOMA.contains("português"))?"Falhou!":"Failed!");
				Thread.currentThread().stop();
			}
			if (!lstTitulos.isEmpty() || !(lstTitulos != null)) {
				lblResultado.setText(TEXTOS.getTextos(25));
				lblResultado.setBackground(CORES.getCor(isNoturno(), 9));
				modificaBg = false;
				System.out.println((IDIOMA.contains("português"))?"Concluído!":"Done!");
				Thread.currentThread().stop();
			} else {
				lblResultado.setText(TEXTOS.getTextos(17));
				System.out.println((IDIOMA.contains("português"))?"Falhou!":"Failed!");
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
