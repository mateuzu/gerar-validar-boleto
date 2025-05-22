package org.functions;

import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;
import org.functions.exceptions.BusinessException;
import org.functions.domain.models.BoletoRequest;
import org.functions.domain.models.BoletoResponse;
import org.functions.service.BoletoService;

/**
 * Azure Functions with HTTP Trigger.
 */
public class Function {

    private final BoletoService service = BoletoService.getInstance();

    @FunctionName("barcode-generate")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.POST}, authLevel = AuthorizationLevel.FUNCTION)
            HttpRequestMessage<Optional<String>> request,
            ExecutionContext context) {

        try {
            String body = request.getBody().orElseThrow(() -> new BusinessException("Requisição vazia!"));

            ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
            context.getLogger().info("JSON recebido: " + body);

            BoletoRequest boletoRequest = mapper.readValue(body, BoletoRequest.class);

            context.getLogger().info("Gerando boleto...");
            BoletoResponse boletoResponse = service.processarBoleto(boletoRequest);

            context.getLogger().info("Boleto gerado: " + boletoResponse.toString());

            return request.createResponseBuilder(HttpStatus.OK)
                    .header("Content-Type", "application/json")
                    .body(mapper.writeValueAsString(boletoResponse))
                    .build();

        } catch (BusinessException e){
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .header("Content-Type", "application/json")
                    .body("Erro de regra de negócio: " + e.getMessage())
                    .build();
        } catch(Exception e){
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Content-Type", "application/json")
                    .body("Erro interno: " + e.getMessage())
                    .build();
        }

    }
}
