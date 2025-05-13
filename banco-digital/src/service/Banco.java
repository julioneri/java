package service;
import java.util.ArrayList;
import java.util.List;

import model.Cliente;
import model.Conta;
import model.ContaCorrente;
import model.ContaPoupanca;
import model.TipoConta;

public class Banco {
    private String nome;               // Nome do banco (não utilizado ainda)
    private static List<Conta> contas; // Lista de todas as contas cadastradas

    public Banco() {
        Banco.contas = new ArrayList<>(); // Inicializa a lista de contas
    }
        
    // Método que verifica se existe uma conta com o CPF informado
    public static Conta buscarCpf(String cpfProcurado) {
        return contas.stream()
                .filter(conta -> conta.getCliente().getCpf().equals(cpfProcurado))
                .findFirst()
                .orElse(null);
    }

    public String criarConta(String nomeCliente, String cpfCliente, TipoConta tipoConta) throws IllegalArgumentException {

        // Verifica se já existe uma conta com o mesmo CPF  
        if (buscarCpf(cpfCliente) != null) {
            throw new IllegalArgumentException("Já existe uma conta utilizando o CPF informado.");  
        }

        // Cria novo cliente
        Cliente cliente = new Cliente(nomeCliente, cpfCliente);

        // Cria conta conforme o tipo informado
        Conta novaConta;
        
        if(tipoConta == TipoConta.CORRENTE) {
            novaConta = new ContaCorrente(cliente);
        } else if (tipoConta == TipoConta.POUPANCA) {
            novaConta = new ContaPoupanca(cliente);
        } else {
            return "Falha ao criar conta: Tipo de conta inválido.";
        }
        
        contas.add(novaConta); // Adiciona a nova conta à lista
        return tipoConta.getDescricao() + " criada com sucesso! Bem-vindo(a), " + nomeCliente + "!";
    }

    
    public Conta acessarConta(String cpfTitular) {
        return contas.stream()
            .filter(conta -> conta.getCliente().getCpf().equals(cpfTitular))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("O CPF informado não corresponde a uma conta existente."));
    }

    protected String getNome() {
        return nome;
    }

    protected void setNome(String nome) {
        this.nome = nome;
    }
}
