package modelo;

import apresentacao.frmChat;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Controle {

    private static final HashMap<String, Topico> todosTopicos;
    private frmChat frmC;
    private String nomeExibicao;
    private String mensagem;

    // Temporário enquanto não utilizar os tópicos do servidor
    static {
        todosTopicos = new HashMap<>();
        todosTopicos.put("#outros", new Topico("outros", "nada demais", ""));
        todosTopicos.put("#testes", new Topico("testes", "descricao", "img.png"));
    }

    public boolean validarConexao(String nome, String ip, String porta) {
        Validacao validacao = new Validacao();
        boolean sucesso = validacao.validarNome(nome) && validacao.validarIp(ip, porta);
        if (!sucesso) {
            this.mensagem = validacao.mensagem;
        }
        return sucesso;
    }

    public void conectar(String nome) {
        this.mensagem = "";
        this.nomeExibicao = nome;
        frmC = new frmChat(this); // TODO: Remover o nome daqui;
        frmC.setVisible(true);
    }

    public List<String> informacoesTopico(String hashtag) {
        Topico topico = todosTopicos.get(hashtag);
        return List.of(topico.getNome(), topico.getDescricao(), topico.getCaminhoFoto(), topico.getHashtag());
    }

    public List<String> todasMensagens(String hashtag) {
        return todosTopicos.get(hashtag).getMensagens();
    }

    public String criarNovoTopico(List<String> infoTopico) {
        Topico topico = new Topico(
                infoTopico.get(0),
                infoTopico.get(1),
                infoTopico.get(2)
        );
        todosTopicos.put(topico.getHashtag(), topico);

        return topico.getHashtag();
    }

    public void excluirTopico(String hashtag) {
        Topico removido = todosTopicos.remove(hashtag);
        if (removido != null) {
            // Informa o servidor
        } else {
            this.mensagem = "Erro ao apagar o tópico";
        }
    }

    public List<List<String>> getTodosTopicos() {
        return todosTopicos.keySet().stream()
                .map(hashtag -> informacoesTopico(hashtag))
                .collect(Collectors.toList());
    }

    public String getMensagem() {
        return mensagem;
    }
    
    public String getNomeExibicao() {
        return nomeExibicao;
    }
}
