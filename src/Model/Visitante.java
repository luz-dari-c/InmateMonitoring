package Model;

public class Visitante extends Persona {

    private String relacionConPreso;
    private boolean requiereSupervision;

    public Visitante(String nombre, String apellido, int edad, int id,
            String sexo, String nacionalidad, String identificacion,
            String relacionConPreso, boolean requiereSupervision) {
        super(nombre, apellido, edad, id, sexo, nacionalidad, identificacion);
        this.relacionConPreso = relacionConPreso;
        this.requiereSupervision = requiereSupervision;
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

}