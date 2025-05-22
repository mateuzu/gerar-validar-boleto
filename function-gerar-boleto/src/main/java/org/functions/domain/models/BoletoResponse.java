package org.functions.domain.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class BoletoResponse {

    private String barcode;
    private Double valorOriginal;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataGeracao;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataVencimento;
    private String imagemBase64;

    public BoletoResponse() {}

    public BoletoResponse(String barcode, Double valorOriginal, LocalDate dataGeracao, LocalDate dataVencimento, String imagemBase64) {
        this.barcode = barcode;
        this.valorOriginal = valorOriginal;
        this.dataGeracao = dataGeracao;
        this.dataVencimento = dataVencimento;
        this.imagemBase64 = imagemBase64;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Double getValorOriginal() {
        return valorOriginal;
    }

    public void setValorOriginal(Double valorOriginal) {
        this.valorOriginal = valorOriginal;
    }

    public LocalDate getDataGeracao() {
        return dataGeracao;
    }

    public void setDataGeracao(LocalDate dataGeracao) {
        this.dataGeracao = dataGeracao;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public String getImagemBase64() {
        return imagemBase64;
    }

    public void setImagemBase64(String imagemBase64) {
        this.imagemBase64 = imagemBase64;
    }

    @Override
    public String toString() {
        return "BoletoResponse{" +
                "barcode='" + barcode + '\'' +
                ", valorOriginal=" + valorOriginal +
                ", dataGeracao=" + dataGeracao +
                ", dataVencimento=" + dataVencimento +
                ", imagemBase64='" + imagemBase64 + '\'' +
                '}';
    }
}
