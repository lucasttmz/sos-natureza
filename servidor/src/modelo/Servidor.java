package modelo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Servidor {

    private final List<ObjectOutputStream> clientesConectados = new ArrayList<>();
    private final List<Mensagem> todasMensagens = new ArrayList<>();
    private ServerSocket servidor;

    public void iniciar() {
        try {
            servidor = new ServerSocket(12345);
            System.out.println("Servidor ouvindo a porta 12345");

            while (true) {
                Socket cliente = servidor.accept();
                System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());
                Thread thread = new Thread(new ThreadCliente(cliente));
                thread.start();

            }
        } catch (IOException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    private class ThreadCliente implements Runnable {

        private final Socket socket;
        private ObjectOutputStream saida;
        private ObjectInputStream entrada;

        public ThreadCliente(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {

                saida = new ObjectOutputStream(socket.getOutputStream());
                entrada = new ObjectInputStream(socket.getInputStream());
                clientesConectados.add(saida);

                // TODO: Sincronizar as mensagens
//                for (Mensagem msg : todasMensagens) {
//                    saida.writeObject(msg);
//                }

                while (true) {
                    Object recebido = entrada.readObject();

                    System.out.print("Mensagem recebida: " + recebido);

                    if (recebido instanceof Mensagem) {
                        todasMensagens.add((Mensagem) recebido);
                    } else {
                        System.err.println("Tipo não reconhecido");
                    }

                    List<ObjectOutputStream> clientesDesconectados = new ArrayList<>();
                    for (ObjectOutputStream cliente : clientesConectados) {
                        try {
                            cliente.flush();
                            cliente.writeObject(recebido);
                            System.out.print("Mensagem enviada: " + recebido);
                        } catch (IOException ex) {
                            clientesDesconectados.add(cliente);
                        }
                    }

                    for (ObjectOutputStream cliente : clientesDesconectados) {
                        clientesConectados.remove(cliente);
                    }

                }

            } catch (IOException ex) {
                System.out.println("Cliente desconectado");
            } catch (ClassNotFoundException ex) {
                System.err.println("Erro na leitura do objeto serializado: " + ex);
            }
        }

    }

}
