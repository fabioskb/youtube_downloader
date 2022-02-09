package app;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

//import org.eclipse.jetty.websocket.api.WebSocketTimeoutException;
//import org.openqa.selenium.support.ui.Sleeper;

import metodos.YoutubeArquivo;

@SuppressWarnings("serial")
public class Youtube extends YoutubeForm {
	private String format, cmdLine;
	private String[] links;
	private boolean modificaBg = true;
	

	@Override
	protected void btnModoNoiteClick(ActionEvent ev) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		if (checkVideo.isSelected()) {
			checkAudio.setSelected(false);
			setVideo(true);
			setAudio(false);
		} else
			setVideo(false);
	}

	@Override
	protected void checkAudioClick(ActionEvent ev) {
		// TODO Auto-generated method stub
		if (checkAudio.isSelected()) {
			checkVideo.setSelected(false);
			setAudio(true);
			setVideo(false);
		} else
			setAudio(false);
	}

	@Override
	protected void baixarClick(ActionEvent ev) {
		// TODO Auto-generated method stub
		@SuppressWarnings("static-access")
		Thread b = new Thread(() -> {
			cmdLine = ""; 
			String link = txtLink.getText();
			int index = 0;

			// Preparando o Download
			if ((isVideo() || isAudio()) && !lstPesquisa.isSelectionEmpty()) {
				txtLink.setText(" Link");
				index = lstPesquisa.getSelectedIndex();
				link = links[index];
			} else if (!isVideo() && !isAudio()) {
				lblResultado.setBackground(CORES.getCor(isNoturno(), 8));
				lblResultado.setText(TEXTOS.getTextos(20));
				modificaBg = false;
				cmd.sleep(2);
				Thread.currentThread().interrupt();
			} else if (!lstPesquisa.isSelectedIndex(index) && !link.startsWith("https://www.youtube.com/watch?v=")) {
				lblResultado.setBackground(CORES.getCor(isNoturno(), 8));
				lblResultado.setText(TEXTOS.getTextos(19));
				modificaBg = false;
				cmd.sleep(3);
				Thread.currentThread().interrupt();
			}
			
			if (link.startsWith("https://www.youtube.com/watch?v=")) {
				modificaBg = true;
				setCores(modificaBg);
				lblResultado.setText(TEXTOS.getTextos(31));
				
				// Configura o Download
				modificaBg = true;
				setCores(modificaBg);
				lblResultado.setText(TEXTOS.getTextos(18));
				format = (isVideo()) ? "{'format': 'bestvideo[ext=mp4]',\n" 
				+ "'outtmpl': pasta+title+'.mp4'}"                        // format video para o YoutubeDL
				:
				"{'format': 'bestaudio[ext=m4a]',\n" 
				+ "'outtmpl': pasta+title+'.mp3',\n"
				+ "'postprocessors': [{\n" 
				+ "    'key': 'FFmpegExtractAudio',\n"                    // format audio para o YoutubeDL
				+ "    'preferredcodec': 'mp3',\n" 
				+ "    'preferredquality': '0'\n" 
				+ "    }]\n" 
				+ "}";                                                     

				YoutubeArquivo arquivo = new YoutubeArquivo("/tmp/baixar");
				arquivo.criar(String.format(
						"#!/usr/bin/python3\n" 
				+ "import youtube_dl\n\n"
				+ "" 
				+ "link = '%s'\n" 
				+ "pasta = '%s'\n"
				+ "v = youtube_dl.YoutubeDL()\n" 
				+ "info = v.extract_info(link, download=False)\n"			
				+ "title = info.get('title')\n" 
				+ "format = %s\n" 
				+ "options = format\n\n" 
				+ ""				
				+ "with youtube_dl.YoutubeDL(options) as ydl:\n" 
				+ "    ydl.download([link])",
						link, pasta, format));

				// Executa o download
				try {
					cmdLine = cmd.comando("python3", "/tmp/baixar");
					arquivo.deletar();
				} catch (Exception e) {
					lblResultado.setBackground(CORES.getCor(isNoturno(), 7));
					lblResultado.setText(TEXTOS.getTextos(21));
					modificaBg = false;
					cmd.sleep(2);
					Thread.currentThread().interrupted();
				
				}
			}

			// Mostra os resultados do download
			if (cmdLine.contains("[download] 100%")) {
				lblResultado.setBackground(CORES.getCor(isNoturno(), 9));
				lblResultado.setText(TEXTOS.getTextos(22) + String.format("<html><center><br/>%s</center></html>", pasta));
				modificaBg = false;
			} else {
				lblResultado.setBackground(CORES.getCor(isNoturno(), 7));
				lblResultado.setText(TEXTOS.getTextos(21));
				modificaBg = false;
			}
		});

		b.start();
	}

	@Override
	protected void btnPesquisaClick(ActionEvent ev) {
		// TODO Auto-generated method stub
		Thread pesquisa = new Thread(() -> {
			if (!lstTitulos.isEmpty()) lstTitulos.removeAllElements();
			links = new String[20];
			modificaBg = true;
			setCores(modificaBg);
			lblResultado.setText(TEXTOS.getTextos(16));
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
				}
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
				
			// Mostrando os resultados no lblResultado.
			} catch (Exception e) {
				System.out.println(e);
				lblResultado.setText(TEXTOS.getTextos(17));
				lblResultado.setBackground(CORES.getCor(isNoturno(), 7));
				modificaBg = false;
			}
			if (!lstTitulos.isEmpty() || !(lstTitulos != null)) {
				lblResultado.setText(TEXTOS.getTextos(25));
				lblResultado.setBackground(CORES.getCor(isNoturno(), 9));
				modificaBg = false;
			} else {
				lblResultado.setText(TEXTOS.getTextos(17));
			}
		});
		pesquisa.start();
	}
	

	@Override
	protected void txtLinkMouseClick(MouseEvent ev) {
		// TODO Auto-generated method stub
		txtLink.selectAll();
	}

	@Override
	protected void txtPesquisaMouseClick(MouseEvent ev) {
		// TODO Auto-generated method stub
		txtPesquisa.selectAll();
	}
	
}
