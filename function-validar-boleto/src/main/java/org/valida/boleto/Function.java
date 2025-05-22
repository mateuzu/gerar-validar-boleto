package org.valida.boleto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.valida.boleto.model.BarcodeRequest;
import org.valida.boleto.model.Result;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Azure Functions with HTTP Trigger.
 */
public class Function {

    ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private static final Logger log = LoggerFactory.getLogger(Function.class);

    @FunctionName("barcode-validate")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.POST}, authLevel = AuthorizationLevel.FUNCTION)
            HttpRequestMessage<Optional<String>> request, ExecutionContext context) {
        try {
            String body = request.getBody().orElse("");
            BarcodeRequest barcodeRequest = mapper.readValue(body, BarcodeRequest.class);
            context.getLogger().info("Barcode recebido" + barcodeRequest.getBarcode());
            context.getLogger().info("Tamanho do barcode: " + barcodeRequest.getBarcode().length());

            if(barcodeRequest.getBarcode().isBlank() || barcodeRequest.getBarcode().isEmpty()){
                return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("O campo barcode é obrigatório!").build();
            }

            if(barcodeRequest.getBarcode().length() != 44){
                return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("O campo barcode deve ter 44 caracteres!").build();
            }

            var datePart = barcodeRequest.getBarcode().substring(3, 11);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate dataVencimento = LocalDate.parse(datePart, formatter);

            var resultOk = new Result(true, "Boleto válido", dataVencimento);
            return request.createResponseBuilder(HttpStatus.OK)
                    .header("Content-Type", "application/json")
                    .body(mapper.writeValueAsString(resultOk))
                    .build();

        } catch (DateTimeParseException e){
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .header("Content-Type", "application/json")
                    .body("Falha ao converter a data: " + e.getMessage())
                    .build();
        } catch (Exception e){
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Content-Type", "application/json")
                    .body("Erro interno: " + e.getMessage())
                    .build();
        }
    }
}
