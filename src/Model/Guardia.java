
package Model;


import java.time.LocalDate;

public class Guardia extends Persona {

    private String placa;
    private String rango;
    private String cargo;
    private LocalDate fechaIngreso;
    private String turno;
    private Seccion seccion;
    private String tipoDeContrato;

    public Guardia(String nombre, String apellido, int edad, int id, String sexo, String nacionalidad, String identificacion, String placa, String rango, String cargo, LocalDate fechaIngreso, String turno, Seccion seccion, String tipoDeContrato) {
        super(nombre, apellido, edad, id, sexo, nacionalidad, identificacion);
        this.placa = placa;
        this.rango = rango;
        this.cargo = cargo;
        this.fechaIngreso = fechaIngreso;
        this.turno = turno;
        this.seccion = seccion;
        this.tipoDeContrato = tipoDeContrato;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getRango() {
        return rango;
    }

    public void setRango(String rango) {
        this.rango = rango;
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

    public Seccion getSeccion() {
        return seccion;
    }

    public void setSeccion(Seccion seccion) {
        this.seccion = seccion;
    }

    public String getTipoDeContrato() {
        return tipoDeContrato;
    }

    public void setTipoDeContrato(String tipoDeContrato) {
        this.tipoDeContrato = tipoDeContrato;
    }
}
