# 🏦 Sistema Bancário em Java

Este projeto simula um sistema bancário simples, permitindo a criação de clientes e contas bancárias (corrente ou poupança), além de realizar operações básicas como depósito, saque, transferência e visualização de extratos. Um dos focos do sistema é evitar duplicidade de contas pelo CPF.

---

## ✅ Funcionalidades

- Criação de clientes com nome e CPF;
- Criação de contas bancárias (Corrente ou Poupança);
- Verificação de CPF para impedir múltiplas contas com o mesmo titular;
- Operações de:
  - Depósito;
  - Saque;
  - Transferência entre contas;
  - Impressão de extratos;
- Sistema modular com uso de interface para definição das operações bancárias.

---

## 🚀 Estrutura do Projeto

Este projeto simula um sistema bancário simples, onde é possível criar, acessar e encerrar contas bancárias. Ele está organizado de forma modular, com diferentes pacotes para modelagem, serviços e interface de usuário. Abaixo está a descrição de cada pacote e suas responsabilidades.

### 📁 **model/**

Este pacote contém as classes que representam os modelos de dados do sistema, como o cliente e as diferentes tipos de contas bancárias.

* **Cliente.java**: Representa um cliente do banco, com os atributos `nome` e `cpf`.
* **Conta.java**: Classe abstrata que serve como base para os tipos de contas bancárias. Ela implementa as operações básicas, como sacar, depositar e transferir, além de possuir informações comuns a todas as contas, como número, agência e saldo.
* **ContaCorrente.java**: Representa uma conta corrente, herdando da classe `Conta` e implementando o método `imprimirExtrato`.
* **ContaPoupanca.java**: Representa uma conta poupança, também herdando de `Conta` e implementando o método `imprimirExtrato`.
* **TipoConta.java**: Enum opcional (caso seja necessário) para representar os tipos de conta disponíveis no sistema, como `CORRENTE` e `POUPANCA`.

### 📁 **service/**

Este pacote contém a lógica de negócios do sistema, como a criação de contas e a verificação de duplicidade de CPF.

* **IConta.java**: Interface que define os métodos de operações bancárias como sacar, depositar, transferir e imprimir extrato. As classes `ContaCorrente` e `ContaPoupanca` implementam essa interface.
* **Banco.java**: A classe principal do serviço bancário, responsável por gerenciar as contas no sistema. Aqui é onde são feitas as operações de criação de contas, validação de CPF e acesso às contas.

### 📁 **ui/**

Este pacote contém as interações de interface com o usuário.

* **OperacoesContaMenu.java**: Classe responsável por exibir o menu inicial para o usuário, permitindo a criação, acesso e encerramento de contas. Ela interage com o banco e chama os métodos necessários para realizar essas operações.

### 📁 **app/**

Este pacote contém a classe principal que executa o programa.

* **Main.java**: O ponto de entrada do programa. Aqui é onde o menu é exibido e as operações são iniciadas.

---

## 📦 Organização dos Pacotes

```
src/
├── app/
│   └── Main.java
├── model/
│   ├── Cliente.java
│   ├── Conta.java
│   ├── ContaCorrente.java
│   ├── ContaPoupanca.java
│   └── TipoConta.java  (opcional)
├── service/
│   ├── IConta.java
│   └── Banco.java
├── ui/
│   └── OperacoesContaMenu.java
```

---

## 🚧 Melhorias Futuras

* Sistema de autenticação de cliente;
* Persistência de dados com arquivos ou banco de dados;
* Interface gráfica (GUI) para experiência visual;
* Relatórios e histórico de operações.

---

## ✍️ Autor

Desenvolvido por **Júlio César (julioneri)**
💼 Projeto com fins didáticos para prática da linguagem Java e orientação a objetos.

---

## 📄 Licença

Este projeto está licenciado sob a [MIT License](LICENSE) – sinta-se livre para usá-lo como base, melhorar ou contribuir.
