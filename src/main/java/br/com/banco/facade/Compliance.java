package br.com.banco.facade;


public class Compliance extends Departamento {

    private static final Compliance instancia = new Compliance();

    private Compliance() {}

    public static Compliance getInstancia() {
        return instancia;
    }

    @Override
    public String getNome() {
        return "Departamento de Compliance";
    }
}