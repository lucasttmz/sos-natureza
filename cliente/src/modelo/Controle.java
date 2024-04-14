package modelo;

import java.util.HashMap;
import java.util.List;

public class Controle {

    private static final HashMap<String, Topico> todosTopicos;
    private String mensagem;

    // Temporário enquanto não utilizar os tópicos do servidor
    static {
        todosTopicos = new HashMap<>();
        todosTopicos.put("#outros", new Topico("outros", "nada demais", ""));
    }
    
    public boolean validarConexao(String nome, String ip, String porta) {
        Validacao validacao = new Validacao();
        boolean sucesso = validacao.validarNome(nome) && validacao.validarIp(ip, porta);
        if (!sucesso) {
            this.mensagem = validacao.mensagem;
        }
        return sucesso;
    }

    public void conectar() {
        // Lógica da conexão

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
        if (removido == null) {
            // Informa que não foi possível remover
        }
    }

    public HashMap<String, Topico> getTodosTopicos() {
        return todosTopicos;
    }
    
    public String getMensagem() {
        return mensagem;
    }
}
