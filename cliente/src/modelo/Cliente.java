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

        Thread thread = new Thread(new RecebimentoDeMensagens(entrada));
        thread.start();
    }

    public void enviarMensagem(Mensagem mensagem) throws IOException, ClassNotFoundException {
        saida.writeObject(mensagem);
    }

    public void desconectar() throws IOException {
        entrada.close();
        saida.close();
        System.out.println("Conexão encerrada");
    }

    private class RecebimentoDeMensagens implements Runnable {

        private final ObjectInputStream entrada;

        public RecebimentoDeMensagens(ObjectInputStream entrada) {
            this.entrada = entrada;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Object obj = entrada.readObject();
                    System.out.println(obj.getClass().getName());
                    if (obj instanceof Mensagem) {
                        Mensagem msg = (Mensagem) obj;
                        System.out.print("Mensagem recebida: " + msg.getMensagem());
                        controle.mostrarMensagem(msg.getUsuario(), msg.getCanal(), msg.getMensagem());
                    } else {
                        System.out.print("Tipo não reconhecido");
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
