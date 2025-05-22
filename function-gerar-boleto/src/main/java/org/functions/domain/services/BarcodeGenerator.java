package org.functions.domain.services;

import org.functions.domain.models.BoletoRequest;

import java.time.format.DateTimeFormatter;

public class BarcodeGenerator {

    public String gerarCodigoBarras(BoletoRequest boletoRequest){
        //Formatando a data
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String dataVencimentoBoleto = boletoRequest.getDataVencimento().format(formatter);

        //Convertendo o valor para centavos e garantindo 8 digitos
        int valorCentavos = (int) (boletoRequest.getValor() * 100);
        String valorBoleto = String.format("%08d", valorCentavos);

        //Codigo banco - fictício
        String codigoBanco = "013";

        //Gerando o codigo base
        String baseCode = codigoBanco + dataVencimentoBoleto + valorBoleto;

        //Garantindo exatamente 44 caracteres no código de barras
        return baseCode.length() < 44 ? String.format("%-44s", baseCode).replace(' ','0')
                : baseCode.substring(0, 44);
    }
}
