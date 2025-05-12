package app;
import java.util.Scanner;

import service.Banco;
import ui.OperacoesContaMenu;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); 
        Banco banco = new Banco();
        OperacoesContaMenu menu = new OperacoesContaMenu(scanner, banco); // Inicia o menu principal

        menu.exibirMenuInicial();
    }
}
