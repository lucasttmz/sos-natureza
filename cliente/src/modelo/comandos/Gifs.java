package modelo.comandos;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;

public class Gifs implements Comando {

    @Override
    public String executar(String tag) {

        String linkGif = "";
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(
                            "https://api.giphy.com/v1/gifs/random?api_key=lhx3YJCp9oQdkUrE6a5WZwq8d0vcE8Sb&tag=" + tag))
                    .GET()
                    .build();

            HttpClient cliente = HttpClient.newBuilder().build();

            HttpResponse<String> resposta = cliente.send(request, HttpResponse.BodyHandlers.ofString());
            if (resposta.statusCode() == 200) {

                linkGif = extrairDados(resposta.body());

            } else {
                System.out.println("Erro na API");
                linkGif = "https://media.moddb.com/images/downloads/1/195/194908/MOSHED-2020-2-20-22-48-16.gif";
            }

        } catch (IOException | URISyntaxException | InterruptedException e) {
            e.printStackTrace();
        }

        return linkGif;
    }

    private String extrairDados(String jsonString) {

        ArrayList<String> listaJSON = new ArrayList<>(Arrays.asList(jsonString.split(",")));
        ArrayList<String> listaURL = new ArrayList<>();

        for (int i = 0; i < listaJSON.size(); i++) {

            if (listaJSON.get(i).contains("\"url\":\"")) {

                listaURL.add(listaJSON.get(i));
            }
        }

        return listaURL.get(3).replace("&ct=g\"}", "").replace("\"url\":\"", "");
    }

}
