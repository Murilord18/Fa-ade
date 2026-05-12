package br.com.banco.facade;


import java.util.ArrayList;
import java.util.List;

public abstract class Departamento {

    private List<Conta> contasComPendencia = new ArrayList<>();

    public void adicionarContaPendente(Conta conta) {
        this.contasComPendencia.add(conta);
    }

    public void removerPendencia(Conta conta) {
        this.contasComPendencia.remove(conta);
    }

    public boolean verificarContaComPendencia(Conta conta) {
        return this.contasComPendencia.contains(conta);
    }


    public abstract String getNome();
}