/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metodos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Formatter;
import java.util.List;

/**
 *
 * @author fabio
 */
public class YoutubeArquivo {
    private String caminho;
    private File arq;
    private Formatter arq1; // Cria
    private BufferedWriter escrever; // Escreve ou edita

    public YoutubeArquivo(String path) {
        this.caminho = path;
        this.arq = new File(this.caminho);
    }
    
    // Métodos personalizados
    /**
     * Cria um arquivo.
     * @param arquivoConteudo Conteúdo do arquivo.
     */
    public void criar(String arquivoConteudo) {
        try {
            this.arq1 = new Formatter(this.caminho);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.arq1.format(arquivoConteudo);
        this.arq1.close();
    }
    
    /**
     * Ler um arquivo.
     * @return Retorna uma String com o conteúdo do arquivo.
     */
    public String ler() {
        String s = null;
        try {
            s = new String(Files.readAllBytes(this.arq.toPath()));
        } catch (IOException ex) {
            
        }
        return s;
    }
    
    /**
     * Lista um arquivo.
     * @return Retorna uma lista com o conteúdo
     * linha por linha.
     */
    public List<String> listar() {
        List<String> l = null;
        this.arq = new File(this.getCaminho());
        try {
            l = Files.readAllLines(this.arq.toPath());
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return l;
    }
    
    /**
     * Adiciona um conteúdo na ultima linha do arquivo
     * e o fecha após isso.
     * @param conteudo String de conteúdo.
     */
    public void editar(String conteudo) {
        try {
            this.escrever = new BufferedWriter(new FileWriter(this.arq));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            this.escrever.append(conteudo);
            this.escrever.close();
        } catch (IOException ex) {
        }
        
    }
    
    /**
     * Adiciona os conteúdos apartir da última linha do arquivo
     * e o fecha após todos os conteúdos serem adicionados.
     * @param conteudo Lista de conteúdos.
     */
    public void editar(List<String> conteudo) {
        try {
            this.escrever = new BufferedWriter(new FileWriter(this.arq));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String item : conteudo) {
            try {
                this.escrever.append(item);
                this.escrever.newLine();
            } catch (IOException ex) {
            }
        }
        try {
            this.escrever.close();
        } catch (IOException ex) {
        }
    }
    /**
     * Deleta o arquivo de acordo com o caminho passado
     * e se é um arquivo válido.
     */
    public void deletar() {
        if (this.arq.isFile()) {
            this.arq.delete();
        }
    }
    
    public void renomear(File novoArq) {
        /*try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
        }
        try {
            Path source = Paths.get(this.arq.getPath());
            Files.move(source, source.resolveSibling(novoArq.getPath()));
        } catch (IOException ex) {
            System.out.println(ex);
        }*/
    }

    // Métodos especiais
    public File getArq() {
        return arq;
    }

    public void setArq(File arq) {
        this.arq = arq;
    }

    public Formatter getArq1() {
        return arq1;
    }
    
    public void setArq1(Formatter arq1) {    
        this.arq1 = arq1;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public BufferedWriter getEscrever() {
        return escrever;
    }

    public void setEscrever(BufferedWriter escrever) {
        this.escrever = escrever;
    }
}
