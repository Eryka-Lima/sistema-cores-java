
package br.com.sistemacores;

public class CorEspecial extends Cor {

    private boolean metalica;
    private String acabamento;

    public CorEspecial(String nome, String familia,
                       String codigoHex, boolean metalica,
                       String acabamento) {
        super(nome, familia, codigoHex, "ESPECIAL");
        this.metalica = metalica;
        this.acabamento = acabamento;
    }

    public CorEspecial(int id, String nome, String familia,
                       String codigoHex, boolean metalica,
                       String acabamento) {
        super(id, nome, familia, codigoHex, "ESPECIAL");
        this.metalica = metalica;
        this.acabamento = acabamento;
    }

    public boolean isMetalica() {
        return metalica;
    }

    public String getAcabamento() {
        return acabamento;
    }

    @Override
    public String toString() {
        return super.toString()
                + " | Metálica: " + (metalica ? "Sim" : "Não")
                + " | Acabamento: " + acabamento;
    }
}
