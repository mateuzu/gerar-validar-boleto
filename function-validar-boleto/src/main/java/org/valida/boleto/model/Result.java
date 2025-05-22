package org.valida.boleto.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class Result {

    private boolean valido;
    private String mensagem;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataVencimento;

    public Result() {
    }

    public Result(boolean valido, String mensagem, LocalDate dataVencimento) {
        this.valido = valido;
        this.mensagem = mensagem;
        this.dataVencimento = dataVencimento;
    }

    public boolean isValido() {
        return valido;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }
}
