package ui;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

import exception.SaldoInsuficienteException;
import exception.ValorInvalidoException;

import model.Conta;
import model.TipoConta;
import model.TipoServico;
import service.Banco;

public class OperacoesContaMenu {
    private Scanner scanner;
    private Banco banco;

    // Construtor para inicializar o scanner e o banco
    public OperacoesContaMenu(Scanner scanner, Banco banco) {
        this.scanner = scanner;
        this.banco = banco;
    }

    // Método que exibe o menu inicial e controla as opções do usuário
    public void menuInicial() {
        while (true) {
            // Exibe as opções do menu
            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║             MENU INICIAL             ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║ 1. Criar conta                       ║");
            System.out.println("║ 2. Acessar conta                     ║");
            System.out.println("║ 0. Sair                              ║");
            System.out.println("╚══════════════════════════════════════╝");
            

            int opcao = lerInt();   // Lê a opção do usuário
            limparTerminal();       // Limpa a tela

            switch (opcao) {
                case 1 -> criarConta();     // Chama o processo de criação de conta
                case 2 -> acessarConta();   // Ainda não implementado
                case 0 -> {
                    System.out.println("\n[!] Encerrando... volte sempre!");
                    return; // Sai do menu e encerra o programa
                }
                case 999 -> System.out.println("Menu do administrador... (breve)");
                default -> {
                    System.out.println("\n[!] Opção inválida. Por favor, tente novamente.");
                }
            }
        }
    }

