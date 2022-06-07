package metodos;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class YoutubeCores {
	private String[] cores = new String[20];
	private String[] coresNight = new String[20];	
        private Map<String, String> colors = new HashMap<>();
        private Map<String, String> nightColors = new HashMap<>();
	
	public YoutubeCores() {
		
		// Default
		cores[0] = "#666666";      // Banner background & Descrição borda
		cores[1] = "#770000";      // Banner Foreground
		cores[2] = "#FFFFFF";      // Area de Texto Resultado Background
		cores[3] = cores[2];       // Background Padrão
		cores[4] = "#999999";      // Background Descrição
		cores[5] = "#CCCCCC";      // Background Botões
		cores[6] = "#333333";      // Foreground Botões e Descrição
		cores[7] = "#990000";      // Erros
		cores[8] = "#FF6600";      // Avisos
		cores[9] = "#006600";      // concluido com sucesso
		cores[10] = "#EEEEEE";     // Área de textos (link, pesquisa e lista)
		cores[11] = "#666666";     // lblProgressBar e downloadProgressbarFG
                
                colors.put("Banner background & Descricao borda", cores[0]);
                colors.put("Banner FG", cores[1]);
                colors.put("lblResultado BG", cores[2]);
                colors.put("Padrao BG", cores[3]);
                colors.put("Descricao BG", cores[4]);
                colors.put("Botoes BG", cores[5]);
                colors.put("Botoes e descricao FG", cores[6]);
                colors.put("Erros", cores[7]);
                colors.put("Avisos", cores[8]);
                colors.put("Concluido", cores[9]);
                colors.put("Area de texto", cores[10]);
                colors.put("lblProgressBar e downloadProgressBar FG", cores[11]);
		
		// Night
		coresNight[0] = "#333333"; // Banner background & Descrição borda
		coresNight[1] = "#CC0000"; // Banner Foreground
		coresNight[2] = "#333333"; // Area de Texto Resultado
		coresNight[3] = "#333333"; // Background Padrão
		coresNight[4] = "#666666"; // Background Descrição
		coresNight[5] = "#666666"; // Background Botões
		coresNight[6] = cores[3];  // Foreground Botões e Descrição
		coresNight[7] = cores[7]; // Erros
		coresNight[8] = cores[8]; // Avisos
		coresNight[9] = cores[9]; // concluido com sucesso
		coresNight[10] = "#C0C0C0";// Área de textos (link, pesquisa e lista)
		coresNight[11] = "#999999";// lblProgressBar e downloadProgressbarFG
                
                nightColors.put("Banner background & Descricao borda", coresNight[0]);
                nightColors.put("Banner FG", coresNight[1]);
                nightColors.put("lblResultado BG", coresNight[2]);
                nightColors.put("Padrao BG", coresNight[3]);
                nightColors.put("Descricao BG", coresNight[4]);
                nightColors.put("Botoes BG", coresNight[5]);
                nightColors.put("Botoes e descricao FG", coresNight[6]);
                nightColors.put("Erros", coresNight[7]);
                nightColors.put("Avisos", coresNight[8]);
                nightColors.put("Concluido", coresNight[9]);
                nightColors.put("Area de texto", coresNight[10]);
                nightColors.put("lblProgressBar e downloadProgressBar FG", coresNight[11]);
	}
	
	/**
         * DEPRECATED
	 * Cores para a aplicação, é aconselhável usar um método para setar o modo noturno, e então
	 * usa-lo como um boolean getter no argumento noite para uma melhor automoção. 
	 * 
	 * @param noite (boolean) - Se true o modo noturno será aplicado para o componente, se false, o padrão será aplicado.
	 * @param indiceCor (int) - [0] Banner BG e Descrição Borda, [1] Banner FG, [2] lblResultado BG,
	 * [3] Padrão BG, [4] Descrição BG, [5] Botões BG, [6] FG botões e descrição, [7] Erros, [8] Avisos,
	 * [9] Concluído com sucesso, [10] Área de texto (link, pesquisa e lista), [11] lblProgressBar e downloadProgressBar FG.
	 * @return Retorna uma cor de cores[].
	 */
	public Color pegarCor(boolean noite, int indiceCor) {
		if (noite) return Color.decode(coresNight[indiceCor]);
		else return Color.decode(cores[indiceCor]);
		
	}
	
	/**
	 * Cores para a aplicação, é aconselhável usar um método para setar o modo noturno, e então
	 * usa-lo como um boolean getter no argumento noite para uma melhor automoção. 
	 * 
	 * @param noite (boolean) - Se true o modo noturno será aplicado para o componente, se false, o padrão será aplicado.
	 * @param key (String) - Banner BG e Descricao borda, Banner FG, lblResultado BG,
	 * Padrao BG, Descricao BG, Botoes BG, Botoes e descricao FG, Erros, Avisos,
	 * Concluido, Area de texto, lblProgressBar e downloadProgressBar FG.
	 * @return Retorna uma cor.
	 */
	public Color pegarCor(boolean noite, String key) {
		if (noite) return Color.decode(nightColors.get(key));
		else return Color.decode(colors.get(key));
		
	}
}
