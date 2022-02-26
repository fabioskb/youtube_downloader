package aplicacao;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import metodos.YoutubeArquivo;

/**
 * Classe responsável pelo evento que configura o Download e o executa.
 * Além de tratar os erros.
 */
public class YoutubeEventos3 extends YoutubeEventos2 {
    
    public YoutubeEventos3(String format, String cmdLineSaida, String[] links, Process pro, BufferedReader read,
            BufferedReader read2) {
        super(format, cmdLineSaida, links, pro, read, read2);        
    }

    @Override
	protected void btnBaixarClick(ActionEvent ev) {
		Thread download = new Thread(() -> {
			setCmdLineSaida(""); 
			String link = getTxtLink().getText();

			if ((isVideo() || isAudio()) && !getLstPesquisa().isSelectionEmpty()) {
				getTxtLink().setText(getTEXTOS().getTextos(7));
				setIndex(getLstPesquisa().getSelectedIndex());
				link = getLinks()[getIndex()];
			} else if (!isVideo() && !isAudio()) {
				getLblResultado().setBackground(getCORES().getCor(isNoturno(), 8));
				getLblResultado().setText(getTEXTOS().getTextos(20));
				setModificaBgLabelResultado(false);
				Thread.currentThread().interrupt();
				setCmdLineSaida("pass");
			} else if (!getLstPesquisa().isSelectedIndex(getIndex()) && !link.startsWith("https://www.youtube.com/watch?v=")) {
				getLblResultado().setBackground(getCORES().getCor(isNoturno(), 8));
				getLblResultado().setText(getTEXTOS().getTextos(19));
				setModificaBgLabelResultado(false);
				Thread.currentThread().interrupt();
				setCmdLineSaida("pass");
			}
			
			if (link.startsWith("https://www.youtube.com/watch?v=")) {

				setModificaBgLabelResultado(true);
				configurarCores(isModificaBgLabelResultado());
				getLblResultado().setText(getTEXTOS().getTextos(31));
				setFormat((isVideo()) ? String.format("{'format': 'bestvideo[ext=mp4]+bestaudio[ext=m4a]',\n" 
				+ "'outtmpl': '%s' + title + '.mp4'}", getPastaPrincipal())                        // format video para o YoutubeDL
				:
				String.format("{'format': 'bestaudio[ext=m4a]',\n" 
				+ "'outtmpl': '%s' + title + '.mp3',\n"
				+ "'postprocessors': [{\n" 
				+ "    'key': 'FFmpegExtractAudio',\n"                    // format audio para o YoutubeDL
				+ "    'preferredcodec': 'mp3',\n" 
				+ "    'preferredquality': '0'\n" 
				+ "    }]\n" 
				+ "}", getPastaPrincipal()));                                                     
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
				link, getFormat()));

				try {
					setPro(getRUN().exec("python3 /tmp/baixar"));
					setRead(new BufferedReader(new InputStreamReader(getPro().getInputStream())));
					setRead2(new BufferedReader(new InputStreamReader(getPro().getErrorStream())));
					
					while ((line = getRead().readLine()) != null) {
						if (!line.startsWith("[download] 100%") && !line.contains("Deleting")) {
							if (getLblResultado().getBackground().toString().equals("java.awt.Color[r=0,g=204,b=0]")) {
								line = line.replace("[download]", "[downloading audio from video]");
								setModificaBgLabelResultado(false);
								getLblResultado().setText(String.format("<html>%s</html>", line));
								getLblResultado().setBackground(getCORES().getCor(isNoturno(), 9));
							} else {
								if (line.contains("[download] " + getPastaPrincipal())) {
									getLblResultado().setText(getTEXTOS().getTextos(30));
									getLblResultado().setBackground(getCORES().getCor(isNoturno(), 8));
									setModificaBgLabelResultado(false);
								} else if (line.startsWith("[youtube]")) {
									setModificaBgLabelResultado(true);
									configurarCores(isModificaBgLabelResultado());
									getLblResultado().setText(getTEXTOS().getTextos(31));
								}
								else {
									setModificaBgLabelResultado(true);
									configurarCores(isModificaBgLabelResultado());
									getLblResultado().setText(String.format("<html>%s</html>", line));
								}
							}
						} else {
							getLblResultado().setText(getTEXTOS().getTextos(22));
							getLblResultado().setBackground(getCORES().getCor(isNoturno(), 9));
							setModificaBgLabelResultado(false);
						}

						setCmdLineSaida(getCmdLineSaida() + line + "\n");

					} if (line == null) {
						while ((line = getRead2().readLine()) != null) {
							if (line.contains("DownloadError:")) {
								setModificaBgLabelResultado(false);
								getLblResultado().setBackground(getCORES().getCor(isNoturno(), 7));
								getLblResultado().setText(getTEXTOS().getTextos(21));
							} else {
								getLblResultado().setText(String.format("<html>%s</html>", line));
								getLblResultado().setBackground(getCORES().getCor(isNoturno(), 7));
								setModificaBgLabelResultado(false);
							}

							setCmdLineSaida(getCmdLineSaida() + line + "\n");

						}

					getRead().close();
					getRead2().close();
					}

				} catch (IOException e) {
					getLblResultado().setText(String.format("<html>%s</html>", e.toString()));
					getLblResultado().setBackground(getCORES().getCor(isNoturno(), 7));
					setModificaBgLabelResultado(false);
				}
			}

			if (getCmdLineSaida().equals("")) {
				getLblResultado().setBackground(getCORES().getCor(isNoturno(), 7));
				getLblResultado().setText(getTEXTOS().getTextos(21));
				setModificaBgLabelResultado(false);
			}
		});
		download.start();
	}

}
