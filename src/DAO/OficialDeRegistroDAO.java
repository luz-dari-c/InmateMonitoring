
package DAO;

import View.OficialDeRegistro;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;


public class OficialDeRegistroDAO {
   
   private static final String JSON_FILE = "C:\\Users\\ASUS\\Documents\\NetBeansProjects\\InmateMonitoring\\src\\Resources\\oficialesDeRegistro.json";

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public List<OficialDeRegistro> cargarTodos() {
        File archivo = new File(JSON_FILE);
        
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
                try (FileWriter writer = new FileWriter(JSON_FILE)) {
                    gson.toJson(new ArrayList<OficialDeRegistro>(), writer);
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
            Type tipoListaOficialDeRegistro = new TypeToken<ArrayList<OficialDeRegistro>>(){}.getType();
            List<OficialDeRegistro> oficiales = gson.fromJson(reader, tipoListaOficialDeRegistro);
            
            return oficiales != null ? oficiales : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Error al leer archivo JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void guardarOficialDeRegistro(OficialDeRegistro oficial) {
        List<OficialDeRegistro> oficiales = cargarTodos();
        oficiales.add(oficial);
        guardarTodos(oficiales);
    }

    public void guardarTodos(List<OficialDeRegistro> oficiales) {
        try (FileWriter writer = new FileWriter(JSON_FILE)) {
            gson.toJson(oficiales, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar en archivo JSON: " + e.getMessage());
        }
    }

    
}
