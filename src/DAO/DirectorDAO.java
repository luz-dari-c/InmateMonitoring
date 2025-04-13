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
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DirectorDAO {
    private static final String ARCHIVO_JSON = "C:\\Users\\gameV\\Desktop\\InmateMonitoring\\src\\Resources\\DATA\\guardias.json";
    private static final String IMAGENES_DIR = "C:\\Users\\gameV\\Desktop\\InmateMonitoring\\src\\Resources\\Images\\Guardias\\";
    private Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
        .create();

    public List<Guardia> cargarTodos() {
        try (Reader reader = new FileReader(ARCHIVO_JSON)) {
            Type tipoLista = new TypeToken<ArrayList<Guardia>>(){}.getType();
            List<Guardia> guardia = gson.fromJson(reader, tipoLista);
            return guardia != null ? guardia : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Error al cargar guardias: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public boolean guardarGuardia(Guardia guardia, File imagenSeleccionada) {
        if (guardia == null) {
            System.err.println("El guardia no puede ser nulo");
            return false;
        }

        List<Guardia> guardias = cargarTodos();

        if (imagenSeleccionada != null) {
            try {
                new File(IMAGENES_DIR).mkdirs(); // Crea el directorio si no existe
                String extension = getExtension(imagenSeleccionada.getName());
                String nombreArchivo = "guardia_" + guardia.getCedula() + extension;
                String rutaDestino = IMAGENES_DIR + nombreArchivo;

                Files.copy(imagenSeleccionada.toPath(),
                        new File(rutaDestino).toPath(),
                        StandardCopyOption.REPLACE_EXISTING);

                guardia.setFotoPath(rutaDestino);
            } catch (IOException e) {
                System.err.println("Error al guardar la imagen del guardia: " + e.getMessage());
                guardia.setFotoPath(null);
            }
        }

        guardias.add(guardia);
        guardarTodos(guardias);
        return true;
    }

    public void guardarTodos(List<Guardia> guardias) {
        try (FileWriter writer = new FileWriter(ARCHIVO_JSON)) {
            gson.toJson(guardias, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar guardias: " + e.getMessage());
        }
    }

    private String getExtension(String nombreArchivo) {
        int punto = nombreArchivo.lastIndexOf(".");
        return (punto >= 0) ? nombreArchivo.substring(punto) : "";
    }
}
