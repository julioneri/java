package service;
import java.util.ArrayList;
import java.util.List;

import model.Cliente;
import model.Conta;
import model.ContaCorrente;
import model.ContaPoupanca;
import model.TipoConta;

public class Banco {
    private String nome;        // Nome do banco (não utilizado ainda)
    private List<Conta> contas; // Lista de todas as contas cadastradas

    public Banco() {
        this.contas = new ArrayList<>(); // Inicializa a lista de contas
    }

    // Método que verifica se já existe uma conta com o mesmo CPF
    private boolean cpfJaCadastrado(String cpfCliente) {
        return contas.stream()
                .anyMatch(conta -> conta.getCliente().getCpf().equals(cpfCliente));
    }

    public String criarConta(String nomeCliente, String cpfCliente, TipoConta tipoConta) {

        if (!contas.isEmpty()) {
            // Verifica se já existe uma conta com o mesmo CPF
            if(cpfJaCadastrado(cpfCliente)) {
                return "Falha ao criar conta: O CPF fornecido já foi cadastrado.";
            }
        }

        // Cria novo cliente
        Cliente cliente = new Cliente(nomeCliente, cpfCliente);

        // Cria conta conforme o tipo informado
        Conta novaConta;
        
        if(tipoConta == TipoConta.CORRENTE) {
            novaConta = new ContaPoupanca(cliente);
        } else if (tipoConta == TipoConta.POUPANCA) {
            novaConta = new ContaCorrente(cliente);
        } else {
            return "Falha ao criar conta: Tipo de conta inválido.";
        }
        
        contas.add(novaConta); // Adiciona a nova conta à lista
        return tipoConta.getDescricao() + " criada com sucesso! Bem-vindo(a), " + nomeCliente + "!";
    }

    protected String getNome() {
        return nome;
    }

    protected void setNome(String nome) {
        this.nome = nome;
    }
}
