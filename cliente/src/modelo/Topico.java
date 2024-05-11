package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Topico implements Serializable {

    private final Integer id;
    private String nome;
    private String descricao;
    private String caminhoFoto;
    private List<Mensagem> mensagens;

    private static int ultimoId = 0;

    public Topico(String nome, String descricao, String caminhoFoto) {
        this.id = ++Topico.ultimoId;
        this.nome = nome;
        this.descricao = descricao;
        this.caminhoFoto = caminhoFoto;
        this.mensagens = new ArrayList<>();

        // Apenas para testes de sincronia
        this.mensagens.add(new Mensagem("admin", this.getHashtag(), "mensagem de teste 1"));
        this.mensagens.add(new Mensagem("admin", this.getHashtag(), "mensagem de teste 2"));
    }

    public String getHashtag() {
        String slug = this.nome.toLowerCase().replaceAll(" ", "-");
        return "#" + slug;
    }

    public Integer getId() {
        return id;
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

}
