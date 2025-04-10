package Model;

public class Guardia {
    private String nombres;
    private String apellidos;
    private String correos;
    private String cedula;
    private String cargo;
    private String turnos;
    private String nacionalidades;
    private String edades;

    public Guardia(String nombres, String apellidos, String correos, String cedula,
                   String cargo, String turnos, String nacionalidades, String edades) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correos = correos;
        this.cedula = cedula;
        this.cargo = cargo;
        this.turnos = turnos;
        this.nacionalidades = nacionalidades;
        this.edades = edades;
    }

    // getters y setters si los necesitas

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreos() {
        return correos;
    }

    public void setCorreos(String correos) {
        this.correos = correos;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getTurnos() {
        return turnos;
    }

    public void setTurnos(String turnos) {
        this.turnos = turnos;
    }

    public String getNacionalidades() {
        return nacionalidades;
    }

    public void setNacionalidades(String nacionalidades) {
        this.nacionalidades = nacionalidades;
    }

    public String getEdades() {
        return edades;
    }

    public void setEdades(String edades) {
        this.edades = edades;
    }
    
}
