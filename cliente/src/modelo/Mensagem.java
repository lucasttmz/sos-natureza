package modelo;

import java.io.Serializable;

public class Mensagem implements Serializable {

    private final String usuario;
    private final String mensagem;
    private final String canal;

    public Mensagem(String usuario, String canal, String mensagem) {
        this.usuario = usuario;
        this.canal = canal;
        this.mensagem = mensagem;
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

    @Override
    public String toString() {
        return "<" + this.getCanal() + "> " + this.getUsuario() + ": " + this.getMensagem();
    }

}
