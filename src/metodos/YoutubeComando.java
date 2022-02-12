package metodos;

import java.io.*;

public class YoutubeComando {
	private final Runtime RUN = Runtime.getRuntime();
	private Process pro;
	private BufferedReader read;
	private String saida, line;
	
	/**
	 * Executa e retorna a saida do comando.
	 * 
	 * @param executavel (String) - String de um executavel
	 * @return (String) saida do comando
	 * @throws IOException
	 */
	public String comando(String command) throws IOException {
		pro = RUN.exec(command);
		read = new BufferedReader(new InputStreamReader(pro.getInputStream()));
		saida = "";
		line = null;

		while ((line=read.readLine())!=null) {
			saida += line + "\n";
		}
		read.close();

		return saida;
	}
	
	/**
	 * Pausa 
	 * @param segundos (double) - tempo que ficará pausado em segundos 
	 */
	public void sleep(double segundos) {
		try {
			Thread.sleep((long) (segundos*1000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

