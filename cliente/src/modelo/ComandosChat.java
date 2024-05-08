package modelo;

import java.util.HashMap;
import java.util.Map;

public class ComandosChat {

    public Map<String, Comando> comandosMap = new HashMap<>();

    public ComandosChat() {
        comandosMap.put("/localizacao", new Geolocalizacao());
//       Exemplo: comandosMap.put("/oi", new Oi());

    }

    public String executar(String comando) {
        if (comandosMap.containsKey(comando)){
            Comando cmd = comandosMap.get(comando);
            return cmd.executar();
        }
        return comando;
    }
}
