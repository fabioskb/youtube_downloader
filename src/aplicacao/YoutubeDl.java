package aplicacao;

import java.io.BufferedReader;

/**
 * Classe responsável por criar o construtor de inicialização
 * da aplicação.
 */
public class YoutubeDl extends YoutubeEventos5 {
    
    private static String format;
    private static String cmdLineSaida;
    private static String[] links;
    private static Process pro;
    private static BufferedReader read;
    private static BufferedReader read2;

    public YoutubeDl(String format, String cmdLineSaida, String[] links, Process pro, BufferedReader read,
            BufferedReader read2) {
        super(format, cmdLineSaida, links, pro, read, read2);
    }

    public YoutubeDl() {
        super(format, cmdLineSaida, links, pro, read, read2);
    }
       
}
