package br.com.sistemacores;

public class Cor {
    private int id;
    private String nome;
    private String familia;
    private String codigoHex;
    private String tipo;

    public Cor(String nome, String familia,
               String codigoHex, String tipo) {
        this.nome = nome;
        this.familia = familia;
        this.codigoHex = codigoHex;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public String getFamilia() {
        return familia;
    }

    public String getCodigoHex() {
        return codigoHex;
    }

    public String getTipo() {
        return tipo;
    }
}