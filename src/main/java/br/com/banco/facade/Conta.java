package br.com.banco.facade;


public class Conta {

    private String numero;
    private String titular;
    private double saldo;

    public Conta(String numero, String titular, double saldoInicial) {
        this.numero = numero;
        this.titular = titular;
        this.saldo = saldoInicial;
    }


    public boolean solicitarEncerramento() {
        return BancoFacade.verificarPendenciasEncerramento(this);
    }


    public boolean solicitarEmprestimo(double valor) {
        return BancoFacade.verificarAprovacaoEmprestimo(this, valor);
    }

    public String getNumero() {
        return numero;
    }

    public String getTitular() {
        return titular;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "Conta{numero='" + numero + "', titular='" + titular + "', saldo=" + saldo + "}";
    }
}
