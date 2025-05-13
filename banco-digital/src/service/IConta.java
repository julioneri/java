package service;

import exception.SaldoInsuficienteException;
import exception.ValorInvalidoException;

public interface IConta {

    private void sacar(double valor) throws ValorInvalidoException, SaldoInsuficienteException;

    private void depositar(double valor) throws ValorInvalidoException;

    private void transferir(double valor, String cpfDestinatario) throws ValorInvalidoException, SaldoInsuficienteException, IllegalArgumentException;

    /* void imprimirExtrato(); */
}