    private void criarConta() {
        while (true) {
            // Escolha do tipo de conta a ser criada
            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║        ABERTURA DE NOVA CONTA        ║");
            System.out.println("║   Qual tipo de conta deseja criar?   ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║ 1. Conta corrente.                   ║");
            System.out.println("║ 2. Conta poupança.                   ║");
            System.out.println("║ 0. Voltar ao menu principal.         ║");
            System.out.println("╚══════════════════════════════════════╝");

            int opcao = lerInt();   // Lê a opção do usuário
            limparTerminal();       // Limpa a tela

            if (opcao == 0) {
                return; // Volta ao menu principal
            }

            // Verifica se a opção é válida (poderia ser substituída por um enum
            // futuramente)
            if (!Set.of(1, 2).contains(opcao)) {
                System.out.println("\n[!] Opção inválida. Por favor, tente novamente.");
                continue;
            }

            // Define o tipo de conta
            TipoConta tipoConta = opcao == 1 ? TipoConta.CORRENTE : TipoConta.POUPANCA;

            scanner.nextLine(); // Consome o '\n' pendente

            // Coleta os dados do cliente
            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║      CRIANDO UMA CONTA " + tipoConta + "      ║");
            System.out.println("║       Insira o nome do titular:      ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.print("> Digite: ");
            String nomeTitular = scanner.nextLine();

            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║      CRIANDO UMA CONTA " + tipoConta + "      ║");
            System.out.println("║       Insira o CPF do titular:       ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.print("> Digite: ");

            String cpfTitular = scanner.nextLine(); // Captura o CPF digitado pelo usuário
            limparTerminal();                       // Limpa a tela

            // Chama o método de criação no banco
            try {
                this.banco.criarConta(nomeTitular, cpfTitular, tipoConta);
                System.out.println("[!] Conta criada com sucesso!");
            } catch (IllegalArgumentException e) {
                System.out.println("[!] ERRO: " + e.getMessage());
            }
            break;
        }
    }

    private void acessarConta() {
        // Cabeçalho visual informando a ação ao usuário
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║             ACESSAR CONTA            ║");
        System.out.println("║    Digite o CPF da conta desejada:   ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.print("> Digite: ");

        scanner.nextLine();                     // Consome o '\n' pendente
        String cpfTitular = scanner.nextLine(); // Captura o CPF digitado pelo usuário

        limparTerminal();   // Limpa a tela

        Conta conta;
        try {
            // Tenta acessar a conta a partir do CPF informado
            conta = banco.acessarConta(cpfTitular);
        } catch (IllegalArgumentException e) {
            // Se o CPF não for encontrado ou for inválido, exibe o erro e encerra o método
            System.out.println("ERRO: " + e.getMessage());
            return;
        }

        System.out.println("\n[!] Bem-vindo(a), " + conta.getCliente().getNome() + "!");
        // Chama o menu da conta (operações como sacar, depositar, etc.)
        menuConta(conta);
    }

    // Exibe o menu de operações bancárias disponíveis para a conta informada
    private void menuConta(Conta conta) {
        while (true) {
            // Interface do menu
            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║      MENU DE OPERAÇÕES BANCÁRIAS     ║");
            System.out.println("║     Tipo de conta: " + conta.getTipoConta().getDescricao() + "    ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║ 1. Consultar saldo                   ║");
            System.out.println("║ 2. Depositar                         ║");
            System.out.println("║ 3. Sacar                             ║");
            System.out.println("║ 4. Transferir                        ║");
            System.out.println("║ 5. Ver extrato                       ║");
            System.out.println("║ 0. Desconectar da conta              ║");
            System.out.println("╚══════════════════════════════════════╝");

            int opcao = lerInt();

            limparTerminal();

            switch (opcao) {
                case 1 -> consultarSaldoConta(conta);   // Exibe o saldo atual da conta do usuário
                case 2 -> depositarConta(conta);        // Solicita o valor e realiza o depósito na conta
                case 3 -> sacarConta(conta);            // Solicita o valor e realiza o saque na conta
                case 4 -> transferirValor(conta);       // Solicita CPF e valor para realizar a transferência
                case 5 -> verExtrato(conta);            // Exibe o extrato detalhado das transações da conta
                case 0 -> {
                    return; // Desconecta da conta e retorna ao menu anterior (MENU INICIAL)
                }
                default -> System.out.println("\n[!] Opção inválida. Por favor, tente novamente.");
            }
        }
    }

    // Exibe o saldo atual da conta informada
    private void consultarSaldoConta(Conta conta) {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║            SALDO DA CONTA            ║");
        System.out.printf("║ Saldo da conta: R$ %-17.2f ║%n", conta.getSaldo());
        System.out.println("╚══════════════════════════════════════╝");
    }

    // Solicita o valor ao usuário e realiza a operação de depósito na conta
    private void depositarConta(Conta conta) {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║          DEPOSITAR NA CONTA          ║");
        System.out.println("║      Insira o valor a depositar:     ║");
        System.out.println("╚══════════════════════════════════════╝");
        try {
            conta.executarServico(TipoServico.DEPOSITO, lerDouble(), null);

            limparTerminal();
            System.out.println("[!] Valor depositado com sucesso!");
        } catch (ValorInvalidoException e) {
            limparTerminal();
            System.out.println("[!] ERRO: " + e.getMessage());
        } catch (Exception e) {
            limparTerminal();
            System.out.println("[!] ERRO desconhecido: " + e.getMessage());
        }
    }

    // Solicita o valor ao usuário e realiza a operação de saque na conta
    private void sacarConta(Conta conta) {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║          DEPOSITAR NA CONTA          ║");
        System.out.println("║        Insira o valor a sacar:       ║");
        System.out.println("╚══════════════════════════════════════╝");
        try {
            conta.executarServico(TipoServico.SAQUE, lerDouble(), null);
            
            limparTerminal();
            System.out.println("[!] Saque realizado com sucesso.");
        } catch (SaldoInsuficienteException | ValorInvalidoException e) {
            limparTerminal();
            System.out.println("[!] ERRO: " + e.getMessage());
        } catch (Exception e) {
            limparTerminal();
            System.out.println("[!] ERRO desconhecido: " + e.getMessage());
        }
    }

    // Solicita CPF do destinatário e valor da transferência, e executa a operação
    private void transferirValor(Conta conta) {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║           TRANSFERIR VALOR           ║");
        System.out.println("║  Digite o CPF da conta destinatária: ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.print("> Digite: ");

        scanner.nextLine(); // Consome o '\n' pendente
        String cpfDestinatario = scanner.nextLine();

        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║           TRANSFERIR VALOR           ║");
        System.out.println("║      Digite o valor a transferir:    ║");
        System.out.println("╚══════════════════════════════════════╝");
        double valor = lerDouble();

        try {
            conta.executarServico(TipoServico.TRANSFERENCIA, valor, cpfDestinatario);

            limparTerminal();
            System.out.println("[!] Transferência realizada com sucesso.");
        } catch (ValorInvalidoException | IllegalArgumentException | SaldoInsuficienteException e) {
            limparTerminal();
            System.out.println("[!] ERRO: " + e.getMessage());
        } catch (Exception e) {
            limparTerminal();
            System.out.println("[!] ERRO desconhecido: " + e.getMessage());
        }
    }

    // Exibe o extrato da conta, listando todas as transações registradas
    private void verExtrato(Conta conta) {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║           EXTRATO DA CONTA           ║");
        System.out.println("╚══════════════════════════════════════╝");
        List<String> transacoes = conta.getTransacoes();

        if (transacoes.isEmpty()) {
            System.out.println("[!] Nenhuma transação encontrada.");
            return;
        }

        transacoes.forEach(transacao -> System.out.println("• " + transacao));
    }

    // Método auxiliar para capturar um número inteiro do usuário, com tratamento de exceções
    private int lerInt() {
        while (true) {
            try {
                System.out.print("> Digite: "); // Solicita ao usuário para digitar um valor inteiro
                return scanner.nextInt();         // Retorna o valor digitado pelo usuário
            } catch (Exception e) {
                // Caso o usuário insira um valor não inteiro, captura a exceção e exibe a mensagem
                System.out.println("Valor inválido. Por favor, digite apenas números.");
                scanner.nextLine(); /// Limpa o buffer do scanner para tentar novamente
            }
        }
    }

    // Método auxiliar para capturar um número double do usuário, com tratamento de exceções
    private double lerDouble() {
        while (true) {
            try {
                System.out.print("> Digite: "); // Solicita ao usuário para digitar um valor double
                return scanner.nextDouble();      // Retorna o valor double digitado pelo usuário
            } catch (Exception e) {
                // Caso o usuário insira um valor não numérico ou inválido, captura a exceção
                System.out.println("Valor inválido. Por favor, digite apenas números.");
                scanner.nextLine(); // Limpa o buffer do scanner para tentar novamente
            }
        }
    }

    // Método auxiliar para limpar o terminal (funciona em sistemas baseados em UNIX)
    private void limparTerminal() {
        System.out.print("\033[H\033[2J");  // Comando ANSI para limpar a tela
        System.out.flush();                   // Garante que o comando seja executado imediatamente
    }
}
