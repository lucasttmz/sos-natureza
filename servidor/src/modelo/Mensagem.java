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
