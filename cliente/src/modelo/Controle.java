package modelo;

import java.util.HashMap;
import java.util.List;

public class Controle {

    private static final HashMap<Integer, Topico> todosTopicos = new HashMap<>();

    public void criarNovoTopico(List<String> infoTopico) {
        // Validar criação e retornar exceção em caso de falha
        
        Topico topico = new Topico(
            infoTopico.get(0),
            infoTopico.get(1),
            infoTopico.get(2)
        );
        todosTopicos.put(topico.getId(), topico);
    }
    
    public void excluirTopico(int id) {
        Topico removido = todosTopicos.remove(id);
        if (removido == null) {
            // Informa que não foi possível remover
        }
    }
}
