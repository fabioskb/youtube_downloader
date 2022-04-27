package metodos;

public class YoutubeText {
	private String idioma;
	private final String[] txts = {
			"<html><center>"
			+ "Idiomas: pt_BR, en;<br>"
			+ "Baixa vídeos e áudios do YouTube;<br>"
			+ "Opções de escolha: baixar direto pelo link ou pesquisar pelo nome;<br>"
			+ "Retorno de até 20 items na pesquisa;<br>"
			+ "Descrição de cada item da lista via ToolTip ao selecionar com o mouse;<br>"
			+ "Modo Noturno (Automático e Manual)<br>"
			+ "Barra de Progresso do Download"
			+ "</center></html>",    							// [0] Descrição
			
			"<html><center>Modo<br/>Noturno</center></html>",   // [1] Botão Modo noite
			
			"<html><center>Vídeo</center></html>",              // [2] Vídeo
			"<html><center>Áudio</center></html>",              // [3] Áudio
			"<html><center>Baixar<br/>Direto</center></html>",  // [4] Botão baixar direto
			"<html><center>Pesquisar</center></html>",          // [5] Botão Pesquisar
			"<html><center>Baixar</center></html>",             // [6] Botão Baixar
			"LINK -> ex: https://www.youtube.com/watch?v"
			+ "=CdRhjkkbD",				                        // [7] Texto de txtLink
			" Nome", 								            // [8] Texto de txtPesquisa
			"<html>Ativa o Modo Noturno</html>",                // [9] Botão ModoNoturno ToolTip
			
			"<html>Insira um link válido do YouTube e "
			+ "clique no botão Baixar</html>",                  // [10] txtLink ToolTip
			"<html>Insira um nome para buscar</html>",          // [11] txtPesquisa ToolTip
			"<html>Baixa através de um link válido</html>",     // [12] btnBaixaDireto ToolTip
			
			"<html>Pesquisa pelo nome (possível) e retorna<br/>"
			+ " até 20 items na lista abaixo</html>",            // [13] btnPesquisa ToolTip
			
			"<html>Selecione para baixar em vídeo (mp4)</html>", // [14] checkVideo toolTip
			"<html>Selecione para baixar em áudio (mp3)</html>", // [15] checkAudio toolTip
			"<html><center>Pesquisando...&nbsp;</center></html> ",      // [16] lblResultado texto Pesquisando...
			"<html><center>A pesquisa falhou!!!&nbsp;</center></html> ",// [17] lblResultado Falha na pesquisa
			"Baixando...",                                           // [18] lblresultado Baixando...
			
			"<html><center>Selecione um item na lista ou adicione um link válido&nbsp;&nbsp;&nbsp;<br/>"
			+ " <strong>POR FAVOR!!!&nbsp;&nbsp;</strong></center></html> ",  // [19] lblResultado aviso de item não selecionado 
			
			"<html><center>Selecione áudio ou vídeo&nbsp;&nbsp;</center> "
			+ "</html>",                                            // [20] lblResultado aviso se não é video ou audio selecionado
			"<html><center>Falha no Download!&nbsp;&nbsp;&nbsp;</center></html> ",  // [21] lblResultado erro falha no download
			
			"<html><center>Download Concluído!&nbsp;&nbsp;&nbsp;<br/>"
			+ "~/YDownloads&nbsp;&nbsp;&nbsp;</center></html> ",                      // [22] lblResultado download concluído
			
			"<html><center>Selecione um item na lista acima(se "
			+ "disponível) ou <br/>adicione um link válido e clique "
			+ "aqui.</center></html>",                               // [23] Botão baixar toolTip
			
			"<html><center>Selecione um item (se disponível)</center>"
			+ "</html>",                                             // [24] lista de resultado da pesquisa ToolTip
			
			"<html><center>Pesquisa concluída!&nbsp;&nbsp;</center></html>",     // [25] lblResultado pesquisa concluída
			
			"<html><center><h1>BEM-VINDO AO YOUTUBE DOWNLOADER</h1><br>"
			+ "As dependências não foram encontradas<br>"
			+ "Será necessário instalar.<br>"
			+ "Tecle OK e aguarde a instalação!<br><br><strong>É necessario uma conexão "
			+ "estável com a internet.</strong></center></html>",      // [26] JOptionPane - Boas vindas ao app

			"<html><center>Não foi possível instalar as dependências<br/>"
			+ " verifique a conexão com a internet e tente novamente! <br/>"
			+ "</center></html>",                                     // [27] JOptionPane - Erro na instalação das dependencias
			
			"<html><center>Dependências instaladas com "
			+ "sucesso!<br/>Tecle OK e <strong>VAMOS "
			+ "LÁ!</strong></center></html>",                          // [28] JOptionPane - Instalação de dependencias concluída
			
			"<html><center><h3>pip não está instalado!</h3><br/>"
			+ "Instale-o e tente novamente.</center></html>",          // [29] JOptionPane - Erro pip não está instaldo.

			"<html><center>Você já baixou esse arquivo!"
			+ "&nbsp;&nbsp;&nbsp;</center></html>",                     // [30] lblResultado - Esses arquivo já foi baixado

			"<html><center>Verificando o download...</center></html>",  // [31] lblResultado Verificando o download

			"<html>Os resultados serão mostrados aqui</html>",          // [32] lblResultado ToolTip
			"<html><center>Pegando o título...   </center></htm>",      // [33] lblResults pegando título
			"<html><center>Não é possível iniciar a pesquisa com<br>"
			+ "download em andamento.</center></html>",                 // [34] Falha ao iniciar a ppesquisa com download em andamento 
			"<html><center>Cancelar<br>Download</center></html>",                    // [35] btnCancelar texto
			"<html><center>Cancela o download atual</center></html>",    // [36] btnCancelar TootTip
			"<html><center>Download Cancelado!   </center></html>",      // [37] lblResultado download cancelado texto 
			"Baixando o áudio do vídeo...",                              // [38] downloadProgressBar baixando o audio do video
			"Sair", /* [39] Exit menu item text */ "Sair do programa", // [40] Exit menu item tooltip
			"Tutorial", /* [41] Tutorial item Menu */ "Tutorial",      // [42] Tutorial
			"Sobre", /* [43] Sobre item menu texto */ "Sobre o programa", // [44] Sobre o programa toolTip

			"<html><center><h1>TUTORIAL</h1></center>1 - Adicione um link válido no campo de link ou pesquise o "
			+"video pelo nome.<br>2 - Selecione vídeo ou áudio (de acordo com o que queira baixar). "
			+"<br>3 - Clique no botão Baixar (se você fez uma pesquisa, selecione um item "
			+"na lista antes de clicar em Baixar).<br><br>Por enquanto, só é possível fazer um "
			+"download por vez.<br> Você pode cancelar o atual download em andamento a qualquer momento "
			+"clicando no botão Cancelar.</html>",                         // [45] Tutorial JOptionPane

			"<html><center><h1>Sobre</h1></center>Uma aplicação voltada para baixar vídeos e áudios "
			+"<br>do YouTube. Contém modo noturno automático, ou seja, abrindo a aplicação "
			+"<br>entre 18:00 e 5:00, o modo noturno é ativado.</html>"
			+"<br><br><center>Autor: Fabio Santos</center></html>",      // [46] Sobre JOptionPane

			"Sobre",                                                       // [47] Sobre JOptionPane Title
			"<html>Ao sair o download em andamento será cancelado."
			+"<br>Você realmente quer sair?",                             // [48] Pergunta ao sair JOptionPane

			"Ok, sair!",                                                   // [49] JOptionPane botão Ok sair
			"Cancelar",                                                   // [50] JOptionPane botão cancelar
	};
	private final String[] enTxts = {
			"<html><center>"
			+ "Languages: pt_BR, en;<br>"
			+ "Downloads videos and audios from YouTube;<br>"
			+ "Choice options: download directly from the link or search by name;<br>"
			+ "Return up to 20 items in the search;<br>"
			+ "Description of each item in the list via ToolTip when selecting with the mouse;<br>"
			+ "Night Mode (Automatic and Manual)<br>"
			+ "Download Progress Bar"
			+ "</center></html>",                            // [0] Description

			"<html><center>Night<br/>Mode</center></html>",  // [1] Night Mode Button

			"<html><center>Video</center></html>",           // [2] Video
			"<html><center>Audio</center></html>",           // [3] Audio
			"<html><center>Direct<br/>Download</center></html>", // [4] Download Button
			"<html><center>Search</center></html>",          // [5] Search Button
			"<html><center>Download</center></html>",        // [6] Download Button
			"LINK -> e.g. https://www.youtube.com/watch?v"
			+ "=CdRhjkkbD",                                  // [7] txtLink
			" Name",							             // [8] txtSearch
			"<html>Enable Night Mode</html>",                // [9] Botão ModoNoturno ToolTip
			
			"<html>Please enter a valid YouTube link" 
			+ " and click the download button</html>",       // [10] txtLink ToolTip
			"<html>Enter a name to search</html>",           // [11] txtPesquisa ToolTip
			"<html>Download via a valid link</html>",        // [12] btnBaixaDireto ToolTip
			
			"<html>Search by name (possible) and return<br/>"
			+ " up to 20 items in the list below</html>",     // [13] btnPesquisa ToolTip
			
			"<html>Select to download video (mp4)</html>",    // [14] checkVideo toolTip
			"<html>Select to download audio (mp3)</html>",    // [15] checkAudio toolTip
			"<html><center>Searching...&nbsp;</center></html>",    // [16] lblResultado texto Pesquisando...
			"<html><center>Failed search!!!&nbsp;&nbsp;</center></html> ",// [17] lblResultado Falha na pesquisa
			" Downloading...",                                   // [18] lblresultado Baixando...
			
			"<html><center>Select an item from the list or add an valid link&nbsp;&nbsp;<br/>"
			+ " <strong>PLEASE!!!&nbsp;&nbsp;</strong></center></html> ",// [19] lblResultado aviso item não selecionado
			"<html><center>Select audio or video&nbsp;&nbsp;</center> "
			+ "</html>",                                      // [20] lblResultado aviso se não é video ou audio selecionado
			"<html><center>Download Failed!&nbsp;&nbsp;</center></html> ",// [21] lblResultado erro falha no download
			
			"<html><center>Download Done!&nbsp;&nbsp;<br/>"
			+ "~/YDownloads&nbsp;&nbsp;</center></html>",                 // [22] lblResultado download concluído
			
			"<html><center>Select an item from the list above (if "
			+ "available) or <br/>add a valid link and click "
			+ "here.</center></html>",                             // [23] ToolTip download button
			
			"<html><center>Select an item (if available)</center>"
			+ "</html>",                                           // [24] ToolTip search result list
			"<html><center>Searching Done!&nbsp;&nbsp;</center></html>",  // [25] lblResultado pesquisa concluída
			
			"<html><center><h1>WELCOME TO YOUTUBE DOWNLOADER</h1><br>"
			+ "The dependencies were not found<br>"
			+ "You will need to install.<br>"
			+ "Press OK and wait to install!<br><br><strong>A stable "
			+ "internet connection is required</strong></center></html>", // [26] JOptionPane Boas vindas
			
			"<html><center>Could not install dependencies<br/>"
			+ "check the internet connection and try again!<br/>"
			+ "</center></html>",                              // [27] JOptionPane - Error installing dependencies
			
			"<html><center>Dependencies installed with "
			+ "success!<br/>Press OK and <strong>LET'S "
			+ "GO!</strong></center></html>",                  // [28] JOptionPane - Installation of dependencies completed
			
			"<html><center><h3>pip is not installed!</h3>"
			+ "Install it and try again.</center></html>",     // [29] JOptionPane - Error pip is not installed.

			"<html><center>You have already downloaded this file!"
            + "   </center></html>",                            // [30] lblResult - This file has already been downloaded

            "<html><center>Checking the download...</center></html>",// [31] Checking the download
			"<html>Results will be shown here</html>",             // [32] lblResult ToolTip
			"<html><center>Getting title...   </center></htm>",    // [33] lblResults getting title
			"<html><center>Cannot start search with<br>"
			+ "download in progress.</center></html>",             // [34] Failed to start psearch with download in progress
			"<html><center>Cancel<br>Download</center></html>",     // [35] btnCancel text 
			"<html><center>Cancel current download</center></html>",// [36] btnCancel TootTip
			"<html><center>Download Cancelled!   </center></html>", // [37] lblResultado canceled download text
			" Downloading audio from video...",                      // [38] progressbar aixando o audio do video
			"Exit", /* [39] Exit menu item text */ "Exit Application", // [40] Exit menu item tooltip
			"Tutorials", /* [41] Tutorial item Menu */ "Tutorials",   // [42] Tutorial
			"About", /* [43] Sobre item menu texto */ "About the program", // [44] Sobre o programa toolTip

			"<html><center><h1>TUTORIAL</h1></center>1 - Add a valid link in the link field or search for the "
			+"video by name.<br>2 - Select video or audio (depending on what you want to download). "
			+"<br>3 - Click the Download button (if you did a search, select an item "
			+"from the list before clicking Download).<br><br>For now, you can only download "
			+"one file at a time.<br> You can cancel the current download in progress at any time "
			+"by clicking the Cancel button.</html>",               // [45] Tutorial JOptionPane

			"<html><center><h1>About</h1></center>An application aimed at downloading videos and audios"
			+"<br>from YouTube. Contains automatic night mode, i.e. opening the application"
			+"<br>between 18:00 and 5:00, night mode is activated."
			+"<br><br><center>Author: Fabio Santos</center></html>", // [46] About JOptionPane

			"About",                                                  // [47] About JOptionPane Title
			"<html>When exiting, the download in progress will be cancelled."
			+"<br>Do you really want to exit?</html>",                // [48] Pergunta ao sair JoptionPane

			"Ok, exit!",                                                   // [49] JOptionPane botão Ok sair
			"Cancel",                                                   // [50] JOptionPane botão cancelar
			};
	
