package br.com.alloha.rabbitmq.consumidor;

import br.com.alloha.rabbitmq.config.RabbitMQConfig;
import br.com.alloha.rabbitmq.dto.StatusMensagemDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumidor {

    @RabbitListener(queues = RabbitMQConfig.FILA)
    public void consumirMensagem(@Payload StatusMensagemDTO statusMensagemDTO) {
        System.out.println("Mensagem recebida da fila: " + statusMensagemDTO);
    }
}
