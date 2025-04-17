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
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class VisitanteDAO {

    private static final String JSON_FILE = "C:\\Users\\nicol\\OneDrive\\Escritorio\\InmateMonitoring\\src\\Resources\\DATA\\visitantes.json";
    private static final String IMAGES_DIR = "C:\\Users\\nicol\\OneDrive\\Escritorio\\InmateMonitoring\\src\\Resources\\Images\\";

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
            Type tipoListaVisitante = new TypeToken<ArrayList<Visitante>>() {
            }.getType();
            List<Visitante> personalesDeControl = gson.fromJson(reader, tipoListaVisitante);

            return personalesDeControl != null ? personalesDeControl : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Error al leer archivo JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public boolean guardarVisitante(Visitante visitante, File imagenSeleccionada) {
        if (visitante == null) {
            System.err.println("Error: El visitante no puede ser nulo");
            return false;
        }

        List<Visitante> visitantes = cargarTodos();

        if (visitante.getId() == 0) {
            int nuevoId = obtenerProximoId(visitantes); 
            visitante.setId(nuevoId);
        }

        if (imagenSeleccionada != null) {
            try {
                String nombreArchivo = "visitante_" + visitante.getId() + getExtension(imagenSeleccionada.getName());
                String rutaDestino = IMAGES_DIR + nombreArchivo;

                new File(IMAGES_DIR).mkdirs();

                Files.copy(imagenSeleccionada.toPath(),
                        new File(rutaDestino).toPath(),
                        StandardCopyOption.REPLACE_EXISTING);

                visitante.setFotoPath(rutaDestino);
            } catch (IOException e) {
                System.err.println("Error al guardar la imagen: " + e.getMessage());
                visitante.setFotoPath(null);
            }
        }

        boolean existe = false;
        for (int i = 0; i < visitantes.size(); i++) {
            if (visitantes.get(i).getId() == visitante.getId()) { 
                visitantes.set(i, visitante);
                existe = true;
                break;
            }
        }

        if (!existe) {
            visitantes.add(visitante);
        }

        try {
            guardarTodos(visitantes);
            return true;
        } catch (Exception e) {
            System.err.println("Error al guardar la lista de visitantes: " + e.getMessage());
            return false;
        }
    }

    private int obtenerProximoId(List<Visitante> visitantes) {
        if (visitantes.isEmpty()) {
            return 1;
        }
        return visitantes.stream()
                .mapToInt(Visitante::getId)
                .max()
                .orElse(0) + 1;
    }

    private String getExtension(String filename) {
        int lastDot = filename.lastIndexOf('.');
        return (lastDot == -1) ? "" : filename.substring(lastDot);
    }

    public Visitante buscarVisitantePorIdentificacion(String identificacion) {
        List<Visitante> visitantes = cargarTodos();
        for (Visitante visitante : visitantes) {
            if (visitante.getIdentificacion().equals(identificacion)) {
                return visitante;
            }
        }
        return null;
    }

    public boolean eliminarVisitante(String identificacion) {
        List<Visitante> visitantes = cargarTodos();
        boolean removed = visitantes.removeIf(v -> v.getIdentificacion().equals(identificacion));
        if (removed) {
            guardarTodos(visitantes);
        }
        return removed;
    }

    public void guardarTodos(List<Visitante> visitantes) {
        try (FileWriter writer = new FileWriter(JSON_FILE)) {
            gson.toJson(visitantes, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar en archivo JSON: " + e.getMessage());
            throw new RuntimeException("Error al guardar los datos", e);
        }
    }

}
