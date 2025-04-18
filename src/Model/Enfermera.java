package Model;

public class Enfermera extends Persona {

    private String correo;
    private String telefono;
    private String turno;
    private String especialidad;
    private String fechaIngreso;
    private String estado;
    private String usuario;
    private String contraseña;

    
    public Enfermera(String nombre, String apellido, int edad, int id, String sexo, String nacionalidad, String identificacion,
                     String correo, String telefono, String turno, String especialidad, String fechaIngreso,
                     String estado, String usuario, String contraseña) {
        super(nombre, apellido, edad, id, sexo, nacionalidad, identificacion);
        this.correo = correo;
        this.telefono = telefono;
        this.turno = turno;
        this.especialidad = especialidad;
        this.fechaIngreso = fechaIngreso;
        this.estado = estado;
        this.usuario = usuario;
        this.contraseña = contraseña;
    }

    
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
