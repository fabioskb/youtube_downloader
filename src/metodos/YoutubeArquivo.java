/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metodos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 *
 * @author fabio
 */
public class YoutubeArquivo {
    private String caminho;
    private Path arq;
    /**
     *
     * @param caminho - Path
     * @param dir - Se true cria um diretorio em vez de um arquivo.
     * @throws java.io.IOException
     */
    public YoutubeArquivo(String caminho, boolean dir) throws IOException {
        this.caminho = caminho;
        arq = Paths.get(caminho);
        if (!dir && Files.notExists(arq)) Files.createFile(arq);
        else if (Files.notExists(arq)) Files.createDirectory(arq);
    }

    // Métodos personalizados
    /**
     * Cria um arquivo.
     * @param arquivoConteudo Conteúdo do arquivo.
     */
    public void criar(String arquivoConteudo) {
        try (FileWriter fw = new FileWriter(arq.toFile());
            BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(arquivoConteudo);
            bw.newLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Ler um arquivo.
     * @return Retorna uma String com o conteúdo do arquivo.
     */
    public String ler() {
        String s = null;
        try {
            s = new String(Files.readAllBytes(arq));
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
        //this.arq = new File(this.getCaminho());
        try {
            l = Files.readAllLines(arq);
        } catch (IOException ex) {
        }
        return l;
    }

    /**
     * Adiciona um conteúdo na ultima linha do arquivo
     * e o fecha após isso.
     * @param conteudo String de conteúdo.
     */
    public void editar(String conteudo) {
        try (FileWriter fw = new FileWriter(arq.toFile());
            BufferedWriter bw = new BufferedWriter(fw)) {
            bw.append(conteudo);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adiciona os conteúdos apartir da última linha do arquivo
     * e o fecha após todos os conteúdos serem adicionados.
     * @param conteudo Lista de conteúdos.
     */
    public void editar(List<String> conteudo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arq.toFile()))) {
            for (String item : conteudo) {
                try {
                    bw.append(item);
                    bw.newLine();
                } catch (IOException ex) {
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Deleta o arquivo de acordo com o caminho passado
     * e se é um arquivo válido.
     */
    public void deletar() {
        try {
            Files.deleteIfExists(arq);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Métodos especiais
    public File getArq() {
        return arq.toFile();
    }

    public void setArq(Path arq) {
        this.arq = arq;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

}
