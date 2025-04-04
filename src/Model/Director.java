package Model;

public class Director extends Persona {

    private String cargo;

    public Director(String nombre, String apellido, int edad, int id, String sexo, String nacionalidad, String identificacion, String cargo) {
        super(nombre, apellido, edad, id, sexo, nacionalidad, identificacion);
        this.cargo = cargo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    
    
}
