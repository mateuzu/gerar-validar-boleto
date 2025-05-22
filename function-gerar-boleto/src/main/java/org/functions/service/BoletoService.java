package org.functions.service;

import org.functions.domain.models.BoletoRequest;
import org.functions.domain.models.BoletoResponse;
import org.functions.domain.services.BarcodeGenerator;
import org.functions.infra.MessageQueueService;
import org.functions.domain.services.BarcodeUtil;

import java.time.LocalDate;

public class BoletoService {

    private static final BoletoService INSTANCE = new BoletoService();

    private final BarcodeGenerator barcodeGenerator;
    private final MessageQueueService queueService;
    private final BarcodeUtil barcodeUtil;

    private BoletoService() {
        this.barcodeGenerator = new BarcodeGenerator(); // ou singleton
        this.queueService = new MessageQueueService();  // ou singleton
        this.barcodeUtil = BarcodeUtil.getInstance();
    }

    public static BoletoService getInstance(){
        return INSTANCE;
    }

    public BoletoResponse processarBoleto(BoletoRequest boletoRequest) throws Exception {
        String codigoBarras = barcodeGenerator.gerarCodigoBarras(boletoRequest);
        String imagemBase64 = barcodeUtil.gerarImagemBase64(codigoBarras);

        BoletoResponse response = new BoletoResponse(
                codigoBarras,
                boletoRequest.getValor(),
                LocalDate.now(),
                boletoRequest.getDataVencimento(),
                imagemBase64);

        queueService.enviarMensagemParaFila(response);

        return response;
    }
}
