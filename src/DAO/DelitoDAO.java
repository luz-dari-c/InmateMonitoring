package DAO;

import Model.Delito;
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

public class DelitoDAO {
   
    private static final String JSON_FILE = "C:\\Users\\ASUS\\Documents\\NetBeansProjects\\InmateMonitoring\\src\\Resources\\DATA\\delitos.json";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public List<Delito> cargarTodos() {
        File archivo = new File(JSON_FILE);
        
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
                try (FileWriter writer = new FileWriter(JSON_FILE)) {
                    gson.toJson(new ArrayList<Delito>(), writer);
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
            Type tipoListaDelitos = new TypeToken<ArrayList<Delito>>(){}.getType();
            List<Delito> delitos = gson.fromJson(reader, tipoListaDelitos);
            
            return delitos != null ? delitos : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Error al leer archivo JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void guardarDelito(Delito delito) {
        List<Delito> delitos = cargarTodos();
        delitos.add(delito);
        guardarTodos(delitos);
    }

    public void guardarTodos(List<Delito> delitos) {
        try (FileWriter writer = new FileWriter(JSON_FILE)) {
            gson.toJson(delitos, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar en archivo JSON: " + e.getMessage());
        }
    }

    public Delito buscarPorCodigo(int codigo) {
        List<Delito> delitos = cargarTodos();
        for (Delito delito : delitos) {
            if (delito.getCodigo() == codigo) {  
                return delito;
            }
        }
        return null;
    }

    public void actualizarDelito(Delito delitoActualizado) {
        List<Delito> delitos = cargarTodos();
        for (int i = 0; i < delitos.size(); i++) {
            if (delitos.get(i).getCodigo() == delitoActualizado.getCodigo()) { 
                delitos.set(i, delitoActualizado);
                break;
            }
        }
        guardarTodos(delitos);
    }

    public void eliminarDelito(int codigo) {
        List<Delito> delitos = cargarTodos();
        delitos.removeIf(delito -> delito.getCodigo() == codigo);  
        guardarTodos(delitos);
    }
}