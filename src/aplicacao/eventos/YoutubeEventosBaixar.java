package aplicacao.eventos;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import metodos.YoutubeArquivo;

/**
 * Classe responsável pelo evento que configura o Download e o executa.
 * Além de tratar os erros.
 */
public class YoutubeEventosBaixar extends YoutubeEventosPainelDireita {
    private boolean downloadDone = false;

    @Override
	protected void btnBaixarClick(ActionEvent ev) {
		Thread download = new Thread(() -> {
			downloadDone = false;
			setCmdLineSaida(""); 
			String link = getTxtLink().getText();

			if ((isVideo() || isAudio()) && !getLstPesquisa().isSelectionEmpty()) {
				getTxtLink().setText(TEXTOS.pegarTexto(7));
				setIndex(getLstPesquisa().getSelectedIndex());
				link = getLinks()[getIndex()];
			} else if (!isVideo() && !isAudio()) {
				getLblResultado().setBackground(CORES.pegarCor(isNoturno(), 8));
				getLblResultado().setText(TEXTOS.pegarTexto(20));
				setModificaBgLabelResultado(false);
				return;
			} else if (!getLstPesquisa().isSelectedIndex(getIndex()) && !link.startsWith("https://www.youtube.com/watch?v=")) {
				getLblResultado().setBackground(CORES.pegarCor(isNoturno(), 8));
				getLblResultado().setText(TEXTOS.pegarTexto(19));
				setModificaBgLabelResultado(false);
				return;
			}
			
			if (link.startsWith("https://www.youtube.com/watch?v=")) {
				setModificaBgLabelResultado(true);
				configurarCores(isModificaBgLabelResultado());
				getLblResultado().setText(TEXTOS.pegarTexto(31));
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
					setPro(RUN.exec("python3 /tmp/baixar"));
					setRead(new BufferedReader(new InputStreamReader(getPro().getInputStream())));
					setRead2(new BufferedReader(new InputStreamReader(getPro().getErrorStream())));
					
					while ((line = getRead().readLine()) != null) {
						if (!getBtnCancelar().isVisible()) {
							getBtnBaixa().setVisible(false);
							getBtnCancelar().setVisible(true);
						}
						if ((!line.startsWith("[download] 100%") && !line.contains("Deleting"))) {
							String progressPercentdownload = "", progressPercentdownload2 = "";
							if (line.contains("%")) {
								getDownloadProgressBar().setVisible(true);
								getLblProgressBar().setVisible(false);
								getLblResultado().setVisible(false);
								progressPercentdownload = line.substring(line.indexOf(" "), line.indexOf(".")).strip();
								progressPercentdownload2 = line.substring(line.indexOf(" ")) + "  ";
								if (!downloadDone) getDownloadProgressBar().setString(TEXTOS.pegarTexto(18) + progressPercentdownload2);
								getDownloadProgressBar().setValue(Integer.parseInt(progressPercentdownload.strip()));
								if (getDownloadProgressBar().getString().contains("100.0%")) CMD.sleep(0.2);
							} else if (getDownloadProgressBar().getValue() == 100) {
								getDownloadProgressBar().setValue(0);
								getDownloadProgressBar().setString(TEXTOS.pegarTexto(38) + progressPercentdownload2);
								//getLblProgressBar().setText(TEXTOS.pegarTexto(38));
								downloadDone = true;
							} else {
								if (line.contains("[download] " + getPastaPrincipal())) {
									getLblResultado().setVisible(true);
									getLblResultado().setText(TEXTOS.pegarTexto(30));
									getLblResultado().setBackground(CORES.pegarCor(isNoturno(), 8));
									setModificaBgLabelResultado(false);
								} else if (line.startsWith("[youtube]")) {
									setModificaBgLabelResultado(true);
									configurarCores(isModificaBgLabelResultado());
									getLblResultado().setText(TEXTOS.pegarTexto(31));
								} else {
									if (line.startsWith("[ffmpeg]") && line.endsWith("skipping")) { continue; }
									setModificaBgLabelResultado(true);
									configurarCores(isModificaBgLabelResultado());
									getLblResultado().setText(line);
								}
							}
						} else if (downloadDone) {
							getBtnBaixa().setVisible(true);
							getLblProgressBar().setVisible(false);
							getLblResultado().setVisible(true);
							getLblResultado().setText(TEXTOS.pegarTexto(22));
							getLblResultado().setBackground(CORES.pegarCor(isNoturno(), 9));
							setModificaBgLabelResultado(false);
							getBtnCancelar().setVisible(false);
							getDownloadProgressBar().setVisible(false);
							getDownloadProgressBar().setString(TEXTOS.pegarTexto(18));
						}

						setCmdLineSaida(getCmdLineSaida() + line + "\n");

					} if (line == null) {
						getDownloadProgressBar().setString(TEXTOS.pegarTexto(18));
						getBtnBaixa().setVisible(true);
						getLblProgressBar().setVisible(false);
						getLblResultado().setVisible(true);
						while ((line = getRead2().readLine()) != null) {
							if (line.contains("DownloadError:")) {
								setModificaBgLabelResultado(false);
								getLblResultado().setBackground(CORES.pegarCor(isNoturno(), 7));
								getLblResultado().setText(TEXTOS.pegarTexto(21));
							} else {
								getLblResultado().setText(String.format("<html>%s</html>", line));
								getLblResultado().setBackground(CORES.pegarCor(isNoturno(), 7));
								setModificaBgLabelResultado(false);
							}

							setCmdLineSaida(getCmdLineSaida() + line + "\n");

						}

					getRead().close();
					getRead2().close();
					}

				} catch (IOException e) {
					getDownloadProgressBar().setString(TEXTOS.pegarTexto(18));
					getBtnBaixa().setVisible(true);
					getBtnCancelar().setVisible(false);
					getLblProgressBar().setVisible(false);
					getLblResultado().setVisible(true);
					if (e.toString().contains("Stream closed")) {
						getLblResultado().setText(TEXTOS.pegarTexto(37));
						getLblResultado().setBackground(CORES.pegarCor(isNoturno(), 8));
						setModificaBgLabelResultado(false);
						getDownloadProgressBar().setVisible(false);
						return;	
					} else {
						getLblResultado().setText(String.format("<html>%s</html>", e.toString()));
						getLblResultado().setBackground(CORES.pegarCor(isNoturno(), 7));
						setModificaBgLabelResultado(false);
						getDownloadProgressBar().setVisible(false);
						return;
					}
				}
			}
			if (getCmdLineSaida().equals("")) {
				getLblProgressBar().setVisible(false);
				getLblResultado().setVisible(true);
				getLblResultado().setBackground(CORES.pegarCor(isNoturno(), 7));
				getLblResultado().setText(TEXTOS.pegarTexto(21));
				setModificaBgLabelResultado(false);
			}
			getDownloadProgressBar().setString(TEXTOS.pegarTexto(18));
			getBtnBaixa().setVisible(true);
			getBtnCancelar().setVisible(false);
			getLblProgressBar().setVisible(false);
			getDownloadProgressBar().setVisible(false);
		});
		download.start();
	}	
}
