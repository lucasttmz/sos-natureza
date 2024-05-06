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
            System.out.println("Servidor escutando na porta 12345");

            while (true) {
                Socket cliente = servidor.accept();
                System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress()  + ":" + cliente.getPort());
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

        private void sincronizarMensagens() throws IOException {
            for (Mensagem msg : todasMensagens) {
                saida.writeObject(msg);
            }
        }

        @Override
        public void run() {
            try {

                saida = new ObjectOutputStream(socket.getOutputStream());
                entrada = new ObjectInputStream(socket.getInputStream());
                clientesConectados.add(saida);

//                sincronizarMensagens();
                while (true) {
                    Object recebido = entrada.readObject();

                    if (recebido instanceof Mensagem) {
                        Mensagem msg = (Mensagem) recebido;
                        System.out.print("Mensagem recebida: " + socket.getInetAddress().getHostAddress()  + 
                                ":" + socket.getPort() + " -> " + msg.toString()
                        );
                        todasMensagens.add(msg);
                    } else {
                        throw new ClassNotFoundException();
                    }

                    List<ObjectOutputStream> clientesDesconectados = new ArrayList<>();
                    for (ObjectOutputStream cliente : clientesConectados) {
                        try {
                            cliente.flush();
                            cliente.writeObject(recebido);
                        } catch (IOException ex) {
                            clientesDesconectados.add(cliente);
                        }
                    }
                    
                    System.out.print("Mensagem reenviada a todos os clientes: " + recebido);

                    for (ObjectOutputStream cliente : clientesDesconectados) {
                        clientesConectados.remove(cliente);
                    }

                }

            } catch (IOException ex) {
                System.out.println("Cliente desconectado: " + socket.getInetAddress().getHostAddress()  + ":" + socket.getPort());
            } catch (ClassNotFoundException ex) {
                System.err.println("Erro na leitura do objeto serializado: " + ex);
            }
        }

    }

}
