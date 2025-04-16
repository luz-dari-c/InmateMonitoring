package Model;

import java.time.LocalDate;

public class Oficial extends Persona {

    private String placa;
    private String cargo;
    private LocalDate fechaIngreso;
    private String turno;
    private String fotoPath; 

    public Oficial(String nombre, String apellido, int edad, int id, String sexo, String nacionalidad, String identificacion,
                   String placa, String cargo, LocalDate fechaIngreso, String turno, String fotoPath) {
        super(nombre, apellido, edad, id, sexo, nacionalidad, identificacion);
        this.placa = placa;
        this.cargo = cargo;
        this.fechaIngreso = fechaIngreso;
        this.turno = turno;
        this.fotoPath = fotoPath; 
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getFotoPath() {
        return fotoPath;
    }

    public void setFotoPath(String fotoPath) {
        this.fotoPath = fotoPath;
    }
}
