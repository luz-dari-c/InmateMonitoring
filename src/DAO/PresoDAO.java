package DAO;

import Model.Preso;
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
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

public class PresoDAO {

    private static final String JSON_FILE = "C:\\Users\\nicol\\OneDrive\\Escritorio\\InmateMonitoring\\src\\Resources\\DATA\\presos.json";
    private static final String IMAGES_DIR = "C:\\Users\\ASUS\\Documents\\NetBeansProjects\\InmateMonitoring\\src\\Resources\\Images\\";

    private Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();

    public List<Preso> cargarTodos() {
        File archivo = new File(JSON_FILE);
        archivo.getParentFile().mkdirs();

        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
                guardarTodos(new ArrayList<>()); // Guardar lista vacía
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
            Type tipoListaPreso = new TypeToken<ArrayList<Preso>>() {
            }.getType();
            List<Preso> presos = gson.fromJson(reader, tipoListaPreso);

            return presos != null ? presos : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Error al leer archivo JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public boolean guardarPreso(Preso preso, File imagenSeleccionada) {
        if (preso == null) {
            System.err.println("Error: El preso no puede ser nulo");
            return false;
        }

        List<Preso> presos = cargarTodos();


        if (imagenSeleccionada != null) {
            try {
                String nombreArchivo = "preso_" + preso.getId() + getExtension(imagenSeleccionada.getName());
                String rutaDestino = IMAGES_DIR + nombreArchivo;

                new File(IMAGES_DIR).mkdirs();

                Files.copy(imagenSeleccionada.toPath(),
                        new File(rutaDestino).toPath(),
                        StandardCopyOption.REPLACE_EXISTING);

                preso.setFotoPath(rutaDestino);
            } catch (IOException e) {
                System.err.println("Error al guardar la imagen: " + e.getMessage());
                preso.setFotoPath(null);
            }
        }

        boolean existe = false;
        for (int i = 0; i < presos.size(); i++) {
            if (presos.get(i).getId() == preso.getId()) {
                presos.set(i, preso);
                existe = true;
                break;
            }
        }

        if (!existe) {
            presos.add(preso);
        }

        try {
            guardarTodos(presos);
            return true;
        } catch (Exception e) {
            System.err.println("Error al guardar la lista de presos: " + e.getMessage());
            return false;
        }
    }

   

    private String getExtension(String filename) {
        int lastDot = filename.lastIndexOf('.');
        return (lastDot == -1) ? "" : filename.substring(lastDot);
    }

    public void guardarTodos(List<Preso> presos) {
        try (FileWriter writer = new FileWriter(JSON_FILE)) {
            gson.toJson(presos, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar en archivo JSON: " + e.getMessage());
            throw new RuntimeException("Error al guardar los datos", e);
        }
    }

    public Preso buscarPresoPorIdentificacion(String identificacion) {
        List<Preso> presos = cargarTodos();
        for (Preso preso : presos) {
            if (preso.getIdentificacion().equals(identificacion)) {
                return preso;
            }

        }

        return null;

    }

}
