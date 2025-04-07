package DAO;

import Model.Visita;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class VisitaDAO {
    private static final String JSON_FILE = "C:\\Users\\nicol\\OneDrive\\Escritorio\\InmateMonitoring\\src\\Resources\\DATA\\visitas.json";
    
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public List<Visita> cargarTodas() {
        File archivo = new File(JSON_FILE);
        
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
                try (FileWriter writer = new FileWriter(JSON_FILE)) {
                    gson.toJson(new ArrayList<Visita>(), writer);
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
            Type tipoListaVisita = new TypeToken<ArrayList<Visita>>(){}.getType();
            List<Visita> visitas = gson.fromJson(reader, tipoListaVisita);
            return visitas != null ? visitas : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Error al leer archivo JSON: " + e.getMessage());
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
