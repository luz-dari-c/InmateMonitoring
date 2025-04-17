package Model;

public class Persona {

    private String nombre;
    private String apellido;
    private int edad;
    private int id;
    private String sexo;
    private String nacionalidad;
    private String identificacion;
    private static int ultimoId = 0; 

    public Persona(String nombre, String apellido, int edad, int id, String sexo, String nacionalidad, String identificacion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.id = ++ultimoId;
        this.sexo = sexo;
        this.nacionalidad = nacionalidad;
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public static int getUltimoId() {
        return ultimoId;
    }

    public static void setUltimoId(int ultimoId) {
        Persona.ultimoId = ultimoId;
    }

}
