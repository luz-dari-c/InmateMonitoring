package Model;

import java.time.LocalDate;
import java.util.List;

public class RegistroVisita {

    private int idRegistro;
    private LocalDate fechaRegistro;
    private List<Visita> visitas;

    public RegistroVisita(int idRegistro, LocalDate fechaRegistro, List<Visita> visitas) {
        this.idRegistro = idRegistro;
        this.fechaRegistro = fechaRegistro;
        this.visitas = visitas;
    }

    public int getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public List<Visita> getVisitas() {
        return visitas;
    }

    public void setVisitas(List<Visita> visitas) {
        this.visitas = visitas;
    }
    
}