	public YoutubeText(String idioma) {
		this.idioma = idioma;
	}
	
	
	/**
	 * Retorna o texto de acordo com o 'indice'.
	 *  
	 * @param indice (int) - [0] Descrição, [1] Modo Noturno, [2] video, [3] audio, [4] Botão BaixarDireto (deprecated), [5] botão pesquisar, [6] botão baixar,
	 * [7] texto do txtlink, [8] texto de txtpesquisar, [9] tooltip do botão noturno, [10] tooltip do txtLink, [11] tooltip do txtPesquisa,
	 * [12] tooltip do botão baixarDireto (deprecated), [13] botão pesquisar tooltip, [14] checkVideo tooltip, [15] checkAudio tooltip, [16] lblresultado pesquisando...,
	 * [17] lblResultado Falha na pesquisa, [18] lblResultado Baixando, [19] lblResultado aviso item não selecionado, [20] lblResultado aviso video/audio,
	 * [21] lblResultado Falha no download, [22] lblResultado download concluido, [23] tooltip botão baixar, [24] tooltip lista results, [25] lblResultado Pesquisa conc, 
	 * [26] JOptionPane Boas vindas, [27] JOptionPane Erro na instalação dependencias, [28] JOptionPane Instalação completada, [29] JOtionPane Erro pip não instalado,
	 * [30] lblResultado arquivo existente, [31] lblresultado verificando o download, [32] lblResultado 
	 * ToolTip, [33] lblResultado pegando título, [34] lblResultado Falha ao iniciar uma pesquisa com download em andamento, [35] btnCancelar texto, [36] btnCancelar ToolTip, [37] lblResultado download cancelado texto, [38] downloadProgressBar baixando o audio do video [30] Texto do itemMenu Sair, [40] ToolTip do itemMenu sair
	 * , [41] Tutorial item menu texto, [42] Menu Tutorial ToolTip, [43] Sobre item menu texto, [44] Sobre item
	 * menu ToolTip, [45] Tutorial JOptionPane, [46] Sobre JOptionPane, [47] Sobre JOptionPane Title, 
	 * [48] Pergunta ao sair JoptionPane, [49] JOptionPane botão ok sair, [59] JOptionPane botão cancelar
	 * @return String - Texto
	 */
	public String pegarTexto(int indice) {
		if (idioma.contains("português")) return txts[indice];
		else return enTxts[indice];
	}
}
