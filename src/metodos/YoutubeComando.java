package metodos;

import org.openqa.selenium.os.CommandLine;

public class YoutubeComando {
	private CommandLine comando;
	private String saida; 
	
	public CommandLine getComando() {
		return comando;
	}

	public void setComando(CommandLine comando) {
		this.comando = comando;
	}

	/**
	 * Executa e retorna a saida do comando com argumento.
	 * 
	 * @param executavel (String) - String de um executavel
	 * @param arg (String) - Argumento do executavel
	 * @return saida do comando
	 */
	public String comando(String executavel, String arg) {
		comando = new CommandLine(executavel, arg);
		comando.execute();
		saida = comando.getStdOut();
		comando.destroy();
		return saida;
	}
	
	/**
	 * Executa e retorna a saida do comando com até 3 argumentos.
	 * 
	 * @param executavel (String) - String de um executavel
	 * @param arg (String) - Argumento do executavel
	 * @param arg0 (String) - segundo argumento do executável
	 * @return saida do comando
	 */
	public String comando(String executavel, String arg, String arg0) {
		comando = new CommandLine(executavel, arg, arg0);
		comando.execute();
		saida = comando.getStdOut();
		comando.destroy();
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

