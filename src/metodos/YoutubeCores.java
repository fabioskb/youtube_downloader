package metodos;

import java.awt.Color;

public class YoutubeCores {
	private String[] cores = new String[20];
	private String[] coresNight = new String[20];	
	
	public YoutubeCores() {
		
		// Default
		cores[0] = "#666666";      // Banner background & Descrição borda
		cores[1] = "#770000";      // Banner Foreground
		cores[2] = "#FFFFFF";      // Area de Texto Resultado Background
		cores[3] = cores[2];       // Background Padrão
		cores[4] = "#999999";      // Background Descrição
		cores[5] = "#CCCCCC";      // Background Botões
		cores[6] = "#333333";      // Foreground Botões e Descrição
		cores[7] = "#CC0000";      // Erros
		cores[8] = "#FF6600";      // Avisos
		cores[9] = "#00CC00";      // concluido com sucesso
		cores[10] = "#EEEEEE";     // Área de textos (link, pesquisa e lista)
		
		// Night
		coresNight[0] = "#333333"; // Banner background & Descrição borda
		coresNight[1] = "#CC0000"; // Banner Foreground
		coresNight[2] = "#333333"; // Area de Texto Resultado
		coresNight[3] = "#333333"; // Background Padrão
		coresNight[4] = "#666666"; // Background Descrição
		coresNight[5] = "#666666"; // Background Botões
		coresNight[6] = cores[3];  // Foreground Botões e Descrição
		coresNight[7] = "#CC0000"; // Erros
		coresNight[8] = "#FF6600"; // Avisos
		coresNight[9] = "#00CC00"; // concluido com sucesso
		coresNight[10] = "#C0C0C0";// Área de textos (link, pesquisa e lista)
	}
	
	/**
	 * Cores para a aplicação, é aconselhável usar um método para setar o modo noturno, e então
	 * usa-lo como um boolean getter no argumento noite para uma melhor automoção. 
	 * 
	 * @param noite - Se true o modo noturno será aplicado para o componente, se false, o padrão será aplicado.
	 * @param indiceCor (int) - [0] Banner BG e Descrição Borda, [1] Banner FG, [2] lblResultado BG,
	 * [3] Padrão BG, [4] Descrição BG, [5] Botões BG, [6] FG botões e descrição, [7] Erros, [8] Avisos,
	 * [9] Concluído com sucesso, [10] Área de texto (link, pesquisa e lista).
	 * @return Retorna uma cor de cores[].
	 */
	public Color getCor(boolean noite, int indiceCor) {
		if (noite) return Color.decode(coresNight[indiceCor]);
		else return Color.decode(cores[indiceCor]);
		
	}
}
