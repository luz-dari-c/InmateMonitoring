package DAO;

import Model.Rol;
import Model.Usuario;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import java.io.FileReader;

public class UsuarioDAO {
    private static final String JSON_FILE = "src/Resources/DATA/usuarios.json";
    
    public Usuario validarCredenciales(String email, String password, Rol rolSeleccionado) {
        try (FileReader reader = new FileReader(JSON_FILE)) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            JsonArray usuariosJson = jsonObject.getAsJsonArray("usuarios");
            
            for (int i = 0; i < usuariosJson.size(); i++) {
                JsonObject usuarioJson = usuariosJson.get(i).getAsJsonObject();
                
                String userEmail = usuarioJson.get("email").getAsString();
                String userPassword = usuarioJson.get("password").getAsString();
                Rol userRol = Rol.valueOf(usuarioJson.get("rol").getAsString());
                
                if (userEmail.equalsIgnoreCase(email) && 
                    userPassword.equals(password) && 
                    userRol == rolSeleccionado) {
                    return new Usuario(userEmail, userPassword, userRol);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean esDirector(Usuario usuario) {
        return usuario != null && usuario.getRol() == Rol.DIRECTOR;
    }
}