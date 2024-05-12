package modelo;

import apresentacao.frmChat;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Comandos.Geolocalizacao;
import Comandos.Gifs;
import Comandos.Telefones;
import java.util.LinkedHashMap;
import java.util.Map;

public class Controle {

    private String canalAtual;
    private String nomeExibicao;
    private String mensagem;

    private static final Map<String, Topico> todosTopicos = new LinkedHashMap<>();
    private final List<Topico> topicosPendentes = new ArrayList<>();
    private Cliente cliente;
    private frmChat frmC;

    public Controle() {
        canalAtual = "#geral";
        registrarComandos();
    }

    public void registrarComandos() {
        Comando.comandos.put("/localizacao", new Geolocalizacao());
        Comando.comandos.put("/telefones", new Telefones());
        Comando.comandos.put("/gif", new Gifs());
    }

    public boolean validarConexao(String nome, String ip, String porta) {
        Validacao validacao = new Validacao();
        boolean sucesso = validacao.validarNome(nome) && validacao.validarIp(ip, porta);
        if (!sucesso) {
            this.mensagem = validacao.mensagem;
        }
        return sucesso;
    }

    public boolean validarCriacaoTopico(String nomeTopico) {
        Validacao validacao = new Validacao();
        boolean sucesso = validacao.validarTopico(getTodosTopicos(), nomeTopico);
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
            sincronizarTopicos();
        } catch (IOException ex) {
            this.mensagem = "Erro ao conectar com o servidor!";
        }
    }

    public void enviarMensagem(String mensagem) {
        Mensagem msg = new Mensagem(this.nomeExibicao, canalAtual, mensagem);
        try {
            cliente.enviarObjeto(msg);
        } catch (IOException | ClassNotFoundException ex) {
            this.mensagem = "Erro ao enviar mensagem";
            System.err.println(this.mensagem);
        }
    }

    public void enviarTopico(Topico topico) {
        try {
            cliente.enviarObjeto(topico);
        } catch (IOException | ClassNotFoundException ex) {
            this.mensagem = "Erro ao enviar topico";
            System.err.println(this.mensagem);
        }
    }

    public void executarComando(String comando) {
        enviarMensagem(Comando.executarComando(comando));
    }

    public void mostrarMensagem(Mensagem msg) {
        boolean colorir = msg.getUsuario().equals(this.getNomeExibicao());
        String formatada = msg.formatarParaExibicao(colorir);
        frmC.adicionarMensagem(formatada, msg.getCanal());
    }

    public List<String> informacoesTopico(String hashtag) {
        Topico topico = todosTopicos.get(hashtag);
        return List.of(topico.getNome(), topico.getDescricao(), topico.getCaminhoFoto(), topico.getHashtag());
    }

    public List<String> todasMensagens(String hashtag) {
        List<String> mensagensFormatadas = new ArrayList<>();
        for (Mensagem msg : todosTopicos.get(hashtag).getMensagens()) {
            boolean colorir = msg.getUsuario().equals(this.getNomeExibicao());
            mensagensFormatadas.add(msg.formatarParaExibicao(colorir));
        }
        return mensagensFormatadas;
    }

    public String criarNovoTopico(List<String> infoTopico) {
        Topico topico = new Topico(infoTopico.get(0), infoTopico.get(1));
        String caminhoArquivo = infoTopico.get(2);
        if (!caminhoArquivo.isBlank()) {
            Arquivo arquivo = new Arquivo();
            topico.setFoto(arquivo.converterParaBytes(caminhoArquivo));
            topico.setExtensaoFoto(arquivo.verificarExtensao(caminhoArquivo));
        }
        enviarTopico(topico);
        return topico.getHashtag();
    }

    public void receberNovoTopico(Topico topico) {
        String hashtag = topico.getHashtag();
        todosTopicos.put(hashtag, topico);
        
        // Salva imagem do tópico
        if (topico.getFoto() != null) {
            Arquivo arquivo = new Arquivo();
            String caminhoFoto = arquivo.salvarArquivo(topico.getFoto(), topico.getExtensaoFoto());
            topico.setCaminhoFoto(caminhoFoto);
        }
        
        // Checa se o tópico foi recebido pelo servidor antes do formulário carregar
        // e armazena para mostrar quando carregar.
        if (frmC == null) {
            topicosPendentes.add(topico);
        } else {
            frmC.adicionarNovoTopico(hashtag);
        }
    }

    public void sincronizarTopicos() {
        for (Topico topico : topicosPendentes) {
            frmC.adicionarNovoTopico(topico.getHashtag());
        }
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
        List<List<String>> topicos = new ArrayList<>();
        for (String hashtag : todosTopicos.keySet()) {
            topicos.add(informacoesTopico(hashtag));
        }
        return topicos;
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
