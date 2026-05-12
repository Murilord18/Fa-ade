package br.com.banco.facade;

public class Credito extends Departamento{

    private static final Credito instancia = new Credito();

    private Credito() {}

    public static Credito getInstancia() {
        return instancia;
    }

    @Override
    public String getNome() {
        return "Departamento de Crédito";
    }
}
