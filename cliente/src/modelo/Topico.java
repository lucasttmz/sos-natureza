package modelo;

import java.util.ArrayList;
import java.util.List;

public class Topico {

    private final Integer id;
    private String nome;
    private String descricao;
    private String caminhoFoto;
    // Mudar para uma list thread-safe
    private List<String> mensagens;

    private static int ultimoId = 0;

    public Topico(String nome, String descricao, String caminhoFoto) {
        this.id = ++Topico.ultimoId;
        this.nome = nome;
        this.descricao = descricao;
        this.caminhoFoto = caminhoFoto;
        this.mensagens = new ArrayList<>();
        this.mensagens.add(this.nome + ": hehe");
        this.mensagens.add(this.nome + ": haha");
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

    public List<String> getMensagens() {
        return mensagens;
    }

    public void setMensagens(List<String> mensagens) {
        this.mensagens = mensagens;
    }

}
