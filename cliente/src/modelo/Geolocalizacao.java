package modelo;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Geolocalizacao implements Comando {

    @Override
    public String executar() {
        String linkGoogleMap = "";
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://ip-api.com/json/?fields=city,lat,lon"))
                    .GET()
                    .build();

            HttpClient cliente = HttpClient.newBuilder().build();

            HttpResponse<String> resposta = cliente.send(request, HttpResponse.BodyHandlers.ofString());
            if (resposta.statusCode() == 200) {
                linkGoogleMap = extrairDados(resposta.body());
            } else {
                System.out.println("Erro na API");
                linkGoogleMap = "Erro";
            }

        } catch (IOException | URISyntaxException | InterruptedException e) {
            e.printStackTrace();
        }

        return linkGoogleMap;
    }

    private String extrairDados(String jsonString) {
        int cidadeIndex = jsonString.indexOf("\"city\":") + "\"city\":".length();
        int cidadeEndIndex = jsonString.indexOf(",", cidadeIndex);
        String cidade = jsonString.substring(cidadeIndex, cidadeEndIndex).replaceAll("\"", "");

        int latIndex = jsonString.indexOf("\"lat\":") + "\"lat\":".length();
        int latEndIndex = jsonString.indexOf(",", latIndex);
        double lat = Double.parseDouble(jsonString.substring(latIndex, latEndIndex));

        int lonIndex = jsonString.indexOf("\"lon\":") + "\"lon\":".length();
        int lonEndIndex = jsonString.indexOf("}", lonIndex);
        double lon = Double.parseDouble(jsonString.substring(lonIndex, lonEndIndex));

        // Imprime os valores extraídos
        System.out.println("Cidade: " + cidade);
        System.out.println("Latitude: " + lat);
        System.out.println("Longitude: " + lon);

        String link = "https://www.google.com/maps/place/" + String.valueOf(lat) + "," + String.valueOf(lon);
        
        return "<a href='"+ link +"'>"+link+"</a>";
    }
    
}
