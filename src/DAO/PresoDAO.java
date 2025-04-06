
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
 
    private static final String JSON_FILE = "C:\\Users\\ASUS\\Documents\\NetBeansProjects\\InmateMonitoring\\src\\Resources\\DATA\\presos.json";
    
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
                try (FileWriter writer = new FileWriter(JSON_FILE)) {
                    gson.toJson(new ArrayList<Preso>(), writer);
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
            Type tipoListaPreso = new TypeToken<ArrayList<Preso>>(){}.getType();
            List<Preso> presos = gson.fromJson(reader, tipoListaPreso);
            
            return presos != null ? presos : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Error al leer archivo JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public boolean guardarPreso(Preso preso, File imagenSeleccionada) {
    // Validación básica
    if (preso == null || preso.getNombre() == null || preso.getNombre().isEmpty()) {
        System.err.println("Error: Datos del preso inválidos");
        return false;
    }

    List<Preso> presos = cargarTodos();
    
    // Verificar si el ID ya existe (para actualizaciones)
    if (presos.stream().anyMatch(p -> p.getId() == preso.getId())) {
        // Si es una actualización, eliminamos el preso existente
        presos.removeIf(p -> p.getId() == preso.getId());
    }
    
    // Manejo de la imagen
    if (imagenSeleccionada != null) {
        try {
            String directorioImagenes = "src/Resources/Images/";
            String nombreArchivo = "preso_" + preso.getId() + "_" + 
                                 System.currentTimeMillis() + 
                                 getExtension(imagenSeleccionada.getName());
            
            File carpetaImagenes = new File(directorioImagenes);
            if (!carpetaImagenes.exists()) {
                carpetaImagenes.mkdirs();
            }
            
            String rutaDestino = directorioImagenes + nombreArchivo;
            Files.copy(imagenSeleccionada.toPath(), 
                      new File(rutaDestino).toPath(), 
                      StandardCopyOption.REPLACE_EXISTING);
            
            preso.setFotoPath(rutaDestino);
        } catch (IOException e) {
            System.err.println("Error al guardar la imagen: " + e.getMessage());
            preso.setFotoPath(null);
        }
    }

    // Añadir el preso a la lista
    presos.add(preso);
    
    // Guardar la lista actualizada
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
        }
    }

    
    
    
    
    
}
