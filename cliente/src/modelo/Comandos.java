package modelo;

import com.maxmind.geoip2.*;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.model.InsightsResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Country;
import com.maxmind.geoip2.record.Location;

import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;

public class Comandos {

    private String cidadeNome;

    ArrayList<String> listaComandos = new ArrayList<>(Arrays.asList("/localizacao"));

    @SuppressWarnings("deprecation")
    public String getLocation(){

        WebServiceClient client = new WebServiceClient.Builder(1010491, "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").build();

        try {

                
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
            String ip = in.readLine();

            InetAddress ipAddress = InetAddress.getByName(ip);
            InsightsResponse response = client.insights(ipAddress);
            
            CityResponse responseCity = client.city(ipAddress);
            CountryResponse responsePais = client.country(ipAddress);
            Location localizacao = response.getLocation();

            City cidade = responseCity.getCity();
            Country pais = responsePais.getCountry();

            cidadeNome = localizacao.getLatitude() + "S " + localizacao.getLongitude() +"W - " + cidade.getName()+ "- " + pais.getName();
        
        } catch (GeoIp2Exception | IOException e) {

            e.printStackTrace();
        }

        return cidadeNome;

    }

}
