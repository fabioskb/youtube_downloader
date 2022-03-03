package aplicacao;

import java.io.BufferedReader;

import aplicacao.eventos.YoutubeEventos5;

/**
 * Classe responsável por criar o construtor de inicialização
 * da aplicação.
 * Herda tudo da aplicação.
 */
public class YoutubeDl extends YoutubeEventos5 {

    public YoutubeDl(String format, String cmdLineSaida, String[] links, Process pro, BufferedReader read,
            BufferedReader read2) {
        super(format, cmdLineSaida, links, pro, read, read2);
    }

    public YoutubeDl() {
        super(format, cmdLineSaida, links, pro, read, read2);
    }
       
}
