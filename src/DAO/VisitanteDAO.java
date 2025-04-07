
package DAO;

import Model.Visitante;
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


public class VisitanteDAO {
    private static final String JSON_FILE = "C:\\Users\\nicol\\OneDrive\\Escritorio\\InmateMonitoring\\src\\Resources\\DATA\\visitantes.json";
    
   private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public List<Visitante> cargarTodos() {
        File archivo = new File(JSON_FILE);
        
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
                try (FileWriter writer = new FileWriter(JSON_FILE)) {
                    gson.toJson(new ArrayList<Visitante>(), writer);
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
            Type tipoListaVisitante = new TypeToken<ArrayList<Visitante>>(){}.getType();
            List<Visitante> personalesDeControl = gson.fromJson(reader, tipoListaVisitante);
            
            return personalesDeControl != null ? personalesDeControl : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Error al leer archivo JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void guardarVisitante(Visitante visitante) {
        List<Visitante> visitantes = cargarTodos();
        visitantes.add(visitante);
        guardarTodos(visitantes);
    }

    public void guardarTodos(List<Visitante> visitantes) {
        try (FileWriter writer = new FileWriter(JSON_FILE)) {
            gson.toJson(visitantes, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar en archivo JSON: " + e.getMessage());
        }
    }

    
    
    
}
