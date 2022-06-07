package metodos;

import java.io.*;

public class YoutubeComando {

    private final Runtime RUN = Runtime.getRuntime();
    private final Runtime RUN2 = Runtime.getRuntime();
    private Process proSearch, proDownload;

    private BufferedReader read, read2;
    private String saida, line;

    /**
     * Executa e retorna a saida do comando.
     *
     * @param executavel (String) - String de um executavel
     * @return (String) saida do comando
     * @throws IOException
     */
    public String comando(String command) {
        saida = "";
        line = null;

        try {
            proSearch = RUN.exec(command);
            read = new BufferedReader(new InputStreamReader(proSearch.getInputStream()));
            read2 = new BufferedReader(new InputStreamReader(proSearch.getErrorStream()));

            while ((line = read.readLine()) != null) {
                saida += line + "\n";
            }
            if (line == null || saida == "") {
                while ((line = read2.readLine()) != null) {
                    saida += line + "\n";
                }
            }

            read.close();
            read2.close();

            return saida;
        } catch (IOException e) {
            saida = "command not found";
            return saida;
        }
    }

    public String comandoDownload(String command) {
        saida = "";
        line = null;

        try {
            proDownload = RUN2.exec(command);
            read = new BufferedReader(new InputStreamReader(proDownload.getInputStream()));
            read2 = new BufferedReader(new InputStreamReader(proDownload.getErrorStream()));

            while ((line = read.readLine()) != null) {
                saida += line + "\n";
            }
            if (line == null || saida == "") {
                while ((line = read2.readLine()) != null) {
                    saida += line + "\n";
                }
            }

            read.close();
            read2.close();

            return saida;
        } catch (IOException e) {
            return e.toString();
        }
    }

    public Process getPro() {
        return proSearch;
    }

    public Process getProDownloadTitle() {
        return proDownload;
    }
    /**
     * Pausa
     *
     * @param segundos (double) - tempo que ficará pausado em segundos
     */
    public void sleep(double segundos) {
        try {
            Thread.sleep((long) (segundos * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void destruir() {
        this.proSearch.destroyForcibly();
        this.proSearch.children().close();
        this.proSearch.descendants().close();
        this.proDownload.destroyForcibly();
        this.proDownload.children().close();
        this.proDownload.descendants().close();
    }

}
