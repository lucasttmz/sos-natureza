package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Topico implements Serializable {

    private String nome;
    private String descricao;
    private byte[] foto;
    private String extensaoFoto;
    private String caminhoFoto;
    private List<Mensagem> mensagens;

    public Topico(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
        this.caminhoFoto = "";
        this.mensagens = new ArrayList<>();
    }

    public String getHashtag() {
        String slug = this.nome.toLowerCase().replaceAll(" ", "-");
        return "#" + slug;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getExtensaoFoto() {
        return extensaoFoto;
    }

    public void setExtensaoFoto(String extensaoFoto) {
        this.extensaoFoto = extensaoFoto;
    }

    public String getCaminhoFoto() {
        return caminhoFoto;
    }

    public void setCaminhoFoto(String caminhoFoto) {
        this.caminhoFoto = caminhoFoto;
    }

    public List<Mensagem> getMensagens() {
        return mensagens;
    }

    public void setMensagens(List<Mensagem> mensagens) {
        this.mensagens = mensagens;
    }

    @Override
    public String toString() {
        return "<" + this.getHashtag() + "> ";
    }

}
