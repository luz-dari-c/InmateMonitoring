
package DAO;

import Model.Celda;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class CeldaDAO {

    private static final String JSON_FILE = "C:\\Users\\ASUS\\Documents\\NetBeansProjects\\InmateMonitoring\\src\\Resources\\DATA\\celdas.json";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public List<Celda> cargarTodas() {
        try (Reader reader = new FileReader(JSON_FILE)) {
            Type listType = new TypeToken<ArrayList<Celda>>() {}.getType();
            List<Celda> celdas = gson.fromJson(reader, listType);
            return celdas != null ? celdas : new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void guardarTodas(List<Celda> celdas) {
        try (Writer writer = new FileWriter(JSON_FILE)) {
            gson.toJson(celdas, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Celda asignarCeldaDisponible(String tipo) {
        List<Celda> celdas = cargarTodas();
        for (Celda celda : celdas) {
            if (!celda.isOcupada() && celda.getTipo().equalsIgnoreCase(tipo)) {
                celda.setOcupada(true);
                guardarTodas(celdas);
                return celda;
            }
        }
        return null; 
    }
    
    public int obtenerUltimoID() {
    List<Celda> celdas = cargarTodas(); 
    int maxId = 0;
    for (Celda celda : celdas) {
        if (celda.getIdCelda() > maxId) {
            maxId = celda.getIdCelda();
        }
    }
    return maxId;
}
    
    public void generarCeldas(String tipo, int cantidad, int capacidad) {
    List<Celda> todas = cargarTodas(); 
    int ultimoID = obtenerUltimoID();

    for (int i = 1; i <= cantidad; i++) {
        Celda celda = new Celda(ultimoID + i, tipo, capacidad, false);
        todas.add(celda);
    }

    guardarTodas(todas); 
}

public boolean liberarCelda(String nombreCelda) {
    List<Celda> celdas = cargarTodas();
    for (Celda celda : celdas) {
        if (celda.getNombreFormateado().equals(nombreCelda)) {
            celda.setOcupada(false);
            guardarTodas(celdas);
            return true;
        }
    }
    return false;
}
    
 
}
