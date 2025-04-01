
package Model;


import java.time.LocalDate;

public class Sancion {

    private int idSancion;
    private String motivo;
    private LocalDate fechaSancion;
    private String descripcion;
    private String tipoSancion;
    private Preso preso;

    public Sancion(int idSancion, String motivo, LocalDate fechaSancion, String descripcion, String tipoSancion, Preso preso) {
    this.idSancion = idSancion;
    this.motivo = motivo;
    this.fechaSancion = fechaSancion;
    this.descripcion = descripcion;
    this.tipoSancion = tipoSancion;
    this.preso = preso;
    }

    public int getIdSancion() {
        return idSancion;
    }

    public void setIdSancion(int idSancion) {
        this.idSancion = idSancion;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public LocalDate getFechaSancion() {
        return fechaSancion;
    }

    public void setFechaSancion(LocalDate fechaSancion) {
        this.fechaSancion = fechaSancion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoSancion() {
        return tipoSancion;
    }

    public void setTipoSancion(String tipoSancion) {
        this.tipoSancion = tipoSancion;
    }

    public Preso getPreso() {
        return preso;
    }

    public void setPreso(Preso preso) {
        this.preso = preso;
    }
}
