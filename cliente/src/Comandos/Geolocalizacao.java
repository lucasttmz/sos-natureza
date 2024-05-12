package Comandos;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import modelo.Comando;

public class Geolocalizacao implements Comando {

    @Override
    public String executar(String vazio) {
        String linkGoogleMap = "";
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://ip-api.com/line/?fields=lat,lon"))
                    .GET()
                    .build();

            HttpClient cliente = HttpClient.newBuilder().build();

            HttpResponse<String> resposta = cliente.send(request, HttpResponse.BodyHandlers.ofString());
            if (resposta.statusCode() == 200) {
                System.out.println(resposta.body());
                linkGoogleMap = extrairDados(resposta.body());
            } else {
                linkGoogleMap = "Erro: " + resposta.body();
            }

        } catch (IOException | URISyntaxException | InterruptedException e) {
            e.printStackTrace();
        }

        return linkGoogleMap;
    }

    private String extrairDados(String dados) {
        String[] linhas = dados.split("\\n");
        String link = "https://www.google.com/maps/place/" + String.valueOf(linhas[0]) + "," + String.valueOf(linhas[1]);
        
        return "<a href='"+ link +"'>"+ link +"</a>";
    }
    
}
