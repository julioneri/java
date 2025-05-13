package model;

public enum TipoServico {
    TRANSFERENCIA("Transferência"),
    DEPOSITO("Depósito"),
    SAQUE("Saque");

    private String descricao;

    TipoServico(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
