package modelo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Cliente {

    private final Controle controle;
    private Socket cliente;
    private ObjectInputStream entrada;
    private ObjectOutputStream saida;

    public Cliente(Controle controle) {
        this.controle = controle;
    }

    public void conectar(String ip, int porta) throws IOException {
        cliente = new Socket(ip, porta);
        entrada = new ObjectInputStream(cliente.getInputStream());
        saida = new ObjectOutputStream(cliente.getOutputStream());

        Thread thread = new Thread(new ThreadRecebimentoMensagens(entrada));
        thread.start();
    }

    public void enviarObjeto(Object mensagem) throws IOException, ClassNotFoundException {
        saida.writeObject(mensagem);
    }

    public void desconectar() throws IOException {
        entrada.close();
        saida.close();
        System.out.println("Conexão encerrada");
    }

    private class ThreadRecebimentoMensagens implements Runnable {

        private final ObjectInputStream entrada;

        public ThreadRecebimentoMensagens(ObjectInputStream entrada) {
            this.entrada = entrada;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Object recebido = entrada.readObject();
                    if (recebido instanceof Mensagem) {
                        Mensagem msg = (Mensagem) recebido;
                        System.out.println("Mensagem recebida: " + msg.getMensagem().strip());
                        controle.mostrarMensagem(msg);
                    } else if (recebido instanceof Topico) {
                        Topico topico = (Topico) recebido;
                        System.out.println("Topico recebido: " + topico.getHashtag());
                        controle.receberNovoTopico(topico);
                    } else {
                        throw new ClassNotFoundException();
                    }
                } catch (IOException ex) {
                    System.err.println("Socket desconectou");
                } catch (ClassNotFoundException ex) {
                    System.err.println("Erro na leitura do objeto serializado: " + ex);
                }
            }
        }
    }
}
