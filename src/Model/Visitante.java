package Model;

public class Visitante extends Persona {

    private String relacionConPreso;
    private boolean requiereSupervision;
    private String fotoPath;

    public Visitante(String relacionConPreso, boolean requiereSupervision, String fotoPath, String nombre, String apellido, int edad, int id, String sexo, String nacionalidad, String identificacion) {
        super(nombre, apellido, edad, id, sexo, nacionalidad, identificacion);
        this.relacionConPreso = relacionConPreso;
        this.requiereSupervision = requiereSupervision;
        this.fotoPath = fotoPath;
    }

    public String getRelacionConPreso() {
        return relacionConPreso;
    }

    public void setRelacionConPreso(String relacionConPreso) {
        this.relacionConPreso = relacionConPreso;
    }

    public boolean isRequiereSupervision() {
        return requiereSupervision;
    }

    public void setRequiereSupervision(boolean requiereSupervision) {
        this.requiereSupervision = requiereSupervision;
    }

    public String getFotoPath() {
        return fotoPath;
    }

    public void setFotoPath(String fotoPath) {
        this.fotoPath = fotoPath;
    }

}
