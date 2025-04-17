
package DAO;

import Model.OficialDeRegistro;
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
import java.io.Writer;
import java.lang.reflect.Type;
import java.time.LocalDate;
import javax.swing.JOptionPane;


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

    public OficialDeRegistro buscarOficialDeRegistroPorIdentificacion(String identificacion) {
        List<OficialDeRegistro> oficiales = cargarTodos();
        for (OficialDeRegistro oficial : oficiales) {
            if (oficial.getIdentificacion().equals(identificacion)) {
                return oficial;
            }
        }
        return null;

    }
    
    public boolean eliminarOficialDeRegistro(String numeroIdentificacion) {
        List<OficialDeRegistro> oficiales = cargarTodos();

        boolean eliminado = oficiales.removeIf(oficial
                -> oficial.getIdentificacion().equals(numeroIdentificacion));

        if (eliminado) {
            try (Writer writer = new FileWriter(JSON_FILE)) {
                gson.toJson(oficiales, writer);
                return true;
            } catch (IOException e) {
                System.err.println("Error al guardar los cambios después de eliminar: " + e.getMessage());
                return false;
            }
        }

        return eliminado;
    }
   
    public boolean actualizarOficialDeRegistro(
    String identificacionOriginal,
    String nuevoNombre,
    String nuevoApellido,
    Integer nuevaEdad,
    String nuevoSexo,
    String nuevaNacionalidad,
    String nuevoNumeroPlaca,
    String nuevoRango,
    LocalDate nuevaFechaIngreso,
    String nuevoTurno
) {
    List<OficialDeRegistro> oficiales = cargarTodos();
    boolean encontrado = false;

    for (OficialDeRegistro oficial : oficiales) {
        if (oficial.getIdentificacion().equals(identificacionOriginal)) {
            encontrado = true;

            if (nuevoNombre != null) oficial.setNombre(nuevoNombre);
            if (nuevoApellido != null) oficial.setApellido(nuevoApellido);
            if (nuevaEdad != null) oficial.setEdad(nuevaEdad);
            if (nuevoSexo != null) oficial.setSexo(nuevoSexo);
            if (nuevaNacionalidad != null) oficial.setNacionalidad(nuevaNacionalidad);
            if (nuevoNumeroPlaca != null) oficial.setNumeroPlaca(nuevoNumeroPlaca);
            if (nuevoRango != null) oficial.setRango(nuevoRango);
            if (nuevaFechaIngreso != null) oficial.setFechaIngreso(nuevaFechaIngreso);
            if (nuevoTurno != null) oficial.setTurno(nuevoTurno);

            break;
        }
    }

    if (!encontrado) {
        JOptionPane.showMessageDialog(null,
            "Oficial de registro no encontrado: " + identificacionOriginal,
            "Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    return guardarCambios(oficiales);
}

    private boolean guardarCambios(List<OficialDeRegistro> oficiales) {
        try {
            guardarTodos(oficiales);
            return true;
        } catch(Exception e) {
            System.err.println("Error al guardar: " + e.getMessage());
            return false;
        }
    }
    
    
}
