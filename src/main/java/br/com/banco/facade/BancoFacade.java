package br.com.banco.facade;


public class BancoFacade {


    public static boolean verificarPendenciasEncerramento(Conta conta) {
        if (Cobranca.getInstancia().verificarContaComPendencia(conta)) {
            System.out.println("[BancoFacade] Encerramento NEGADO: pendência em " +
                    Cobranca.getInstancia().getNome());
            return false;
        }
        if (Credito.getInstancia().verificarContaComPendencia(conta)) {
            System.out.println("[BancoFacade] Encerramento NEGADO: pendência em " +
                    Credito.getInstancia().getNome());
            return false;
        }
        if (Compliance.getInstancia().verificarContaComPendencia(conta)) {
            System.out.println("[BancoFacade] Encerramento NEGADO: pendência em " +
                    Compliance.getInstancia().getNome());
            return false;
        }
        System.out.println("[BancoFacade] Encerramento APROVADO para conta: " + conta.getNumero());
        return true;
    }


    public static boolean verificarAprovacaoEmprestimo(Conta conta, double valor) {
        if (Cobranca.getInstancia().verificarContaComPendencia(conta)) {
            System.out.println("[BancoFacade] Empréstimo NEGADO: pendência em " +
                    Cobranca.getInstancia().getNome());
            return false;
        }
        if (Compliance.getInstancia().verificarContaComPendencia(conta)) {
            System.out.println("[BancoFacade] Empréstimo NEGADO: pendência em " +
                    Compliance.getInstancia().getNome());
            return false;
        }
        if (conta.getSaldo() < valor * 0.1) {
            System.out.println("[BancoFacade] Empréstimo NEGADO: saldo insuficiente como garantia");
            return false;
        }
        System.out.println("[BancoFacade] Empréstimo APROVADO de R$ " + valor +
                " para conta: " + conta.getNumero());
        return true;
    }
}
