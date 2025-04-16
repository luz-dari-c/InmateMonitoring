package Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExpedienteJudicial {

    private int codigoExpediente;
    private int numeroRegistro;
    private LocalDate fechaApertura;
    private String estado;
    private List<String> delitos;
    private LocalDate fechaSentencia;
    private String juzgado;
    private int totalVisitas;
    private String nivelAdaptacion;
    private List<String> actividades;
    private String observacionesConducta;
    private String nivelRiesgo;

    public ExpedienteJudicial() {
        this.delitos = new ArrayList<>();
        this.actividades = new ArrayList<>();
        this.estado = "Activo";
        this.nivelAdaptacion = "Por evaluar";
        this.observacionesConducta = "Ninguna";
    }

    public ExpedienteJudicial(int codigoExpediente, int numeroRegistro, LocalDate fechaApertura, String estado,
            List<String> delitos, LocalDate fechaSentencia, String juzgado, int totalVisitas,
            String nivelAdaptacion, List<String> actividades, String observacionesConducta,
            String nivelRiesgo) {
        this.codigoExpediente = codigoExpediente;
        this.numeroRegistro = numeroRegistro;
        this.fechaApertura = fechaApertura;
        this.estado = estado;
        this.delitos = delitos != null ? delitos : new ArrayList<>();
        this.fechaSentencia = fechaSentencia;
        this.juzgado = juzgado;
        this.totalVisitas = totalVisitas;
        this.nivelAdaptacion = nivelAdaptacion;
        this.actividades = actividades != null ? actividades : new ArrayList<>();
        this.observacionesConducta = observacionesConducta;
        this.nivelRiesgo = nivelRiesgo;
    }

    public int getCodigoExpediente() {
        return codigoExpediente;
    }

    public void setCodigoExpediente(int codigoExpediente) {
        this.codigoExpediente = codigoExpediente;
    }

    public int getNumeroRegistro() {
        return numeroRegistro;
    }

    public void setNumeroRegistro(int numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    public LocalDate getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(LocalDate fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<String> getDelitos() {
        return delitos;
    }

    public void setDelitos(List<String> delitos) {
        this.delitos = delitos != null ? delitos : new ArrayList<>();
    }

    public LocalDate getFechaSentencia() {
        return fechaSentencia;
    }

    public void setFechaSentencia(LocalDate fechaSentencia) {
        this.fechaSentencia = fechaSentencia;
    }

    public String getJuzgado() {
        return juzgado;
    }

    public void setJuzgado(String juzgado) {
        this.juzgado = juzgado;
    }

    public int getTotalVisitas() {
        return totalVisitas;
    }

    public void setTotalVisitas(int totalVisitas) {
        this.totalVisitas = totalVisitas;
    }

    public String getNivelAdaptacion() {
        return nivelAdaptacion;
    }

    public void setNivelAdaptacion(String nivelAdaptacion) {
        this.nivelAdaptacion = nivelAdaptacion;
    }

    public List<String> getActividades() {
        return actividades;
    }

    public void setActividades(List<String> actividades) {
        this.actividades = actividades != null ? actividades : new ArrayList<>();
    }

    public String getObservacionesConducta() {
        return observacionesConducta;
    }

    public void setObservacionesConducta(String observacionesConducta) {
        this.observacionesConducta = observacionesConducta;
    }

    public String getNivelRiesgo() {
        return nivelRiesgo;
    }

    public void setNivelRiesgo(String nivelRiesgo) {
        this.nivelRiesgo = nivelRiesgo;
    }
}
