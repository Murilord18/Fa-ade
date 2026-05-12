package br.com.banco.facade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("Testes do BancoFacade")
public class BancoFacadeTest {

    private Conta contaSemPendencia;
    private Conta contaComPendenciaCobranca;
    private Conta contaComPendenciaCredito;
    private Conta contaComPendenciaCompliance;
    private Conta contaComTodasPendencias;
    private Conta contaSaldoBaixo;

    @BeforeEach
    void setUp() {
        
        // Remove todas as pendências anteriores recriando os objetos


        contaSemPendencia         = new Conta("001-1", "João Silva", 5000.00);
        contaComPendenciaCobranca = new Conta("002-2", "Maria Costa", 1000.00);
        contaComPendenciaCredito  = new Conta("003-3", "Carlos Souza", 2000.00);
        contaComPendenciaCompliance = new Conta("004-4", "Ana Lima", 3000.00);
        contaComTodasPendencias   = new Conta("005-5", "Pedro Rocha", 500.00);
        contaSaldoBaixo           = new Conta("006-6", "Lucia Ferreira", 50.00);

        // Registra pendências nos departamentos (Singletons)


        Cobranca.getInstancia().adicionarContaPendente(contaComPendenciaCobranca);
        Credito.getInstancia().adicionarContaPendente(contaComPendenciaCredito);
        Compliance.getInstancia().adicionarContaPendente(contaComPendenciaCompliance);

        Cobranca.getInstancia().adicionarContaPendente(contaComTodasPendencias);
        Credito.getInstancia().adicionarContaPendente(contaComTodasPendencias);
        Compliance.getInstancia().adicionarContaPendente(contaComTodasPendencias);
    }


    // Testes de Encerramento de Conta


    @Test
    @DisplayName("Deve permitir encerramento quando conta não tem pendências")
    void devePermitirEncerramentoSemPendencias() {
        boolean resultado = contaSemPendencia.solicitarEncerramento();
        assertEquals(true, resultado);
    }

    @Test
    @DisplayName("Deve negar encerramento quando há pendência no Departamento de Cobrança")
    void deveNegarEncerramentoComPendenciaCobranca() {
        boolean resultado = contaComPendenciaCobranca.solicitarEncerramento();
        assertEquals(false, resultado);
    }

    @Test
    @DisplayName("Deve negar encerramento quando há pendência no Departamento de Crédito")
    void deveNegarEncerramentoComPendenciaCredito() {
        boolean resultado = contaComPendenciaCredito.solicitarEncerramento();
        assertEquals(false, resultado);
    }

    @Test
    @DisplayName("Deve negar encerramento quando há pendência no Departamento de Compliance")
    void deveNegarEncerramentoComPendenciaCompliance() {
        boolean resultado = contaComPendenciaCompliance.solicitarEncerramento();
        assertEquals(false, resultado);
    }

    @Test
    @DisplayName("Deve negar encerramento quando há pendências em todos os departamentos")
    void deveNegarEncerramentoComTodasPendencias() {
        boolean resultado = contaComTodasPendencias.solicitarEncerramento();
        assertEquals(false, resultado);
    }


    // Testes de Aprovação de Empréstimo


    @Test
    @DisplayName("Deve aprovar empréstimo quando conta está regular e tem saldo suficiente")
    void deveAprovarEmprestimoContaRegular() {
        boolean resultado = contaSemPendencia.solicitarEmprestimo(10000.00);
        // Saldo 5000, 10% de garantia = 1000. Saldo (5000) >= 1000 → aprovado
        assertEquals(true, resultado);
    }

    @Test
    @DisplayName("Deve negar empréstimo quando há pendência na Cobrança")
    void deveNegarEmprestimoComPendenciaCobranca() {
        boolean resultado = contaComPendenciaCobranca.solicitarEmprestimo(5000.00);
        assertEquals(false, resultado);
    }

    @Test
    @DisplayName("Deve negar empréstimo quando há pendência no Compliance")
    void deveNegarEmprestimoComPendenciaCompliance() {
        boolean resultado = contaComPendenciaCompliance.solicitarEmprestimo(5000.00);
        assertEquals(false, resultado);
    }

    @Test
    @DisplayName("Deve negar empréstimo quando saldo é insuficiente como garantia")
    void deveNegarEmprestimoComSaldoInsuficiente() {
        // Saldo 50, 10% de garantia = 5. Solicita 10000 → garantia mínima = 1000 > 50
        boolean resultado = contaSaldoBaixo.solicitarEmprestimo(10000.00);
        assertEquals(false, resultado);
    }

    @Test
    @DisplayName("Deve aprovar empréstimo pequeno mesmo com saldo baixo")
    void deveAprovarEmprestimoPequenoComSaldoBaixo() {
        // Saldo 50, 10% de garantia = 5. Solicita 400 → garantia mínima = 40 < 50
        boolean resultado = contaSaldoBaixo.solicitarEmprestimo(400.00);
        assertEquals(true, resultado);
    }


    // Testes via BancoFacade diretamente


    @Test
    @DisplayName("BancoFacade deve retornar true para encerramento sem pendências (chamada direta)")
    void facadeDeveRetornarTrueParaEncerramentoSemPendencias() {
        boolean resultado = BancoFacade.verificarPendenciasEncerramento(contaSemPendencia);
        assertEquals(true, resultado);
    }

    @Test
    @DisplayName("BancoFacade deve retornar false para empréstimo com pendência em compliance (chamada direta)")
    void facadeDeveRetornarFalseParaEmprestimoComPendenciaCompliance() {
        boolean resultado = BancoFacade.verificarAprovacaoEmprestimo(contaComPendenciaCompliance, 1000.00);
        assertEquals(false, resultado);
    }
}
