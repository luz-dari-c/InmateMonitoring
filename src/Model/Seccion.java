
package Model;


public class Seccion {

    private int idSeccion;
    private char tipo;
    private int numeroDeCeldas;

    public Seccion(int idSeccion, char tipo, int numeroDeCeldas) {
        this.idSeccion = idSeccion;
        this.tipo = tipo;
        this.numeroDeCeldas = numeroDeCeldas;
    }

    public int getIdSeccion() {
        return idSeccion;
    }

    public void setIdSeccion(int idSeccion) {
        this.idSeccion = idSeccion;
    }

    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }

    public int getNumeroDeCeldas() {
        return numeroDeCeldas;
    }

    public void setNumeroDeCeldas(int numeroDeCeldas) {
        this.numeroDeCeldas = numeroDeCeldas;
    }
}
