
package DAO;

import View.Guardia;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class DirectorDAO {
     private static final String ARCHIVO_JSON = "C:\\Users\\gameV\\Desktop\\InmateMonitoring\\src\\Resources\\DATA\\guardias.json";
    private Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
        .create();



    
    public List<Guardia> cargarTodos() {
        try (Reader reader = new FileReader(ARCHIVO_JSON)) {
            Type tipoLista = new TypeToken<ArrayList<Guardia>>(){}.getType();
            List<Guardia> guardia = gson.fromJson(reader, tipoLista);
            return guardia != null ? guardia : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("tienes eso malo y no se puede cargar: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public void guardarGuardia(Guardia guardia) {
        List<Guardia> guardias = cargarTodos();
        guardias.add(guardia);
        guardarTodos(guardias);
    }
    
    public void guardarTodos(List<Guardia> guardia) {
        try (FileWriter writer = new FileWriter(ARCHIVO_JSON)) {
            gson.toJson(guardia, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar médicos: " + e.getMessage());
        }
}
}
