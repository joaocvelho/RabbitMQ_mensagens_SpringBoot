package br.com.alloha.rabbitmq.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Estrutura padrão de resposta de erro")
public class ErroResposta {

    @Schema(description = "Data e hora do erro", example = "2026-06-18T12:00:00")
    private LocalDateTime timestamp;

    @Schema(description = "Código HTTP do erro", example = "503")
    private Integer status;

    @Schema(description = "Descrição do erro", example = "Service Unavailable")
    private String erro;

    @Schema(description = "Mensagem detalhada do erro", example = "Não foi possível enviar a mensagem para a fila RabbitMQ")
    private String mensagem;

    @Schema(description = "Caminho do endpoint", example = "/envio-email")
    private String caminho;

    @Schema(description = "Erros de validação dos campos")
    private List<ErroValidacao> errosValidacao;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Erro de validação de campo específico")
    public static class ErroValidacao {

        @Schema(description = "Nome do campo", example = "email")
        private String campo;

        @Schema(description = "Mensagem de erro do campo", example = "Email não pode estar vazio")
        private String mensagem;
    }
}
