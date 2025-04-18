package DAO;

import Model.Oficial;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class OficialDAO {

    private static final String JSON_FILE = "C:\\Users\\nicol\\OneDrive\\Escritorio\\InmateMonitoring\\src\\Resources\\DATA\\oficiales.json"; 
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public List<Oficial> cargarTodos() {
        File archivo = new File(JSON_FILE);

        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
                guardarTodos(new ArrayList<>());
            } catch (IOException e) {
                System.err.println("Error al crear archivo JSON: " + e.getMessage());
                return new ArrayList<>();
            }
        }

        if (archivo.length() == 0) {
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(JSON_FILE)) {
            Type tipoLista = new TypeToken<ArrayList<Oficial>>() {
            }.getType();
            List<Oficial> oficiales = gson.fromJson(reader, tipoLista);
            return oficiales != null ? oficiales : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Error al leer archivo JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void guardarOficial(Oficial oficial) {
        List<Oficial> oficiales = cargarTodos();
        oficiales.add(oficial);
        guardarTodos(oficiales);
    }

    public void guardarTodos(List<Oficial> oficiales) {
        try (FileWriter writer = new FileWriter(JSON_FILE)) {
            gson.toJson(oficiales, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar en archivo JSON: " + e.getMessage());
        }
    }

    public void actualizarOficial(Oficial oficialActualizado) {
        List<Oficial> oficiales = cargarTodos();
        for (int i = 0; i < oficiales.size(); i++) {
            Oficial o = oficiales.get(i);
            if (o.getIdentificacion().equals(oficialActualizado.getIdentificacion())) {
                oficiales.set(i, oficialActualizado);
                guardarTodos(oficiales);
                return;
            }
        }
        System.out.println("Oficial no encontrado para actualizar.");
    }

    public void eliminarOficial(String identificacion) {
        List<Oficial> oficiales = cargarTodos();
        oficiales.removeIf(o -> o.getIdentificacion().equals(identificacion));
        guardarTodos(oficiales);
    }

    public Oficial buscarPorIdentificacion(String identificacion) {
        for (Oficial o : cargarTodos()) {
            if (o.getIdentificacion().equals(identificacion)) {
                return o;
            }
        }
        return null;
    }
}
