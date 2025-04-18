package DAO;

import Model.Guardia;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.*;
import javax.swing.JOptionPane;

public class GuardiaDAO {
    private static final String RUTA_JSON = "C:\\Users\\gameV\\Desktop\\InmateMonitoring\\src\\Resources\\DATA\\guardias.json";
    private static final String RUTA_IMAGENES = "src/Resources/imagenes_guardias/";

    public GuardiaDAO() {
        File carpeta = new File(RUTA_IMAGENES);
        if (!carpeta.exists())
            carpeta.mkdirs();
    }

    public boolean guardarGuardia(Guardia guardia, File imagenSeleccionada) {
        try {
            // Verificar si el guardia tiene datos vacíos antes de guardar
            if (guardia.getNombre().isEmpty() || guardia.getApellido().isEmpty() || guardia.getCedula().isEmpty()) {
                System.out.println("Los datos del guardia están incompletos, no se guardará.");
                return false;
            }

            // Validar que no exista un guardia con la misma cédula
            if (existeGuardiaConCedula(guardia.getCedula())) {
                System.out.println("Ya existe un guardia con la cédula: " + guardia.getCedula());
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
        List<Guardia> guardias = new ArrayList<>();
        BufferedReader br = null;

        try {
            File archivo = new File(RUTA_JSON);

            // Si el archivo no existe o está vacío, retornar lista vacía
            if (!archivo.exists() || archivo.length() == 0) {
                return guardias;
            }

            br = new BufferedReader(new FileReader(archivo));
            Gson gson = new Gson();

            // Usar TypeToken para el parseo correcto
            Type tipoListaGuardias = new TypeToken<Map<String, List<Guardia>>>() {
            }.getType();
            Map<String, List<Guardia>> datos = gson.fromJson(br, tipoListaGuardias);

            if (datos != null && datos.containsKey("guardias")) {
                guardias = datos.get("guardias");
            }

        } catch (Exception e) {
            System.err.println("Error al leer guardias: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException e) {
                System.err.println("Error al cerrar el reader: " + e.getMessage());
            }
        }

        return guardias;
    }

    public boolean actualizarGuardia(String cedulaOriginal, Guardia nuevosDatos, File nuevaImagen) {
        try {
            System.out.println("Buscando guardia con cédula: " + cedulaOriginal); // Debug

            List<Guardia> guardias = obtenerGuardias();
            System.out.println("Total guardias: " + guardias.size()); // Debug
            boolean encontrado = false;

            for (Guardia guardia : guardias) {
                if (guardia.getCedula().equals(cedulaOriginal)) {
                    encontrado = true;

                    // Actualizar solo los campos que no son null o vacíos
                    if (nuevosDatos.getNombre() != null && !nuevosDatos.getNombre().isEmpty()) {
                        guardia.setNombre(nuevosDatos.getNombre());
                    }
                    if (nuevosDatos.getApellido() != null && !nuevosDatos.getApellido().isEmpty()) {
                        guardia.setApellido(nuevosDatos.getApellido());
                    }
                    if (nuevosDatos.getCargo() != null && !nuevosDatos.getCargo().isEmpty()) {
                        guardia.setCargo(nuevosDatos.getCargo());
                    }
                    if (nuevosDatos.getTurno() != null && !nuevosDatos.getTurno().isEmpty()) {
                        guardia.setTurno(nuevosDatos.getTurno());
                    }
                    if (nuevosDatos.getEdad() > 0) { // Asumiendo que edad no puede ser 0 o negativo
                        guardia.setEdad(nuevosDatos.getEdad());
                    }
                    if (nuevosDatos.getNacionalidad() != null && !nuevosDatos.getNacionalidad().isEmpty()) {
                        guardia.setNacionalidad(nuevosDatos.getNacionalidad());
                    }
                    if (nuevosDatos.getCorreo() != null && !nuevosDatos.getCorreo().isEmpty()) {
                        guardia.setCorreo(nuevosDatos.getCorreo());
                    }
                    if (nuevosDatos.getFechaNacimiento() != null && !nuevosDatos.getFechaNacimiento().isEmpty()) {
                        guardia.setFechaNacimiento(nuevosDatos.getFechaNacimiento());
                    }

                    if (nuevosDatos.getFechaNacimiento() != null && !nuevosDatos.getFechaNacimiento().isEmpty()) {
                        guardia.setFechaNacimiento(nuevosDatos.getFechaNacimiento());
                    }

                    // Manejar la imagen si se proporciona una nueva
                    if (nuevaImagen != null) {
                        String nombreImagen = guardia.getCedula() + "_" + nuevaImagen.getName();
                        String rutaImagenFinal = RUTA_IMAGENES + nombreImagen;

                        // Eliminar la imagen anterior si existe
                        if (guardia.getRutaImagen() != null && !guardia.getRutaImagen().isEmpty()) {
                            try {
                                Files.deleteIfExists(Paths.get(guardia.getRutaImagen()));
                            } catch (IOException e) {
                                System.out.println("No se pudo eliminar la imagen anterior: " + e.getMessage());
                            }
                        }

                        // Copiar la nueva imagen
                        Files.copy(nuevaImagen.toPath(), Paths.get(rutaImagenFinal),
                                StandardCopyOption.REPLACE_EXISTING);
                        guardia.setRutaImagen(rutaImagenFinal);
                    }

                    break;
                }
            }

            if (!encontrado) {
                JOptionPane.showMessageDialog(null, "No se encontró un guardia con la cédula: " + cedulaOriginal,
                        "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Guardar los cambios en el JSON
            return guardarListaGuardias(guardias);

        } catch (IOException e) {
            System.out.println("Error al actualizar el guardia: " + e.getMessage());
            return false;
        }
    }

    private boolean guardarListaGuardias(List<Guardia> guardias) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(Collections.singletonMap("guardias", guardias));

        FileWriter fw = new FileWriter(RUTA_JSON);
        fw.write(json);
        fw.close();

        return true;
    }

    public Guardia buscarGuardiaPorCedula(String cedula) {
        List<Guardia> guardias = obtenerGuardias();
        for (Guardia guardia : guardias) {
            if (guardia.getCedula().equals(cedula)) {
                return guardia;
            }
        }
        return null;
    }

    public boolean existeGuardiaConCedula(String cedula) {
        List<Guardia> guardias = obtenerGuardias();
        for (Guardia guardia : guardias) {
            if (guardia.getCedula().equals(cedula)) {
                return true;
            }
        }
        return false;
    }

    public boolean eliminarGuardia(String cedula) {
        try {
            List<Guardia> guardias = obtenerGuardias();
            Iterator<Guardia> iterator = guardias.iterator();
            boolean encontrado = false;

            while (iterator.hasNext()) {
                Guardia guardia = iterator.next();
                if (guardia.getCedula().equals(cedula)) {
                    // Eliminar la imagen asociada si existe
                    if (guardia.getRutaImagen() != null && !guardia.getRutaImagen().isEmpty()) {
                        try {
                            Files.deleteIfExists(Paths.get(guardia.getRutaImagen()));
                        } catch (IOException e) {
                            System.err.println("Error al eliminar imagen: " + e.getMessage());
                        }
                    }
                    iterator.remove();
                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                return false;
            }

            // Guardar los cambios en el JSON
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(Collections.singletonMap("guardias", guardias));

            try (FileWriter writer = new FileWriter(RUTA_JSON)) {
                writer.write(json);
                return true;
            }
        } catch (Exception e) {
            System.err.println("Error al eliminar guardia: " + e.getMessage());
            return false;
        }
    }

}
