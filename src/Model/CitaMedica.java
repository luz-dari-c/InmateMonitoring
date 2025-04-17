package Model;

import java.time.LocalDate;

public class CitaMedica {

    private int id;
    private LocalDate fecha;
    private String motivo;
    private Guardia guardia; 
    private Preso preso;

    public CitaMedica(int id, LocalDate fecha, String motivo, Guardia guardia, Preso preso) {
        this.id = id;
        this.fecha = fecha;
        this.motivo = motivo;
        this.guardia = guardia;
        this.preso = preso;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Guardia getGuardia() {
        return guardia;
    }

    public void setGuardia(Guardia guardia) {
        this.guardia = guardia;
    }

    public Preso getPreso() {
        return preso;
    }

    public void setPreso(Preso preso) {
        this.preso = preso;
    }

}
