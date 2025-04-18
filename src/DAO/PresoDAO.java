package DAO;

import DAO.DelitoDAO;
import DAO.CeldaDAO;
import Model.Celda;
import Model.ExpedienteJudicial;

import Model.Delito;
import Model.Preso;
import Model.Sentencia;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.reflect.TypeToken;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import javax.swing.JOptionPane;

public class PresoDAO {

    private static final String JSON_FILE = "C:\\Users\\nicol\\OneDrive\\Escritorio\\InmateMonitoring\\src\\Resources\\DATA\\presos.json";
    private static final String IMAGES_DIR = "C:\\Users\\ASUS\\Documents\\NetBeansProjects\\InmateMonitoring\\src\\Resources\\Images\\";

    private Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();
    
    private static class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
    @Override
    public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(date.toString()); // "yyyy-mm-dd"
    }

    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) 
        throws JsonParseException {
        return LocalDate.parse(json.getAsString());
    }
}

    private int obtenerProximoId() {
        List<Preso> presos = cargarTodos();
        if (presos.isEmpty()) {
            return 1;
        }

        int maxId = presos.stream()
             .mapToInt(Preso::getId)
                .max()
                .orElse(0);

        return maxId + 1;
    }

    public List<Preso> cargarTodos() {
        File archivo = new File(JSON_FILE);
        archivo.getParentFile().mkdirs();

        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
                guardarTodos(new ArrayList<>());
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

        if (preso.getId() == 0) {
            preso.setId(obtenerProximoId());
        }

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

    public boolean eliminarPreso(String numeroIdentificacion) {
        List<Preso> presos = cargarTodos();

        boolean eliminado = presos.removeIf(preso
                -> preso.getIdentificacion().equals(numeroIdentificacion));

        if (eliminado) {
            try (Writer writer = new FileWriter(JSON_FILE)) {
                gson.toJson(presos, writer);
                return true;
            } catch (IOException e) {
                System.err.println("Error al guardar los cambios después de eliminar: " + e.getMessage());
                return false;
            }
        }

        return eliminado;
    }

  public boolean actualizarPresoConDelitos(
    String identificacionOriginal,
    String nuevoNombre,
    String nuevoApellido,
    Integer nuevaEdad,
    String nuevoSexo,
    String nuevaNacionalidad,
    Float nuevaEstatura,
    Float nuevoPeso,
    Sentencia nuevaSentencia,  
    String nuevoGrupoSanguineo,
    String nuevaSeccionAsignada,
    String nuevoNivelSeguridad,
    Boolean nuevoEnAislamiento,
    String nuevoNivelRiesgo,
    File nuevaFoto,
    List<Delito> nuevosDelitos) {

    List<Preso> presos = cargarTodos();
    boolean encontrado = false;

    for(Preso preso : presos) {
        if(preso.getIdentificacion().equals(identificacionOriginal)) {
            encontrado = true;

            if(nuevoNombre != null) preso.setNombre(nuevoNombre);
            if(nuevoApellido != null) preso.setApellido(nuevoApellido);
            if(nuevaEdad != null) preso.setEdad(nuevaEdad);
            if(nuevoSexo != null) preso.setSexo(nuevoSexo);
            if(nuevaNacionalidad != null) preso.setNacionalidad(nuevaNacionalidad);
            if(nuevaEstatura != null) preso.setEstatura(nuevaEstatura);
            if(nuevoPeso != null) preso.setPeso(nuevoPeso);
            if(nuevoGrupoSanguineo != null) preso.setGrupoSanguineo(nuevoGrupoSanguineo);

            // Datos judiciales (solo si al menos un campo tiene valor)
            if(nuevaSentencia != null || nuevaSeccionAsignada != null || 
               nuevoNivelSeguridad != null || nuevoEnAislamiento != null || 
               nuevoNivelRiesgo != null) {
                if(!actualizarDatosJudiciales(preso, nuevaSentencia, nuevaSeccionAsignada,
                                             nuevoNivelSeguridad, nuevoEnAislamiento,
                                             nuevoNivelRiesgo)) {
                    return false;
                }
            }

            if(nuevaFoto != null) actualizarFotoPreso(preso, nuevaFoto);
            if(nuevosDelitos != null && !nuevosDelitos.isEmpty()) {
                agregarDelitosAExpediente(preso, nuevosDelitos);
            }

            break;
        }
    }

    if(!encontrado) {
        JOptionPane.showMessageDialog(null, 
            "Preso no encontrado: " + identificacionOriginal, 
            "Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    return guardarCambios(presos);
}

 private boolean actualizarDatosJudiciales(Preso preso, Sentencia nuevaSentencia, String nuevaSeccion, 
                                      String nivelSeguridad, Boolean enAislamiento, 
                                      String nivelRiesgo) {
    CeldaDAO celdaDAO = new CeldaDAO();

    if(nuevaSeccion != null && !nuevaSeccion.equals(preso.getSeccionAsignada())) {
        Celda nuevaCelda = celdaDAO.asignarCeldaDisponible(nuevaSeccion);
        if(nuevaCelda == null) {
            JOptionPane.showMessageDialog(null, 
                "No hay celdas disponibles en la sección " + nuevaSeccion, 
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        celdaDAO.liberarCelda(preso.getCeldaAsignada());
        preso.setCeldaAsignada(nuevaCelda.getNombreFormateado());
        preso.setSeccionAsignada(nuevaSeccion);
    }

    if(nuevaSentencia != null) {
        preso.setSentencia(nuevaSentencia);
    }
    
    if(nivelSeguridad != null) preso.setNivelDeSeguridad(nivelSeguridad);
    if(enAislamiento != null) preso.setEnAislamiento(enAislamiento);
    if(nivelRiesgo != null) preso.setNivelDeRiesgo(nivelRiesgo);

    return true;
}
    private void actualizarFotoPreso(Preso preso, File nuevaFoto) {
        try {
            String extension = nuevaFoto.getName().substring(nuevaFoto.getName().lastIndexOf("."));
            String nombreArchivo = "preso_" + preso.getId() + extension;
            String rutaDestino = IMAGES_DIR + nombreArchivo;

            new File(IMAGES_DIR).mkdirs();
            Files.copy(nuevaFoto.toPath(), new File(rutaDestino).toPath(), StandardCopyOption.REPLACE_EXISTING);
            preso.setFotoPath(rutaDestino);
        } catch(IOException e) {
            System.err.println("Error al guardar imagen: " + e.getMessage());
        }
    }

    private void agregarDelitosAExpediente(Preso preso, List<Delito> nuevosDelitos) {
        ExpedienteJudicial expediente = preso.getExpediente();
        if(expediente == null) {
            expediente = new ExpedienteJudicial();
            preso.setExpediente(expediente);
        }

        expediente.getDelitos().addAll(nuevosDelitos);
        preso.getDelitos().addAll(nuevosDelitos);

        DelitoDAO delitoDAO = new DelitoDAO();
        for(Delito delito : nuevosDelitos) {
            delitoDAO.guardarDelito(delito);
        }
    }

    private boolean guardarCambios(List<Preso> presos) {
        try {
            guardarTodos(presos);
            return true;
        } catch(Exception e) {
            System.err.println("Error al guardar: " + e.getMessage());
            return false;
        }
    }
    
    
}