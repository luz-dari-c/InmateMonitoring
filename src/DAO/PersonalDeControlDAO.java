
package DAO;

import View.PersonalDeControl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class PersonalDeControlDAO {
    private static final String JSON_FILE = "C:\\Users\\nicol\\OneDrive\\Escritorio\\InmateMonitoring\\src\\Resources\\DATA\\personalesDeControl.json";
    
   private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public List<PersonalDeControl> cargarTodos() {
        File archivo = new File(JSON_FILE);
        
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
                try (FileWriter writer = new FileWriter(JSON_FILE)) {
                    gson.toJson(new ArrayList<PersonalDeControl>(), writer);
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
            Type tipoListaPersonalDeControl = new TypeToken<ArrayList<PersonalDeControl>>(){}.getType();
            List<PersonalDeControl> personalesDeControl = gson.fromJson(reader, tipoListaPersonalDeControl);
            
            return personalesDeControl != null ? personalesDeControl : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Error al leer archivo JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void guardarPersonalDeControl(PersonalDeControl personalDeControl) {
        List<PersonalDeControl> personalesDeControl = cargarTodos();
        personalesDeControl.add(personalDeControl);
        guardarTodos(personalesDeControl);
    }

    public void guardarTodos(List<PersonalDeControl> personalesDeControl) {
        try (FileWriter writer = new FileWriter(JSON_FILE)) {
            gson.toJson(personalesDeControl, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar en archivo JSON: " + e.getMessage());
        }
    }

    
}
