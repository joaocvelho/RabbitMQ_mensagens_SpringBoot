package br.com.alloha.rabbitmq.produtor;

import br.com.alloha.rabbitmq.config.RabbitMQConfig;
import br.com.alloha.rabbitmq.dto.StatusMensagemDTO;
import br.com.alloha.rabbitmq.exception.ConexaoException;
import br.com.alloha.rabbitmq.exception.MensagemNaoEnviadaException;
import br.com.alloha.rabbitmq.model.RabbitMQModel;
import org.springframework.amqp.AmqpConnectException;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQProdutor {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void enviarParaFila(RabbitMQModel rabbitMQModel) {
        try {
            StatusMensagemDTO statusMensagemDTO = new StatusMensagemDTO
                    (rabbitMQModel, "Mensagem enviada com sucesso!", rabbitMQModel.getStatus().name());
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, statusMensagemDTO);
        } catch (AmqpConnectException e) {
            throw new ConexaoException(
                    "Não foi possível conectar ao RabbitMQ. Verifique se o servidor está em execução.", e);
        } catch (AmqpException e) {
            throw new MensagemNaoEnviadaException(
                    "Falha ao enviar mensagem para a fila RabbitMQ: " + e.getMessage(), e);
        }
    }
}
