package modelo;

import apresentacao.frmChat;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Controle {

    private String canalAtual;
    private String nomeExibicao;
    private String mensagem;

    private static HashMap<String, Topico> todosTopicos;
    private List<Mensagem> mensagensPendentes; // TODO
    private Cliente cliente;
    private frmChat frmC;

    // Temporário enquanto não utilizar os tópicos do servidor
    public Controle() {
        canalAtual = "#geral";
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

    public void conectar(String nome, String ip, int porta) {
        this.mensagem = "";
        this.nomeExibicao = nome;
        this.cliente = new Cliente(this);

        try {
            this.cliente.conectar(ip, porta);
            this.frmC = new frmChat(this);
            this.frmC.setVisible(true);
        } catch (IOException ex) {
            this.mensagem = "Erro ao conectar com o servidor!";
        }
    }

    public void enviarMensagem(String mensagem) {
        Mensagem msg = new Mensagem(this.nomeExibicao, canalAtual, mensagem);
        try {
            cliente.enviarMensagem(msg);
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Erro ao enviar mensagem");
        }
    }

    public void mostrarMensagem(Mensagem msg) {
        frmC.adicionarMensagem(msg.getUsuario(), msg.getCanal(), msg.getMensagem(), msg.getDataFormatada());
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

    public String getCanalAtual() {
        return canalAtual;
    }

    public void setCanalAtual(String canalAtual) {
        this.canalAtual = canalAtual;
    }

}
