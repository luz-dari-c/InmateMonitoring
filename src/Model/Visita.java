package Model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Visita {

    private int id;
    private LocalDate fechaVisita;
    private LocalTime horaVisita;
    private String duracionVisita;
    private String tipoVisita;
    private String lugarVisita;
    private Preso preso;
    private List<Visitante> visitantes;
    private static int ultimoId = 0;

    public Visita(int id, LocalDate fechaVisita, LocalTime horaVisita, String duracionVisita, String tipoVisita, String lugarVisita, Preso preso, List<Visitante> visitantes) {
        this.id = ++ultimoId;
        this.fechaVisita = fechaVisita;
        this.horaVisita = horaVisita;
        this.duracionVisita = duracionVisita;
        this.tipoVisita = tipoVisita;
        this.lugarVisita = lugarVisita;
        this.preso = preso;
        this.visitantes = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Preso getPreso() {
        return preso;
    }

    public void setPreso(Preso preso) {
        this.preso = preso;
    }

    public List<Visitante> getVisitantes() {
        return visitantes;
    }

    public void setVisitantes(List<Visitante> visitantes) {
        this.visitantes = visitantes;
    }

    public static int getUltimoId() {
        return ultimoId;
    }

    public static void setUltimoId(int ultimoId) {
        Visita.ultimoId = ultimoId;
    }

}
