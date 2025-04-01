
package Model;


import java.time.LocalDate;
import java.time.LocalTime;

public class Visita {

    private int idVisita;
    private LocalDate fechaVisita;
    private LocalTime horaVisita;
    private String duracionVisita;
    private String tipoVisita;
    private String lugarVisita;
    private boolean visitaAprobada;
    private Preso preso;
    private Visitante visitante;

    public Visita(int idVisita, LocalDate fechaVisita, LocalTime horaVisita, String duracionVisita, String tipoVisita, String lugarVisita, boolean visitaAprobada, Preso preso, Visitante visitante) {
        this.idVisita = idVisita;
        this.fechaVisita = fechaVisita;
        this.horaVisita = horaVisita;
        this.duracionVisita = duracionVisita;
        this.tipoVisita = tipoVisita;
        this.lugarVisita = lugarVisita;
        this.visitaAprobada = visitaAprobada;
        this.preso = preso;
        this.visitante = visitante;
    }

    public int getIdVisita() {
        return idVisita;
    }

    public void setIdVisita(int idVisita) {
        this.idVisita = idVisita;
    }

    public LocalDate getFechaVisita() {
        return fechaVisita;
    }

    public void setFechaVisita(LocalDate fechaVisita) {
        this.fechaVisita = fechaVisita;
    }

    public LocalTime getHoraVisita() {
        return horaVisita;
    }

    public void setHoraVisita(LocalTime horaVisita) {
        this.horaVisita = horaVisita;
    }

    public String getDuracionVisita() {
        return duracionVisita;
    }

    public void setDuracionVisita(String duracionVisita) {
        this.duracionVisita = duracionVisita;
    }

    public String getTipoVisita() {
        return tipoVisita;
    }

    public void setTipoVisita(String tipoVisita) {
        this.tipoVisita = tipoVisita;
    }

    public String getLugarVisita() {
        return lugarVisita;
    }

    public void setLugarVisita(String lugarVisita) {
        this.lugarVisita = lugarVisita;
    }

    public boolean isVisitaAprobada() {
        return visitaAprobada;
    }

    public void setVisitaAprobada(boolean visitaAprobada) {
        this.visitaAprobada = visitaAprobada;
    }

    public Preso getPreso() {
        return preso;
    }

    public void setPreso(Preso preso) {
        this.preso = preso;
    }

    public Visitante getVisitante() {
        return visitante;
    }

    public void setVisitante(Visitante visitante) {
        this.visitante = visitante;
    }
}
