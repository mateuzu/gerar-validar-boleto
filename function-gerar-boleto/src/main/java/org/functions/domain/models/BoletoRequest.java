package org.functions.domain.models;

import java.time.LocalDate;

public class BoletoRequest {

    private LocalDate dataVencimento;
    private Double valor;

    public BoletoRequest() {
    }

    public BoletoRequest(LocalDate dataVencimento, Double valor) {
        this.dataVencimento = dataVencimento;
        this.valor = valor;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
