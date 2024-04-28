package teste;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Cliente {

    private frmCliente frmC;
    private Socket cliente;
    private ObjectInputStream entrada;
    private ObjectOutputStream saida;

    public void conectar() throws IOException {
        cliente = new Socket("127.0.0.1", 12345);
        entrada = new ObjectInputStream(cliente.getInputStream());
        saida = new ObjectOutputStream(cliente.getOutputStream());
        
        Thread thread = new Thread(new ThreadMensagem(entrada));
        thread.start();
    }

    public void enviarMensagem(String mensagem) throws IOException, ClassNotFoundException {
        saida.writeObject(mensagem);
    }
    
    public void desconectar() throws IOException {
        entrada.close();
        saida.close();
        System.out.println("Conexão encerrada");
    }

    private class ThreadMensagem implements Runnable { 

        private final ObjectInputStream entrada;
        
        public ThreadMensagem(ObjectInputStream entrada) {
            this.entrada = entrada;
        }
        
        @Override
        public void run() {
            while (true) {
                try {
                    String msg = String.valueOf(entrada.readObject());
                    System.out.println("Mensagem recebida: "+msg);
                    frmC.mostrarMensagem(msg);
                } catch (IOException | ClassNotFoundException ex) {
                    System.err.println(ex);
                }
            }
        }
    }
    
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Cliente cliente = new Cliente();
        cliente.frmC = new frmCliente(cliente);
        cliente.frmC.setVisible(true);
        
    }
}
