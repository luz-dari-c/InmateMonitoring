/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Rol;
import Model.Usuario;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileReader;


public class UsuarioDAO {
    private static final String JSON_FILE = "C:\\Users\\gameV\\Desktop\\InmateMonitoring\\src\\Resources\\DATA\\usuarios.json";
    //Crea una variable estatica para la ubicacion de mi JSon
    
    public Usuario validarCredenciales (String email, String password){
        try (FileReader reader = new FileReader(JSON_FILE)){
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            JsonArray usuariosJson = jsonObject.getAsJsonArray("usuarios");
            
            for (int i = 0; i < usuariosJson.size(); i++) { //Tamaño del JSON
                JsonObject usuarioJson = usuariosJson.get(i).getAsJsonObject();
                
                String userEmail = usuarioJson.get("email").getAsString();//Hace oarte de usuarios
                String userPassword = usuarioJson.get("password").getAsString();
                
                if (userEmail.equals(email) && userPassword.equals(password)){
                    Rol rol = Rol.valueOf(usuarioJson.get("rol").getAsString()); //El enum son los roles y el rol de esta linea viene del JSON
                    return new  Usuario (userEmail, userPassword, rol, null);
                    
                }
                
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
        
    }
    
    public boolean esDirector (Usuario usuario){
        return usuario != null && usuario.getRoles() == Rol.DIRECTOR;
    }
    
    
}
