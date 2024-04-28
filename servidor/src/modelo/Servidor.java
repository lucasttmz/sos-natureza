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
    private final List<String> todasMensagens = new ArrayList<>();
    private ServerSocket servidor;

    public void iniciar() {
        try {
            servidor = new ServerSocket(12345);
            System.out.println("Servidor ouvindo a porta 12345");

            while (true) {
                Socket cliente = servidor.accept();
                System.out.println("123");
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
                
                for (String msg : todasMensagens) {
                    saida.writeObject(msg);
                }

                while (true) {
                    String resposta = String.valueOf(entrada.readObject());
                    System.out.println("3");
                    System.out.println("Mensagem recebida:" + resposta);
                    todasMensagens.add(resposta);

                    List<ObjectOutputStream> remover = new ArrayList<>();
                    for (ObjectOutputStream cliente : clientesConectados) {
                        try {
                            cliente.flush();
                            cliente.writeObject(resposta);
                        } catch (IOException ex) {
                            System.out.println("Cliente desconectado");
                            remover.add(cliente);
                        }
                    }
                    
                    for (ObjectOutputStream r : remover) {
                        clientesConectados.remove(r);
                    }

                }

            } catch (IOException | ClassNotFoundException ex) {
                System.err.println(ex);
            }
        }

    }

}
