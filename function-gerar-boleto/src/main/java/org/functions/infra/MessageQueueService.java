package org.functions.infra;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.functions.domain.models.BoletoResponse;

public class MessageQueueService {

    private final String serviceusConnectionString = System.getenv("ServiceBusConnectionString");

    public void enviarMensagemParaFila(BoletoResponse boletoResponse) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

        String messageBody = mapper.writeValueAsString(boletoResponse);

        String queueName = "gerador-codigo-barras";
        ServiceBusSenderClient senderClient = new ServiceBusClientBuilder()
                .connectionString(serviceusConnectionString)
                .sender()
                .queueName(queueName)
                .buildClient();

        senderClient.sendMessage(new ServiceBusMessage(messageBody));

        senderClient.close();
    }
}
