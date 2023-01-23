package numero;

public class Numero
{
    private String numero;
    private int premio;

    public Numero(String numero, int premio) {
        this.numero = numero;
        this.premio = premio;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getPremio() {
        return premio;
    }

    public void setPremio(int premio) {
        this.premio = premio;
    }
}
