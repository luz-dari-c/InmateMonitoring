package Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExpedienteJudicial {

    private String codigoExpediente;
    private String numeroRegistro;
    private LocalDate fechaApertura;
    private String estado;
    private List<Delito> delitos;
    private LocalDate fechaSentencia;
    private String juzgado;
    private int totalVisitas;
    private String nivelAdaptacion;
    private List<String> actividades;
    private String observacionesConducta;
    private String nivelRiesgo;

    public ExpedienteJudicial() {
        this.codigoExpediente = GeneradorDeCodigos.generarCodigoExpediente();
        this.numeroRegistro = GeneradorDeCodigos.generarNumeroRegistro();
        this.fechaApertura = LocalDate.now();
        this.delitos = new ArrayList<>();
        this.actividades = new ArrayList<>();
        this.estado = "Activo";
        this.nivelAdaptacion = "Por evaluar";
        this.observacionesConducta = "Ninguna";
    }

    public ExpedienteJudicial(String codigoExpediente, String numeroRegistro, LocalDate fechaApertura,
                               String estado, List<Delito> delitos, LocalDate fechaSentencia,
                               String juzgado, int totalVisitas, String nivelAdaptacion,
                               List<String> actividades, String observacionesConducta,
                               String nivelRiesgo) {
        this();
        if (codigoExpediente != null) this.codigoExpediente = codigoExpediente;
        if (numeroRegistro != null) this.numeroRegistro = numeroRegistro;
        if (fechaApertura != null) this.fechaApertura = fechaApertura;
        if (estado != null) this.estado = estado;
        if (delitos != null) this.delitos = delitos;
        this.fechaSentencia = fechaSentencia;
        this.juzgado = juzgado;
        this.totalVisitas = totalVisitas;
        if (nivelAdaptacion != null) this.nivelAdaptacion = nivelAdaptacion;
        if (actividades != null) this.actividades = actividades;
        if (observacionesConducta != null) this.observacionesConducta = observacionesConducta;
        this.nivelRiesgo = nivelRiesgo;
    }

    public String getCodigoExpediente() { return codigoExpediente; }
    public void setCodigoExpediente(String codigoExpediente) { this.codigoExpediente = codigoExpediente; }

    public String getNumeroRegistro() { return numeroRegistro; }
    public void setNumeroRegistro(String numeroRegistro) { this.numeroRegistro = numeroRegistro; }

    public LocalDate getFechaApertura() { return fechaApertura; }
    public void setFechaApertura(LocalDate fechaApertura) { this.fechaApertura = fechaApertura; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public List<Delito> getDelitos() { return delitos; }
    public void setDelitos(List<Delito> delitos) { this.delitos = delitos != null ? delitos : new ArrayList<>(); }

    public LocalDate getFechaSentencia() { return fechaSentencia; }
    public void setFechaSentencia(LocalDate fechaSentencia) { this.fechaSentencia = fechaSentencia; }

    public String getJuzgado() { return juzgado; }
    public void setJuzgado(String juzgado) { this.juzgado = juzgado; }

    public int getTotalVisitas() { return totalVisitas; }
    public void setTotalVisitas(int totalVisitas) { this.totalVisitas = totalVisitas; }

    public String getNivelAdaptacion() { return nivelAdaptacion; }
    public void setNivelAdaptacion(String nivelAdaptacion) { this.nivelAdaptacion = nivelAdaptacion; }

    public List<String> getActividades() { return actividades; }
    public void setActividades(List<String> actividades) {
        this.actividades = actividades != null ? actividades : new ArrayList<>();
    }

    public String getObservacionesConducta() { return observacionesConducta; }
    public void setObservacionesConducta(String observacionesConducta) { this.observacionesConducta = observacionesConducta; }

    public String getNivelRiesgo() { return nivelRiesgo; }
    public void setNivelRiesgo(String nivelRiesgo) { this.nivelRiesgo = nivelRiesgo; }
} 
