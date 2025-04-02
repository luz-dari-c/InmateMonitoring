package Model;

import java.time.LocalDate;

public class PersonalDeControl extends Persona {

    private LocalDate fechaIngreso;
    private String turno;

    public PersonalDeControl(String nombre, String apellido, int edad,
            int id, String sexo, String nacionalidad, String identificacion,
            LocalDate fechaIngreso, String turno) {
        super(nombre, apellido, edad, id, sexo, nacionalidad, identificacion);
        this.fechaIngreso = fechaIngreso;
        this.turno = turno;
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
}
