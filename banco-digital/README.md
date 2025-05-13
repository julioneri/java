# 🏦 Sistema Bancário em Java

Este projeto simula um sistema bancário simples, permitindo a criação de clientes e contas bancárias (corrente ou poupança), além de realizar operações básicas como depósito, saque, transferência e visualização de extratos. O sistema também implementa a verificação de CPF para impedir a criação de múltiplas contas com o mesmo titular. As operações bancárias são realizadas de forma modular e segura, seguindo boas práticas de programação.

---

## ✅ Funcionalidades

- **Criação de clientes** com nome e CPF;
- **Criação de contas bancárias** (Corrente ou Poupança);
- **Verificação de CPF** para impedir múltiplas contas com o mesmo titular;
- **Operações de:**
  - Depósito;
  - Saque;
  - Transferência entre contas;
  - Impressão de extratos;
- **Sistema modular**, com uso de interfaces para definir as operações bancárias;
- **Notificação de transações** com detalhamento das operações realizadas.

---

## 🚀 Estrutura do Projeto

Este projeto simula um sistema bancário simples, com operações completas de conta, validação de dados e tratamento de exceções. Está organizado de forma modular em pacotes:

### 📁 **model/**

Contém as classes que representam os dados principais do sistema:

* **Cliente.java**: Representa um cliente com nome e CPF.
* **Conta.java**: Classe abstrata base para as contas, com lógica de saque, depósito, transferência e notificação de transações.
* **ContaCorrente.java** e **ContaPoupanca.java**: Especializações de `Conta`, com implementação personalizada de extrato.
* **TipoConta.java**: Enum que representa os tipos de conta disponíveis (`CORRENTE`, `POUPANCA`).

### 📁 **service/**

Contém a lógica principal de manipulação das contas:

* **Banco.java**: Gerencia as contas, criação, busca e validação de CPF.
* **IConta.java**: Interface que define as operações essenciais de uma conta bancária.

### 📁 **exception/**

Contém exceções personalizadas que ajudam no controle e na legibilidade do código:

* **ContaInexistenteException.java**: Lançada quando uma conta não é encontrada.
* **SaldoInsuficienteException.java**: Lançada ao tentar sacar/transferir mais do que o saldo disponível.
* **ValorInvalidoException.java**: Lançada quando um valor negativo ou nulo é informado em uma operação financeira.

### 📁 **util/**

Contém classes utilitárias auxiliares:

* **ValidadorDeValor.java**: Verifica se um valor é positivo antes de realizar operações financeiras.

### 📁 **ui/**

Controla a interação com o usuário (via terminal):

* **OperacoesContaMenu.java**: Apresenta menus de criação e gerenciamento de contas, chamando os métodos do serviço bancário.

### 📁 **app/**

Contém o ponto de entrada da aplicação:

* **Main.java**: Inicia o programa e apresenta o menu inicial.

---

## 📦 Organização dos Pacotes

```
src/
├── app/
│   └── Main.java
├── exception/
│   ├── ContaInexistenteException.java
│   ├── SaldoInsuficienteException.java
│   └── ValorInvalidoException.java
├── model/
│   ├── Cliente.java
│   ├── Conta.java
│   ├── ContaCorrente.java
│   ├── ContaPoupanca.java
│   └── TipoConta.java
├── service/
│   ├── Banco.java
│   └── IConta.java
├── ui/
│   └── OperacoesContaMenu.java
└── util/
    └── ValidadorDeValor.java

```

---

## 🚧 Melhorias Futuras

- Sistema de autenticação de cliente para maior segurança;
- Persistência de dados com arquivos ou banco de dados, permitindo que as informações do sistema sejam salvas entre sessões;
- Interface gráfica (GUI) para uma experiência visual mais agradável e interativa;
- Relatórios e histórico de operações para melhor acompanhamento dos usuários.

---

## ✍️ Autor

Desenvolvido por **Júlio César (julioneri)**  
💼 Projeto com fins didáticos para a prática de linguagem Java e orientação a objetos.

---

## 📄 Licença

Este projeto está licenciado sob a [MIT License](../LICENSE) – sinta-se livre para usá-lo como base, melhorar ou contribuir.

---

### Observações:

1. O sistema garante que **não haverá duplicidade de contas** associadas ao mesmo CPF, evitando inconsistências de dados no banco.
2. As operações de **notificação de transações** fornecem informações detalhadas sobre as movimentações realizadas, incluindo o tipo de serviço (Saque, Depósito, Transferência), valores e saldos atualizados.
