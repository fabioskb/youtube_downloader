package metodos;

public class YoutubeText {
	private String idioma;
	private final String[] txts = {
			"<html><center>"
			+ "Idiomas: pt_BR, En;<br>"
			+ "Baixa vídeos e áudios do YouTube;<br>"
			+ "Opções de escolha: baixar direto pelo link ou pesquisar pelo nome;<br>"
			+ "Retorno de até 20 items na pesquisa;<br>"
			+ "Descrição de cada item da lista via ToolTip ao selecionar com o mouse;<br>"
			+ "Modo Noturno (Automático e Manual)"
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
			"<html><center>Baixando...&nbsp;</center></html>",         // [18] lblresultado Baixando...
			
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
			
			"<html><center>BEM-VINDO AO YOUTUBE DOWNLOADER<br/>Será"
			+ " necessário instalar dependências.<br/>"
			+ "Tecle OK e aguarde!</center></html>",                  // [26] JOptionPane - Boas vindas ao app

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
	};
	private final String[] enTxts = {
			"<html><center>"
			+ "Languages: pt_BR, En;<br>"
			+ "Downloads videos and audios from YouTube;<br>"
			+ "Choice options: download directly from the link or search by name;<br>"
			+ "Return up to 20 items in the search;<br>"
			+ "Description of each item in the list via ToolTip when selecting with the mouse;<br>"
			+ "Night Mode (Automatic and Manual)"
			+ "</center></html>",                            // [0] Description

			"<html><center>Night<br/>Mode</center></html>",  // [1] Night Mode Button

			"<html><center>Video</center></html>",           // [2] Video
			"<html><center>Audio</center></html>",           // [3] Audio
			"<html><center>Direct<br/>Download</center></html>", // [4] Download Button
			"<html><center>Search</center></html>",          // [5] Search Button
			"<html><center>Download</center></html>",        // [6] Download Button
			"LINK -> eg. https://www.youtube.com/watch?v"
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
			"<html><center>Downloading...&nbsp;</center></html> ", // [18] lblresultado Baixando...
			
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
			
			"<html><center>WELCOME TO YOUTUBE DOWNLOADER<br/>It will be "
			+ "need to install dependencies.<br/>"
			+ "Press OK and wait!</center></html>",            // [26] JOptionPane Boas vindas
			
			"<html><center>Could not install dependencies<br/>"
			+ "check the internet connection and try again!<br/>"
			+ "</center></html>",                              // [27] JOptionPane - Error installing dependencies
			
			"<html><center>Dependencies installed with "
			+ "success!<br/>Press OK and <strong>LET'S "
			+ "GO!</strong></center></html>",                  // [28] JOptionPane - Installation of dependencies completed
			
			"<html><center><h3>pip is not installed!</h3><br/>"
			+ "Install it and try again.</center></html>",     // [29] JOptionPane - Error pip is not installed.

			"<html><center>You have already downloaded this file!"
            + "   </center></html>",                            // [30] lblResult - This file has already been downloaded

            "<html><center>Checking the download...</center></html>",// [31] Checking the download
			"<html>Results will be shown here</html>",          // [32] lblResult ToolTip
			"<html><center>Getting title...   </center></htm>", // [33] lblResults getting title
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
	 * ToolTip, [33] lblResultado pegando título
	 * @return String - Texto
	 */
	public String getTextos(int indice) {
		if (idioma.contains("português")) return txts[indice];
		else return enTxts[indice];
	}
}
