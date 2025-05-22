package org.valida.boleto.model;

public class BarcodeRequest {

    private String barcode;

    public BarcodeRequest() {
    }

    public BarcodeRequest(String barcode) {
        this.barcode = barcode;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}
