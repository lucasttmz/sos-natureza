package modelo;

import java.util.HashMap;
import java.util.Map;

public interface Comando {

    public static Map<String, Comando> comandos = new HashMap<>();

    public String executar();

    public static String executarComando(String comando) {
        if (comandos.containsKey(comando)) {
            Comando cmd = comandos.get(comando);
            return cmd.executar();
        }
        return comando;
    }

}
