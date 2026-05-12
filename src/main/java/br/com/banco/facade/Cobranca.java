package br.com.banco.facade;

public class Cobranca extends Departamento{

    private static final Cobranca instancia = new Cobranca();

    private Cobranca() {}

    public static Cobranca getInstancia() {
        return instancia;
    }

    @Override
    public String getNome() {
        return "Departamento de Cobrança";
    }
}
