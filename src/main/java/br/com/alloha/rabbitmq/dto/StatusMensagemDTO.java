package br.com.alloha.rabbitmq.dto;

import br.com.alloha.rabbitmq.model.RabbitMQModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "DTO de status trafegado na fila RabbitMQ")
public class StatusMensagemDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Dados completos da mensagem persistida")
    private RabbitMQModel rabbitMQModel;

    @Schema(description = "Mensagem descritiva do status", example = "Mensagem enviada com sucesso!")
    private String mensagem;

    @Schema(description = "Status da operação", example = "ENVIADO", allowableValues = {"ENVIADO", "ERRO_ENVIO"})
    private String status;
}
