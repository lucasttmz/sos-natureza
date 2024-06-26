package modelo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Servidor {

    private final List<ObjectOutputStream> clientesConectados = new ArrayList<>();
    private final Map<String, Topico> todosTopicos = new LinkedHashMap<>(); // Preserva ordem
    private ServerSocket servidor;

    public void iniciar() {
        try {
            servidor = new ServerSocket(12345);
            System.out.println("Servidor escutando na porta 12345");

            while (true) {
                Socket cliente = servidor.accept();
                System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress() + ":" + cliente.getPort());
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

        private void sincronizarTopicos() throws IOException {
            for (Topico topico : todosTopicos.values()) {
                saida.writeObject(topico);
            }
        }

        @Override
        public void run() {
            try {
                saida = new ObjectOutputStream(socket.getOutputStream());
                entrada = new ObjectInputStream(socket.getInputStream());
                clientesConectados.add(saida);

                sincronizarTopicos();

                while (true) {
                    Object recebido = entrada.readObject();

                    if (recebido instanceof Mensagem) {
                        Mensagem msg = (Mensagem) recebido;
                        Topico topico = todosTopicos.get(msg.getCanal());
                        topico.getMensagens().add(msg);

                        System.out.println("Mensagem recebida: " + socket.getInetAddress().getHostAddress()
                                + ":" + socket.getPort() + " -> " + msg.toString().strip()
                        );
                    } else if (recebido instanceof Topico) {
                        Topico topico = (Topico) recebido;
                        todosTopicos.put(topico.getHashtag(), topico);

                        System.out.println("Topico criado: " + topico.getHashtag());
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

                    System.out.println("Mensagem reenviada a todos os clientes: " + recebido.toString().strip());

                    for (ObjectOutputStream cliente : clientesDesconectados) {
                        clientesConectados.remove(cliente);
                    }

                }

            } catch (IOException ex) {
                System.out.println("Cliente desconectado: " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
            } catch (ClassNotFoundException ex) {
                System.err.println("Erro na leitura do objeto serializado: " + ex);
            }
        }

    }

}
