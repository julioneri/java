package model;

// Classe que representa um cliente do banco
public class Cliente {
    private String nome;
    private String cpf;

    // Construtor recebe nome e CPF ao instanciar um cliente
    public Cliente(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    // Getter para o nome do cliente
    public String getNome() {
        return nome;
    }
    
    // Getter para o CPF do cliente
    public String getCpf() {
        return cpf;
    }
}
