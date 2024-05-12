package modelo;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validacao {
    
    public String mensagem;
    
    public boolean validarNome(String nome) {
        if (nome.length() < 3) {
            this.mensagem = "O nome de exibição deve ter pelo menos 3 caracteres";
            return false;
        }
        return true;
    }
    
    public boolean validarIp(String ip, String porta) {
        // Validar porta entre 1024 ~ 65535
        try {
            int numPorta = Integer.parseInt(porta.strip());
            if (numPorta < 1024 || numPorta > 65535) {
                throw new Exception();
            }
            
        } catch (Exception e) {
            this.mensagem = "Porta fora do intervalo válido";
            return false;
        }

        // Validar endereço entre 0~255.0~255.0~255.0~255
        String bloco = "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
        String endereco = bloco + "\\." + bloco + "\\." + bloco + "\\." + bloco;
        
        Pattern padraoCompilado = Pattern.compile(endereco);
        Matcher matcher = padraoCompilado.matcher(ip.strip());
        
        if (!matcher.matches()) {
            this.mensagem = "O IP está fora do intervalo válido\n";
            return false;
        }
        return true;
    }
    
    public boolean validarTopico(List<List<String>> todosTopicos, String nomeTopico) {
        // Mínimo de 3 caracteres
        if (nomeTopico.length() < 3) {
            this.mensagem = "Nome do tópico deve conter pelo menos 3 caracteres\n";
            return false;
        }

        // Nomes devem ser diferentes
        for (List<String> topico : todosTopicos) {
            if (nomeTopico.equalsIgnoreCase(topico.get(0)) || nomeTopico.equalsIgnoreCase("geral")) {
                this.mensagem = "Tópico com o mesmo nome já existe\n";
                return false;
            }
        }
        return true;
    }
}
