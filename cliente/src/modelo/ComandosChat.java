package modelo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import modelo.comandos.Comando;
import modelo.comandos.Geolocalizacao;
import modelo.comandos.Gifs;
import modelo.comandos.Telefones;

public class ComandosChat {

    public Map<String, Comando> comandos = new HashMap<>();

    public ComandosChat() {
        registrarComando("/localizacao", new Geolocalizacao());
        registrarComando("/telefones", new Telefones());
        registrarComando("/gif", new Gifs());
    }

    public String executarComando(String comando) {

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

    public void registrarComando(String comandoStr, Comando comando) {
        comandos.put(comandoStr, comando);
    }
}
