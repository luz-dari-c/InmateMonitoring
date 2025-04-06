
package Model;


public class Enfermera {
    private int idEnfermera;
    private String nombre;
    private String apellidos;
    private String identificacion;
    private String correo;
    private String telefono;
    private String turno;
    private String especialidad;
    private String fechaIngreso;
    private String estado;
    private String usuario;
    private String contraseña;

    // Constructor
    public Enfermera(int idEnfermera, String nombre, String apellidos, String identificacion, String correo,
                     String telefono, String turno, String especialidad, String fechaIngreso,
                     String estado, String usuario, String contraseña) {
        this.idEnfermera = idEnfermera;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.identificacion = identificacion;
        this.correo = correo;
        this.telefono = telefono;
        this.turno = turno;
        this.especialidad = especialidad;
        this.fechaIngreso = fechaIngreso;
        this.estado = estado;
        this.usuario = usuario;
        this.contraseña = contraseña;
    }

    // Getters y Setters
    public int getIdEnfermera() {
        return idEnfermera;
    }

    public void setIdEnfermera(int idEnfermera) {
        this.idEnfermera = idEnfermera;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
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
