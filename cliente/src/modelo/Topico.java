package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Topico implements Serializable {

    public enum Acao {
        CRIAR,
        EDITAR,
        APAGAR
    }

    private Acao acao;
    private final Integer id;
    private String nome;
    private String descricao;
    private String caminhoFoto;
    private List<Mensagem> mensagens;

    private static int ultimoId = 0;

    public Topico(String nome, String descricao, String caminhoFoto) {
        this(Acao.CRIAR, nome, descricao, caminhoFoto);
    }

    public Topico(Acao acao, String nome, String descricao, String caminhoFoto) {
        this.id = ++Topico.ultimoId;
        this.acao = acao;
        this.nome = nome;
        this.descricao = descricao;
        this.caminhoFoto = caminhoFoto;
        this.mensagens = new ArrayList<>();
    }

    public String getHashtag() {
        String slug = this.nome.toLowerCase().replaceAll(" ", "-");
        return "#" + slug;
    }

    public Integer getId() {
        return id;
    }

    public Acao getAcao() {
        return acao;
    }

    public void setAcao(Acao acao) {
        this.acao = acao;
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
