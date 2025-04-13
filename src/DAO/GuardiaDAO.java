package DAO;

import Model.Guardia;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class GuardiaDAO {
    private static final String RUTA_JSON = "C:\\Users\\gameV\\Desktop\\InmateMonitoring\\src\\Resources\\DATA\\guardias.json";
    private static final String RUTA_IMAGENES = "imagenes_guardias/";

    public GuardiaDAO() {
        File carpeta = new File(RUTA_IMAGENES);
        if (!carpeta.exists()) carpeta.mkdirs();
    }

    public boolean guardarGuardia(Guardia guardia, File imagenSeleccionada) {
        try {
            // Verificar si el guardia tiene datos vacíos antes de guardar
            if (guardia.getNombre().isEmpty() || guardia.getApellido().isEmpty() || guardia.getCedula().isEmpty()) {
                System.out.println("Los datos del guardia están incompletos, no se guardará.");
                return false;
            }

            // Copiar la imagen al directorio correspondiente
            String nombreImagen = guardia.getCedula() + "_" + imagenSeleccionada.getName();
            String rutaImagenFinal = RUTA_IMAGENES + nombreImagen;
            Files.copy(imagenSeleccionada.toPath(), Paths.get(rutaImagenFinal), StandardCopyOption.REPLACE_EXISTING);
            guardia.setRutaImagen(rutaImagenFinal);

            // Obtener la lista de guardias actuales desde el archivo JSON
            List<Guardia> lista = obtenerGuardias();

            // Añadir el nuevo guardia a la lista
            lista.add(guardia);

            // Convertir la lista a JSON con formato bonito
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(Collections.singletonMap("guardias", lista));

            // Escribir los datos JSON en el archivo
            FileWriter fw = new FileWriter(RUTA_JSON);
            fw.write(json);
            fw.close();

            System.out.println("Datos guardados correctamente en JSON: " + json);
            return true;

        } catch (IOException e) {
            System.out.println("Error al guardar el guardia: " + e.getMessage());
            return false;
        }
    }

    public List<Guardia> obtenerGuardias() {
        try {
            File archivo = new File(RUTA_JSON);
            if (!archivo.exists()) {
                System.out.println("El archivo no existe. Se creará uno nuevo.");
                return new ArrayList<>();
            }

            // Leer el archivo JSON
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            Gson gson = new Gson();

            // Parsear el JSON
            Map<String, List<Guardia>> datosGuardias = gson.fromJson(br, new TypeToken<Map<String, List<Guardia>>>(){}.getType());
            br.close();

            // Retornar la lista de guardias
            return datosGuardias != null && datosGuardias.containsKey("guardias") 
                    ? datosGuardias.get("guardias") 
                    : new ArrayList<>();
        } catch (Exception e) {
            System.out.println("Error al leer el archivo JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
  

}
