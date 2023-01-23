package gestorpeticiones;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import numero.Numero;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;

public class GestorPeticionesHTTP {



    public static Numero comprobarNumero(String numero) throws Exception {

        numero = URLEncoder.encode(numero, StandardCharsets.UTF_8);
        String direccion = "https://api.elpais.com/ws/LoteriaNinoPremiados?n=" + numero;

        HttpClient httpClient = HttpClient.
                newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(direccion))
                .headers("Content-Type", "text/plain")
                .setHeader("User-Agent", "Mozilla/5.0")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String respuesta = response.body().replace("busqueda=", "");

        Gson gson = new Gson();
        JsonObject numeroObj = gson.fromJson(respuesta, JsonObject.class);

        Numero numeroADevolver = new Numero(numeroObj.get("numero").toString(),Integer.parseInt(numeroObj.get("premio").toString()));



        return numeroADevolver;

    }

    public static ArrayList<Numero> enviaNumerosPremiadosEnResumen() throws Exception{
        ArrayList<Numero> numerosPremiados = new ArrayList<>();

        String direccion = "https://api.elpais.com/ws/LoteriaNinoPremiados?n=resumen";

        HttpClient httpClient = HttpClient.
                newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(direccion))
                .headers("Content-Type", "text/plain")
                .setHeader("User-Agent", "Mozilla/5.0")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String respuesta = response.body().replace("premios=", "");

        Gson gson = new Gson();
        JsonObject numeroObj = gson.fromJson(respuesta, JsonObject.class);

        //Numero numeroADevolver = new Numero(numeroObj.get("numero").toString(),Integer.parseInt(numeroObj.get("premio").toString()));

        String gordoTexto = numeroObj.get("premio1").toString();
        Numero gordoNumero = GestorPeticionesHTTP.comprobarNumero(gordoTexto);

        numerosPremiados.add(gordoNumero);

        String segundoPremioTexto = numeroObj.get("premio2").toString();
        Numero segundoPremioNumero = GestorPeticionesHTTP.comprobarNumero(segundoPremioTexto);

        numerosPremiados.add(segundoPremioNumero);

        String tercerPremioTexto = numeroObj.get("premio3").toString();
        Numero tercerPremioNumero = GestorPeticionesHTTP.comprobarNumero(tercerPremioTexto);

        numerosPremiados.add(tercerPremioNumero);

        return numerosPremiados;
    }




}
