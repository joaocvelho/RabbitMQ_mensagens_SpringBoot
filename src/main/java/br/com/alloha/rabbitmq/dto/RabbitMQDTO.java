package br.com.alloha.rabbitmq.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "DTO de entrada para envio de e-mail")
public class RabbitMQDTO {

    // Schema descreve cada campo dos modelos/DTOs que aparecem no Swagger UI
    @Schema(description = "ID gerado automaticamente", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank
    @Email
    @Schema(description = "E-mail do destinatário", example = "usuario@exemplo.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @NotBlank
    @Schema(description = "Nome do destinatário", example = "João Silva", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nome;

    @NotBlank
    @Schema(description = "Conteúdo da mensagem", example = "Olá, este é um e-mail de teste.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String mensagem;

    @Schema(description = "Observação opcional", example = "Envio em lote - campanha promocional")
    private String observacao;
}
