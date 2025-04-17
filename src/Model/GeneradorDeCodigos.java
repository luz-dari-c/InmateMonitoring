package Model;

import java.util.HashSet;
import java.util.Random;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.io.FileReader;

public class GeneradorDeCodigos {
    private static HashSet<String> numerosRegistroExistentes = new HashSet<>(); 
    private static HashSet<String> codigosExpedienteExistentes = new HashSet<>();
    private static HashSet<String> codigosVisitaExistentes = new HashSet<>();


    public static void cargarCodigosExistentes(String rutaArchivo) {
        try {

            com.google.gson.JsonParser parser = new com.google.gson.JsonParser();
            
            JsonObject datosPrison = parser.parse(new FileReader(rutaArchivo)).getAsJsonObject();

            numerosRegistroExistentes.clear();
            codigosExpedienteExistentes.clear();
            codigosVisitaExistentes.clear();

            if (datosPrison.has("presos")) {
                JsonArray presos = datosPrison.getAsJsonArray("presos");
                for (JsonElement preso : presos) {
                    JsonObject p = preso.getAsJsonObject();
                    numerosRegistroExistentes.add(p.get("numeroRegistro").getAsString());
                    codigosExpedienteExistentes.add(p.get("codigoExpediente").getAsString());
                    
                    if (p.has("visitas")) {
                        JsonArray visitas = p.getAsJsonArray("visitas");
                        for (JsonElement visita : visitas) {
                            codigosVisitaExistentes.add(visita.getAsJsonObject().get("codigoVisita").getAsString());
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error al cargar códigos existentes: " + e.getMessage());
        }
    }

    public static String generarNumeroRegistro() {
        String nuevoRegistro;
        do {
            int añoActual = java.time.LocalDate.now().getYear();
            int secuencia = new Random().nextInt(9000) + 1000;
            nuevoRegistro = String.format("REG-%d-%04d", añoActual, secuencia);
        } while (numerosRegistroExistentes.contains(nuevoRegistro));

        numerosRegistroExistentes.add(nuevoRegistro);
        return nuevoRegistro;
    }

    public static String generarCodigoExpediente() {
        String nuevoCodigo;
        do {
            nuevoCodigo = "EXP-" + generarRandomString(4) + "-" + generarRandomString(4);
        } while (codigosExpedienteExistentes.contains(nuevoCodigo));

        codigosExpedienteExistentes.add(nuevoCodigo);
        return nuevoCodigo;
    }

    public static String generarCodigoVisita() {
        String nuevoCodigo;
        do {
            String fecha = java.time.LocalDate.now().format(
                java.time.format.DateTimeFormatter.ofPattern("yy-MM-dd"));
            nuevoCodigo = "VIS-" + fecha + "-" + generarRandomString(4);
        } while (codigosVisitaExistentes.contains(nuevoCodigo));

        codigosVisitaExistentes.add(nuevoCodigo);
        return nuevoCodigo;
    }

    private static String generarRandomString(int longitud) {
        Random random = new Random();
        String caracteres = "ABCDEFGHJKLMNPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < longitud; i++) {
            sb.append(caracteres.charAt(random.nextInt(caracteres.length())));
        }
        
        return sb.toString();
    }
    
    

    public static HashSet<String> getNumerosRegistroExistentes() {
        return numerosRegistroExistentes;
    }

    public static HashSet<String> getCodigosExpedienteExistentes() {
        return codigosExpedienteExistentes;
    }

    public static HashSet<String> getCodigosVisitaExistentes() {
        return codigosVisitaExistentes;
    }
}
