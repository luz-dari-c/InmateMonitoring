
package Model;

import java.time.LocalDate;

public class Delito {

    private int codigo;
    private String nombre;
    private String articuloLey;
    private String gravedad;
    private String descripcion;
    private LocalDate fechaComision;

    public Delito(int codigo, String nombre, String articuloLey, String gravedad, String descripcion, LocalDate fechaComision) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.articuloLey = articuloLey;
        this.gravedad = gravedad;
        this.descripcion = descripcion;
        this.fechaComision = fechaComision;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getArticuloLey() {
        return articuloLey;
    }

    public void setArticuloLey(String articuloLey) {
        this.articuloLey = articuloLey;
    }

    public String getGravedad() {
        return gravedad;
    }

    public void setGravedad(String gravedad) {
        this.gravedad = gravedad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaComision() {
        return fechaComision;
    }

    public void setFechaComision(LocalDate fechaComision) {
        this.fechaComision = fechaComision;
    }
}
