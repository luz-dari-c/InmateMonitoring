package Model;

public class Guardia {
    private String nombre;
    private String apellido;
    private String cedula;
    private String cargo;
    private String turno;
    private int edad;
    private String nacionalidad;
    private String correo;
    private String fechaNacimiento;
    private String rutaImagen;

    // Constructor
    public Guardia(String nombre, String apellido, String cedula, String cargo, String turno, int edad, 
                   String nacionalidad, String correo, String fechaNacimiento, String rutaImagen) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.cargo = cargo;
        this.turno = turno;
        this.edad = edad;
        this.nacionalidad = nacionalidad;
        this.correo = correo;
        this.fechaNacimiento = fechaNacimiento;
        this.rutaImagen = rutaImagen;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }
    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }
    public String getNacionalidad() { return nacionalidad; }
    public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public String getRutaImagen() { return rutaImagen; }
    public void setRutaImagen(String rutaImagen) { this.rutaImagen = rutaImagen; }
    
}
