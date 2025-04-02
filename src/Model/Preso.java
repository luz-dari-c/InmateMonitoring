package Model;

import java.time.LocalDate;

public class Preso extends Persona {

    private float estatura;
    private float peso;
    private Delito delito;
    private byte numeroDeExpediente;
    private LocalDate fechaIngreso;
    private LocalDate fechaSalida;
    private String nivelDeSeguridad;
    private String condicion;
    private byte sentencia;
    private String celdaAsignada;
    private boolean enAislamiento;
    private String nivelDeRiesgo;
    private int numeroDeVisitas;
    private String grupoSanguineo;

    public Preso(String nombre, String apellido, int edad, int id, String sexo, String nacionalidad,
            String identificacion, float estatura, float peso, Delito delito, byte numeroDeExpediente,
            LocalDate fechaIngreso, LocalDate fechaSalida, String nivelDeSeguridad, String condicion,
            byte sentencia, String celdaAsignada, boolean enAislamiento, String nivelDeRiesgo,
            int numeroDeVisitas, String grupoSanguineo) {
        super(nombre, apellido, edad, id, sexo, nacionalidad, identificacion);
        this.estatura = estatura;
        this.peso = peso;
        this.delito = delito;
        this.numeroDeExpediente = numeroDeExpediente;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
        this.nivelDeSeguridad = nivelDeSeguridad;
        this.condicion = condicion;
        this.sentencia = sentencia;
        this.celdaAsignada = celdaAsignada;
        this.enAislamiento = enAislamiento;
        this.nivelDeRiesgo = nivelDeRiesgo;
        this.numeroDeVisitas = numeroDeVisitas;
        this.grupoSanguineo = grupoSanguineo;
    }

    public float getEstatura() {
        return estatura;
    }

    public void setEstatura(float estatura) {
        this.estatura = estatura;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public Delito getDelito() {
        return delito;
    }

    public void setDelito(Delito delito) {
        this.delito = delito;
    }

    public byte getNumeroDeExpediente() {
        return numeroDeExpediente;
    }

    public void setNumeroDeExpediente(byte numeroDeExpediente) {
        this.numeroDeExpediente = numeroDeExpediente;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getNivelDeSeguridad() {
        return nivelDeSeguridad;
    }

    public void setNivelDeSeguridad(String nivelDeSeguridad) {
        this.nivelDeSeguridad = nivelDeSeguridad;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    public byte getSentencia() {
        return sentencia;
    }

    public void setSentencia(byte sentencia) {
        this.sentencia = sentencia;
    }

    public String getCeldaAsignada() {
        return celdaAsignada;
    }

    public void setCeldaAsignada(String celdaAsignada) {
        this.celdaAsignada = celdaAsignada;
    }

    public boolean isEnAislamiento() {
        return enAislamiento;
    }

    public void setEnAislamiento(boolean enAislamiento) {
        this.enAislamiento = enAislamiento;
    }

    public String getNivelDeRiesgo() {
        return nivelDeRiesgo;
    }

    public void setNivelDeRiesgo(String nivelDeRiesgo) {
        this.nivelDeRiesgo = nivelDeRiesgo;
    }

    public int getNumeroDeVisitas() {
        return numeroDeVisitas;
    }

    public void setNumeroDeVisitas(int numeroDeVisitas) {
        this.numeroDeVisitas = numeroDeVisitas;
    }

    public String getGrupoSanguineo() {
        return grupoSanguineo;
    }

    public void setGrupoSanguineo(String grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
    }
}
