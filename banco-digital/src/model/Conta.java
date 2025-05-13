package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import exception.SaldoInsuficienteException;
import exception.ValorInvalidoException;
import service.Banco;
import service.IConta;
import util.ValidadorDeValor;

public abstract class Conta implements IConta {
    private static int AGENCIA_PADRAO = 1;
    private static int SEQUENCIAL = 1;
    private List<String> transacoes = new ArrayList<>();
    
    protected int agencia;
    protected int numero;
    protected double saldo;
    protected Cliente cliente;
    protected TipoConta tipoConta;

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    // Construtor da classe Conta, utilizado para criar uma nova conta bancária associada a um cliente e a um tipo de conta.
    public Conta(Cliente cliente, TipoConta tipoConta) {
        this.agencia = AGENCIA_PADRAO;
        this.numero = SEQUENCIAL++;
        this.cliente = cliente;
        this.tipoConta = tipoConta;
    }

    public int getAgencia() {
        return agencia;
    }

    public int getNumero() {
        return numero;
    }

    public double getSaldo() {
        return saldo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public TipoConta getTipoConta() {
        return tipoConta;
    }

    public List<String> getTransacoes() {
        return transacoes;
    }

    /**
     * Executa um serviço bancário baseado no tipo de operação solicitado (saque, depósito ou transferência).
     * 
     * Dependendo do tipo de serviço, o método chama a operação correspondente, validando os valores e realizando
     * as ações necessárias. Após a execução, uma notificação é enviada para o usuário sobre a transação realizada,
     * exceto no caso de transferências, que são tratadas de maneira específica.
     * 
     * @param tipoServico O tipo de serviço bancário a ser executado (SAQUE, DEPOSITO, TRANSFERENCIA).
     * @param valor O valor a ser utilizado na operação (positivo).
     * @param cpfDestinatario O CPF do destinatário, utilizado no caso de uma transferência (caso contrário, nulo).
     * @throws ValorInvalidoException Se o valor informado for negativo ou zero.
     * @throws SaldoInsuficienteException Se o saldo for insuficiente para realizar a operação.
     */
    public void executarServico(TipoServico tipoServico, double valor, String cpfDestinatario) throws ValorInvalidoException, SaldoInsuficienteException {

        // Verifica o tipo de serviço a ser executado e executa a ação correspondente.
        switch (tipoServico) {
            case SAQUE -> {
                sacar(valor);
                // Notifica a transação do tipo "SAQUE", sem especificar destinatário.
                notificarTransacao(TipoServico.SAQUE, valor, null, null);
            }
            case DEPOSITO -> {
                depositar(valor);
                // Notifica a transação do tipo "DEPOSITO", sem especificar destinatário.
                notificarTransacao(TipoServico.DEPOSITO, valor, null, null);
            }
            case TRANSFERENCIA -> {
                transferir(valor, cpfDestinatario);
            }
            // Caso o tipo de serviço não seja reconhecido, retorna sem fazer nada.
            default -> { return; }
        }
    }

    // Método responsável por notificar a transação de um determinado serviço bancário (saque, depósito ou transferência).
    public void notificarTransacao(TipoServico tipoServico, double valor, Conta remetente, Conta destinatario) {
        // Formatação da data e hora atual para incluir na mensagem de transação
        String mensagem = "[" + LocalDateTime.now().format(dateFormatter) + "] ";
        
        // Trata diferentes tipos de serviço bancário (SAQUE, DEPÓSITO, TRANSFERÊNCIA)
        switch (tipoServico) {

            // Caso de saque: cria mensagem sobre o saque realizado, incluindo o valor e o saldo após a transação
            case SAQUE -> {mensagem += "Saque -R$ " + valor + " | Saldo: R$ " + saldo;
            }

            // Caso de depósito: cria mensagem sobre o depósito realizado, incluindo o valor e o saldo após a transação
            case DEPOSITO -> {
                mensagem += "Depósito +R$ " + valor + " | Saldo: R$ " + saldo;
            }

            // Caso de transferência: cria mensagem o envio ou recebimento de uma transferência, dependendo do remetente ou destinatário
            case TRANSFERENCIA -> {
                if (this == remetente) {
                    // Notifica o envio de uma transferência e chama a notificação no destinatário
                    mensagem += "Transferência Enviada para: " +
                                destinatario.getCliente().getNome() +
                     " (CPF: " + destinatario.getCliente().getCpf() +
                     "): -R$ " + valor + " | Saldo: R$ " + saldo;
                     destinatario.notificarTransacao(TipoServico.TRANSFERENCIA, valor, remetente, destinatario);
                } else {
                    // Notifica o recebimento de uma transferência
                    mensagem += "Transferência Recebida de: " +
                                remetente.getCliente().getNome() +
                     " (CPF: " + remetente.getCliente().getCpf() +
                     "): -R$ " + valor + " | Saldo: R$ " + saldo;
                }
            }
            default -> mensagem = "Serviço desconhecido.";
        }
        transacoes.add(mensagem);
    }

    /**
     * Realiza o saque de um valor da conta.
     * 
     * Este método verifica se o valor solicitado para o saque é positivo e se a conta
     * possui saldo suficiente. Caso algum desses requisitos não seja atendido, são lançadas
     * exceções apropriadas. Após a validação, o valor é subtraído do saldo da conta.
     * 
     * @param valor O valor a ser sacado.
     * @throws ValorInvalidoException Se o valor informado for negativo ou zero.
     * @throws SaldoInsuficienteException Se o saldo da conta for insuficiente para realizar o saque.
     */
    @Override
    public void sacar(double valor) throws ValorInvalidoException, SaldoInsuficienteException {
        // Valida se o valor é positivo. Caso contrário, lança uma exceção personalizada.
        ValidadorDeValor.valorPositivo(valor);

        // Verifica se o saldo é suficiente para realizar o saque.
        if (valor > saldo) {
            // Lança exceção caso o saldo seja insuficiente.
            throw new SaldoInsuficienteException("Saldo insuficiente.");
        }

        // Subtrai o valor do saldo da conta após as validações.
        saldo -= valor;
    }

    /**
     * Realiza o depósito de um valor na conta.
     * 
     * Este método valida se o valor do depósito é positivo. Se o valor for válido, ele é
     * somado ao saldo da conta. Caso contrário, uma exceção será lançada.
     * 
     * @param valor O valor a ser depositado.
     * @throws ValorInvalidoException Se o valor informado for negativo ou zero.
     */
    @Override
    public void depositar(double valor) throws ValorInvalidoException {
        // Valida se o valor é positivo. Caso contrário, lança uma exceção personalizada.
        ValidadorDeValor.valorPositivo(valor);

        // Adiciona o valor ao saldo da conta após validação.
        saldo += valor;
    }

    @Override
    public void transferir(double valor, String cpfDestinatario) throws ValorInvalidoException, SaldoInsuficienteException, IllegalArgumentException {

        // Verifica se o CPF do destinatário é o mesmo do cliente que está fazendo a transferência
        if (cpfDestinatario.equals(cliente.getCpf())) {
            throw new IllegalArgumentException("Não é permitido realizar transferências para sua própria conta.");
        }

        // Busca a conta do destinatário através do CPF
        Conta destinatario = Banco.buscarCpf(cpfDestinatario);

        // Verifica se a conta do destinatário existe
        if (destinatario == null) {
            throw new IllegalArgumentException("Não encontramos uma conta associada a este CPF. Verifique o número e tente novamente.");
        }

        // Realiza o saque da conta de origem
        this.sacar(valor);

        // Realiza o depósito na conta de destino
        destinatario.depositar(valor);

        // Solicita notificação da transação realizada
        notificarTransacao(TipoServico.TRANSFERENCIA, valor, this, destinatario);
        
    }

    /*
    protected void imprimirInfosComuns() {
        System.out.println(String.format("Titular: %s", this.cliente.getNome()));
        System.out.println(String.format("Agência: %d", this.agencia));
        System.out.println(String.format("Numero: %d", this.numero));
        System.out.println(String.format("Saldo: %.2f", this.saldo));
    }
    */
}
