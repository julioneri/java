package ui;
import java.util.Scanner;
import java.util.Set;

import model.TipoConta;
import service.Banco;

public class OperacoesContaMenu {
    private Scanner scanner;
    private Banco banco;

    public OperacoesContaMenu(Scanner scanner, Banco banco) {
        this.scanner = scanner;
        this.banco = banco;
    }

    public void exibirMenuInicial() {
        while (true) {
            // Exibe as opções principais do sistema
            System.out.println("\n=== MENU INICIAL ===");
            System.out.println("1. Criar conta");
            System.out.println("2. Acessar conta");
            System.out.println("3. Encerrar conta");
            System.out.println("0. Sair");

            int opcao = lerInt();

            switch (opcao) {
                case 1 -> criarConta();                                 // Chama o processo de criação de conta
                case 2 -> System.out.println("2. Acessar conta");     // Ainda não implementado
                case 0 -> {
                    System.out.println("\nEncerrando... volte sempre!");
                    return;                                             // Sai do loop e encerra o programa
                }
                default -> System.out.println("\nOpção inválida. Por favor, tente novamente.");
            }
        }
    }

    private void criarConta() {
        while (true) {
            // Escolha do tipo de conta a ser criada
            System.out.println("\n=== Qual tipo de conta deseja criar? ===");
            System.out.println("1. Conta corrente.");
            System.out.println("2. Conta poupança.");
            System.out.println("0. Voltar ao menu principal.");

            int opcao = lerInt();
            if (opcao == 0) {
                return; // Volta ao menu principal
            }

            // Verifica se a opção é válida (poderia ser substituída por um enum futuramente)
            if (!Set.of(1, 2).contains(opcao)) {
                System.out.println("\nOpção inválida. Por favor, tente novamente.");
                continue;
            }

            // Define o tipo de conta
            TipoConta tipoConta = opcao == 1 ? TipoConta.CORRENTE : TipoConta.POUPANCA;
            
            scanner.nextLine(); // Consome o '\n' pendente
            
            // Coleta os dados do cliente
            System.out.print("Digite o nome do titular: ");
            String nomeTitular = scanner.nextLine();

            System.out.print("Digite o CPF do titular: ");
            String cpfTitular = scanner.nextLine();

            // Chama o método de criação no banco
            String status = this.banco.criarConta(nomeTitular, cpfTitular, tipoConta);

            System.out.println("\n" + status);
            break;
        }
    }

    // Método auxiliar para controlar a entrada de dados inteiros
    private int lerInt() {
        while (true) {
            try {
                System.out.print("Digite: "); // Tenta ler um número inteiro
                return scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Valor inválido. Por favor, digite apenas números.");
                scanner.nextLine(); // Limpa o buffer em caso de erro
            }
        }
    }
}
