
package Model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Visitante {

    private String relacionConPreso;
    private String tipoVisita;
    private LocalDate fechaVisita;
    private LocalTime horaVisita;
    private String duracionVisita;
    private boolean requiereSupervision;

    public Visitante(String relacionConPreso, String tipoVisita, LocalDate fechaVisita, LocalTime horaVisita, String duracionVisita, boolean requiereSupervision) {
        this.relacionConPreso = relacionConPreso;
        this.tipoVisita = tipoVisita;
        this.fechaVisita = fechaVisita;
        this.horaVisita = horaVisita;
        this.duracionVisita = duracionVisita;
        this.requiereSupervision = requiereSupervision;
    }

    public String getRelacionConPreso() {
        return relacionConPreso;
    }

    public void setRelacionConPreso(String relacionConPreso) {
        this.relacionConPreso = relacionConPreso;
    }

    public String getTipoVisita() {
        return tipoVisita;
    }

    public void setTipoVisita(String tipoVisita) {
        this.tipoVisita = tipoVisita;
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

    public boolean isRequiereSupervision() {
        return requiereSupervision;
    }

    public void setRequiereSupervision(boolean requiereSupervision) {
        this.requiereSupervision = requiereSupervision;
    }
}
