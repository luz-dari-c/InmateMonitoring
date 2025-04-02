package Model;

public class Usuario {

    private String email;
    private String contraseña;
    private Rol roles;
    private Persona persona;

    public Usuario(String email, String contraseña, Rol roles, Persona persona) {
        this.email = email;
        this.contraseña = contraseña;
        this.roles = roles;
        this.persona = persona;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Rol getRoles() {
        return roles;
    }

    public void setRoles(Rol roles) {
        this.roles = roles;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
    
    

}
