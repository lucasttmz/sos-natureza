package modelo;

import java.util.HashMap;
import java.util.List;

public class Controle {

    private static final HashMap<Integer, Topico> todosTopicos = new HashMap<>();
    private String mensagem;

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

    public String criarNovoTopico(List<String> infoTopico) {
        Topico topico = new Topico(
                infoTopico.get(0),
                infoTopico.get(1),
                infoTopico.get(2)
        );
        todosTopicos.put(topico.getId(), topico);

        return topico.getHashtag();
    }

    public void excluirTopico(int id) {
        Topico removido = todosTopicos.remove(id);
        if (removido == null) {
            // Informa que não foi possível remover
        }
    }

    public String getMensagem() {
        return mensagem;
    }
}
