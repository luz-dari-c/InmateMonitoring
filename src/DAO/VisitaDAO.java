package DAO;

import Model.Visita;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class VisitaDAO {

    private static final String JSON_FILE = "C:\\Users\\nicol\\OneDrive\\Escritorio\\InmateMonitoring\\src\\Resources\\DATA\\visitas.json";
    private Gson gson;

    public VisitaDAO() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                .create();
    }

    private static class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
        private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        @Override
        public JsonElement serialize(LocalDate date, Type type, JsonSerializationContext context) {
            return date == null ? JsonNull.INSTANCE : new JsonPrimitive(date.format(formatter));
        }

        @Override
        public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext context) {
            return json.isJsonNull() ? null : LocalDate.parse(json.getAsString(), formatter);
        }
    }

    private static class LocalTimeAdapter implements JsonSerializer<LocalTime>, JsonDeserializer<LocalTime> {

        private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        @Override
        public JsonElement serialize(LocalTime time, Type type, JsonSerializationContext context) {
            return time == null ? JsonNull.INSTANCE : new JsonPrimitive(time.format(formatter));
        }

        @Override
        public LocalTime deserialize(JsonElement json, Type type, JsonDeserializationContext context) {
            return json.isJsonNull() ? null : LocalTime.parse(json.getAsString(), formatter);
        }
    }

    public List<Visita> cargarTodas() {
        File archivo = new File(JSON_FILE);

        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
                guardarTodas(new ArrayList<>());
                return new ArrayList<>();
            } catch (IOException e) {
                System.err.println("Error al crear archivo JSON: " + e.getMessage());
                return new ArrayList<>();
            }
        }

        if (archivo.length() == 0) {
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(JSON_FILE)) {
            Type tipoListaVisita = new TypeToken<ArrayList<Visita>>() {
            }.getType();
            List<Visita> visitas = gson.fromJson(reader, tipoListaVisita);
            return visitas != null ? visitas : new ArrayList<>();
        } catch (JsonSyntaxException | IOException e) {
            System.err.println("Error al leer archivo JSON: " + e.getMessage());
            guardarTodas(new ArrayList<>());
            return new ArrayList<>();
        }
    }

    public void guardarVisita(Visita visita) {
        List<Visita> visitas = cargarTodas();
        visitas.add(visita);
        guardarTodas(visitas);
    }

    public void guardarTodas(List<Visita> visitas) {
        try (FileWriter writer = new FileWriter(JSON_FILE)) {
            gson.toJson(visitas, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar en archivo JSON: " + e.getMessage());
        }
    }
}
