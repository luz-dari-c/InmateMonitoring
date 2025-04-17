package DAO;

import Model.CitaMedica;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CitaMedicaDAO {

    private static final String JSON_FILE = "C:\\Users\\nicol\\OneDrive\\Escritorio\\InmateMonitoring\\src\\Resources\\DATA\\citasMedicas.json";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public List<CitaMedica> cargarTodas() {
        File archivo = new File(JSON_FILE);

        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
                try (FileWriter writer = new FileWriter(JSON_FILE)) {
                    gson.toJson(new ArrayList<CitaMedica>(), writer);
                }
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
            Type tipoLista = new TypeToken<ArrayList<CitaMedica>>() {
            }.getType();
            List<CitaMedica> citas = gson.fromJson(reader, tipoLista);
            return citas != null ? citas : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Error al leer el archivo JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void guardarCita(CitaMedica cita) {
        List<CitaMedica> citas = cargarTodas();
        citas.add(cita);
        guardarTodas(citas);
    }

    public void guardarTodas(List<CitaMedica> citas) {
        try (FileWriter writer = new FileWriter(JSON_FILE)) {
            gson.toJson(citas, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar citas en JSON: " + e.getMessage());
        }
    }
}
