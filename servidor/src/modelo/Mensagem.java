package modelo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Mensagem implements Serializable {

    private final String usuario;
    private final String mensagem;
    private final String canal;
    private final LocalDateTime data;

    public Mensagem(String usuario, String canal, String mensagem) {
        this.usuario = usuario;
        this.canal = canal;
        this.mensagem = mensagem;
        this.data = LocalDateTime.now();
    }

    public String formatarParaExibicao(boolean colorirUsuario) {
        // Colorir a data
        String dataFormatada = "<span style='color:#adadad'>" + this.getDataFormatada() + "</span>";

        // Colorir o usuário
        String usuarioFormatado;
        if (colorirUsuario) {
            usuarioFormatado = "<span style='font-weight: bold; color:#47de91'>" + this.getUsuario() + "</span>";
        } else {
            usuarioFormatado = "<span style='font-weight: bold'>" + this.getUsuario() + "</span>";
        }

        // Adicionar HTML na mensagem se possuir link ou imagem
        String mensagemFormatada = this.getMensagem().strip();
        String[] palavras = mensagemFormatada.split(" ");
        for (int i = 0; i < palavras.length; i++) {
            if (palavras[i].startsWith("https://") || palavras[i].startsWith("http://")) {
                if (palavras[i].endsWith(".jpg") || palavras[i].endsWith(".png") || palavras[i].endsWith(".gif")) {
                    palavras[i] = "<br><img src='" + palavras[i] + "'/>";
                } else {
                    palavras[i] = "<a href='" + palavras[i] + "'>" + palavras[i] + "</a>";
                }
            }
        }
        mensagemFormatada = String.join(" ", palavras);
        return dataFormatada + " - " + usuarioFormatado + ": " + mensagemFormatada;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getCanal() {
        return canal;
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getDataFormatada() {
        return data.format(DateTimeFormatter.ofPattern("dd/MM HH:mm"));
    }

    @Override
    public String toString() {
        return "<" + this.getCanal() + "> " + this.getUsuario() + ": " + this.getMensagem();
    }

}
