package DAO;

import Model.Guardia;
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

public class GuardiaDAO {
    private static final String JSON_FILE = "C:\\Users\\nicol\\OneDrive\\Escritorio\\InmateMonitoring\\src\\Resources\\DATA\\guardias.json";

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public List<Guardia> cargarTodos() {
        File archivo = new File(JSON_FILE);

        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
                try (FileWriter writer = new FileWriter(JSON_FILE)) {
                    gson.toJson(new ArrayList<Guardia>(), writer);
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
            Type tipoListaGuardia = new TypeToken<ArrayList<Guardia>>(){}.getType();
            List<Guardia> guardias = gson.fromJson(reader, tipoListaGuardia);
            return guardias != null ? guardias : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Error al leer archivo JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void guardarGuardia(Guardia guardia) {
        List<Guardia> guardias = cargarTodos();
        guardias.add(guardia);
        guardarTodos(guardias);
    }

    public void guardarTodos(List<Guardia> guardias) {
        try (FileWriter writer = new FileWriter(JSON_FILE)) {
            gson.toJson(guardias, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar en archivo JSON: " + e.getMessage());
        }
    }
}
