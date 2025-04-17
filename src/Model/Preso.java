package Model;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Preso extends Persona {

    private float estatura;
    private float peso;
    private List<Delito> delitos = new ArrayList<>();
    private ExpedienteJudicial expediente;
    private Sentencia sentencia;
    private String nivelDeSeguridad;
    private String seccionAsignada;
    private String condicion;
    private String celdaAsignada;
    private boolean enAislamiento = false;
    private String nivelDeRiesgo;
    private int numeroDeVisitas = 0;
    private String grupoSanguineo;
    private String fotoPath;

    public Preso(String nombre, String apellido, int edad, int id, String sexo, String nacionalidad,
                 String identificacion, float estatura, float peso, List<Delito> delitos,Sentencia sentencia,
                  String nivelDeSeguridad,String seccionAsignada , String condicion,
                  String celdaAsignada, boolean enAislamiento, String nivelDeRiesgo,
                 int numeroDeVisitas, String grupoSanguineo, String fotoPath) {
        super(nombre, apellido, edad, id, sexo, nacionalidad, identificacion);
        this.estatura = estatura;
        this.peso = peso;
        this.delitos = delitos != null ? delitos : new ArrayList<>();
        this.nivelDeSeguridad = nivelDeSeguridad;
        this.seccionAsignada =seccionAsignada;
        this.condicion = condicion;
        this.sentencia = sentencia;
        this.celdaAsignada = celdaAsignada;
        this.enAislamiento = enAislamiento;
        this.nivelDeRiesgo = nivelDeRiesgo;
        this.numeroDeVisitas = numeroDeVisitas;
        this.grupoSanguineo = grupoSanguineo;
        this.fotoPath = fotoPath;
        this.expediente = crearExpedienteBasico();
    }

    private ExpedienteJudicial crearExpedienteBasico() {
        ExpedienteJudicial expediente = new ExpedienteJudicial();
        expediente.setCodigoExpediente(GeneradorDeCodigos.generarCodigoExpediente());
        expediente.setNumeroRegistro(GeneradorDeCodigos.generarNumeroRegistro());
        expediente.setFechaApertura(LocalDate.now());

        expediente.setDelitos(this.delitos); 
        expediente.setJuzgado("Por determinar");
        expediente.setNivelRiesgo(this.nivelDeRiesgo);
        return expediente;
    }

    private void actualizarExpediente() {
        if (this.expediente != null) {
            this.expediente.setDelitos(this.delitos); 
        }
    }

    public String getSeccionAsignada() {
        return seccionAsignada;
    }

    public void setSeccionAsignada(String seccionAsignada) {
        this.seccionAsignada = seccionAsignada;
    }

   
    
    public float getEstatura() { return estatura; }
    public void setEstatura(float estatura) { this.estatura = estatura; }

    public float getPeso() { return peso; }
    public void setPeso(float peso) { this.peso = peso; }

    public List<Delito> getDelitos() { return delitos; }
    public void setDelitos(List<Delito> delitos) {
        this.delitos = delitos != null ? delitos : new ArrayList<>();
        actualizarExpediente();
    }

    public void agregarDelito(Delito delito) {
        if (delito != null) {
            this.delitos.add(delito);
            actualizarExpediente();
        }
    }

    public boolean eliminarDelito(Delito delito) {
        boolean eliminado = this.delitos.remove(delito);
        if (eliminado) {
            actualizarExpediente();
        }
        return eliminado;
    }

    public ExpedienteJudicial getExpediente() { return expediente; }
    public void setExpediente(ExpedienteJudicial expediente) { this.expediente = expediente; }

    public Sentencia getSentencia() {
        return sentencia;
    }

    public void setSentencia(Sentencia sentencia) {
        this.sentencia = sentencia;
    }

  
    public String getNivelDeSeguridad() { return nivelDeSeguridad; }
    public void setNivelDeSeguridad(String nivelDeSeguridad) { this.nivelDeSeguridad = nivelDeSeguridad; }

    public String getCondicion() { return condicion; }
    public void setCondicion(String condicion) { this.condicion = condicion; }


    public String getCeldaAsignada() { return celdaAsignada; }
    public void setCeldaAsignada(String celdaAsignada) { this.celdaAsignada = celdaAsignada; }

    public boolean isEnAislamiento() { return enAislamiento; }
    public void setEnAislamiento(boolean enAislamiento) { this.enAislamiento = enAislamiento; }

    public String getNivelDeRiesgo() { return nivelDeRiesgo; }
    public void setNivelDeRiesgo(String nivelDeRiesgo) {
        this.nivelDeRiesgo = nivelDeRiesgo;
        if (this.expediente != null) {
            this.expediente.setNivelRiesgo(nivelDeRiesgo);
        }
    }

    public int getNumeroDeVisitas() { return numeroDeVisitas; }
    public void setNumeroDeVisitas(int numeroDeVisitas) {
        this.numeroDeVisitas = numeroDeVisitas;
        if (this.expediente != null) {
            this.expediente.setTotalVisitas(numeroDeVisitas);
        }
    }

    public String getGrupoSanguineo() { return grupoSanguineo; }
    public void setGrupoSanguineo(String grupoSanguineo) { this.grupoSanguineo = grupoSanguineo; }

    public void actualizarSentencia(int años, int meses, LocalDate fechaIngreso) {
    this.sentencia = new Sentencia(años, meses, fechaIngreso);
}

public String getSentenciaFormateada() {
    return sentencia != null ? sentencia.getSentenciaFormateada() : "No definida";
}

public int getSentenciaEnMeses() {
    return sentencia != null ? sentencia.getAños() * 12 + sentencia.getMeses() : 0;
}

    
    public String getFotoPath() { return fotoPath; }
    public void setFotoPath(String fotoPath) { this.fotoPath = fotoPath; }

    public String getNumeroRegistro() { return expediente.getNumeroRegistro(); }
    public String getCodigoExpediente() { return expediente.getCodigoExpediente(); }

    public String getDatosExpedienteBasico() {
        StringBuilder sb = new StringBuilder();
        sb.append("Registro: ").append(getNumeroRegistro())
          .append("\nExpediente: ").append(getCodigoExpediente())
          .append("\nDelitos:");
        for (Delito delito : delitos) {
            sb.append("\n- ").append(delito.getNombre())
              .append(" (Código: ").append(delito.getCodigo())
              .append(", Gravedad: ").append(delito.getGravedad())
              .append(", Fecha: ").append(delito.getFechaComision())
              .append(")");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Preso{" +
               "nombre='" + getNombre() + '\'' +
               ", apellido='" + getApellido() + '\'' +
               ", identificacion='" + getIdentificacion() + '\'' +
               ", delitos=" + delitos.size() +
               '}';
    }
} 
