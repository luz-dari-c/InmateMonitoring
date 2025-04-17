package Model;

public class Celda {

    private int idCelda;
    private String tipo;
    private int capacidadMaxima;
    private boolean ocupada;
    
    public Celda(){
        
    }

    public Celda(int idCelda, String tipo, int capacidadMaxima, boolean ocupada) {
        this.idCelda = idCelda;
        this.tipo = tipo;
        this.capacidadMaxima = capacidadMaxima;
        this.ocupada = ocupada;
    }

    public int getIdCelda() {
        return idCelda;
    }

    public void setIdCelda(int idCelda) {
        this.idCelda = idCelda;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }
    
     public String getNombreFormateado() {
    return "CELD-" + idCelda;
}
}
