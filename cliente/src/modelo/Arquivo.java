package modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class Arquivo {

    private final String caminhoPasta = "fotos";
    private String mensagem = "";

    public String salvarArquivo(byte[] bytesArquivo, String formato) {
        // Cria a pasta onde será salvo o arquivo se necessário
        File diretorio = new File(caminhoPasta);
        if (!diretorio.exists()) {
            boolean criado = diretorio.mkdir();
            if (!criado) {
                System.err.println("Erro ao criar o diretório das fotos.");
            }
        }

        // Gera um nome único para o arquivo para não ter conflitos futuros
        String uid = UUID.randomUUID().toString();
        String novoCaminho = caminhoPasta + "/" + uid + "." + formato;

        // Salva o arquivo
        try {
            FileOutputStream saida = new FileOutputStream(novoCaminho);
            saida.write(bytesArquivo);
            saida.close();
        } catch (FileNotFoundException ex) {
            this.mensagem = "Erro ao abrir o local que será salvo o arquivo";
        } catch (IOException ex) {
            this.mensagem = "Erro ao salvar o arquivo";
        }

        return novoCaminho;
    }

    public String verificarExtensao(String caminhoArquivo) {
        File arquivo = new File(caminhoArquivo);
        return arquivo.getName().split("\\.")[1];
    }
    
    public byte[] converterParaBytes(String caminho) {
        byte[] bytes = null;
        try {
            FileInputStream entrada = new FileInputStream(new File(caminho));
            bytes = new byte[(int) new File(caminho).length()];
            entrada.read(bytes);
            entrada.close();
        } catch (FileNotFoundException ex) {
            this.mensagem = "Erro ao abrir o arquivo";
        } catch (IOException ex) {
            this.mensagem = "Erro ao ler o arquivo";
        }
        return bytes;
    }

    public String getMensagem() {
        return mensagem;
    }

}
