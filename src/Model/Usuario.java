package Model;

public class Usuario {
    private String email;
    private String contraseña;
    private Rol rol;
    private Persona persona;

    // Constructor principal
    public Usuario(String email, String contraseña, Rol rol) {
        this(email, contraseña, rol, null);
    }

    // Constructor completo
    public Usuario(String email, String contraseña, Rol rol, Persona persona) {
        this.email = email;
        this.contraseña = contraseña;
        this.rol = rol;
        this.persona = persona;
    }

    // Getters
    public String getEmail() {
        return email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public Rol getRol() {
        return rol;
    }

    public Persona getPersona() {
        return persona;
    }

    // Setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
}