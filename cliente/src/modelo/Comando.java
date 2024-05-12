package modelo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public interface Comando {

    public static Map<String, Comando> comandos = new HashMap<>();

    public String executar(String parametro);

    public static String executarComando(String comando) {

        ArrayList<String> comandoParametro = new ArrayList<>(Arrays.asList(comando.split(" ")));
        if (comandoParametro.size() == 1) {
            comandoParametro.add("");
        }

        if (comandos.containsKey(comandoParametro.get(0))) {
            Comando cmd = comandos.get(comandoParametro.get(0));

            return cmd.executar(comandoParametro.get(1));
        }
        return comando;
    }

}
