package util;

import exception.ValorInvalidoException;

public class ValidadorDeValor {
    public static void valorPositivo(double valor) throws ValorInvalidoException {
        if (valor <= 0) {
            throw new ValorInvalidoException("O valor mínimo permitido deve ser maior ou igual a R$ 1,00.");
        }
    }
}
